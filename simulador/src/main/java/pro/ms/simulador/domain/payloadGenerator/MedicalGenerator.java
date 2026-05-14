package pro.ms.simulador.domain.payloadGenerator;

import java.util.Random;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.MedicalPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.BloodCountFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.ElectrolytePanelFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.LipidPanelFactory;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.MetabolicPanelFactory;

/**
 * Generates a new {@link MedicalPayload} based on a previous state.
 *
 * <p>This generator simulates the temporal evolution of medical data by producing
 * new panel values derived from the previous payload. Each panel is generated
 * independently using its corresponding factory, applying stochastic variations
 * (e.g., Gaussian noise with drift towards physiological targets).</p>
 *
 * <p>If the input payload is {@code null}, a new baseline payload is generated
 * using default initialization rules defined in each panel factory.</p>
 *
 * <p>The generation process includes the following panels:
 * <ul>
 *   <li>{@link MetabolicPanel}</li>
 *   <li>{@link LipidPanel}</li>
 *   <li>{@link ElectrolytePanel}</li>
 *   <li>{@link BloodCountPanel}</li>
 * </ul>
 *
 * <p>Note: This class uses a shared {@link Random} instance. If deterministic
 * behavior is required (e.g., for testing), consider injecting the random source.</p>
 */
public class MedicalGenerator implements PayloadGenerator<MedicalPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public MedicalPayload generate(MedicalPayload currentPayload) {
        MetabolicPanel metabolic = currentPayload != null ? currentPayload.metabolic() : null;
        LipidPanel lipids = currentPayload != null ? currentPayload.lipids() : null;
        ElectrolytePanel electrolytes = currentPayload != null ? currentPayload.electrolytes() : null;
        BloodCountPanel bloodCount = currentPayload != null ? currentPayload.bloodCount() : null;

        MetabolicPanel nextMetabolic = MetabolicPanelFactory.create(RANDOM, metabolic);
        LipidPanel nextLipids = LipidPanelFactory.createLipidPanel(RANDOM, lipids);
        ElectrolytePanel nextElectrolytes = ElectrolytePanelFactory.create(RANDOM,electrolytes);
        BloodCountPanel nextBloodCount = BloodCountFactory.create(RANDOM,bloodCount);

        return new MedicalPayload(nextMetabolic, nextLipids, nextElectrolytes, nextBloodCount);
    }
}
