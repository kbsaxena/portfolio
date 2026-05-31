package bitmanipulation;

public class BitwiseOperators {
    public static void main(String[] args) {
        System.out.println(49&29); //Bitwise AND => 17
        System.out.println(49|29); //Bitwise OR => 61
        System.out.println(49^29); //Bitwise XOR => 44
        System.out.println(18^18); //0
        System.out.println(45^0); //45
        System.out.println(8>>2); //Right shift 2
        System.out.println(8<<2); //Left shift 32
        System.out.println(1<<4); //equal to 2 to power n => 16
    }
}
