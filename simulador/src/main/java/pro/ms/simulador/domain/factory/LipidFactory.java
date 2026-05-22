package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.payloadGenerator.LipidGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class LipidFactory implements PayloadFactory<LipidPayload> {
    @Override
    public LipidPayload initialState() {
        throw new IllegalArgumentException("LIPID requiere userId");
    }

    @Override
    public LipidPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para LIPID");
        }

        LipidPanel lipids = new LipidPanel(
                185.0,
                ConcentrationUnit.MG_DL,
                135.0,
                ConcentrationUnit.MG_DL
        );

        return new LipidPayload(lipids);
    }

    @Override
    public PayloadGenerator<LipidPayload> generator() {
        return new LipidGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.LIPID;
    }
}
