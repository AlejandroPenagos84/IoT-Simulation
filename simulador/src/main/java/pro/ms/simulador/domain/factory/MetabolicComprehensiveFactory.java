package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.MetabolicComprehensivePayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.ElectrolyteUnit;
import pro.ms.simulador.domain.payloadGenerator.MetabolicComprehensiveGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class MetabolicComprehensiveFactory implements PayloadFactory<MetabolicComprehensivePayload> {
    @Override
    public MetabolicComprehensivePayload initialState() {
        throw new IllegalArgumentException("METABOLIC_COMPREHENSIVE requiere userId");
    }

    @Override
    public MetabolicComprehensivePayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para METABOLIC_COMPREHENSIVE");
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

        return new MetabolicComprehensivePayload(new MetabolicPayload(metabolic),
                                                 new LipidPayload(lipids),
                                                 new ElectrolytePayload(electrolytes));
    }

    @Override
    public PayloadGenerator<MetabolicComprehensivePayload> generator() {
        return new MetabolicComprehensiveGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.METABOLIC_COMPREHENSIVE;
    }
}
