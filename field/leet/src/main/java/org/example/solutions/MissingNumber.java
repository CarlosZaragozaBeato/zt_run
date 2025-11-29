package org.example.solutions;

import java.util.Arrays;

public class MissingNumber {


    private int[] nums;

    public MissingNumber(int[] nums) {
        this.nums = nums;
    }


    public int solution1(){
        int result = 0;
        Arrays.sort(this.nums);
        for (int num : this.nums) {
            if (result == num) {
                result++;
            }
        }
        return result;
    }

    public int solution2(){
        int n = nums.length;
        int[] v = new int[n+1];
        Arrays.fill(v, -1);
        for (int num : nums) {
            v[num] = num;
        }
        for (int i = 0; i < v.length; i++) {
            if(v[i] == -1) return i;
        }
        return 0;
    }
}
