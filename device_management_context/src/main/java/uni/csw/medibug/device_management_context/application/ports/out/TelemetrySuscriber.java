package uni.csw.medibug.device_management_context.application.ports.out;

import java.util.UUID;

public interface TelemetrySuscriber <T>{
    Class<T> getClassType();
    void handle(String topic,T message);
}
