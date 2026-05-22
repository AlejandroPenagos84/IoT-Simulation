package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.CardiometabolicPayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.payloadGenerator.CardiometabolicGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class CardiometabolicFactory implements PayloadFactory<CardiometabolicPayload> {
    @Override
    public CardiometabolicPayload initialState() {
        throw new IllegalArgumentException("CARDIOMETABOLIC requiere userId");
    }

    @Override
    public CardiometabolicPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para CARDIOMETABOLIC");
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

        return new CardiometabolicPayload(new MetabolicPayload(metabolic),
                                          new LipidPayload(lipids));
    }

    @Override
    public PayloadGenerator<CardiometabolicPayload> generator() {
        return new CardiometabolicGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.CARDIOMETABOLIC;
    }
}
