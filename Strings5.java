public class Strings5{
    public static void main(String[] args){
        String S = "Hello";
        int n = S.length();
        int sp = n-2;

        for (int ind = 0; ind < n; ind++){
            if (ind >= 0 && ind <= (n/2)-1){
                System.out.print(S.charAt(ind));
                for (int itr = 1; itr <= sp; itr++){
                    System.out.print(" ");
                }
                System.out.println(S.charAt((n - ind)-1));
                if (ind != (n/2)-1){
                    sp-=2;
                }else{
                    sp=1;
                }
               
            } else if (ind == n/2) {
                System.out.print(S.charAt(ind));
                System.out.println(S.charAt(ind));
            } else if (ind >= n/2 + 1 && ind <= n-1){
                System.out.print(S.charAt(ind));
                for (int itr = 1; itr <= sp; itr++){
                    System.out.print(" ");
                }
                System.out.println(S.charAt((n-ind)-1));
                sp+=2;
            }
            
        }
        
    }
}