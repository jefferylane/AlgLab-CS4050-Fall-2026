/**
 * A deliberately naive timing example. This is NOT the framework you are
 * asked to build. It exists so you can see, firsthand, why "just call
 * System.currentTimeMillis() before and after" is not good enough, before
 * you design something better.
 *
 * Run this class a few times (timings will vary run to run) and look
 * closely at both halves of the output.
 */
public class NaiveTimingDemo {

    /*
    public static void main(String[] args) {
        System.out.println("--- Part 1: reusing the same array across trials ---");
        part1_mutationBug();

        System.out.println();
        System.out.println("--- Part 2: one trial per size, no warm-up ---");
        part2_singleTrialNoise();
    }
    */

    /**
     * Sorts the SAME array object five times in a row without regenerating
     * it. Watch what happens to the timings after trial 0: InsertionSort
     * mutates its input, and its best case (an already-sorted array) is
     * O(n) rather than O(n^2), so trial 1 onward is a dramatically easier
     * sort than the one you intended to time.
     *
     * Any framework you build that reuses a single generated input across
     * multiple timed trials has this exact bug, but it will not always be
     * this obvious. An algorithm without a fast best case can hide the same
     * bug behind numbers that merely look a little too consistent.
     */
    private static void part1_mutationBug() {
        InsertionSort insertionSort = new InsertionSort();
        RandomIntArrayGenerator gen = new RandomIntArrayGenerator(42L);
        int[] input = gen.generate(8000);

        for (int trial = 0; trial < 5; trial++) {
            long start = System.nanoTime();
            insertionSort.execute(input);
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            System.out.printf("  trial=%d  elapsed=%4d ms%n", trial, elapsedMs);
        }
    }

    /**
     * Times one run at each of several sizes, with no warm-up and no
     * repeated trials. A single measurement per size gives you no way to
     * tell whether a number is typical or an outlier caused by JIT
     * compilation kicking in, a garbage-collection pause, or OS scheduling
     * noise. Run this class a few times back to back and compare the
     * numbers you get for the same sizes.
     */
    private static void part2_singleTrialNoise() {
        InsertionSort insertionSort = new InsertionSort();
        RandomIntArrayGenerator gen = new RandomIntArrayGenerator(7L);
        int[] sizes = {500, 1000, 2000, 4000, 8000};

        for (int size : sizes) {
            int[] input = gen.generate(size);
            long start = System.nanoTime();
            insertionSort.execute(input);
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            System.out.printf("  n=%6d  elapsed=%4d ms%n", size, elapsedMs);
        }
    }
}
