package pro.ms.simulador.domain.medicalPayload.lipids;

import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;

public record LipidPanel(
        Double totalCholesterol,
        ConcentrationUnit totalCholesterolUnit,
        Double triglycerides,
        ConcentrationUnit triglyceridesUnit
) {
    /*
    public LipidPanel {
        if (!Double.isFinite(totalCholesterol) || totalCholesterol < 0) {
            throw new IllegalArgumentException("totalCholesterol must be a non-negative finite value");
        }
        if (totalCholesterolUnit == null) {
            throw new IllegalArgumentException("totalCholesterolUnit must not be null");
        }
        if (!Double.isFinite(triglycerides) || triglycerides < 0) {
            throw new IllegalArgumentException("triglycerides must be a non-negative finite value");
        }
        if (triglyceridesUnit == null) {
            throw new IllegalArgumentException("triglyceridesUnit must not be null");
        }
    }*/
}

