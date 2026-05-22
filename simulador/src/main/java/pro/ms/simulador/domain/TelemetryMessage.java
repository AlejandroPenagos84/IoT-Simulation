package pro.ms.simulador.domain;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import pro.ms.simulador.domain.payload.Payload;

public record TelemetryMessage<T extends Payload> (
        UUID deviceId,
        String userId,
        Instant timestamp,
        DeviceType device,

        @JsonUnwrapped
        T payload) {

        public <R extends Payload> TelemetryMessage<R> withPayload(R newPayload) {
                return new TelemetryMessage<>(
                        this.deviceId,
                        this.userId,
                        this.timestamp,
                        this.device,
                        newPayload
                );
        }
}
