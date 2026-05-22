package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.ElectrolytePanelFactory;

public class ElectrolyteGenerator implements PayloadGenerator<ElectrolytePayload> {
    private static final Random RANDOM = new Random();

    @Override
    public ElectrolytePayload generate(ElectrolytePayload currentPayload) {
        ElectrolytePanel electrolytes = currentPayload != null ? currentPayload.electrolytes() : null;
        ElectrolytePanel nextElectrolytes = ElectrolytePanelFactory.create(RANDOM, electrolytes);

        return new ElectrolytePayload(nextElectrolytes);
    }
}
