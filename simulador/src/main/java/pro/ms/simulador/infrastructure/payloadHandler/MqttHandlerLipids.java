package pro.ms.simulador.infrastructure.payloadHandler;

import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.out.PayloadHandler;
import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;

@Component
public class MqttHandlerLipids implements PayloadHandler<LipidPayload> {
	@Override
	public String topicKey() {
		return "lipid";
	}

	@Override
	public TelemetryMessage<LipidPayload> buildPayload(TelemetryMessage<LipidPayload> message) {
		LipidPayload fragment = new LipidPayload(message.payload().lipids());
		return new TelemetryMessage<>(
				message.uuid(),
				message.deviceId(),
				message.userId(),
				message.timestamp(),
				message.device(),
				fragment
		);
	}

}
