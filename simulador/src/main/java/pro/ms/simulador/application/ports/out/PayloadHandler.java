package pro.ms.simulador.application.ports.out;

import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.payload.Payload;

public interface PayloadHandler<P extends Payload> {

    String topicKey();

    TelemetryMessage<P> buildPayload(TelemetryMessage<P> message);

}
