import java.util.*;
public class Patter4{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        for (int i=0; i<num; i++){
            for (int j=1; j<=i && i!=0; j++){
                System.out.print(" ");
            }
            int itr = num - i;
            for (int j=(2*itr-1); j>=1; j--){
                System.out.print("*");
            }
            System.out.println();
        }
    }
}