package basics;

public class SimpleInterest {
    public static void main(String[] args) {
        double principle = 1000;
        double rate = 5.5;
        double time = 5;

        double si = principle * rate * time / 100;

        System.out.println("Simple Interest is - " + si);
    }
}
