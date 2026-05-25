package pro.ms.simulador.application.ports.out;

import pro.ms.simulador.application.ports.in.TelemetrySource;

import java.util.UUID;

public interface DeviceSchedulerPort {
    void schedule(TelemetrySource<?> device, Runnable task);
    void cancel(String deviceId);
    void cancelAll();
}
