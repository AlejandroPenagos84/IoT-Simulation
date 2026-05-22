package uni.csw.medibug.device_management_context.domain;

import java.time.Instant;
import java.util.UUID;

//Este es el Payload que se enviara para registrar un bicho
public record CommandPayload(
        UUID commandId,          // 1. Identificador único de la orden
        String action,           // 2. Qué tiene que hacer (El "Verbo")
        Instant timestamp,
        Integer interval       // 3. Cuándo se emitió la orden
) implements Payload {}