package pro.ms.simulador.domain.medicalPayload.bloodCount;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import pro.ms.simulador.domain.payload.Payload;

public record BloodCountMedicalPayload(
        @JsonUnwrapped
        BloodCountPanel bloodCount
) implements Payload {

    public BloodCountMedicalPayload {
        Objects.requireNonNull(bloodCount, "bloodCount must not be null");
    }

    @Override
    public List<Payload> getPayloads() {
        return List.of();
    }
}
