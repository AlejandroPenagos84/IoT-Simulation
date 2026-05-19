package pro.ms.simulador.domain.medicalPayload.bloodCount;

import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.CountingUnit;

public record BloodCountPanel(
        Double hemoglobin,
        ConcentrationUnit hemoglobinUnit,
        Integer whiteBloodCells,
        CountingUnit whiteBloodCellsUnit,
        Integer platelets,
        CountingUnit plateletsUnit,
        Double iron,
        ConcentrationUnit ironUnit
) {
/*
    public BloodCountPanel {
        if (!Double.isFinite(hemoglobin) || hemoglobin < 0) {
            throw new IllegalArgumentException("hemoglobin must be a non-negative finite value");
        }
        if (hemoglobinUnit == null) {
            throw new IllegalArgumentException("hemoglobinUnit must not be null");
        }
        if (whiteBloodCells < 0) {
            throw new IllegalArgumentException("whiteBloodCells must be non-negative");
        }
        if (whiteBloodCellsUnit == null) {
            throw new IllegalArgumentException("whiteBloodCellsUnit must not be null");
        }
        if (platelets < 0) {
            throw new IllegalArgumentException("platelets must be non-negative");
        }
        if (plateletsUnit == null) {
            throw new IllegalArgumentException("plateletsUnit must not be null");
        }
        if (!Double.isFinite(iron) || iron < 0) {
            throw new IllegalArgumentException("iron must be a non-negative finite value");
        }
        if (ironUnit == null) {
            throw new IllegalArgumentException("ironUnit must not be null");
        }
    }*/
}

