package pro.ms.simulador.infrastructure.payloadHandler;

import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.out.PayloadHandler;
import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;

@Component
public class MqttHandlerBloodCount implements PayloadHandler<BloodCountMedicalPayload> {

    @Override
    public String topicKey() {
        return "blood";
    }

    @Override
    public TelemetryMessage<BloodCountMedicalPayload> buildPayload(
            TelemetryMessage<BloodCountMedicalPayload> message
    ) {
        BloodCountPanel panel = message.payload().bloodCount();

        BloodCountMedicalPayload payload =
                new BloodCountMedicalPayload(panel);

        return new TelemetryMessage<>(
                message.uuid(),
                message.deviceId(),
                message.userId(),
                message.timestamp(),
                message.device(),
                payload
        );
    }
}