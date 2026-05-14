package pro.ms.simulador.domain.factory;

import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.payload.Payload;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

public interface PayloadFactory<T extends Payload> {
    T initialState();
    default T initialState(String userId) {
        return initialState();
    }
    PayloadGenerator<T> generator();
    DeviceType deviceType();
}