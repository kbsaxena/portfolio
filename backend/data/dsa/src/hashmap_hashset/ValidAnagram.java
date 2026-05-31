package hashmap_hashset;

import java.util.HashMap;

public class ValidAnagram {
    public static boolean areAnagrams(String s1, String s2) {
        if(s1.length() != s2.length()) return false;

        HashMap<Character, Integer> map = new HashMap<>();
        for(char c: s1.toCharArray()){
            if(!map.containsKey(c)) map.put(c, 1);
            else map.put(c, map.get(c)+1);
        }

        for(char c: s2.toCharArray()){
            if(!map.containsKey(c)) return false;
            else {
                int frequency = map.get(c);
                if(frequency == 1) map.remove(c);
                else map.put(c, frequency-1);
            }
        }

        return map.size()==0;

    }
}
