package uni.csw.medibug.device_management_context.application.ports.out;

import uni.csw.medibug.device_management_context.domain.Payload;

public interface MQTTClient {
    void publish(String deviceId, Payload telemetryMessage);
}
