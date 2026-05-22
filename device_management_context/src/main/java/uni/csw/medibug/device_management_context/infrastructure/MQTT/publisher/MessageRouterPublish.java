package uni.csw.medibug.device_management_context.infrastructure.MQTT.publisher;

import org.springframework.stereotype.Component;
import uni.csw.medibug.device_management_context.domain.CommandPayload;
import uni.csw.medibug.device_management_context.domain.Payload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MessageRouterPublish {
    private static final Map<Class<? extends Payload>, String> TOPIC_REGISTRY = Map.of(
            CommandPayload.class, "sensor/{deviceId}/commands"
    );

    public PublishTask route(String deviceId, Payload message) {
        String topicTemplate = TOPIC_REGISTRY.get(message.getClass());

        if (topicTemplate == null) {
                throw new IllegalStateException("No existe un tópico MQTT registrado para: " + message.getClass().getSimpleName());
        }

        String finalTopic = topicTemplate.replace("{deviceId}", deviceId);

        return new PublishTask(finalTopic, message);
    }
}
