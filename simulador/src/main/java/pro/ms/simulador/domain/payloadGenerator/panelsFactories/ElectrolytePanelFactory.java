package pro.ms.simulador.domain.payloadGenerator.panelsFactories;

import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.units.ElectrolyteUnit;
import pro.ms.simulador.domain.payloadGenerator.shared.Probabilities;

import java.util.Random;

public class ElectrolytePanelFactory {
    public static ElectrolytePanel create(Random RANDOM, ElectrolytePanel currentElectrolytePanel) {
        return new ElectrolytePanel(
                Probabilities.nextValue(RANDOM,currentElectrolytePanel != null ? currentElectrolytePanel.sodium() : 140.0, 140.0, 0.8, 133.0, 147.0),
                currentElectrolytePanel != null ? currentElectrolytePanel.sodiumUnit() : ElectrolyteUnit.MEQ_L,
                Probabilities.nextValue(RANDOM, currentElectrolytePanel != null ? currentElectrolytePanel.potassium() : 4.2, 4.2, 0.12, 3.5, 5.1),
                currentElectrolytePanel != null ? currentElectrolytePanel.potassiumUnit() : ElectrolyteUnit.MEQ_L
        );
    }
}
