package pro.ms.simulador.domain.medicalPayload.lipids;

import java.util.Objects;
import pro.ms.simulador.domain.payload.Payload;

public record LipidPayload(
        LipidPanel lipids
) implements Payload {

    public LipidPayload {
        Objects.requireNonNull(lipids, "lipids must not be null");
    }
}

