package pro.ms.simulador.application.ports.out;

import java.util.UUID;

public interface TelemetrySuscriber {
    void suscribeToCommandTopic(UUID deviceId);
}
