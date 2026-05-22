package pro.ms.simulador.infrastructure.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.out.TelemetrySuscriber;
import pro.ms.simulador.domain.CommandPayload;
import pro.ms.simulador.infrastructure.DTO.request.DeviceRequestDTO;
import pro.ms.simulador.infrastructure.MQTT.MqttTopic;
import pro.ms.simulador.infrastructure.MQTT.consumer.TelemetryReceived;

import java.util.UUID;

@MqttTopic("sensor/+/commands")
@Component
public class CommandHandler implements TelemetrySuscriber< CommandPayload> {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CommandHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Class<CommandPayload> getClassType() {
        return  CommandPayload.class;
    }

    @Override
    public void handle(String topic, CommandPayload message) {
        System.out.println("[CommandHandler] publish event type=commandListener topic=" + topic + " payload=" + message);
        applicationEventPublisher.publishEvent(new
                TelemetryReceived<>("commandListener",topic, message)
        );
    }
}
