package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.LipidElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.ElectrolytePanelFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.LipidPanelFactory;

public class LipidElectrolyteGenerator implements PayloadGenerator<LipidElectrolytePayload> {
    private static final Random RANDOM = new Random();

    @Override
    public LipidElectrolytePayload generate(LipidElectrolytePayload currentPayload) {
        LipidPanel lipids = currentPayload != null ? currentPayload.lipids().lipids() : null;
        ElectrolytePanel electrolytes = currentPayload != null ? currentPayload.electrolytes().electrolytes() : null;

        LipidPanel nextLipids = LipidPanelFactory.createLipidPanel(RANDOM, lipids);
        ElectrolytePanel nextElectrolytes = ElectrolytePanelFactory.create(RANDOM, electrolytes);

        return new LipidElectrolytePayload(new LipidPayload(nextLipids),
                                           new ElectrolytePayload(nextElectrolytes));
    }
}
