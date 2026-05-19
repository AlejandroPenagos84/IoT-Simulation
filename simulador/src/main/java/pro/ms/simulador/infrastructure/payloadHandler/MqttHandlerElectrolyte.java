package pro.ms.simulador.infrastructure.payloadHandler;

import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.out.PayloadHandler;
import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;

@Component
public class MqttHandlerElectrolyte implements PayloadHandler<ElectrolytePayload> {
    @Override
    public String topicKey() {
        return "electrolyte";
    }

    @Override
    public TelemetryMessage<ElectrolytePayload> buildPayload(TelemetryMessage<ElectrolytePayload> message) {
        ElectrolytePayload fragment = new ElectrolytePayload(message.payload().electrolytes());
        return new TelemetryMessage<>(
                message.deviceId(),
                message.userId(),
                message.timestamp(),
                message.device(),
                fragment
        );
    }
}
