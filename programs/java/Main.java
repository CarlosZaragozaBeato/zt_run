import java.util.*;

public class Main {
  public static void main(String args[]) {

  }

  public static List<List<String>> groupAnagrams(String[] strs) {
    // Step 1 : Create a HashMap to store groups
    Map<String, List<String>> anagramGroups = new HashMap<>();
    // Step 2 : Iterate through each string
    for (String word : strs) {
      // Convert string to char array and sort it
      char[] chars = word.toCharArray();
      Arrays.sort(chars);
      String sortedWord = new String(chars);
      // If key doesn't exist, create a new Array List
      if (!anagramGroups.containsKey(sortedWord)) {
        anagramGroups.put(sortedWord, new ArrayList<>());
      }
      // Add the original word to the group
      anagramGroups.get(sortedWord).add(word);
    }
    // Step 3: Return all the grouped lists
    return new ArrayList<>(anagramGroups.values());
  }

  public static List<List<String>> groupAnagramsAlternative(String[] strs) {
    Map<String, List<String>> anagramGroups = new HashMap<>();
    for (String word : strs) {
      char[] chars = word.toCharArray();
      Arrays.sort(chars);
      String sortedWord = new String(chars);
      anagramGroups.computeIfAbsent(sortedWord, k -> new ArrayList<>()).add(word);
    }
    return new ArrayList<>(anagramGroups.values());
  }

  public static int[] twoSums(int[] nums, int target) {
    HashMap<Integer, Integer> result = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      int diff = target - nums[i];
      if (result.containsKey(diff)) {
        return new int[] { result.get(diff), i };
      }
      result.put(nums[i], i);
    }
    return new int[] {};
  }

  public static boolean validAnagram(String s, String t) {
    HashMap<Character, Integer> resS = new HashMap<>();
    HashMap<Character, Integer> resT = new HashMap<>();
    if (s.length() != t.length()) {
      return false;
    }
    for (int i = 0; i < s.length(); i++) {
      resS.put(s.charAt(i), resS.getOrDefault(s.charAt(i), 0) + 1);
      resT.put(t.charAt(i), resT.getOrDefault(t.charAt(i), 0) + 1);
    }
    return resS.equals(resT);
  }

  public static boolean containsDuplicates(int[] nums) {
    HashSet<Integer> seen = new HashSet<Integer>();

    for (int value : nums) {
      if (seen.contains(value)) {
        return true;
      } else {
        seen.add(value);
      }
    }
    return false;
  }
}
