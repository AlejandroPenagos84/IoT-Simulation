package pro.ms.simulador.application.ports.out;

import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.payload.Payload;

public interface MQTTClient {
    void publish(TelemetryMessage<? extends Payload> telemetryMessage);
}
