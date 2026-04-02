import java.util.*;
public class Task2{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Number 1: ");
        int num1 = sc.nextInt();
        System.out.print("Enter Number 2: ");
        int num2 = sc.nextInt();
        System.out.print("Enter Number 3: ");
        int num3 = sc.nextInt();
        if (num1 > num2 && num1 > num3){
            System.out.print(num1 + " is the greatest number.");
        }else if (num2 > num3){
            System.out.print(num2 + " is the greatest number.");
        }else {
            System.out.print(num3 + " is the greatest number.");
        }
    }
}