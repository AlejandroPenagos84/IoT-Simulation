package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.MetabolicComprehensivePayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.ElectrolytePanelFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.LipidPanelFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.MetabolicPanelFactory;

public class MetabolicComprehensiveGenerator implements PayloadGenerator<MetabolicComprehensivePayload> {
    private static final Random RANDOM = new Random();

    @Override
    public MetabolicComprehensivePayload generate(MetabolicComprehensivePayload currentPayload) {
        MetabolicPanel metabolic = currentPayload != null ? currentPayload.metabolic().metabolic() : null;
        LipidPanel lipids = currentPayload != null ? currentPayload.lipids().lipids() : null;
        ElectrolytePanel electrolytes = currentPayload != null ? currentPayload.electrolytes().electrolytes() : null;

        MetabolicPanel nextMetabolic = MetabolicPanelFactory.create(RANDOM, metabolic);
        LipidPanel nextLipids = LipidPanelFactory.createLipidPanel(RANDOM, lipids);
        ElectrolytePanel nextElectrolytes = ElectrolytePanelFactory.create(RANDOM, electrolytes);

        return new MetabolicComprehensivePayload(new MetabolicPayload(nextMetabolic),
                                                 new LipidPayload(nextLipids),
                                                 new ElectrolytePayload(nextElectrolytes));
    }
}
