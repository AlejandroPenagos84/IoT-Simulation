package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.LipidPanelFactory;

public class LipidGenerator implements PayloadGenerator<LipidPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public LipidPayload generate(LipidPayload currentPayload) {
        LipidPanel lipids = currentPayload != null ? currentPayload.lipids() : null;
        LipidPanel nextLipids = LipidPanelFactory.createLipidPanel(RANDOM, lipids);

        return new LipidPayload(nextLipids);
    }
}
