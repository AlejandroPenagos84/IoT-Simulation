package pro.ms.simulador.domain.medicalPayload;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payload.Payload;

public record HematometabolicPayload(
        MetabolicPayload metabolic,
        BloodCountMedicalPayload bloodCount
) implements Payload {

    public HematometabolicPayload(List<Payload> leaves) {
        this(
                leaves.stream().filter(MetabolicPayload.class::isInstance).map(MetabolicPayload.class::cast).findFirst().orElse(null),
                leaves.stream().filter(BloodCountMedicalPayload.class::isInstance).map(BloodCountMedicalPayload.class::cast).findFirst().orElse(null)
        );
    }

    @Override
    public List<Payload> getPayloads() {
        return Stream.<Payload>of(metabolic, bloodCount)
                .filter(Objects::nonNull)
                .toList();
    }
}
