package pro.ms.simulador.application.ports.out;

import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.payload.Payload;

public interface TelemetryPublisher<T extends Payload> {
    DeviceType supportedDeviceType();

    void publish(TelemetryMessage<T> telemetryMessage);
}