package pro.ms.simulador.infrastructure.payloadHandler;

import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.out.PayloadHandler;
import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;

@Component
public class MqttHandlerMetabolism implements PayloadHandler<MetabolicPayload> {
	@Override
	public String topicKey() {
		return "metabolic";
	}

	@Override
	public TelemetryMessage<MetabolicPayload> buildPayload(TelemetryMessage<MetabolicPayload> message) {
		MetabolicPayload fragment = new MetabolicPayload(message.payload().metabolic());
		return new TelemetryMessage<>(
				message.deviceId(),
				message.userId(),
				message.timestamp(),
				message.device(),
				fragment
		);
	}
}
