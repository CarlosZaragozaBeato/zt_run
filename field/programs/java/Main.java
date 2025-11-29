import java.util.*;

public class Main {
  public static void main(String args[]) {

  }

  public int[] topKFrequent(int[] nums, int k) {
    // Step 1: Count Frequencies
    Map<Integer, Integer> frequencyMap = new HashMap<>();
    for (int num : nums) {
      frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
    }
    // Step 2: Convert to list and sort by frequency
    List<Integer> uniqueNums = new ArrayList<>(frequencyMap.keySet());
    uniqueNums.sort((a, b) -> frequencyMap.get(b) - frequencyMap.get(a));
    // Step 3: Get top k elements
    int[] result = new int[k];
    for (int i = 0; i < k; i++) {
      result[i] = uniqueNums.get(i);
    }
    return result;
  }

  public int[] topKFrequentBuckeSort(int[] nums, int k) {
    Map<Integer, Integer> frequencyMap = new HashMap<>();
    for (int num : nums) {
      frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
    }
    List<Integer>[] buckets = new List[nums.length + 1];
    for (int num : frequencyMap.keySet()) {
      int freq = frequencyMap.keySet();
      if (buckets[freq] == null) {
        buckets[freq] = new ArrayList<>();
      }
      buckets[freq].add(num);
    }
    int[] result = new int[k];
    int index = 0;
    for (int i = buckets.length - 1; i >= 0 && index < k; i--) {
      if (buckets[i] != null) {
        for (int num : buckets[i]) {
          result[index++] = num;
          if (index == k)
            break;
        }
      }
    }
    return result;
  }

  public int[] topKFrequentMinHeap(int[] nums, int k) {
    // Step 1: Count Frequencies
    Map<Integer, Integer> frequencyMap = new HashMap<>();
    for (int num : nums) {
      frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
    }
    // Step 2: Min heap of size k (sorted by frequency)
    PriorityQueue<Integer> heap = new PriorityQueue<>(
        (a, b) -> frequencyMap.get(a) - frequencyMap.get(b));
    // Step 3: Maintain heap of size k
    for (int num : frequencyMap.keySet()) {
      heap.offer(num);
      if (heap.size() > k) {
        heap.poll(); // Remove least frequent
      }
    }
    // Step 4: Extract result
    int[] result = new int[k];
    for (int i = 0; i < k; i++) {
      result[i] = heap.poll();
    }
    return result;
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
