package pro.ms.simulador.domain.payload;

import java.util.List;

public interface Payload {
    List<Payload> getPayloads();

    default List<Payload> flatten() {
        List<Payload> children = getPayloads();
        if (children == null || children.isEmpty()) {
            return List.of(this);
        }
        return children.stream()
                .flatMap(p -> p.flatten().stream())
                .toList();
    }
}


