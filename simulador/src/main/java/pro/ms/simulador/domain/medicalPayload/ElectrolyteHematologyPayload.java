package pro.ms.simulador.domain.medicalPayload;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.payload.Payload;

public record ElectrolyteHematologyPayload(
        ElectrolytePayload electrolytes,
        BloodCountMedicalPayload bloodCount
) implements Payload {

    public ElectrolyteHematologyPayload(List<Payload> leaves) {
        this(
                leaves.stream().filter(ElectrolytePayload.class::isInstance).map(ElectrolytePayload.class::cast).findFirst().orElse(null),
                leaves.stream().filter(BloodCountMedicalPayload.class::isInstance).map(BloodCountMedicalPayload.class::cast).findFirst().orElse(null)
        );
    }

    @Override
    public List<Payload> getPayloads() {
        return Stream.<Payload>of(electrolytes, bloodCount)
                .filter(Objects::nonNull)
                .toList();
    }
}
