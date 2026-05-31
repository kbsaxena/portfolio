package bitmanipulation;

public class SingleNumber {
    int getSingle(int arr[]) {
        int ans = 0;
        for(int ele: arr){
            ans = ans ^ ele;
        }
        return ans;
    }
}
