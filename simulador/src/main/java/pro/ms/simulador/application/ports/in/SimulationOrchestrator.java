package pro.ms.simulador.application.ports.in;

import pro.ms.simulador.domain.CommandPayload;
import pro.ms.simulador.domain.TelemetryMessage;
import pro.ms.simulador.infrastructure.DTO.request.DeviceRequestDTO;
import pro.ms.simulador.infrastructure.DTO.respond.DeviceRespondDTO;
import pro.ms.simulador.infrastructure.MQTT.consumer.TelemetryReceived;

import java.util.UUID;
 import java.util.concurrent.CompletableFuture;

public interface SimulationOrchestrator {
    CompletableFuture<DeviceRespondDTO> addDevice(DeviceRequestDTO deviceRequestDTO);
    void executeCommand(TelemetryReceived<CommandPayload> command);
    void removeDevice(String deviceId);
}
