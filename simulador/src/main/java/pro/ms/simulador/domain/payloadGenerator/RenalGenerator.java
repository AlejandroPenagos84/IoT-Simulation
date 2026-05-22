package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.RenalPayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.ElectrolytePanelFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.MetabolicPanelFactory;

public class RenalGenerator implements PayloadGenerator<RenalPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public RenalPayload generate(RenalPayload currentPayload) {
        MetabolicPanel metabolic = currentPayload != null ? currentPayload.metabolic().metabolic() : null;
        ElectrolytePanel electrolytes = currentPayload != null ? currentPayload.electrolytes().electrolytes() : null;

        MetabolicPanel nextMetabolic = MetabolicPanelFactory.create(RANDOM, metabolic);
        ElectrolytePanel nextElectrolytes = ElectrolytePanelFactory.create(RANDOM, electrolytes);

        return new RenalPayload(new MetabolicPayload(nextMetabolic),
                                new ElectrolytePayload(nextElectrolytes));
    }
}
