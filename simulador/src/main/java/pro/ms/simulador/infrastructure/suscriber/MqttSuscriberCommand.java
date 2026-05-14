package pro.ms.simulador.infrastructure.suscriber;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.out.TelemetrySuscriber;

import java.util.UUID;

@Component
public class MqttSuscriberCommand implements TelemetrySuscriber{
    private final IMqttAsyncClient pahoClient;
    private final String commandTopic;
    private final int qos;

    @Autowired
    public MqttSuscriberCommand(
            IMqttAsyncClient pahoClient,
            @Value("${mqtt.topic_command}") String commandTopic,
            @Value("${mqtt.qos:1}") int qos
    ) {
        this.pahoClient = pahoClient;
        this.commandTopic = commandTopic;
        this.qos = qos;
    }

    @Override
    public void suscribeToCommandTopic(UUID deviceId) {
        String topic = String.format(commandTopic, deviceId);

        try {
            pahoClient.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
