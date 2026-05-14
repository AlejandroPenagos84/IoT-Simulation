package pro.ms.simulador.domain.payloadGenerator.panelsFactories;

import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.CountingUnit;
import pro.ms.simulador.domain.payloadGenerator.shared.Probabilities;

import java.util.Random;

public class BloodCountFactory {
    public static BloodCountPanel create(Random RANDOM, BloodCountPanel currentBloodCountPanel) {
        return new BloodCountPanel(
                Probabilities.nextValue(RANDOM, currentBloodCountPanel != null ? currentBloodCountPanel.hemoglobin() : 14.0, 14.0, 0.35, 12.0, 17.5),
                currentBloodCountPanel != null ? currentBloodCountPanel.hemoglobinUnit() : ConcentrationUnit.G_DL,
                Probabilities.nextCount(RANDOM, currentBloodCountPanel != null ? currentBloodCountPanel.whiteBloodCells() : 7200, 7200, 450, 3500, 12000),
                currentBloodCountPanel != null ? currentBloodCountPanel.whiteBloodCellsUnit() : CountingUnit.CELLS_MCL,
                Probabilities.nextCount(RANDOM, currentBloodCountPanel != null ? currentBloodCountPanel.platelets() : 250000, 250000, 14000, 140000, 450000),
                currentBloodCountPanel != null ? currentBloodCountPanel.plateletsUnit() : CountingUnit.CELLS_MCL,
                Probabilities.nextValue(RANDOM, currentBloodCountPanel != null ? currentBloodCountPanel.iron() : 105.0, 105.0, 7.0, 50.0, 170.0),
                currentBloodCountPanel != null ? currentBloodCountPanel.ironUnit() : ConcentrationUnit.MG_DL
        );
    }
}
