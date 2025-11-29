package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.example.solutions.ContainsDuplicate;
import org.example.solutions.ContainsNearbyDuplicate;
import org.example.solutions.MissingNumber;

public class Main {

    static void main() {
        // Solutions
        int[] nums = {3,0,1};
        MissingNumber currentSolution = new MissingNumber(nums);


        // Check results
        HashMap<String, Integer> results = new HashMap<String, Integer>();
        // String solution = String.valueOf(currentSolution.solution1());
        String solution2 = String.valueOf(currentSolution.solution2());


        //results.put("solution3", currentSolution.solution1());
        //results.put("solution4", currentSolution.solution1());
        //for (Map.Entry<String, Integer> entry : results.entrySet()) {
        //    String key = entry.getKey();
        //    int value = entry.getValue();
        //    System.out.println("KEY: " + key + " VALUE: " + value);
        //}
    }
}
