package pro.ms.simulador.application.ports.in;

import pro.ms.simulador.infrastructure.DTO.request.DeviceRequestDTO;
import pro.ms.simulador.infrastructure.DTO.respond.DeviceRespondDTO;

import java.util.UUID;
 import java.util.concurrent.CompletableFuture;

public interface SimulationOrchestrator {
    CompletableFuture<DeviceRespondDTO> addDevice(DeviceRequestDTO deviceRequestDTO);
    void executeCommand(UUID deviceId, String command);
    void removeDevice(String deviceId);
}
