package pro.ms.simulador.domain;

import java.time.Instant;
import java.util.UUID;
import pro.ms.simulador.domain.payload.Payload;

public record TelemetryMessage<T extends Payload>(
        UUID uuid,
        UUID deviceId,
        UUID userId,
        Instant timestamp,
        DeviceType device,
        T payload) {
}
