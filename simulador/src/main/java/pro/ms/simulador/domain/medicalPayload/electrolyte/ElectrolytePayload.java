package pro.ms.simulador.domain.medicalPayload.electrolyte;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import pro.ms.simulador.domain.payload.Payload;

public record ElectrolytePayload(
        @JsonUnwrapped
        ElectrolytePanel electrolytes
) implements Payload {

    public ElectrolytePayload {
        Objects.requireNonNull(electrolytes, "electrolytes must not be null");
    }

    @Override
    public List<Payload> getPayloads() {
        return List.of();
    }
}
