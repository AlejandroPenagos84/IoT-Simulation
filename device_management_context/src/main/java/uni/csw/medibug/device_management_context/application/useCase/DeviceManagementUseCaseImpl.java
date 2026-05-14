package uni.csw.medibug.device_management_context.application.useCase;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import uni.csw.medibug.device_management_context.application.DTO.request.DeviceRequestDTO;
import uni.csw.medibug.device_management_context.application.DTO.respond.DeviceRespondDTO;
import uni.csw.medibug.device_management_context.application.ports.in.DeviceManagementUseCase;
import uni.csw.medibug.device_management_context.application.ports.out.DeviceManagament;
import uni.csw.medibug.device_management_context.application.ports.out.TelemetryPublisher;

import java.util.UUID;

@Component
public class DeviceManagementUseCaseImpl implements DeviceManagementUseCase {

    private final DeviceManagament deviceManagament;
    private final TelemetryPublisher telemetryPublisher;

    public DeviceManagementUseCaseImpl(DeviceManagament deviceManagament, TelemetryPublisher telemetryPublisher) {
        this.deviceManagament = deviceManagament;
        this.telemetryPublisher = telemetryPublisher;
    }

    @Override
    public Mono<DeviceRespondDTO> registerDevice(DeviceRequestDTO deviceRequestDTO) {
        Mono<DeviceRespondDTO> deviceRespondDTOMono = deviceManagament.register(deviceRequestDTO);
        if (deviceRespondDTOMono != null){
            telemetryPublisher.publish("REGISTER", UUID.fromString(deviceRequestDTO.deviceId()));
            return deviceRespondDTOMono;
        }
        return Mono.error(new IllegalArgumentException("Error al registrar el dispositivo"));
    }

    @Override
    public void activateDevice(String deviceId) {
        telemetryPublisher.publish("START", UUID.fromString(deviceId));
    }
}
