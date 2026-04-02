class Solution {
    public String reverseWords(String s) {
        StringBuilder str = new StringBuilder();
        StringBuilder res = new StringBuilder();
        for (int i=0; i<s.length(); i++){
            if (s.charAt(i)!= ' ' && i != s.length()-1){
                str.append(s.charAt(i));
            }else{
                if (s.charAt(i) == ' '){
                    res.append(str.reverse());
                    res.append(" ");
                    str = new StringBuilder();
                }else if (i == s.length()-1){
                    str.append(s.charAt(i));
                    res.append(str.reverse());
                }
            }
        }
        return res.toString();
    }
}