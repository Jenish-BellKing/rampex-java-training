import java.util.*;
public class Pattern5{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int dec = num;
        for (int i=1; i<=(2*num-1); i++){
            if (i<=num){
                for (int j=1; j<=i; j++){
                    System.out.print("*");
                }
            } else {
                for (int j=1; j<dec; j++){
                    System.out.print("*");
                }
                dec--;
            }
            System.out.println();
        }
    }
}