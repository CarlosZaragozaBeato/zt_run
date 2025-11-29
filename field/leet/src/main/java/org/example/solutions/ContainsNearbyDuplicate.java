package org.example.solutions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContainsNearbyDuplicate {

    private int[] nums;
    private int k;


    public ContainsNearbyDuplicate(int[] nums, int k){
        this.nums = nums;
        this.k = k;
    }

    public boolean ownSolution(){
        HashMap<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < this.nums.length; i++) {
            int value = this.nums[i];
            if (seen.containsKey(value)) {
                if (Math.abs((seen.get(value) - i)) < k){
                    return true;
                }
            }
            seen.put(this.nums[i], i);
        }
        return false;
    }

    public boolean solution1(){
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < this.nums.length; i++) {
            int val = this.nums[i];

            if (seen.containsKey(val) && i - seen.get(val) <= k){
                return true;
            }
            seen.put(val, i);
        }
        return false;
    }

    public boolean solution2(){
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < this.nums.length; i++) {
            if (i > k ){
                seen.remove(nums[i - k - 1]);
            }
            if (seen.contains(nums[i])){
                return true;
            }
            seen.add(nums[i]);
        }
        return false;
    }
}
