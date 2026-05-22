package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.LipidHematologyPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.BloodCountFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.LipidPanelFactory;

public class LipidHematologyGenerator implements PayloadGenerator<LipidHematologyPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public LipidHematologyPayload generate(LipidHematologyPayload currentPayload) {
        LipidPanel lipids = currentPayload != null ? currentPayload.lipids().lipids() : null;
        BloodCountPanel bloodCount = currentPayload != null ? currentPayload.bloodCount().bloodCount() : null;

        LipidPanel nextLipids = LipidPanelFactory.createLipidPanel(RANDOM, lipids);
        BloodCountPanel nextBloodCount = BloodCountFactory.create(RANDOM, bloodCount);

        return new LipidHematologyPayload(new LipidPayload(nextLipids),
                                          new BloodCountMedicalPayload(nextBloodCount));
    }
}
