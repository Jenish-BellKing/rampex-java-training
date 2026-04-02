class Solution {
    public String categorizeBox(int length, int width, int height, int mass) {
        if ((length >= 10000 || width >= 10000 || height >= 10000) || (long)length*width*height >= 1000000000){
            if (mass >= 100){
                return "Both";
            } else {
                return "Bulky";
            }
        }else{
            if (mass >= 100){
                return "Heavy";
            } else {
                return "Neither";
            }
        }
    }
}