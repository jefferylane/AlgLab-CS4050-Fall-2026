import java.util.Random;

/**
 * Generates a fresh set of random integer-coordinate points, wrapped in a
 * fresh ClosestPairInput with un-set answer fields every call.
 * See ClosestPairInput for why a fresh instance (not a reused one) matters.
 */
public final class PointSetGenerator implements InputGenerator<ClosestPairInput> {

    private final long seed;
    private final int coordinateBound;

    public PointSetGenerator(long seed, int coordinateBound) {
        this.seed = seed;
        this.coordinateBound = coordinateBound;
    }

    @Override
    public String getDescription() {
        return "random int points in [0, " + coordinateBound + ") x [0, " + coordinateBound + "), seed=" + seed;
    }

    @Override
    public ClosestPairInput generate(int size) {
        Random rnd = new Random(seed + size);
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(rnd.nextInt(coordinateBound), rnd.nextInt(coordinateBound));
        }
        return new ClosestPairInput(points);
    }
}
