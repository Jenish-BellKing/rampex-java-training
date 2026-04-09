package day_12;
//Autoboxing:int-Integer, double-Double, char-Character, boolean-Boolean
//unboxing:Integer-int, Double-double, Character-char, Boolean-boolean
public class wrapper {
    public int test1(String s)
    {
        return Integer.valueOf(s);
    }

public static void main (String[] args) {
    Integer a=Integer.valueOf("123");
    Integer b=Integer.parseInt("0987");
    System.out.println(a);
    System.out.println(b);

}
}
