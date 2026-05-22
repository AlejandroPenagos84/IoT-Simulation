package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.ElectrolyteHematologyPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.BloodCountFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.ElectrolytePanelFactory;

public class ElectrolyteHematologyGenerator implements PayloadGenerator<ElectrolyteHematologyPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public ElectrolyteHematologyPayload generate(ElectrolyteHematologyPayload currentPayload) {
        ElectrolytePanel electrolytes = currentPayload != null ? currentPayload.electrolytes().electrolytes() : null;
        BloodCountPanel bloodCount = currentPayload != null ? currentPayload.bloodCount().bloodCount() : null;

        ElectrolytePanel nextElectrolytes = ElectrolytePanelFactory.create(RANDOM, electrolytes);
        BloodCountPanel nextBloodCount = BloodCountFactory.create(RANDOM, bloodCount);

        return new ElectrolyteHematologyPayload(new ElectrolytePayload(nextElectrolytes),
                                                new BloodCountMedicalPayload(nextBloodCount));
    }
}
