package hashmap_hashset;

import java.util.HashSet;

public class LongestSubstringWithoutRepeatingCharacters {
    int longestUniqueSubstring(String s) {
        HashSet<Character> set = new HashSet<>();
        int i=0, j=0, maxLen = 0;
        while(j<s.length()){
            char c = s.charAt(j);
            if(!set.contains(c)) {
                set.add(c);
            } else{
                int len = j-i;
                maxLen = Math.max(maxLen, len);
                while(s.charAt(i)!=c) {
                    set.remove(s.charAt(i));
                    i++;
                }
                i++;
            }
            j++;
        }
        int len = j-i;
        maxLen = Math.max(maxLen, len);

        return maxLen;
    }
}
