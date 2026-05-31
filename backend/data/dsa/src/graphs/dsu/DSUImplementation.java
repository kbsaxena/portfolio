package graphs.dsu;

import java.util.Arrays;

class DSU{
    int[] parent;
    int[] size;
    DSU(int vertices){
        parent = new int[vertices];
        for(int i=0;i<vertices;i++) make(i);
        size = new int[vertices];
        Arrays.fill(size, 1);
    }

    void make(int n){
        parent[n] = n;
    }

    int findGroupLeader(int n){
        if(parent[n] == n) return n;
        int leader = findGroupLeader(parent[n]);
        parent[n] = leader; //Path Compression
        return leader;
        //Below is also valid, first it assigns value to parent[n] then returns
        //return parent[n] = findGroupLeader(parent[n]);
    }

    //Normal Union
    /*void union(int a, int b){
        parent[findGroupLeader(b)] = findGroupLeader(a);
    }*/

    //union by size
    void union(int a, int b){
        a = findGroupLeader(a);
        b = findGroupLeader(b);
        if(size[a]>size[b]){
            parent[b] = a; //Updating b's parent as a
            size[a] += size[b]; //Increasing leader size
        } else{
            parent[a] = b;
            size[b] += size[a];
        }
    }
}

public class DSUImplementation {
    public static void main(String[] args) {
        DSU dsu = new DSU(6);

        System.out.println(Arrays.toString(dsu.parent));
        dsu.union(0,1);
        dsu.union(2,3);
        dsu.union(0,2);
        dsu.union(4,5);
        dsu.union(2,5);
        System.out.println(Arrays.toString(dsu.parent));
        System.out.println("group Leader of 5 is - " + dsu.findGroupLeader(5));

    }
}
