package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;

import pro.ms.simulador.domain.medicalPayload.CardiometabolicPayload;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.LipidPanelFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.MetabolicPanelFactory;

public class CardiometabolicGenerator implements PayloadGenerator<CardiometabolicPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public CardiometabolicPayload generate(CardiometabolicPayload currentPayload) {
        MetabolicPanel metabolic = currentPayload != null ? currentPayload.metabolic().metabolic() : null;
        LipidPanel lipids = currentPayload != null ? currentPayload.lipids().lipids() : null;

        MetabolicPanel nextMetabolic = MetabolicPanelFactory.create(RANDOM, metabolic);
        LipidPanel nextLipids = LipidPanelFactory.createLipidPanel(RANDOM, lipids);

        return new CardiometabolicPayload(new MetabolicPayload(nextMetabolic),
                                          new LipidPayload(nextLipids));
    }
}
