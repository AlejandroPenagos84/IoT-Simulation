package pro.ms.simulador.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.Device;
import pro.ms.simulador.domain.payload.Payload;
import pro.ms.simulador.domain.medicalPayload.MedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.infrastructure.DTO.respond.DeviceRespondDTO;

@Component
public class DeviceRespondMapper {

    public DeviceRespondDTO toResponse(Device<?> device) {
        if (device == null) {
            throw new IllegalArgumentException("device no puede ser nulo");
        }

        return new DeviceRespondDTO(
                device.getDeviceId() != null ? device.getDeviceId().toString() : null,
                device.getUserId() != null ? device.getUserId().toString() : null,
                device.getType() != null ? device.getType().name() : null,
                device.getDuration() != null ? Math.toIntExact(device.getDuration().getSeconds()) : null,
                device.getState() != null ? device.getState().name() : null
        );
    }
}

