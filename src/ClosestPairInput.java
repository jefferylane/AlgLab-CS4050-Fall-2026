/**
 * The input type for closest-pair algorithms: a set of points, plus mutable
 * fields the algorithm is required to fill in with its answer.
 *
 * Why the mutable answer fields exist: a closest-pair algorithm doesn't
 * naturally mutate its input the way an in-place sort does — it just
 * computes an answer.
 *
 * Algorithm<T>.execute() returns void, so if that answer were computed
 * and then discarded, there would be no effect outside execute() at all,
 * and an aggressive JIT is entitled to eliminate the entire computation
 * as dead code: timing nothing, no matter how many trials you run.
 *
 * Writing the answer into fields of the input object (which the caller
 * still holds a reference to) keeps the work observable, the same way
 * sorting's in-place mutation does. Same family of measurement pitfall
 * as the mutation bug in NaiveTimingDemo, different failure mode:
 * the JVM will not spend time on work it can prove nobody looks at.
 *
 * As with the sorting algorithms, a fresh ClosestPairInput is expected
 * for every trial, not a reused one with stale answer fields.
 */
public final class ClosestPairInput {

    public final Point[] points;

    public double closestDistance = Double.NaN;
    public Point first;
    public Point second;

    public ClosestPairInput(Point[] points) {
        if (points.length < 2) {
            throw new IllegalArgumentException("need at least 2 points");
        }
        this.points = points;
    }
}
