package pro.ms.simulador.domain.payloadGenerator;

import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.payloadGenerator.panelsFactories.MetabolicPanelFactory;
import java.util.Random;

/**
 * Generates a new {@link MetabolicPayload} based on a previous state.
 *
 * <p>This generator simulates the temporal evolution of metabolic data by
 * producing a new {@link MetabolicPanel} derived from the previous one.
 * The generation logic is delegated to {@link MetabolicPanelFactory},
 * which applies stochastic variations (e.g., Gaussian noise with drift
 * towards physiological target values).</p>
 *
 * <p>If the input payload is {@code null}, a new baseline metabolic panel
 * is created using the default initialization rules defined in the factory.</p>
 *
 * <p>Note: This class uses a shared {@link Random} instance. For deterministic
 * behavior (e.g., testing or reproducibility), consider injecting the random source.</p>
 */
public class MetabolicGenerator implements PayloadGenerator<MetabolicPayload> {
    private static final Random RANDOM = new Random();

    @Override
    public MetabolicPayload generate(MetabolicPayload currentPayload) {
        MetabolicPanel metabolic = currentPayload != null ? currentPayload.metabolic() : null;
        MetabolicPanel nextMetabolic = MetabolicPanelFactory.create(RANDOM, metabolic);

        return new MetabolicPayload(nextMetabolic);
    }
}
