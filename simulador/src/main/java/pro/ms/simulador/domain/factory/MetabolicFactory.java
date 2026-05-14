package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.payloadGenerator.MetabolicGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class MetabolicFactory implements PayloadFactory<MetabolicPayload> {
    @Override
    public MetabolicPayload initialState() {
        throw new IllegalArgumentException("MEDICAL requiere userId");
    }

    @Override
    public MetabolicPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para MEDICAL");
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

        return new MetabolicPayload(metabolic);
    }

    @Override
    public PayloadGenerator<MetabolicPayload> generator() {
        return new MetabolicGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.METABOLIC;
    }
}
