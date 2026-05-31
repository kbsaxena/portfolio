package basics;

import java.util.Arrays;

class Student implements Comparable<Student>{
    String name;
    double cgpa;
    int rollNo;
    Student(String name, double cgpa,int rollNo){
        this.name = name;
        this.cgpa = cgpa;
        this.rollNo = rollNo;
    }

    void print(){
        System.out.println(name + " " + rollNo + " " + cgpa);
    }

    @Override
    public int compareTo(Student s) {
        //return this.rollNo-s.rollNo;
        //return Integer.compare(this.rollNo,s.rollNo);
        //return Double.compare(this.cgpa, s.cgpa);
        return this.name.compareTo(s.name);
    }
}

public class ComparableExample {
    public static void main(String[] args) {
        Student a = new Student("KB", 79.9,9);
        Student b = new Student("Rohith", 62.5,7);
        Student c = new Student("Meet", 79.8,3);
        Student d = new Student("Adarsh", 71.9,1);

        Student[] arr = {a,b,c,d};
        Arrays.sort(arr);
        for(Student s:arr) s.print();
    }
}
