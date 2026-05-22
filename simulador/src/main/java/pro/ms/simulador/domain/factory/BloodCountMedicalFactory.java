package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.CountingUnit;
import pro.ms.simulador.domain.payloadGenerator.BloodCountGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class BloodCountMedicalFactory implements PayloadFactory<BloodCountMedicalPayload> {
    @Override
    public BloodCountMedicalPayload initialState() {
        throw new IllegalArgumentException("BLOOD_COUNT requiere userId");
    }

    @Override
    public BloodCountMedicalPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para BLOOD_COUNT");
        }

        BloodCountPanel bloodCount = new BloodCountPanel(
                14.0,
                ConcentrationUnit.G_DL,
                7200,
                CountingUnit.CELLS_MCL,
                250000,
                CountingUnit.CELLS_MCL,
                105.0,
                ConcentrationUnit.MG_DL
        );

        return new BloodCountMedicalPayload(bloodCount);
    }

    @Override
    public PayloadGenerator<BloodCountMedicalPayload> generator() {
        return new BloodCountGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.BLOOD_COUNT;
    }
}
