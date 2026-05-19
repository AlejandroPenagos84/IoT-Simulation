package pro.ms.simulador.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pro.ms.simulador.application.ports.in.SimulationOrchestrator;
import pro.ms.simulador.application.ports.in.TelemetryDispatcher;
import pro.ms.simulador.application.ports.in.TelemetrySource;
import pro.ms.simulador.application.ports.out.DeviceSchedulerPort;
import pro.ms.simulador.application.ports.out.TelemetrySuscriber;
import pro.ms.simulador.domain.Device;
import pro.ms.simulador.domain.payload.Payload;
import pro.ms.simulador.infrastructure.DTO.request.DeviceRequestDTO;
import pro.ms.simulador.infrastructure.DTO.respond.DeviceRespondDTO;
import pro.ms.simulador.infrastructure.mapper.DeviceRespondMapper;
import pro.ms.simulador.infrastructure.suscriber.MqttSuscriberCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class SimulationOrchestrationUseCase implements SimulationOrchestrator{
    private final TelemetryDispatcher telemetryPublisher;
    private final DeviceSchedulerPort deviceSchedulerPort;
    private final DeviceRegistry deviceRegistry;
    private final DeviceFactory deviceFactory;
    private final DeviceRespondMapper deviceRespondMapper;
    private final TelemetrySuscriber mqttSuscriberCommand;

    @Autowired
    public SimulationOrchestrationUseCase(DelegatingTelemetryPublisher telemetryPublisher,
                                          DeviceSchedulerPort deviceSchedulerPort,
                                          DeviceRegistry deviceRegistry,
                                          DeviceFactory deviceFactory,
                                          DeviceRespondMapper deviceRespondMapper, MqttSuscriberCommand mqttSuscriberCommand) {
        this.telemetryPublisher = telemetryPublisher;
        this.deviceSchedulerPort = deviceSchedulerPort;
        this.deviceRegistry = deviceRegistry;
        this.deviceFactory = deviceFactory;
        this.deviceRespondMapper = deviceRespondMapper;
        this.mqttSuscriberCommand = mqttSuscriberCommand;
    }

    @Async
    @Override
    public CompletableFuture<DeviceRespondDTO> addDevice(DeviceRequestDTO deviceRequestDTO) {
        System.out.println(deviceRequestDTO);
        Device<?> device = createDevice(deviceRequestDTO);
        System.out.println(device);
        if (device == null) {
            throw new IllegalArgumentException("device no puede ser nulo");
        }

        if (device.getDeviceId() == null) {
            device.setDeviceId(UUID.randomUUID());
        }

        deviceRegistry.register(device);
        mqttSuscriberCommand.suscribeToCommandTopic(device.getDeviceId());
        return CompletableFuture.completedFuture(deviceRespondMapper.toResponse(device));
    }

    @Override
    public void executeCommand(UUID deviceId, String command) {
        if (command.equals("START")) {
            startTelemetry(deviceId);
        }
    }

    @Override
    public void removeDevice(String deviceId) {

    }

    private void startTelemetry(UUID deviceId) {
        TelemetrySource<?> device = deviceRegistry.findById(deviceId).orElse(null);
        if (device == null) {
            throw new IllegalArgumentException("device no puede ser nulo");
        }
        deviceSchedulerPort.schedule(device, () -> publishTelemetry(device));
    }

    private void stopTelemetry(UUID deviceId) {
        deviceSchedulerPort.cancel(deviceId);
    }

    private <T extends Payload> void publishTelemetry(TelemetrySource<T> device) {
        device.generateTelemetryMessage()
                .ifPresent(telemetryPublisher::publish);
    }

    private Device<?> createDevice(DeviceRequestDTO deviceRequestDTO) {
        return deviceFactory.create(
                deviceRequestDTO.getDeviceId(),
                deviceRequestDTO.getDeviceType(),
                deviceRequestDTO.getDeviceInterval(),
                deviceRequestDTO.getUserId()
        );
    }
}