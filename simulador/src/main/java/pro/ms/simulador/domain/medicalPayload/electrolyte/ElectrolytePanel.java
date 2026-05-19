package pro.ms.simulador.domain.medicalPayload.electrolyte;

import pro.ms.simulador.domain.medicalPayload.units.ElectrolyteUnit;

public record ElectrolytePanel(
        Double sodium,
        ElectrolyteUnit sodiumUnit,
        Double potassium,
        ElectrolyteUnit potassiumUnit
) {
    /*
    public ElectrolytePanel {
        if (!Double.isFinite(sodium) || sodium < 0) {
            throw new IllegalArgumentException("sodium must be a non-negative finite value");
        }
        if (sodiumUnit == null) {
            throw new IllegalArgumentException("sodiumUnit must not be null");
        }
        if (!Double.isFinite(potassium) || potassium < 0) {
            throw new IllegalArgumentException("potassium must be a non-negative finite value");
        }
        if (potassiumUnit == null) {
            throw new IllegalArgumentException("potassiumUnit must not be null");
        }
    }*/
}

