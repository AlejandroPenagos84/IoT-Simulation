package uni.csw.medibug.device_management_context.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import uni.csw.medibug.device_management_context.application.ports.out.StatusPayloadHandler;
import uni.csw.medibug.device_management_context.application.ports.out.TelemetryPublisher;
import uni.csw.medibug.device_management_context.infrastructure.payloadHandlers.MqttTopic;

import java.util.UUID;

@Component
public class MqttTelemetryStatusPayload implements TelemetryPublisher {
    private final IMqttAsyncClient pahoClient;
    private final StatusPayloadHandler handler;
    private final ObjectMapper objectMapper;
    private final int qos;

    public MqttTelemetryStatusPayload(
            IMqttAsyncClient pahoClient, StatusPayloadHandler handler, ObjectMapper objectMapper1,
            @Value("${mqtt.qos:1}") int qos
    ) {
        this.pahoClient = pahoClient;
        this.handler = handler;
        this.objectMapper = objectMapper1;
        this.qos = qos;
    }

    @Override
    public void publish(String telemetryMessage, UUID deviceId) {
        try {
            String handlerStatus = handler.buildPayload(telemetryMessage);

            Class<?> handlerClass = AopUtils.getTargetClass(handler);
            MqttTopic annotation = AnnotationUtils.findAnnotation(handlerClass, MqttTopic.class);

            if (annotation == null) {
                throw new IllegalStateException("Handler sin @MqttTopic");
            }

            String topic = annotation.value()
                    .replace("{deviceId}", deviceId.toString());

            byte[] payloadBytes = objectMapper.writeValueAsBytes(handlerStatus);

            MqttMessage mqttMessage = new MqttMessage(payloadBytes);
            mqttMessage.setQos(qos);

            IMqttToken token = pahoClient.publish(topic, mqttMessage);
            token.waitForCompletion(5000);

        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo publicar telemetry payload por MQTT", ex);
        }
    }
}
