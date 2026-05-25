package pro.ms.simulador.domain.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public interface Payload {
    @JsonIgnore
    List<Payload> getPayloads();

    @JsonIgnore
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


