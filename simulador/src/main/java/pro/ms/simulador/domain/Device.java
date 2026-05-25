package pro.ms.simulador.domain;

import lombok.Builder;
import lombok.Data;
import pro.ms.simulador.application.ports.in.TelemetrySource;
import pro.ms.simulador.domain.payload.Payload;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Builder
@Data
public class Device<T extends Payload> implements TelemetrySource<T> {
    private String deviceId;
    private String userId;
    private DeviceState state;
    private DeviceType type;
    private Duration duration;
    private T payloadState;
    private PayloadGenerator<T> payloadGenerator;

    @Override
    public Optional<TelemetryMessage<T>> generateTelemetryMessage() {
        T payload = payloadGenerator.generate(this.payloadState);
        TelemetryMessage<T> message = new TelemetryMessage<>(
                this.deviceId,
                this.userId,
                Instant.now(),
                this.type,
                payload);

        return Optional.of(message);
    }
}
