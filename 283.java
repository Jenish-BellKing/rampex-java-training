class Solution {
    public void moveZeroes(int[] nums) {
        int begin = 0;
        for (int ind = 0; ind < nums.length; ind++){
            if (nums[ind] != 0){
                int temp = nums[ind];
                nums[ind] = nums[begin];
                nums[begin] = temp;
                begin++;
            }
        }
    }
}