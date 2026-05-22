package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.RenalPayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.ElectrolyteUnit;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;
import pro.ms.simulador.domain.payloadGenerator.RenalGenerator;

@Component
public class RenalFactory implements PayloadFactory<RenalPayload> {
    @Override
    public RenalPayload initialState() {
        throw new IllegalArgumentException("RENAL requiere userId");
    }

    @Override
    public RenalPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para RENAL");
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

        return new RenalPayload(new MetabolicPayload(metabolic),
                                new ElectrolytePayload(electrolytes));
    }

    @Override
    public PayloadGenerator<RenalPayload> generator() {
        return new RenalGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.RENAL;
    }
}
