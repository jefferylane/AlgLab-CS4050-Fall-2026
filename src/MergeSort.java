/** Classic O(n log n) top-down merge sort. */
public class MergeSort implements Algorithm<int[]> {

    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    public void execute(int[] input) {
        if (input.length < 2) {
            return;
        }
        int[] buffer = new int[input.length];
        sort(input, buffer, 0, input.length - 1);
    }

    private void sort(int[] a, int[] buffer, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, buffer, lo, mid);
        sort(a, buffer, mid + 1, hi);
        merge(a, buffer, lo, mid, hi);
    }

    private void merge(int[] a, int[] buffer, int lo, int mid, int hi) {
        System.arraycopy(a, lo, buffer, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = buffer[j++];
            } else if (j > hi) {
                a[k] = buffer[i++];
            } else if (buffer[i] <= buffer[j]) {
                a[k] = buffer[i++];
            } else {
                a[k] = buffer[j++];
            }
        }
    }
}
