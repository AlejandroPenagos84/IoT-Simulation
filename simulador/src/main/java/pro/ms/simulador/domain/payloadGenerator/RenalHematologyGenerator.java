package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.RenalHematologyPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.BloodCountFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.ElectrolytePanelFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.MetabolicPanelFactory;

public class RenalHematologyGenerator implements PayloadGenerator<RenalHematologyPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public RenalHematologyPayload generate(RenalHematologyPayload currentPayload) {
        MetabolicPanel metabolic = currentPayload != null ? currentPayload.metabolic().metabolic() : null;
        ElectrolytePanel electrolytes = currentPayload != null ? currentPayload.electrolytes().electrolytes() : null;
        BloodCountPanel bloodCount = currentPayload != null ? currentPayload.bloodCount().bloodCount() : null;

        MetabolicPanel nextMetabolic = MetabolicPanelFactory.create(RANDOM, metabolic);
        ElectrolytePanel nextElectrolytes = ElectrolytePanelFactory.create(RANDOM, electrolytes);
        BloodCountPanel nextBloodCount = BloodCountFactory.create(RANDOM, bloodCount);

        return new RenalHematologyPayload(new MetabolicPayload(nextMetabolic),
                                          new ElectrolytePayload(nextElectrolytes),
                                          new BloodCountMedicalPayload(nextBloodCount));
    }
}
