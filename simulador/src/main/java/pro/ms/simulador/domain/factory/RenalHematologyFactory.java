package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.RenalHematologyPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.CountingUnit;
import pro.ms.simulador.domain.medicalPayload.units.ElectrolyteUnit;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;
import pro.ms.simulador.domain.payloadGenerator.RenalHematologyGenerator;

@Component
public class RenalHematologyFactory implements PayloadFactory<RenalHematologyPayload> {
    @Override
    public RenalHematologyPayload initialState() {
        throw new IllegalArgumentException("RENAL_HEMATOLOGY requiere userId");
    }

    @Override
    public RenalHematologyPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para RENAL_HEMATOLOGY");
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

        return new RenalHematologyPayload(new MetabolicPayload(metabolic),
                                          new ElectrolytePayload(electrolytes),
                                          new BloodCountMedicalPayload(bloodCount));
    }

    @Override
    public PayloadGenerator<RenalHematologyPayload> generator() {
        return new RenalHematologyGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.RENAL_HEMATOLOGY;
    }
}
