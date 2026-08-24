import java.util.Arrays;

/**
 * Wraps {@link Arrays#sort(int[])} (a dual-pivot quicksort for primitives in
 * the JDK) so the standard library sort can be plugged into the framework
 * and compared against the others on equal footing.
 */
public class ArraysSortWrapper implements Algorithm<int[]> {

    @Override
    public String getName() {
        return "Arrays.sort (JDK)";
    }

    @Override
    public void execute(int[] input) {
        Arrays.sort(input);
    }
}
