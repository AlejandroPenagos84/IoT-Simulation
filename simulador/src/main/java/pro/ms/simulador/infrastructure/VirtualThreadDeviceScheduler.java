package pro.ms.simulador.infrastructure;

import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.in.TelemetrySource;
import pro.ms.simulador.application.ports.out.DeviceSchedulerPort;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;
@Component
public class VirtualThreadDeviceScheduler implements DeviceSchedulerPort {
    private final Map<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void schedule(TelemetrySource<?> device, Runnable task) {
        // Cancelamos si ya existía una tarea previa para este mismo bicho
        cancel(device.getDeviceId());

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
            // Cada vez que se cumple el tiempo, delegamos la ejecución a un hilo virtual fresco
            virtualExecutor.submit(() -> {
                try {
                    task.run();
                } catch (Exception e) {
                    System.err.printf("Error en dispositivo %s: %s%n", device.getDeviceId(), e.getMessage());
                }
            });
        }, 0, device.getDuration().toSeconds(), TimeUnit.SECONDS);

        tasks.put(device.getDeviceId(), scheduledFuture);
    }

    @Override
    public void cancel(String deviceId) {
        Optional.ofNullable(tasks.remove(deviceId))
                .ifPresent(f -> f.cancel(false));

    }

    @Override
    public void cancelAll() {
        // Cancelamos las tareas activas de los bichos, pero dejamos los ejecutores vivos
        // para que el simulador pueda recibir nuevas peticiones HTTP de creación después.
        tasks.keySet().forEach(this::cancel);
        tasks.clear();
    }
}
