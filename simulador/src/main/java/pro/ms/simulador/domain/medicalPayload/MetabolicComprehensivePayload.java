package pro.ms.simulador.domain.medicalPayload;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payload.Payload;

public record MetabolicComprehensivePayload(
        MetabolicPayload metabolic,
        LipidPayload lipids,
        ElectrolytePayload electrolytes
) implements Payload {

    public MetabolicComprehensivePayload(List<Payload> leaves) {
        this(
                leaves.stream().filter(MetabolicPayload.class::isInstance).map(MetabolicPayload.class::cast).findFirst().orElse(null),
                leaves.stream().filter(LipidPayload.class::isInstance).map(LipidPayload.class::cast).findFirst().orElse(null),
                leaves.stream().filter(ElectrolytePayload.class::isInstance).map(ElectrolytePayload.class::cast).findFirst().orElse(null)
        );
    }

    @Override
    public List<Payload> getPayloads() {
        return Stream.<Payload>of(metabolic, lipids, electrolytes)
                .filter(Objects::nonNull)
                .toList();
    }
}
