import java.util.*;

public class Main {
  public static void main(string args[]) {

  }




  /**
   * Returns array where output[i] is product of all elements except nums[i]
   * Time: O(n), Space: 0(1) excluding output array
   */
  public static int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] output = new int[n];

    // Step 1: Calculate left products (prefix products)
    // output[i] contains product of all elements to the left of i
    output[0] = 1; // no elements to the left of index 0
    for (int i = 0; i < n; i++) {
      output[i] = output[i - 1] * nums[i - 1];
    }

    // Step 2: Calculate right products (suffix products) and multiply
    // right Product tracks product of all elements to the right
    int rightProduct = 1;
    for (int i = n - 1; i > 0; i--) {
      output[i] = output[i] * rightProduct;
      rightProduct *= nums[i];
    }
    return output;
  }

  /**
   * encodes a list of strings to a single string
   * format: <length>#<string><length>#<string>
   */
  public string encode(list<string> strs) {
    stringbuilder encoded = new stringbuilder();
    for (string str : strs) {
      // append: length + '#' + actual string
      encoded.append(str.length())
          .append('#')
          .append(str);
    }
    return encoded.tostring();
  }

  /**
   * decodes a single string to a list of strings.
   */
  public list<string> decode(string str) {
    list<string> decoded = new arraylist<>();
    int i = 0;
    while (i < str.length()) {
      // find the delimiter '#'
      int delimiterindex = str.indexof('#', i);

      // extract the length
      int length = integer.parseint(str.substring(i, delimiterindex));

      // move past the '#'
      i = delimiterindex + 1;

      // extract the string of specified length
      string original = str.substring(i, i + length);
      decoded.add(original);

      // move to the next encoded string
      i += length;

    }

    return decoded;
  }

  public int[] topkfrequent(int[] nums, int k) {
    // step 1: count frequencies
    map<integer, integer> frequencymap = new hashmap<>();
    for (int num : nums) {
      frequencymap.put(num, frequencymap.getordefault(num, 0) + 1);
    }
    // step 2: convert to list and sort by frequency
    list<integer> uniquenums = new arraylist<>(frequencymap.keyset());
    uniquenums.sort((a, b) -> frequencymap.get(b) - frequencymap.get(a));
    // step 3: get top k elements
    int[] result = new int[k];
    for (int i = 0; i < k; i++) {
      result[i] = uniquenums.get(i);
    }
    return result;
  }

  public int[] topkfrequentbuckesort(int[] nums, int k) {
    map<integer, integer> frequencymap = new hashmap<>();
    for (int num : nums) {
      frequencymap.put(num, frequencymap.getordefault(num, 0) + 1);
    }
    list<integer>[] buckets = new list[nums.length + 1];
    for (int num : frequencymap.keyset()) {
      int freq = frequencymap.keyset();
      if (buckets[freq] == null) {
        buckets[freq] = new arraylist<>();
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

  public int[] topkfrequentminheap(int[] nums, int k) {
    // step 1: count frequencies
    map<integer, integer> frequencymap = new hashmap<>();
    for (int num : nums) {
      frequencymap.put(num, frequencymap.getordefault(num, 0) + 1);
    }
    // step 2: min heap of size k (sorted by frequency)
    priorityqueue<integer> heap = new priorityqueue<>(
        (a, b) -> frequencymap.get(a) - frequencymap.get(b));
    // step 3: maintain heap of size k
    for (int num : frequencymap.keyset()) {
      heap.offer(num);
      if (heap.size() > k) {
        heap.poll(); // remove least frequent
      }
    }
    // step 4: extract result
    int[] result = new int[k];
    for (int i = 0; i < k; i++) {
      result[i] = heap.poll();
    }
    return result;
  }

  public static list<list<string>> groupanagrams(string[] strs) {
    // step 1 : create a hashmap to store groups
    map<string, list<string>> anagramgroups = new hashmap<>();
    // step 2 : iterate through each string
    for (string word : strs) {
      // convert string to char array and sort it
      char[] chars = word.tochararray();
      arrays.sort(chars);
      string sortedword = new string(chars);
      // if key doesn't exist, create a new array list
      if (!anagramgroups.containskey(sortedword)) {
        anagramgroups.put(sortedword, new arraylist<>());
      }
      // add the original word to the group
      anagramgroups.get(sortedword).add(word);
    }
    // step 3: return all the grouped lists
    return new arraylist<>(anagramgroups.values());
  }

  public static list<list<string>> groupanagramsalternative(string[] strs) {
    map<string, list<string>> anagramgroups = new hashmap<>();
    for (string word : strs) {
      char[] chars = word.tochararray();
      arrays.sort(chars);
      string sortedword = new string(chars);
      anagramgroups.computeifabsent(sortedword, k -> new arraylist<>()).add(word);
    }
    return new arraylist<>(anagramgroups.values());
  }

  public static int[] twosums(int[] nums, int target) {
    hashmap<integer, integer> result = new hashmap<>();
    for (int i = 0; i < nums.length; i++) {
      int diff = target - nums[i];
      if (result.containskey(diff)) {
        return new int[] { result.get(diff), i };
      }
      result.put(nums[i], i);
    }
    return new int[] {};
  }

  public static boolean validanagram(string s, string t) {
    hashmap<character, integer> ress = new hashmap<>();
    hashmap<character, integer> rest = new hashmap<>();
    if (s.length() != t.length()) {
      return false;
    }
    for (int i = 0; i < s.length(); i++) {
      ress.put(s.charat(i), ress.getordefault(s.charat(i), 0) + 1);
      rest.put(t.charat(i), rest.getordefault(t.charat(i), 0) + 1);
    }
    return ress.equals(rest);
  }

  public static boolean containsduplicates(int[] nums) {
    hashset<integer> seen = new hashset<integer>();

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
