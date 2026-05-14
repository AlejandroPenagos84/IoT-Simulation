package pro.ms.simulador.domain.payloadGenerator.shared;

import java.util.Random;
/**
 * Utility class for generating values that evolve over time using
 * a Gaussian (normal) distribution with a drift towards a target value.
 *
 * The generated value is influenced by:
 * - A drift component that moves the value towards the target
 * - Random Gaussian noise controlled by sigma
 * - Clamping between a minimum and maximum range
 */
public class Probabilities {
    public static double nextValue(Random RANDOM, double current, double target, double sigma, double min, double max) {
        double drift = (target - current) * 0.15;
        double gaussianNoise = RANDOM.nextGaussian() * sigma;
        double candidate = current + drift + gaussianNoise;
        return clamp(candidate, min, max);
    }

    public static int nextCount(Random RANDOM,int current, int target, double sigma, int min, int max) {
        double next = nextValue(RANDOM,current, target, sigma, min, max);
        return (int) Math.round(next);
    }

    private static double clamp(double value, double min, double max) {
        // Ensure correct clamping between min and max.
        // Math.clamp with (value, min, max) is not available on all JVMs and
        // the previous call had parameters in the wrong order. Use explicit
        // min/max to be safe and clear.
        if (Double.isNaN(value)) return min;
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
