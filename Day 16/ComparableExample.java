import java.util.*;

class Student implements Comparable<Student> {
    int marks;
    String name;

    Student(int marks, String name) {
        this.marks = marks;
        this.name = name;
    }

    // Natural ordering: by marks
    @Override
    public int compareTo(Student s) {
        return Integer.compare(this.marks, s.marks);
    }

    @Override
    public String toString() {
        return name + " - " + marks;
    }
}

public class ComparableExample {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();

        list.add(new Student(85, "Jenish"));
        list.add(new Student(72, "Arun"));
        list.add(new Student(90, "Kiran"));

        Collections.sort(list); // uses compareTo()

        System.out.println("Sorted by marks (Comparable):");
        for (Student s : list) {
            System.out.println(s);
        }
    }
}