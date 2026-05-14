package pro.ms.simulador.domain.payloadGenerator;

import pro.ms.simulador.domain.payload.Payload;

/**
 * Functional contract for generating the next state of a {@link Payload}.
 *
 * <p>Implementations are expected to produce a new payload instance based on
 * the current one, typically simulating temporal evolution (e.g., applying
 * stochastic changes or deterministic transformations).</p>
 *
 * <p>The input payload may be {@code null}, in which case implementations
 * should generate an initial baseline state.</p>
 *
 * @param <T> the type of payload handled by this generator
 */
@FunctionalInterface
public interface PayloadGenerator<T extends Payload> {

	/**
	 * Generates the next payload state based on the current one.
	 *
	 * @param currentPayload the current payload; may be {@code null}
	 * @return a new payload instance representing the next state
	 */
	T generate(T currentPayload);
}