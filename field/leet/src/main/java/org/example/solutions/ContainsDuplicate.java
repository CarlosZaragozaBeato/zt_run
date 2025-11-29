package org.example.solutions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ContainsDuplicate {
    private int[] nums;

    public ContainsDuplicate(int[] nums){
        this.nums = nums;
    }

    public int[] getNums(){
        return this.nums;
    }

    /**
     * Brute force
     * @return
     */
    public boolean solution1(){
        int n = this.nums.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n ; j++) {
                if (this.nums[i] == this.nums[j]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean solution2(){
        Arrays.sort(this.nums);
        int n = this.nums.length;
        for (int i = 1; i < n; i++) {
            if (this.nums[i] == this.nums[i - 1]){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean solution3(){
        HashSet<Integer> seen = new HashSet<>();
        for (int num: this.nums){
            if (seen.contains(num)){
                return true;
            }
            seen.add(num);
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean solution4(){
        HashMap<Integer, Integer> seen = new HashMap<>();
        for (int num: this.nums){
            if (seen.containsKey(num) && seen.get(num) >= 1){
                return true;
            }
            seen.put(num, seen.getOrDefault(num, 0) + 1);
        }
        return false;
    }

}
