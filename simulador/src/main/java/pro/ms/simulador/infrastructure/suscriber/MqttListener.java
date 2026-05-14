package pro.ms.simulador.infrastructure.suscriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.in.SimulationOrchestrator;

import java.util.UUID;

@Component
public class MqttListener implements MqttCallback {

    private final SimulationOrchestrator simulationOrchestrator;

    public MqttListener(SimulationOrchestrator simulationOrchestrator1) {
        this.simulationOrchestrator = simulationOrchestrator1;
    }

    @Override
    public void connectionLost(Throwable cause) {
        cause.printStackTrace();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());

        UUID deviceId = extractDeviceId(topic);
        String command = extractCommand(payload);

        simulationOrchestrator.executeCommand(deviceId, command);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // no necesario para subscriber
    }

    private UUID extractDeviceId(String topic) {
        String[] parts = topic.split("/");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Topic inválido: " + topic);
        }

        return UUID.fromString(parts[1]);
    }

    private String extractCommand(String payload) {
        if (payload.contains("START")) return "START";
        if (payload.contains("STOP")) return "STOP";

        return "UNKNOWN";
    }
}