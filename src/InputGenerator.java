/**
 * Produces inputs of a requested "size" for a given {@link Algorithm} to consume.
 *
 * <p>What "size" means is up to the generator: array length, graph
 * vertex+edge count, string length, whatever fits the algorithm under
 * test. The framework you build should treat size as an opaque integer
 * label for the x-axis of your plots, not assume it can be derived from
 * the generated object itself.
 *
 * @param <T> the type of input produced
 */
public interface InputGenerator<T> {

    /** A short, human-readable description (e.g. "random int[], uniform distribution"). */
    String getDescription();

    /**
     * Produces a fresh input of the requested size. Called once per trial —
     * implementations do not need to cache or reuse anything.
     */
    T generate(int size);
}
