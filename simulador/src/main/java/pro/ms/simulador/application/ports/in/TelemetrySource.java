package pro.ms.simulador.application.ports.in;

import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.payload.Payload;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

public interface TelemetrySource <T extends Payload>{
    UUID getDeviceId();
    Duration getDuration();
    Optional<TelemetryMessage<T>> generateTelemetryMessage();
}
