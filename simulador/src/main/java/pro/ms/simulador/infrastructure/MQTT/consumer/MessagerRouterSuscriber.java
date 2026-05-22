package pro.ms.simulador.infrastructure.MQTT.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.out.TelemetrySuscriber;
import pro.ms.simulador.infrastructure.MQTT.MqttTopic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessagerRouterSuscriber {
    private final ObjectMapper objectMapper;
    //Inyeccion Handlers
    private Map<String, TelemetrySuscriber<?>> registry = new HashMap<>();

    public MessagerRouterSuscriber(ObjectMapper objectMapper1, List<TelemetrySuscriber<?>> suscriberHandlers) {
        this.objectMapper = objectMapper1;
        for(TelemetrySuscriber<?> handler : suscriberHandlers) {
            // Obtengo la clase del handler
            Class<?> targetClass = AopUtils.getTargetClass(handler);

            // La anotacion es con lo que obtener el topic
            MqttTopic topicAnnotation = targetClass.getAnnotation(MqttTopic.class);

            if(topicAnnotation != null) {
                // Agrego el elemento
                registry.put(topicAnnotation.value(), handler);
            }

        }
    }

    public List<String> getRegisteredTopics(){
        return registry.keySet().stream().toList();
    }

    public void route(String topic, String message){
            TelemetrySuscriber<?> suscriber = registry.entrySet().stream()
                    .filter(entry -> matches(entry.getKey(),topic))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(null);

            if (suscriber == null) {
                throw new RuntimeException("No such TelemetrySuscriber: " + topic);
            }
            try{
                Class<?> targetClass = suscriber.getClassType();
                Object payload = objectMapper.readValue(message, targetClass);
                invokeHandler(topic,suscriber, payload);
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    private boolean matches(String pattern, String topic) {
        String[] patternParts = pattern.split("/");
        String[] topicParts = topic.split("/");

        if (patternParts.length != topicParts.length) return false;

        for (int i = 0; i < patternParts.length; i++) {
            if (!patternParts[i].equals("+") &&
                    !patternParts[i].equals(topicParts[i])) {
                return false;
            }
        }

        return true;
    }
    // uncheked porque ya verificamos anteriormente que era.
    @SuppressWarnings("unchecked")
    private <T> void invokeHandler(String topic,TelemetrySuscriber<T> handler, Object payload) {
        T typePayload = (T) payload;
        handler.handle(topic,typePayload);
    }

}
