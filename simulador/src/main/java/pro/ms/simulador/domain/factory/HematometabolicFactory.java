package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.HematometabolicPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.CountingUnit;
import pro.ms.simulador.domain.payloadGenerator.HematometabolicGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class HematometabolicFactory implements PayloadFactory<HematometabolicPayload> {
    @Override
    public HematometabolicPayload initialState() {
        throw new IllegalArgumentException("HEMATOMETABOLIC requiere userId");
    }

    @Override
    public HematometabolicPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para HEMATOMETABOLIC");
        }

        MetabolicPanel metabolic = new MetabolicPanel(
                95.0,
                ConcentrationUnit.MG_DL,
                1.0,
                14.0,
                5.2,
                7.40,
                9.4
        );

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

        return new HematometabolicPayload(new MetabolicPayload(metabolic),
                                          new BloodCountMedicalPayload(bloodCount));
    }

    @Override
    public PayloadGenerator<HematometabolicPayload> generator() {
        return new HematometabolicGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.HEMATOMETABOLIC;
    }
}
