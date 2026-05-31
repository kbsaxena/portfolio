package basics;

public class AllCharacters {
    public static void main(String[] args) {
        //All Capital Letters
        /*
        for(int i=65; i <=90; i++){
            System.out.println((char)i + " -> " + i);
        }

        //All Small Letters
        for(int i=97; i <=122; i++){
            System.out.println((char)i + " -> " + i);
        }
        */

        for(int i=97; i <=122; i++){
            System.out.println((char)i + " " + (char)(i-32)) ;
        }
    }
}
