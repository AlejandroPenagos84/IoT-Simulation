package pro.ms.simulador.domain.medicalPayload.metabolic;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import pro.ms.simulador.domain.payload.Payload;

public record MetabolicPayload(
        @JsonUnwrapped
        MetabolicPanel metabolic
) implements Payload {

    public MetabolicPayload {
        Objects.requireNonNull(metabolic, "metabolic must not be null");
    }

    @Override
    public List<Payload> getPayloads() {
        return List.of();
    }
}
