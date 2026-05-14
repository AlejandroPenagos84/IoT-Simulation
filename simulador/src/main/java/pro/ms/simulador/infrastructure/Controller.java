package pro.ms.simulador.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.ms.simulador.application.ports.in.SimulationOrchestrator;
import pro.ms.simulador.infrastructure.DTO.request.DeviceRequestDTO;
import pro.ms.simulador.infrastructure.DTO.respond.DeviceRespondDTO;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/device")
public class Controller {
    private final SimulationOrchestrator orchestrator;

    @Autowired
    public Controller(SimulationOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @PostMapping("/activate")
    public CompletableFuture<ResponseEntity<DeviceRespondDTO>> createDevice(
            @RequestBody DeviceRequestDTO deviceRequestDTO) {

        return orchestrator.addDevice(deviceRequestDTO)
                .thenApply(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto));
    }
}