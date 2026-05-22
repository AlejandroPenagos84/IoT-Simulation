package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.HematometabolicPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.BloodCountFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.MetabolicPanelFactory;

public class HematometabolicGenerator implements PayloadGenerator<HematometabolicPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public HematometabolicPayload generate(HematometabolicPayload currentPayload) {
        MetabolicPanel metabolic = currentPayload != null ? currentPayload.metabolic().metabolic() : null;
        BloodCountPanel bloodCount = currentPayload != null ? currentPayload.bloodCount().bloodCount() : null;

        MetabolicPanel nextMetabolic = MetabolicPanelFactory.create(RANDOM, metabolic);
        BloodCountPanel nextBloodCount = BloodCountFactory.create(RANDOM, bloodCount);

        return new HematometabolicPayload(new MetabolicPayload(nextMetabolic),
                                          new BloodCountMedicalPayload(nextBloodCount));
    }
}
