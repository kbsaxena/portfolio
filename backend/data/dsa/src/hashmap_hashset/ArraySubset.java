package hashmap_hashset;

import java.util.HashMap;

public class ArraySubset {
    //a[] = [11, 7, 1, 13, 21, 3, 7, 3], b[] = [11, 3, 7, 1, 7]
    public boolean isSubset(int a[], int b[]) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int ele:a){
            if(!map.containsKey(ele)) map.put(ele, 1);
            else map.put(ele, map.get(ele)+1);
        }

        for(int ele:b){
            if(!map.containsKey(ele)) return false;
            else {
                int frequency = map.get(ele);
                if(frequency == 1) map.remove(ele);
                else map.put(ele, frequency-1);
            }
        }
        return true;
    }
}
