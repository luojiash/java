package test.bean;

public class Student extends Person {

    
    public static void main(String[] args) {
        Student[] students = new Student[10];
        Person[] persons=students;
        persons[0]=new Person();
    }
}
