package sorting.cyclicsort;

public class MissingInArray {
    //Method 3 Using Cyclic Sort
    int missingNum(int arr[]) {
        int i=0;
        while(i<arr.length){
            if(arr[i] == i+1 || arr[i] == arr.length+1) i++;
            else {
                int idx = arr[i] - 1;
                int temp = arr[i];
                arr[i] = arr[idx];
                arr[idx] = temp;
            }
        }
        for(i=0;i<arr.length;i++){
            if(arr[i] != i+1) return i+1;
        }
        return i+1;
    }

    //Method 2 Negative Marking Method
    int missingNum2(int arr[]) {
        for(int i = 0; i < arr.length; i++) {
            int val = Math.abs(arr[i]);
            if(val <= arr.length) {   // only mark valid indices
                int idx = val - 1;
                if(arr[idx] > 0)
                    arr[idx] = -arr[idx];
            }
        }
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] > 0)
                return i + 1;
        }
        return arr.length + 1;
    }

    //Method 1 Using Sum
    int missingNum1(int arr[]) {
        int sum = 0;
        for(int i = 0; i<arr.length; i++){
            sum += arr[i];
        }

        long n = arr.length+1;
        int sumN = (int)(n*(n+1)/2);

        return sumN - sum;
    }
}
