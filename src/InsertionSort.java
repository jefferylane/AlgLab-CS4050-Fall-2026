/** Classic O(n^2) insertion sort. */
public class InsertionSort implements Algorithm<int[]> {

    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    public void execute(int[] input) {
        for (int i = 1; i < input.length; i++) {
            int key = input[i];
            int j = i - 1;
            while (j >= 0 && input[j] > key) {
                input[j + 1] = input[j];
                j--;
            }
            input[j + 1] = key;
        }
    }
}
