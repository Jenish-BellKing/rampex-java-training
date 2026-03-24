class Solution {
    public String maximumOddBinaryNumber(String s) {
        StringBuilder res =  new StringBuilder("1");
        int one = 0;
        for (int i=0; i<s.length(); i++){
            if (s.charAt(i) == '0'){
                res.append('0');
            }else{
                one++;
            }
        }
        for (int i=1; i<one; i++){
            res.append(1);
        }
        res = res.reverse();
        String result = res.toString();
        return result;
    }
}