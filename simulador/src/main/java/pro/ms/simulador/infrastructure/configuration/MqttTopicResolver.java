package pro.ms.simulador.infrastructure.configuration;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MqttTopicResolver {

    private final Environment environment;
    private final String defaultTopic;

    @Autowired
    public MqttTopicResolver(
            Environment environment,
            @Value("${mqtt.topic.default:sensor/{deviceId}/telemetry}") String defaultTopic
    ) {
        this.environment = environment;
        this.defaultTopic = defaultTopic;
    }

    public String resolve(String topicKey, UUID deviceId) {
        if (topicKey == null || topicKey.isBlank()) {
            throw new IllegalArgumentException("topicKey no puede ser nulo o vacio para resolver topic MQTT");
        }
        if (deviceId == null) {
            throw new IllegalArgumentException("deviceId no puede ser nulo para resolver topic MQTT");
        }

        String byFragmentKey = environment.getProperty("mqtt.topic.fragments." + topicKey);
        if (byFragmentKey != null && !byFragmentKey.isBlank()) {
            return byFragmentKey.replace("{deviceId}", deviceId.toString());
        }
        if (defaultTopic != null && !defaultTopic.isBlank()) {
            return defaultTopic.replace("{deviceId}", deviceId.toString());
        }
        throw new IllegalArgumentException("No existe topic MQTT para topicKey: " + topicKey);
    }
}

