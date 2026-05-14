package pro.ms.simulador.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.in.TelemetryDispatcher;
import pro.ms.simulador.application.ports.out.TelemetryPublisher;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.payload.Payload;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class DelegatingTelemetryPublisher implements TelemetryDispatcher{
    private final Map<DeviceType, TelemetryPublisher<?>> publishersByType;

    @Autowired
    public DelegatingTelemetryPublisher(List<TelemetryPublisher<?>> publishers) {
        this.publishersByType = new EnumMap<>(DeviceType.class);
        for (TelemetryPublisher<?> publisher : publishers) {
            DeviceType deviceType = publisher.supportedDeviceType();
            TelemetryPublisher<?> previous = publishersByType.putIfAbsent(deviceType, publisher);
            if (previous != null) {
                throw new IllegalStateException("Mas de un TelemetryPublisher registrado para " + deviceType);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Payload> void publish(TelemetryMessage<T> message) {
        TelemetryPublisher<?> publisher = publishersByType.get(message.device());
        if (publisher == null) {
            throw new IllegalArgumentException("No TelemetryPublisher for device type: " + message.device());
        }
        ((TelemetryPublisher<T>) publisher).publish(message);
    }
}