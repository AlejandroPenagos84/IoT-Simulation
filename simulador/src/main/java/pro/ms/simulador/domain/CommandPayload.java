package pro.ms.simulador.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record CommandPayload(
        UUID commandId,          // 1. Identificador único de la orden
        String action,           // 2. Qué tiene que hacer (El "Verbo")
        Instant timestamp,
        Integer interval       // 3. Cuándo se emitió la orden
) implements Serializable {}