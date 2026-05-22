package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePayload;
import pro.ms.simulador.domain.medicalPayload.units.ElectrolyteUnit;
import pro.ms.simulador.domain.payloadGenerator.ElectrolyteGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class ElectrolyteFactory implements PayloadFactory<ElectrolytePayload> {
    @Override
    public ElectrolytePayload initialState() {
        throw new IllegalArgumentException("ELECTROLYTE requiere userId");
    }

    @Override
    public ElectrolytePayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para ELECTROLYTE");
        }

        ElectrolytePanel electrolytes = new ElectrolytePanel(
                140.0,
                ElectrolyteUnit.MEQ_L,
                4.2,
                ElectrolyteUnit.MEQ_L
        );

        return new ElectrolytePayload(electrolytes);
    }

    @Override
    public PayloadGenerator<ElectrolytePayload> generator() {
        return new ElectrolyteGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.ELECTROLYTE;
    }
}
