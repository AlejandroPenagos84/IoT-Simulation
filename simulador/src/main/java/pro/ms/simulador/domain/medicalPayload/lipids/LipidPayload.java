package pro.ms.simulador.domain.medicalPayload.lipids;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import pro.ms.simulador.domain.payload.Payload;

public record LipidPayload(
        @JsonUnwrapped
        LipidPanel lipids
) implements Payload {

    public LipidPayload {
        Objects.requireNonNull(lipids, "lipids must not be null");
    }

    @Override
    public List<Payload> getPayloads() {
        return List.of();
    }
}
