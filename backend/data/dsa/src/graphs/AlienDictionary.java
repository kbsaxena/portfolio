package graphs;

import java.util.*;

public class AlienDictionary {
    public String findOrder(String[] words) {
        // Using hashset to handle same edge appears multiple times
        ArrayList<HashSet<Integer>> adjList = new ArrayList<>();
        for(int i=0;i<26;i++) adjList.add(new HashSet<>());
        int[] in_degree = new int[26];
        boolean[] exists = new boolean[26];
        for(String word: words){
            for(int i=0;i<word.length();i++){
                char ch = word.charAt(i);
                exists[ch-97] = true;
            }
        }
        for(int i=0;i<words.length-1;i++){
            String s1 = words[i];
            String s2 = words[i+1];
            int j=0;
            while(j<s1.length() && j<s2.length()){
                char ch1 = s1.charAt(j);
                char ch2 = s2.charAt(j);
                if(ch1!=ch2) { //edge from ch1 to ch2
                    if (!adjList.get(ch1 - 97).contains(ch2 - 97)) {
                        adjList.get(ch1 - 97).add(ch2 - 97);
                        in_degree[ch2 - 97]++;
                    }
                    break;
                }
                j++;
            }
            if (j == Math.min(s1.length(), s2.length())) {
                if (s1.length() > s2.length()) return "";
            }
        }

        //Topological Sort
        StringBuilder sb = new StringBuilder();
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<26;i++){
            if(in_degree[i]==0 && exists[i]) q.add(i);
        }

        while(q.size()>0){
            int front = q.remove();
            sb.append((char)(front+97));
            for(int ele: adjList.get(front)){
                in_degree[ele]--;
                if(in_degree[ele]==0) q.add(ele);
            }
        }

        //For cycle check
        int count = 0;
        for (boolean e : exists) if (e) count++;

        return (sb.length() != count) ? "" : sb.toString();
    }
}
