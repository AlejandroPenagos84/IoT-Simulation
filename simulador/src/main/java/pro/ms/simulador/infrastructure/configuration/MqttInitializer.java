package pro.ms.simulador.infrastructure.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import pro.ms.simulador.infrastructure.suscriber.MqttListener;

@Component
public class MqttInitializer {

    private final IMqttAsyncClient client;
    private final MqttListener listener;

    public MqttInitializer(IMqttAsyncClient client, MqttListener listener) {
        this.client = client;
        this.listener = listener;
    }

    @PostConstruct
    public void init() {
        try {
            client.setCallback(listener);
        } catch (Exception e) {
            throw new IllegalStateException("Error inicializando MQTT", e);
        }
    }
}