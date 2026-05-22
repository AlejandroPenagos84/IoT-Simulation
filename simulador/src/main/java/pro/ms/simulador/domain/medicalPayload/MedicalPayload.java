package pro.ms.simulador.domain.medicalPayload;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payload.Payload;

public record MedicalPayload(
        MetabolicPayload metabolic,
        LipidPayload lipids,
        ElectrolytePayload electrolytes,
        BloodCountMedicalPayload bloodCount
) implements Payload {

    public MedicalPayload(List<Payload> leaves) {
        this(
                leaves.stream().filter(MetabolicPayload.class::isInstance).map(MetabolicPayload.class::cast).findFirst().orElse(null),
                leaves.stream().filter(LipidPayload.class::isInstance).map(LipidPayload.class::cast).findFirst().orElse(null),
                leaves.stream().filter(ElectrolytePayload.class::isInstance).map(ElectrolytePayload.class::cast).findFirst().orElse(null),
                leaves.stream().filter(BloodCountMedicalPayload.class::isInstance).map(BloodCountMedicalPayload.class::cast).findFirst().orElse(null)
        );
    }

    public MedicalPayload {
        Objects.requireNonNull(metabolic, "metabolic must not be null");
        Objects.requireNonNull(lipids, "lipids must not be null");
        Objects.requireNonNull(electrolytes, "electrolytes must not be null");
        Objects.requireNonNull(bloodCount, "bloodCount must not be null");
    }

    @Override
    public List<Payload> getPayloads() {
        return Stream.<Payload>of(metabolic, lipids, electrolytes, bloodCount)
                .filter(Objects::nonNull)
                .toList();
    }
}
