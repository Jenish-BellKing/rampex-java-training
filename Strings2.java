
public class Strings2{
    public static StringBuilder even (String S){
        StringBuilder words = new StringBuilder();
        StringBuilder res = new StringBuilder();
        int count = 0;
        for (int ind = 0; ind < S.length(); ind++){
            if (S.charAt(ind) != ' ' && ind != S.length()-1){
                count++;
                words.append(S.charAt(ind));
            } else if (S.charAt(ind) == ' '  && ind != S.length()-1){
                if (count % 2 == 0){
                    res.append(words);
                    res.append(' ');
                    words.setLength(0);
                    count = 0;
                }else{
                    words.setLength(0);
                    count = 0;
                }
            } else if (ind == S.length()-1) {
                if (S.charAt(ind) != ' '){
                    count++;
                    words.append(S.charAt(ind));
                }
                if (count%2 == 0){
                res.append(words);
                }
            }
        }
        return res;
    }
    public static void main(String[] args){
        System.out.println(even("words with even length"));
    }
}