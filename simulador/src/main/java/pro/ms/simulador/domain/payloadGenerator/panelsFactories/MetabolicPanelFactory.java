package pro.ms.simulador.domain.payloadGenerator.panelsFactories;

import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.payloadGenerator.shared.Probabilities;

import java.util.Random;

public class MetabolicPanelFactory {
    public static MetabolicPanel create(Random RANDOM, MetabolicPanel currentMetabolicPanel) {
        return new MetabolicPanel(
                Probabilities.nextValue(RANDOM,currentMetabolicPanel != null ? currentMetabolicPanel.glucose() : 95.0, 95.0, 8.0, 70.0, 140.0),
                currentMetabolicPanel != null ? currentMetabolicPanel.glucoseUnit() : ConcentrationUnit.MG_DL,
                Probabilities.nextValue(RANDOM,currentMetabolicPanel != null ? currentMetabolicPanel.creatinine() : 1.0, 1.0, 0.10, 0.6, 1.5),
                Probabilities.nextValue(RANDOM,currentMetabolicPanel != null ? currentMetabolicPanel.bloodUreaNitrogen() : 14.0, 14.0, 2.0, 7.0, 25.0),
                Probabilities.nextValue(RANDOM,currentMetabolicPanel != null ? currentMetabolicPanel.uricAcid() : 5.2, 5.2, 0.35, 2.5, 7.5),
                Probabilities.nextValue(RANDOM,currentMetabolicPanel != null ? currentMetabolicPanel.ph() : 7.40, 7.40, 0.03, 7.30, 7.50),
                Probabilities.nextValue(RANDOM,currentMetabolicPanel != null ? currentMetabolicPanel.calcium() : 9.4, 9.4, 0.25, 8.4, 10.4)
        );
    }
}
