package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.payload.Payload;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PayloadFactoryRegistry {
    private final Map<DeviceType, PayloadFactory<?>> factories;

    public PayloadFactoryRegistry(List<PayloadFactory<?>> factories) {
        this.factories = factories.stream()
                .collect(Collectors.toMap(PayloadFactory::deviceType, f -> f));
    }

    @SuppressWarnings("unchecked")
    public <T extends Payload> PayloadFactory<T> get(DeviceType type) {
        return (PayloadFactory<T>) Optional.ofNullable(factories.get(type))
                .orElseThrow(() -> new IllegalArgumentException("No factory for: " + type));
    }
}
