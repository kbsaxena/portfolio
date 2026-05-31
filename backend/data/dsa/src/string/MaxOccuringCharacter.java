package string;

import java.util.Arrays;

public class MaxOccuringCharacter {
    public static void main(String[] args) {
        System.out.println(getMaxOccuringChar2("success"));
        System.out.println(getMaxOccuringChar2("testsample"));
        System.out.println(getMaxOccuringChar2("output"));
    }

    //Brute Force TC - O(n^2)
    public static char getMaxOccuringChar(String s) {
        int maxCount = Integer.MIN_VALUE;
        char ans = s.charAt(0);

        for (int i = 0; i < s.length(); i++) {
            int count = 0;
            for (int j = i + 1; j < s.length(); j++) {
                if(s.charAt(i) == s.charAt(j)){
                    count++;
                }
            }
            if(count>maxCount){
                maxCount = count;
                ans = s.charAt(i);
            }
            if(count == maxCount){
                if(s.charAt(i)<ans){
                    ans = s.charAt(i);
                }
            }

        }
        return ans;
    }

    //Using Sliding Window and Sorting TC=O(nlogn)
    public static char getMaxOccuringChar1(String s) {
        int maxCount = Integer.MIN_VALUE;
        char ans = s.charAt(0);

        char[] arr = s.toCharArray();
        Arrays.sort(arr);

        int i=0, j=0;
        while(j<arr.length){
            if(arr[i] == arr[j]) j++;
            else{ //i to j-1
                int len = j-i;
                if(len>maxCount){
                    maxCount = len;
                    ans = arr[i];
                }
                i = j;
            }
        }
        //for last sequence of elements
        int len = j-i;
        if(len>maxCount){
            maxCount = len;
            ans = arr[i];
        }

        return ans;
    }

    //Using Frequency Array TC=O(n)
    public static char getMaxOccuringChar2(String s) {
        int[] freq = new int[26]; //AS O(1)

        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);
            freq[ch-97]++;
        }

        int maxCount = Integer.MIN_VALUE;
        char ans = s.charAt(0);
        for(int i=0;i<freq.length;i++){
            if(freq[i]>maxCount){
                maxCount = freq[i];
                ans = (char)(i+97);
            }
        }

        return ans;
    }
}
