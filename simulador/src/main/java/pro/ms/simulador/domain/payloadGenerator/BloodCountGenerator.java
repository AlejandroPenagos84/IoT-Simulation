package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.BloodCountFactory;

public class BloodCountGenerator implements PayloadGenerator<BloodCountMedicalPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public BloodCountMedicalPayload generate(BloodCountMedicalPayload currentPayload) {
        BloodCountPanel bloodCount = currentPayload != null ? currentPayload.bloodCount() : null;
        BloodCountPanel nextBloodCount = BloodCountFactory.create(RANDOM, bloodCount);

        return new BloodCountMedicalPayload(nextBloodCount);
    }
}
