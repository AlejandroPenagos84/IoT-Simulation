package pro.ms.simulador.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.out.PayloadHandler;
import pro.ms.simulador.application.ports.out.TelemetryPublisher;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.infrastructure.configuration.MqttTopicResolver;

@Component
public class MqttTelemetryMetabolicPayload implements TelemetryPublisher<MetabolicPayload> {

    private final PayloadHandler<MetabolicPayload> metabolicPayloadHandler;
    private final ObjectMapper objectMapper;
    private final IMqttAsyncClient  pahoClient;
    private final MqttTopicResolver topicResolver;
    private final int qos;

    public MqttTelemetryMetabolicPayload(
            PayloadHandler<MetabolicPayload> metabolicPayloadHandler,
            ObjectMapper objectMapper,
            IMqttAsyncClient pahoClient,
            MqttTopicResolver topicResolver,
            @Value("${mqtt.qos:1}") int qos
    ) {
        this.metabolicPayloadHandler = metabolicPayloadHandler;
        this.objectMapper = objectMapper;
        this.pahoClient = pahoClient;
        this.topicResolver = topicResolver;
        this.qos = qos;
    }

    @Override
    public DeviceType supportedDeviceType() {
        return DeviceType.METABOLIC;
    }
    @Override
    public void publish(TelemetryMessage<MetabolicPayload> message) {
        try {
            publishMetabolic(message);
        } catch (MqttException e) {
            throw new IllegalStateException("MQTT error publishing medical payload", e);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Serialization error publishing medical payload", e);
        }
    }

    private void publishMetabolic(TelemetryMessage<MetabolicPayload> message)
            throws MqttException, JsonProcessingException {
        publish(message, metabolicPayloadHandler.topicKey());
    }

    private void publish(TelemetryMessage<?> result, String topicKey)
            throws MqttException, JsonProcessingException {
        byte[] data = objectMapper.writeValueAsBytes(result);
        MqttMessage mqtt = new MqttMessage(data);
        mqtt.setQos(qos);
        String topic = topicResolver.resolve(topicKey, result.deviceId());
        pahoClient.publish(topic, mqtt).waitForCompletion(5000);
    }
}