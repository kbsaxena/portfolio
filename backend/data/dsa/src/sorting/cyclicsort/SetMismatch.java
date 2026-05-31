package sorting.cyclicsort;

public class SetMismatch {
    public int[] findErrorNums(int[] arr) {
        int i = 0;

        while(i < arr.length) {
            if(arr[i] == i + 1 || arr[i] == arr[arr[i] - 1]) {
                i++;
            } else {
                int idx = arr[i] - 1;

                int temp = arr[i];
                arr[i] = arr[idx];
                arr[idx] = temp;
            }
        }

        for(i = 0; i < arr.length; i++) {
            if(arr[i] != i + 1) {
                return new int[]{arr[i], i + 1}; // duplicate => arr[i] and missing => i+1
            }
        }

        return new int[]{-1, -1};              // default return
    }
}
