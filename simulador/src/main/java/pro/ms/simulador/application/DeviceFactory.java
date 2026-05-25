package pro.ms.simulador.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.Device;
import pro.ms.simulador.domain.DeviceState;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.factory.PayloadFactory;
import pro.ms.simulador.domain.factory.PayloadFactoryRegistry;
import pro.ms.simulador.domain.payload.Payload;

import java.time.Duration;
import java.util.UUID;

@Component
public class DeviceFactory {
    private final PayloadFactoryRegistry payloadFactoryRegistry;

    @Autowired
    public DeviceFactory(PayloadFactoryRegistry payloadFactoryRegistry) {
        this.payloadFactoryRegistry = payloadFactoryRegistry;
    }

    public <T extends Payload> Device<T> create(String deviceId, DeviceType type, Integer interval, String userId){
        PayloadFactory<T> factory = payloadFactoryRegistry.get(type);

        return Device.<T>builder()
                .deviceId(deviceId)
                .type(type)
                .userId(userId)
                .state(DeviceState.CONNECTED)
                .duration(Duration.ofSeconds(interval))
                .payloadState(factory.initialState(userId))
                .payloadGenerator(factory.generator())
                .build();
    }
}
