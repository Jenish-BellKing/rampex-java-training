
public class Strings4{
    public static void main(String[] args){
        String word = "aaaaaaaaaaaaaaaaaaaaaaaaaccbbbbbbbbbbbbb";
        int count = 0;
        for (int ind = 0; ind < word.length(); ind++){
            if (ind != word.length()-1){
                count++;
                if(word.charAt(ind+1) != word.charAt(ind)){
                    for (int i=1; i<=(count/9); i++){
                        System.out.print(9);
                        System.out.print(word.charAt(ind));
                    }
                    if (count%9 != 0){
                        System.out.print(count%9);
                        System.out.print(word.charAt(ind));
                    }
                    /*System.out.print(count);
                    System.out.print(word.charAt(ind));
                    count = 0;*/
                    count=0;
                }
            }else{
                count++;
                for (int i=1; i<=(count/9); i++){
                    System.out.print(9);
                    System.out.print(word.charAt(ind));
                }
                if (count%9 != 0){
                    System.out.print(count%9);
                    System.out.print(word.charAt(ind));
                }    
                /*System.out.print(count+1);
                System.out.print(word.charAt(ind));*/
            }
        }
    }
}