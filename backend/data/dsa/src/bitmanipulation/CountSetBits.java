package bitmanipulation;

public class CountSetBits {
    //Method 3 Clearing right most bit till we reach 0 TC O(logn)
    static int setBits(int n) {
        int count = 0;
        while(n!=0){
            n = n & (n-1);
            count++;
        }
        return count;
    }

    //Method 2 Dividing by 2 until we get 0 TC O(logn)
    static int setBits2(int n) {
        int count = 0;
        while(n!=0){
            count += (n%2);
            n = n/2;
        }
        return count;
    }

    //Method 1 Looping all 32 bits TC O(32)
    static int setBits1(int n) {
        int count = 0;
        for(int i=0;i<=31;i++){
            if((n>>i)%2 == 1) count++;
        }
        return count;
    }
}
