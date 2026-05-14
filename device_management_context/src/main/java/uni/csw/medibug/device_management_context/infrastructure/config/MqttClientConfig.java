package uni.csw.medibug.device_management_context.infrastructure.config;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class MqttClientConfig {

    @Bean(destroyMethod = "disconnect")
    public IMqttAsyncClient mqttAsyncClient(
            @Value("${mqtt.broker-url:tcp://mqtt:1883}") String brokerUrl,
            @Value("${mqtt.client-id:}") String configuredClientId,
            @Value("${mqtt.connection-timeout:30}") int connectionTimeout,
            @Value("${mqtt.keep-alive-interval:20}") int keepAliveInterval,
            @Value("${mqtt.clean-session:true}") boolean cleanSession
    ) {
        try {
            String clientId = (configuredClientId == null || configuredClientId.isBlank())
                    ? "simulador-" + UUID.randomUUID()
                    : configuredClientId;

            IMqttAsyncClient client = new MqttAsyncClient(brokerUrl, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(cleanSession);
            options.setConnectionTimeout(connectionTimeout);
            options.setKeepAliveInterval(keepAliveInterval);
            options.setMaxInflight(1000);

            client.connect(options).waitForCompletion();
            return client;
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo inicializar el cliente MQTT", ex);
        }
    }
}

