/** Classic O(n^2) selection sort. */
public class SelectionSort implements Algorithm<int[]> {

    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    public void execute(int[] input) {
        int n = input.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (input[j] < input[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int tmp = input[i];
                input[i] = input[minIndex];
                input[minIndex] = tmp;
            }
        }
    }
}
