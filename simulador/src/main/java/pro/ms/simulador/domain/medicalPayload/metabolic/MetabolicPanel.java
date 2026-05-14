package pro.ms.simulador.domain.medicalPayload.metabolic;

import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;

public record MetabolicPanel(
        double glucose,
        ConcentrationUnit glucoseUnit,
        double creatinine,
        double bloodUreaNitrogen,
        double uricAcid,
        double ph,
        double calcium
) {
    /*
    public MetabolicPanel {
        if (!Double.isFinite(glucose) || glucose < 0) {
            throw new IllegalArgumentException("glucose must be a non-negative finite value");
        }
        if (glucoseUnit == null) {
            throw new IllegalArgumentException("glucoseUnit must not be null");
        }
        if (!Double.isFinite(creatinine) || creatinine < 0) {
            throw new IllegalArgumentException("creatinine must be a non-negative finite value");
        }
        if (!Double.isFinite(bloodUreaNitrogen) || bloodUreaNitrogen < 0) {
            throw new IllegalArgumentException("bloodUreaNitrogen must be a non-negative finite value");
        }
        if (!Double.isFinite(uricAcid) || uricAcid < 0) {
            throw new IllegalArgumentException("uricAcid must be a non-negative finite value");
        }
        if (!Double.isFinite(ph) || ph < 0 || ph > 14) {
            throw new IllegalArgumentException("ph must be a finite value between 0 and 14");
        }
        if (!Double.isFinite(calcium) || calcium < 0) {
            throw new IllegalArgumentException("calcium must be a non-negative finite value");
        }
    }*/
}

