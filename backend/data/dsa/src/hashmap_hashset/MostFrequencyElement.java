package hashmap_hashset;

import java.util.HashMap;

public class MostFrequencyElement {
    public int mostFreqEle(int[] arr) {
        // code here
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int ele: arr){
            if(!map.containsKey(ele)) map.put(ele, 1);
            else map.put(ele, map.get(ele)+1);
        }
        int maxFrequency = 1, ans = arr[0];
        for(int key: map.keySet()){
            int frequency = map.get(key);
            if(maxFrequency<frequency || (frequency == maxFrequency && key>ans)){
                maxFrequency = frequency;
                ans = key;
            }

        }
        return ans;
    }
}
