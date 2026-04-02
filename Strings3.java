public class Strings3{
    public static void main(String[] args){
        String S = " abcbad";
        int[] freq = new int[26];
        for (int ind = 0; ind < S.length()-1; ind++){
            if (S.charAt(ind) != ' '){
                freq[S.charAt(ind) - 97]++;
            }
        }
        for (int ind = 0; ind < S.length()-1; ind++){
            if (S.charAt(ind) != ' ')
            if (freq[S.charAt(ind) - 97] == 1){
                char res = S.charAt(ind);
                System.out.println(res);
                break;
            }
        }
    
    }
}