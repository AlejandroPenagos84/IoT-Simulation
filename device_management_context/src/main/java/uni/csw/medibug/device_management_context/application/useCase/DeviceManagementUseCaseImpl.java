package uni.csw.medibug.device_management_context.application.useCase;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import uni.csw.medibug.device_management_context.application.DTO.request.DeviceRequestDTO;
import uni.csw.medibug.device_management_context.application.DTO.respond.DeviceRespondDTO;
import uni.csw.medibug.device_management_context.application.ports.in.DeviceManagementUseCase;
import uni.csw.medibug.device_management_context.application.ports.out.DeviceManagament;
import uni.csw.medibug.device_management_context.application.ports.out.MQTTClient;
import uni.csw.medibug.device_management_context.domain.CommandPayload;

import java.time.Instant;
import java.util.UUID;

@Component
public class DeviceManagementUseCaseImpl implements DeviceManagementUseCase {

    private final DeviceManagament deviceManagament;
    private final MQTTClient mqttClient;

    public DeviceManagementUseCaseImpl(DeviceManagament deviceManagament, MQTTClient mqttClient) {
        this.deviceManagament = deviceManagament;
        this.mqttClient = mqttClient;
    }

    @Override
    public Mono<DeviceRespondDTO> registerDevice(DeviceRequestDTO deviceRequestDTO) {
        DeviceRequestDTO normalizedRequest = new DeviceRequestDTO(
                deviceRequestDTO.deviceId(),
                deviceRequestDTO.userId(),
                deviceRequestDTO.deviceType(),
                deviceRequestDTO.interval()
        );
        Mono<DeviceRespondDTO> deviceRespondDTOMono = deviceManagament.register(normalizedRequest);
        CommandPayload commandPayload =
                new CommandPayload(UUID.randomUUID(), "REGISTER",
                        Instant.now(), null);

        if (deviceRespondDTOMono != null){
            mqttClient.publish(deviceRequestDTO.deviceId(), commandPayload);
            return deviceRespondDTOMono;
        }
        return Mono.error(new IllegalArgumentException("Error al registrar el dispositivo"));
    }

    @Override
    public void activateDevice(String deviceId) {
        CommandPayload commandPayload =
                new CommandPayload(UUID.randomUUID(), "ACTIVATE",
                        Instant.now(), null);
        mqttClient.publish(deviceId, commandPayload);
    }

    @Override
    public void deactivateDevice(String deviceId) {
        CommandPayload commandPayload =
                new CommandPayload(UUID.randomUUID(), "DEACTIVATE",
                        Instant.now(), null);
        mqttClient.publish(deviceId, commandPayload);
    }
}
