package hashmap_hashset;

import java.util.HashMap;

public class PairsWithDifferenceK {
    int countPairs(int[] arr, int k) {
        // code here
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0)+1);
        }

        int countPairs = 0;
        for(int key:map.keySet()){
            if(map.containsKey(key+k)){
                countPairs += map.get(key) * map.get(key + k);
            }
        }

        return countPairs;
    }
}
