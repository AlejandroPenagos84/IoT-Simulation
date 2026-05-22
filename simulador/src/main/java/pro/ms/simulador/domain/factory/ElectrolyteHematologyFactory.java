package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.ElectrolyteHematologyPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.CountingUnit;
import pro.ms.simulador.domain.medicalPayload.units.ElectrolyteUnit;
import pro.ms.simulador.domain.payloadGenerator.ElectrolyteHematologyGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class ElectrolyteHematologyFactory implements PayloadFactory<ElectrolyteHematologyPayload> {
    @Override
    public ElectrolyteHematologyPayload initialState() {
        throw new IllegalArgumentException("ELECTROLYTE_HEMATOLOGY requiere userId");
    }

    @Override
    public ElectrolyteHematologyPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para ELECTROLYTE_HEMATOLOGY");
        }

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

        return new ElectrolyteHematologyPayload(new ElectrolytePayload(electrolytes),
                                                new BloodCountMedicalPayload(bloodCount));
    }

    @Override
    public PayloadGenerator<ElectrolyteHematologyPayload> generator() {
        return new ElectrolyteHematologyGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.ELECTROLYTE_HEMATOLOGY;
    }
}
