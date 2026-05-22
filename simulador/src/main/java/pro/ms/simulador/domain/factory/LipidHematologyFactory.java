package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.LipidHematologyPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.CountingUnit;
import pro.ms.simulador.domain.payloadGenerator.LipidHematologyGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class LipidHematologyFactory implements PayloadFactory<LipidHematologyPayload> {
    @Override
    public LipidHematologyPayload initialState() {
        throw new IllegalArgumentException("LIPID_HEMATOLOGY requiere userId");
    }

    @Override
    public LipidHematologyPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para LIPID_HEMATOLOGY");
        }

        LipidPanel lipids = new LipidPanel(
                185.0,
                ConcentrationUnit.MG_DL,
                135.0,
                ConcentrationUnit.MG_DL
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

        return new LipidHematologyPayload(new LipidPayload(lipids),
                                          new BloodCountMedicalPayload(bloodCount));
    }

    @Override
    public PayloadGenerator<LipidHematologyPayload> generator() {
        return new LipidHematologyGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.LIPID_HEMATOLOGY;
    }
}
