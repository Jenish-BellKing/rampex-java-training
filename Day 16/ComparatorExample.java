import java.util.*;
class Student {
    int marks;
    String name;
    Student(int marks, String name){
        this.marks = marks;
        this.name = name;
    }
    @Override
    public String toString(){
        return name + " - " + marks;
    }
}
// Comparator for marks
class SortByMarks implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        return Integer.compare(s1.marks, s2.marks);
    }
}

// Comparator for name
class SortByName implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        return s1.name.compareTo(s2.name);
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();

        list.add(new Student(85, "Jenish"));
        list.add(new Student(72, "Arun"));
        list.add(new Student(90, "Kiran"));

        // Sort by marks
        Collections.sort(list, new SortByMarks());
        System.out.println("Sorted by marks (Comparator):");
        for (Student s : list) {
            System.out.println(s);
        }

        // Sort by name
        Collections.sort(list, new SortByName());
        System.out.println("\nSorted by name (Comparator):");
        for (Student s : list) {
            System.out.println(s);
        }
    }
}