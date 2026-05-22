package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.CardioHematologyPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.BloodCountFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.LipidPanelFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.MetabolicPanelFactory;

public class CardioHematologyGenerator implements PayloadGenerator<CardioHematologyPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public CardioHematologyPayload generate(CardioHematologyPayload currentPayload) {
        MetabolicPanel metabolic = currentPayload != null ? currentPayload.metabolic().metabolic() : null;
        LipidPanel lipids = currentPayload != null ? currentPayload.lipids().lipids() : null;
        BloodCountPanel bloodCount = currentPayload != null ? currentPayload.bloodCount().bloodCount() : null;

        MetabolicPanel nextMetabolic = MetabolicPanelFactory.create(RANDOM, metabolic);
        LipidPanel nextLipids = LipidPanelFactory.createLipidPanel(RANDOM, lipids);
        BloodCountPanel nextBloodCount = BloodCountFactory.create(RANDOM, bloodCount);

        return new CardioHematologyPayload(new MetabolicPayload(nextMetabolic),
                                           new LipidPayload(nextLipids),
                                           new BloodCountMedicalPayload(nextBloodCount));
    }
}
