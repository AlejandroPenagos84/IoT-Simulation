package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.ElectroLipidHematologyPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.CountingUnit;
import pro.ms.simulador.domain.medicalPayload.units.ElectrolyteUnit;
import pro.ms.simulador.domain.payloadGenerator.ElectroLipidHematologyGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class ElectroLipidHematologyFactory implements PayloadFactory<ElectroLipidHematologyPayload> {
    @Override
    public ElectroLipidHematologyPayload initialState() {
        throw new IllegalArgumentException("ELECTRO_LIPID_HEMATOLOGY requiere userId");
    }

    @Override
    public ElectroLipidHematologyPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para ELECTRO_LIPID_HEMATOLOGY");
        }

        LipidPanel lipids = new LipidPanel(
                185.0,
                ConcentrationUnit.MG_DL,
                135.0,
                ConcentrationUnit.MG_DL
        );

        ElectrolytePanel electrolytes = new ElectrolytePanel(
                140.0,
                ElectrolyteUnit.MEQ_L,
                4.2,
                ElectrolyteUnit.MEQ_L
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

        return new ElectroLipidHematologyPayload(new LipidPayload(lipids),
                                                 new ElectrolytePayload(electrolytes),
                                                 new BloodCountMedicalPayload(bloodCount));
    }

    @Override
    public PayloadGenerator<ElectroLipidHematologyPayload> generator() {
        return new ElectroLipidHematologyGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.ELECTRO_LIPID_HEMATOLOGY;
    }
}
