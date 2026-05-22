package pro.ms.simulador.domain.medicalPayload;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.payload.Payload;

public record LipidHematologyPayload(
        LipidPayload lipids,
        BloodCountMedicalPayload bloodCount
) implements Payload {

    public LipidHematologyPayload(List<Payload> leaves) {
        this(
                leaves.stream().filter(LipidPayload.class::isInstance).map(LipidPayload.class::cast).findFirst().orElse(null),
                leaves.stream().filter(BloodCountMedicalPayload.class::isInstance).map(BloodCountMedicalPayload.class::cast).findFirst().orElse(null)
        );
    }

    @Override
    public List<Payload> getPayloads() {
        return Stream.<Payload>of(lipids, bloodCount)
                .filter(Objects::nonNull)
                .toList();
    }
}
