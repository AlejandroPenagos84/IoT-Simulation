package pro.ms.simulador.domain.payloadGenerator.panelsFactories;

import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.payloadGenerator.shared.Probabilities;

import java.util.Random;

public class LipidPanelFactory {
    public static LipidPanel createLipidPanel(Random RANDOM, LipidPanel currentLipidsPanel) {
        return new LipidPanel(
                Probabilities.nextValue(RANDOM,currentLipidsPanel != null ? currentLipidsPanel.totalCholesterol() : 185.0, 185.0, 12.0, 120.0, 260.0),
                currentLipidsPanel != null ? currentLipidsPanel.totalCholesterolUnit() : ConcentrationUnit.MG_DL,
                Probabilities.nextValue(RANDOM, currentLipidsPanel != null ? currentLipidsPanel.triglycerides() : 135.0, 135.0, 10.0, 70.0, 220.0),
                currentLipidsPanel != null ? currentLipidsPanel.triglyceridesUnit() : ConcentrationUnit.MG_DL
        );
    }
}
