package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.LipidElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.ElectrolyteUnit;
import pro.ms.simulador.domain.payloadGenerator.LipidElectrolyteGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class LipidElectrolyteFactory implements PayloadFactory<LipidElectrolytePayload> {
    @Override
    public LipidElectrolytePayload initialState() {
        throw new IllegalArgumentException("LIPID_ELECTROLYTE requiere userId");
    }

    @Override
    public LipidElectrolytePayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para LIPID_ELECTROLYTE");
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

        return new LipidElectrolytePayload(new LipidPayload(lipids),
                                           new ElectrolytePayload(electrolytes));
    }

    @Override
    public PayloadGenerator<LipidElectrolytePayload> generator() {
        return new LipidElectrolyteGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.LIPID_ELECTROLYTE;
    }
}
