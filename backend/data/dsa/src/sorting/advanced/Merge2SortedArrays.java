package sorting.advanced;

public class Merge2SortedArrays {
    public static void main(String[] args) {
        int[] a = {1,5,10,15,20};
        int[] b = {2,6,12,19,45,60};
        int[] c = new int[a.length + b.length];

        merge1(a,b,c);
        print(c);

        System.out.println();
        a = new int[]{1, 2, 3, 0, 0, 0};
        b = new int[]{2,5,6};

        merge(a, 3, b, 3);
        print(a);
    }

    private static void print(int[] c) {
        for(int ele: c){
            System.out.print(ele + " ");
        }
    }

    private static void merge(int[] a, int[] b, int[] c) {
        int i=0, j=0, k=0;
        while(i<a.length && j<b.length){
            if(a[i] <= b[j]) c[k++] = a[i++];
            else c[k++] = b[j++];
        }

        while(i<a.length) c[k++] = a[i++];
        while(j<b.length) c[k++] = b[j++];
    }

    // Here i and j start from the end
    private static void merge1(int[] a, int[] b, int[] c) {
        int i=a.length-1, j=b.length-1, k=c.length-1;
        while(i>=0 && j>=0){
            if(a[i] > b[j]) c[k--] = a[i--];
            else c[k--] = b[j--];
        }

        while(i>=0) c[k--] = a[i--];
        while(j>=0) c[k--] = b[j--];
    }

    //a = new int[]{1, 2, 3, 0, 0, 0}; b = new int[]{2,5,6}
    public static void merge(int[] a, int m, int[] b, int n) {
        int i=m-1, j=n-1, k=(m+n)-1;
        while(i>=0 && j>=0){
            if(a[i] > b[j]) a[k--] = a[i--];
            else a[k--] = b[j--];
        }

        while(i>=0) a[k--] = a[i--];
        while(j>=0) a[k--] = b[j--];
    }
}
