package arrays_and_arraylists.twoD;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {
    public static void main(String[] args) {
        new PascalTriangle().generate(5);
    }

    public List<List<Integer>> generate(int n){
        List<List<Integer>> result = new ArrayList<>();

        for(int i=0; i<n; i++){
            result.add(new ArrayList<>());
            for(int j=0; j<=i; j++){
                if(j==0 || j==i) result.get(i).add(1);
                else result.get(i).add(result.get(i-1).get(j)+result.get(i-1).get(j-1));
            }
        }

        return result;
    }
}
