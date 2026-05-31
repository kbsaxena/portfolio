package hashmap_hashset;

import java.util.ArrayList;
import java.util.HashSet;

public class MissingElementInRange {
    public ArrayList<Integer> missingRange(int[] arr, int low, int high) {
        // code here
        HashSet<Integer> hs = new HashSet<>();
        for(int i:arr) hs.add(i);

        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=low;i<=high;i++){
            if(!hs.contains(i)) ans.add(i);
        }

        return ans;
    }
}
