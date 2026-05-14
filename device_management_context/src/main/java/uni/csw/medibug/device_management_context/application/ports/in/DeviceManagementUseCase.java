package uni.csw.medibug.device_management_context.application.ports.in;

import reactor.core.publisher.Mono;
import uni.csw.medibug.device_management_context.application.DTO.request.DeviceRequestDTO;
import uni.csw.medibug.device_management_context.application.DTO.respond.DeviceRespondDTO;

public interface DeviceManagementUseCase {
    Mono<DeviceRespondDTO> registerDevice(DeviceRequestDTO deviceRequestDTO);
    void activateDevice(String deviceId);
}
