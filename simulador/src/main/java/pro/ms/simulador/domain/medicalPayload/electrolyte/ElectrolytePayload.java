package pro.ms.simulador.domain.medicalPayload.electrolyte;

import java.util.Objects;
import pro.ms.simulador.domain.payload.Payload;

public record ElectrolytePayload(
        ElectrolytePanel electrolytes
) implements Payload {

    public ElectrolytePayload {
        Objects.requireNonNull(electrolytes, "electrolytes must not be null");
    }
}

