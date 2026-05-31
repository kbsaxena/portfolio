package bitmanipulation;

public class UniqueNumbers2 {
    public int[] singleNum(int[] arr) {
        int xorOfAll = 0;
        for(int ele:arr) xorOfAll = xorOfAll ^ ele;

        //extract righmost set bit of xorOfAll of the 2 single numbers
        int extract = xorOfAll ^ (xorOfAll & (xorOfAll-1));

        int x = 0; //set bucket
        int y = 0; //unset bucket
        for(int ele:arr) {
            if((ele & extract) == 0) y = y ^ ele;
            else x = x ^ ele;
        }

        int[] ans = new int[2];
        ans[0] = Math.min(x,y);
        ans[1] = Math.max(x,y);
        return ans;
    }
}
