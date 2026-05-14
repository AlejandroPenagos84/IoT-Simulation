package pro.ms.simulador.application.ports.in;

import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.payload.Payload;

public interface TelemetryDispatcher {
    <T extends Payload> void publish(TelemetryMessage<T> message);
}
