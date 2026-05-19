package pro.ms.simulador.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pro.ms.simulador.application.ports.out.PayloadHandler;
import pro.ms.simulador.application.ports.out.TelemetryPublisher;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.medicalPayload.MedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payload.Payload;
import pro.ms.simulador.infrastructure.configuration.MqttTopicResolver;

@Component
public class MqttTelemetryMedicalPayload
        implements TelemetryPublisher<MedicalPayload> {

    private final PayloadHandler<MetabolicPayload> metabolicPayloadHandler;
    private final PayloadHandler<ElectrolytePayload> electrolytePayloadHandler;
    private final PayloadHandler<BloodCountMedicalPayload> bloodCountMedicalPayloadHandler;
    private final PayloadHandler<LipidPayload> lipidPayloadHandler;

    private final ObjectMapper objectMapper;
    private final IMqttAsyncClient  pahoClient;
    private final MqttTopicResolver topicResolver;
    private final int qos;

    public MqttTelemetryMedicalPayload(
            PayloadHandler<MetabolicPayload> metabolicPayloadHandler, List<PayloadHandler<? extends Payload>> handlers, PayloadHandler<ElectrolytePayload> electrolytePayloadHandler, PayloadHandler<BloodCountMedicalPayload> bloodCountMedicalPayloadHandler, PayloadHandler<LipidPayload> lipidPayloadHandler,
            ObjectMapper objectMapper,
            IMqttAsyncClient pahoClient,
            MqttTopicResolver topicResolver,
            @Value("${mqtt.qos:1}") int qos
    ) {
        this.metabolicPayloadHandler = metabolicPayloadHandler;
        this.electrolytePayloadHandler = electrolytePayloadHandler;
        this.bloodCountMedicalPayloadHandler = bloodCountMedicalPayloadHandler;
        this.lipidPayloadHandler = lipidPayloadHandler;
        this.objectMapper = objectMapper;
        this.pahoClient = pahoClient;
        this.topicResolver = topicResolver;
        this.qos = qos;
    }

    @Override
    public DeviceType supportedDeviceType() {
        return DeviceType.MEDICAL;
    }

    @Override
    public void publish(TelemetryMessage<MedicalPayload> message) {
        try {
            publishMetabolic(message);
            publishElectrolyte(message);
            publishBloodCount(message);
            publishLipid(message);
        } catch (MqttException e) {
            throw new IllegalStateException("MQTT error publishing medical payload", e);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Serialization error publishing medical payload", e);
        }
    }

    private void publishMetabolic(TelemetryMessage<MedicalPayload> message)
            throws MqttException, JsonProcessingException {
        MetabolicPayload metabolicPayload = new MetabolicPayload(message.payload().metabolic());
        TelemetryMessage<MetabolicPayload> newMessage = new TelemetryMessage<>(
                message.deviceId(),
                message.userId(),
                message.timestamp(),
                message.device(),
                metabolicPayload
        );

        TelemetryMessage<MetabolicPayload> result = metabolicPayloadHandler.buildPayload(newMessage);
        publish(result, metabolicPayloadHandler.topicKey());
    }

    private void publishElectrolyte(TelemetryMessage<MedicalPayload> message)
            throws MqttException, JsonProcessingException {
        ElectrolytePayload electrolytePayload = new ElectrolytePayload(message.payload().electrolytes());
        TelemetryMessage<ElectrolytePayload> newMessage = new TelemetryMessage<>(
                message.deviceId(),
                message.userId(),
                message.timestamp(),
                message.device(),
                electrolytePayload
        );
        TelemetryMessage<ElectrolytePayload> result = electrolytePayloadHandler.buildPayload(newMessage);
        publish(result, electrolytePayloadHandler.topicKey());
    }

    private void publishBloodCount(TelemetryMessage<MedicalPayload> message)
            throws MqttException, JsonProcessingException {
        BloodCountMedicalPayload bloodCountMedicalPayload = new BloodCountMedicalPayload(message.payload().bloodCount());
        TelemetryMessage<BloodCountMedicalPayload> newMessage = new TelemetryMessage<>(
                message.deviceId(),
                message.userId(),
                message.timestamp(),
                message.device(),
                bloodCountMedicalPayload
        );

        TelemetryMessage<BloodCountMedicalPayload> result = bloodCountMedicalPayloadHandler.buildPayload(newMessage);
        publish(result, bloodCountMedicalPayloadHandler.topicKey());
    }

    private void publishLipid(TelemetryMessage<MedicalPayload> message)
            throws MqttException, JsonProcessingException {
        LipidPayload lipidPayload = new LipidPayload(message.payload().lipids());
        TelemetryMessage<LipidPayload> newMessage = new TelemetryMessage<>(
                message.deviceId(),
                message.userId(),
                message.timestamp(),
                message.device(),
                lipidPayload
        );

        TelemetryMessage<LipidPayload> result = lipidPayloadHandler.buildPayload(newMessage);
        publish(result, lipidPayloadHandler.topicKey());
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