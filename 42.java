class Solution {
    public int trap(int[] height) {
        boolean start = false;
       for (int ind=0; ind<height.length; ind++){
            if(!start){
                if (height[ind] > 0){
                    start = true;
                }
            }else{
                
            }
       }
    }
}