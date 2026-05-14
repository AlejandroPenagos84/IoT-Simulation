package uni.csw.medibug.device_management_context.application.ports.out;

import java.util.UUID;

public interface TelemetryPublisher {
    void publish(String telemetryMessage, UUID deviceId);
}