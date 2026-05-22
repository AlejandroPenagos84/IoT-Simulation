package uni.csw.medibug.device_management_context.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import uni.csw.medibug.device_management_context.application.DTO.request.DeviceRequestActivateDTO;
import uni.csw.medibug.device_management_context.application.DTO.request.DeviceRequestDTO;
import uni.csw.medibug.device_management_context.application.DTO.respond.DeviceRespondDTO;
import uni.csw.medibug.device_management_context.application.ports.in.DeviceManagementUseCase;

@RestController
@RequestMapping("api/v1/device_management")
public class DeviceManagamentController {
    private final DeviceManagementUseCase deviceManagementUseCase;

    public DeviceManagamentController(DeviceManagementUseCase deviceManagementUseCase) {
        this.deviceManagementUseCase = deviceManagementUseCase;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<DeviceRespondDTO>> registerDevice(
            @RequestBody DeviceRequestDTO deviceRequestDTO) {
        return deviceManagementUseCase.registerDevice(deviceRequestDTO)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto));
    }

    @PostMapping("/activate")
    public Mono<ResponseEntity<Boolean>> activateDevice(
            @RequestBody DeviceRequestActivateDTO deviceRequestActivateDTO) {
        deviceManagementUseCase.activateDevice(deviceRequestActivateDTO.deviceId());
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(true));
    }

    @PostMapping("/deactivate")
    public Mono<ResponseEntity<Boolean>> deactivateDevice(
            @RequestBody DeviceRequestActivateDTO deviceRequestActivateDTO) {
        deviceManagementUseCase.deactivateDevice(deviceRequestActivateDTO.deviceId());
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(true));
    }
}
