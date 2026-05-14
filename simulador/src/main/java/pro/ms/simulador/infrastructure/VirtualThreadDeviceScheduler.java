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
    private final Map<UUID, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(
            0, Thread.ofVirtual().factory()  // hilos virtuales aquí
    );

    @Override
    public void schedule(TelemetrySource<?> device, Runnable task) {
        ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(
                () ->{
                    try{task.run();}
                    catch (Exception e){
                        System.out.println("Error ejecutando tarea para dispositivo " + device.getDeviceId() + ": " + e.getMessage());
                    }
                },0,
                device.getDuration().toSeconds(),
                TimeUnit.SECONDS);
        tasks.put(device.getDeviceId(), scheduledFuture);
    }

    @Override
    public void cancel(UUID deviceId) {
        Optional.ofNullable(tasks.remove(deviceId))
                .ifPresent(f -> f.cancel(false));

    }

    @Override
    public void cancelAll() {
        tasks.keySet().forEach(this::cancel);
        executor.shutdown();
    }
}
