package stacks;

import java.util.Stack;

public class CelebrityProblem {
    //Solution TC - O(n square)
    public int celebrity1(int mat[][]) {
        // mat[i][j] = 1 means i knows j
        // mat[j][i] = 0 means j does not know i

        for (int i = 0; i < mat.length; i++) {
            boolean flag = true; //true means i is a celeb
            for (int j = 0; j < mat.length; j++) {
                if (j == i) continue;
                if (mat[i][j] == 1 || mat[j][i] == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) return i;
        }
        return -1;
    }

    //Solution TC - O(n)
    public int celebrity(int mat[][]) {
        // mat[i][j] = 1 means i knows j
        // mat[j][i] = 0 means j does not know i

        Stack<Integer> st = new Stack<>();
        for(int i=0;i<mat.length;i++){
            st.push(i);
        }

        while(st.size()>1){
            int a = st.pop();
            int b = st.pop();
            //Check for a to be Celeb
            if(mat[a][b] == 0 && mat[b][a] == 1) st.push(a);

            //Check for b to be Celeb
            if(mat[b][a] == 0 && mat[a][b] == 1) st.push(b);
        }

        if(st.size() == 0) return -1;

        int celeb = st.pop();
        //Check Celeb
        for(int i=0;i<mat.length;i++){
            if(i==celeb) continue;
            if(mat[celeb][i] == 1 || mat[i][celeb] == 0) return -1;
        }
        return celeb;
    }
}
