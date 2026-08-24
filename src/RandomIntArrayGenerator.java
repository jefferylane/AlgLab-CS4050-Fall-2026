import java.util.Random;

/**
 * Generates arrays of uniformly random ints. Uses a seed derived from the
 * requested size so that repeated calls at the same size are reproducible
 * from run to run, while different sizes still get different data.
 */
public class RandomIntArrayGenerator implements InputGenerator<int[]> {

    private final long seed;

    public RandomIntArrayGenerator(long seed) {
        this.seed = seed;
    }

    @Override
    public String getDescription() {
        return "random int[], uniform distribution (seed=" + seed + ")";
    }

    @Override
    public int[] generate(int size) {
        Random rnd = new Random(seed + size);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rnd.nextInt();
        }
        return arr;
    }
}
