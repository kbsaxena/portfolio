package hashmap_hashset;

import java.util.ArrayList;
import java.util.HashSet;

public class RemoveDuplicates {
    ArrayList<Integer> remDuplicate(int arr[]) {
        // code here
        HashSet<Integer> hs = new HashSet<>();
        for(int i: arr) hs.add(i);
        return new ArrayList<>(hs);
    }
}
