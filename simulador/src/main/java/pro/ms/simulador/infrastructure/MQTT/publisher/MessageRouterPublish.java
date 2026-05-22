package pro.ms.simulador.infrastructure.MQTT.publisher;

import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.out.PayloadHandler;
import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payload.Payload;
import pro.ms.simulador.infrastructure.MQTT.MqttTopic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageRouterPublish {
    private static final Map<Class<? extends Payload>, String> TOPIC_REGISTRY = Map.of(
            MetabolicPayload.class, "sensor/{deviceId}/metabolic",
            BloodCountMedicalPayload.class, "sensor/{deviceId}/blood",
            ElectrolytePayload.class, "sensor/{deviceId}/electrolyte",
            LipidPayload.class, "sensor/{deviceId}/lipid"
    );

    public List<PublishTask> route(TelemetryMessage<? extends Payload> message) {
        System.out.println(message);
        List<PublishTask> tasks = new ArrayList<>();

        List<Payload> leaves = message.payload().flatten();

        System.out.println("leaves size: " + leaves.size());
        for (Payload leaf : leaves) {
            String topicTemplate = TOPIC_REGISTRY.get(leaf.getClass());

            if (topicTemplate == null) {
                throw new IllegalStateException("No existe un tópico MQTT registrado para: " + leaf.getClass().getSimpleName());
            }

            String finalTopic = topicTemplate.replace("{deviceId}", message.deviceId().toString());

            TelemetryMessage<?> leafMessage = message.withPayload(leaf);

            tasks.add(new PublishTask(finalTopic, leafMessage));
        }
        System.out.println("MessageRouterPublish: " + tasks.size() + " tareas de publicación generadas para el mensaje del dispositivo " + message.deviceId());
        return tasks;
    }
}
