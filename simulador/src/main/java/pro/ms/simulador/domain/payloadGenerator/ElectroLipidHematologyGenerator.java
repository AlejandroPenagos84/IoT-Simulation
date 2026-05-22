package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.ElectroLipidHematologyPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.BloodCountFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.ElectrolytePanelFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.LipidPanelFactory;

public class ElectroLipidHematologyGenerator implements PayloadGenerator<ElectroLipidHematologyPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public ElectroLipidHematologyPayload generate(ElectroLipidHematologyPayload currentPayload) {
        LipidPanel lipids = currentPayload != null ? currentPayload.lipids().lipids() : null;
        ElectrolytePanel electrolytes = currentPayload != null ? currentPayload.electrolytes().electrolytes() : null;
        BloodCountPanel bloodCount = currentPayload != null ? currentPayload.bloodCount().bloodCount() : null;

        LipidPanel nextLipids = LipidPanelFactory.createLipidPanel(RANDOM, lipids);
        ElectrolytePanel nextElectrolytes = ElectrolytePanelFactory.create(RANDOM, electrolytes);
        BloodCountPanel nextBloodCount = BloodCountFactory.create(RANDOM, bloodCount);

        return new ElectroLipidHematologyPayload(new LipidPayload(nextLipids),
                                                 new ElectrolytePayload(nextElectrolytes),
                                                 new BloodCountMedicalPayload(nextBloodCount));
    }
}
