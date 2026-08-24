/**
 * Represents an algorithm under experimental study.
 *
 * <p>Implementations should be side-effect free with respect to timing:
 * {@link #execute} performs only the work being measured. Do not print,
 * sleep, or otherwise do work inside execute() that isn't part of the
 * algorithm itself; it will be included in every timing.
 *
 * <p>Note that many algorithms (sorting, in particular) mutate their
 * input. If yours does, think carefully about what that means for how
 * your framework generates inputs for repeated trials.
 *
 * @param <T> the type of input this algorithm consumes
 */
public interface Algorithm<T> {

    /** A short, human-readable name used in reports and plots (e.g. "Merge Sort"). */
    String getName();

    /** Runs the algorithm on the given input. */
    void execute(T input);
}
