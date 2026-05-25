package pro.ms.simulador.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.in.SimulationOrchestrator;
import pro.ms.simulador.application.ports.in.TelemetrySource;
import pro.ms.simulador.application.ports.out.DeviceSchedulerPort;
import pro.ms.simulador.application.ports.out.MQTTClient;
import pro.ms.simulador.domain.CommandPayload;
import pro.ms.simulador.domain.Device;
import pro.ms.simulador.domain.payload.Payload;
import pro.ms.simulador.infrastructure.DTO.request.DeviceRequestDTO;
import pro.ms.simulador.infrastructure.DTO.respond.DeviceRespondDTO;
import pro.ms.simulador.infrastructure.MQTT.consumer.TelemetryReceived;
import pro.ms.simulador.infrastructure.mapper.DeviceRespondMapper;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class SimulationOrchestrationUseCase implements SimulationOrchestrator{
    private final DeviceSchedulerPort deviceSchedulerPort;
    private final DeviceRegistry deviceRegistry;
    private final DeviceFactory deviceFactory;
    private final DeviceRespondMapper deviceRespondMapper;
    private final MQTTClient mqttClient;

    @Autowired
    public SimulationOrchestrationUseCase(DeviceSchedulerPort deviceSchedulerPort,
                                          DeviceRegistry deviceRegistry,
                                          DeviceFactory deviceFactory,
                                          DeviceRespondMapper deviceRespondMapper,
                                          MQTTClient mqttClient) {
        this.deviceSchedulerPort = deviceSchedulerPort;
        this.deviceRegistry = deviceRegistry;
        this.deviceFactory = deviceFactory;
        this.deviceRespondMapper = deviceRespondMapper;
        this.mqttClient = mqttClient;
    }

    @Async
    @Override
    public CompletableFuture<DeviceRespondDTO> addDevice(DeviceRequestDTO deviceRequestDTO) {
        System.out.println("Executing addDevice with request: " + deviceRequestDTO);
        System.out.println(deviceRequestDTO);

        Device<?> device = createDevice(deviceRequestDTO);
        if (device == null) {
            throw new IllegalArgumentException("device no puede ser nulo");
        }

        deviceRegistry.register(device);
        return CompletableFuture.completedFuture(deviceRespondMapper.toResponse(device));
    }

    @Override
    public void executeCommand(TelemetryReceived<CommandPayload> command) {
        System.out.println("Received command: " + command.messageToDeserialize().action() + " for device: " + extractId(command.topic()));
        switch (command.messageToDeserialize().action()) {
            case "ACTIVATE" -> startTelemetry(extractId(command.topic()));
            case "DEACTIVATE" -> stopTelemetry(extractId(command.topic()));
        }
    }


    @Override
    public void removeDevice(String deviceId) {}

    private void startTelemetry(String deviceId) {
        System.out.println("Starting Telemetry");
        TelemetrySource<?> device = deviceRegistry.findById(deviceId).orElse(null);
        if (device == null) {
            throw new IllegalArgumentException("device no puede ser nulo");
        }
        deviceSchedulerPort.schedule(device, () -> publishTelemetry(device));
    }

    private void stopTelemetry(String deviceId) {
        deviceSchedulerPort.cancel(deviceId);
    }

    private <T extends Payload> void publishTelemetry(TelemetrySource<T> device) {
        device.generateTelemetryMessage()
                .ifPresent(mqttClient::publish);
    }

    private Device<?> createDevice(DeviceRequestDTO deviceRequestDTO) {
        return deviceFactory.create(
                deviceRequestDTO.getDeviceId(),
                deviceRequestDTO.getDeviceType(),
                deviceRequestDTO.getDeviceInterval(),
                deviceRequestDTO.getUserId()
        );
    }

    public String extractId(String topic) {

        String[] parts = topic.split("/");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid topic");
        }

        return parts[1];
    }
}