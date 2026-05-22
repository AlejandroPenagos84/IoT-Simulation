package uni.csw.medibug.device_management_context.infrastructure.MQTT;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uni.csw.medibug.device_management_context.application.ports.out.MQTTClient;
import uni.csw.medibug.device_management_context.domain.Payload;
import uni.csw.medibug.device_management_context.infrastructure.MQTT.consumer.MessagerRouterSuscriber;
import uni.csw.medibug.device_management_context.infrastructure.MQTT.publisher.MessageRouterPublish;
import uni.csw.medibug.device_management_context.infrastructure.MQTT.publisher.PublishTask;


@Service
@Slf4j
public class MosquittoClient implements MQTTClient {

    private final MessagerRouterSuscriber router;
    private final MessageRouterPublish messageRouterPublish;
    private final ObjectMapper mapper;
    private final String brokerUrl;
    private final String clientId;
    private final int qos;
    private IMqttAsyncClient pahoClient;

    public MosquittoClient(
            MessagerRouterSuscriber router, MessageRouterPublish messageRouterPublish, ObjectMapper mapper,
            @Value("${mqtt.broker-url}") String brokerUrl,
            @Value("${mqtt.client-id}") String clientId,
            @Value("${mqtt.qos:1}") int qos1
    ) {
        this.router = router;
        this.messageRouterPublish = messageRouterPublish;
        this.mapper = mapper;
        this.brokerUrl = brokerUrl;
        this.clientId = clientId;
        this.qos = qos1;
    }

    /**
     * Inicialización controlada después de construir el bean.
     */
    @PostConstruct
    public void init() {
        try {
            connect(brokerUrl, clientId);
        } catch (MqttException e) {
            log.error("Error crítico al inicializar cliente MQTT: {}", e.getMessage(), e);
            throw new RuntimeException("No se pudo crear cliente MQTT", e);
        }
    }

    public void subscribe(String topic) {
        try {
            if (pahoClient != null && pahoClient.isConnected()) {
                pahoClient.subscribe(topic, 1).waitForCompletion();
                System.out.println("Enviando MQTT: " + topic);
                log.info("Suscrito exitosamente al topic: {}", topic);
            } else {
                log.warn("No se pudo suscribir a {} porque MQTT no está conectado.", topic);
            }
        } catch (MqttException e) {
            log.error("Error subscribing to topic {}: {}", topic, e.getMessage(), e);
        }
    }

    public void unsubscribe(String topic) {
        try {
            if (pahoClient != null && pahoClient.isConnected()) {
                pahoClient.unsubscribe(topic).waitForCompletion();
                log.info("Desuscrito exitosamente del topic: {}", topic);
            }
        } catch (MqttException e) {
            log.error("Error unsubscribing from topic {}: {}", topic, e.getMessage(), e);
        }
    }

    /**
     * Configuración y conexión MQTT.
     */
    private void connect(String brokerUrl, String clientId) throws MqttException {

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(60);

        this.pahoClient = new MqttAsyncClient(
                brokerUrl,
                clientId,
                new MemoryPersistence()
        );

        this.pahoClient.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                log.info("MQTT connect complete. reconnect={}, server={}", reconnect, serverURI);
                subscribeAllTopics();
            }

            @Override
            public void connectionLost(Throwable cause) {
                log.error("Conexión MQTT perdida: {}", cause.getMessage(), cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                try {
                    String payload = new String(message.getPayload());
                    System.out.println("Recibo: " + payload + "de" + topic);
                    log.debug("Mensaje recibido en [{}]: {}", topic, payload);

                    router.route(topic, payload);

                } catch (Exception e) {
                    log.error(
                            "Error procesando mensaje del topic {}: {}",
                            topic,
                            e.getMessage(),
                            e
                    );
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // QoS > 0 acknowledgment callback
            }
        });

        log.info("Intentando conectar a Mosquitto en {}...", brokerUrl);

        this.pahoClient.connect(options).waitForCompletion();

        log.info("Cliente MQTT conectado correctamente.");
    }

    /**
     * Suscribe automáticamente todos los topics registrados.
     */
    private void subscribeAllTopics() {
        for (String topic : router.getRegisteredTopics()) {
            subscribe(topic);
        }
    }

    /**
     * Cleanup seguro al apagar aplicación.
     */
    @PreDestroy
    public void cleanup() {
        try {
            if (pahoClient != null) {

                if (pahoClient.isConnected()) {
                    log.info("Desconectando cliente MQTT...");
                    pahoClient.disconnect().waitForCompletion();
                }

                pahoClient.close();
                log.info("Cliente MQTT cerrado correctamente.");
            }

        } catch (MqttException e) {
            log.error("Error cerrando cliente MQTT: {}", e.getMessage(), e);
        }
    }

    @Override
    public void publish(String deviceId, Payload telemetryMessage) {
        PublishTask task = messageRouterPublish.route(deviceId,telemetryMessage);
            try {
                MqttMessage mqtt = new MqttMessage(mapper.writeValueAsBytes(task.messageToSerialize()));
                mqtt.setQos(qos);
                pahoClient.publish(task.topic(), mqtt).waitForCompletion(5000);
            } catch (Exception e) {
                throw new IllegalStateException("Error enviando a: " + task.topic(), e);
            }
        }
    }

