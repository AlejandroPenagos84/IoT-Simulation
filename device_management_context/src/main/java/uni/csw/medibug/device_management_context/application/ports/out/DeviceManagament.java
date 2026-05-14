package uni.csw.medibug.device_management_context.application.ports.out;

import reactor.core.publisher.Mono;
import uni.csw.medibug.device_management_context.application.DTO.request.DeviceRequestDTO;
import uni.csw.medibug.device_management_context.application.DTO.respond.DeviceRespondDTO;

public interface DeviceManagament {
    Mono<DeviceRespondDTO> register(DeviceRequestDTO deviceRequestDTO);
}
