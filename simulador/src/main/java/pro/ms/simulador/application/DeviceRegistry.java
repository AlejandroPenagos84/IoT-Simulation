package pro.ms.simulador.application;

import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.in.TelemetrySource;
import pro.ms.simulador.domain.payload.Payload;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DeviceRegistry {
    private final Map<String, TelemetrySource<?>> devices = new ConcurrentHashMap<>();

    public <T extends Payload> void register(TelemetrySource<T> device) {
        devices.put(device.getDeviceId(), device);
    } 

    public void unregister(String deviceId) {
        devices.remove(deviceId);
    }

    public Optional<TelemetrySource<?>> findById(String deviceId) {
        return Optional.ofNullable(devices.get(deviceId));
    }

    public Collection<TelemetrySource<?>> all() {
        return devices.values();
    }
}