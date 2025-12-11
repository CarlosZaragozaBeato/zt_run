import java.util.*;

class MinStack {

  private Stack<Integer> stack;
  private Stack<Integer> minStack;

  public MinStack() {
    stack = new Stack<>();
    minStack = new Stack<>();
  }

  public void push(int val) {
    stack.push(val);

    if (minStack.isEmpty() || val <= minStack.peek()) {
      minStack.push(val);
    }
  }

  public void pop() {
    int removed = stack.pop();

    if (removed == minStack.peek()) {
      minStack.pop();
    }
  }

  public int top() {
    return stack.peek();
  }

  public int getMin() {
    return minStack.peek();
  }
}

public class Main {
  public static void main(string args[]) {

  }

  public static int carFleet(int target, int[] position, int[] speed){
    int n = position.length;
    int[][] cars = new int[n][2];
    for(int i = 0; i < n; i++){
      cars[i][0] = position[i];
      cars[i][1] = speed[i];
    }
    Arrays.sort(cars, (a, b) -> b[0] - a[0]);

    int fleets = 0;
    double slowestTime = 0;

    for (int i = 0; i < n; i++){
      double time = (double)(target - cars[i][0] / cars[i][1]);

      if (time > slowestTime){
        fleets++;
        slowestTime = time;
      }
    }
    return fleets;
  }

  public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] result = new int[n];
    Stack<Integer> stack = new Stack<>();

    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
        int prevIndex = stack.pop();
        result[prevIndex] = i - prevIndex;
      }
      stack.push(i);
    }
    return result;
  }

  public static int evalRPN(String[] tokens) {
    Stack<Integer> stack = new Stack<>();

    for (String token : tokens) {
      if (token.equals("+") || token.equals("-") ||
          token.equals("*") || token.equals("/")) {
        int b = stack.pop();
        int a = stack.pop();
        int result = 0;

        if (token.equals("+")) {
          result = a + b;
        } else if (token.equals("-")) {
          result = a - b;
        } else if (token.equals("*")) {
          result = a * b;
        } else { // division
          result = a / b; // truncates toward zero automatically in Java
        }
        stack.push(result);
      } else {
        stack.push(Integer.parseInt(token));
      }
    }
    return stack.pop();
  }

  public static boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();

    for (char ch : s.toCharArray()) {
      if (ch == '(' || ch == '{' || ch == '[') {
        stack.push(ch);
      } else {
        if (stack.isEmpty())
          return false;
        char top = stack.pop();

        if ((ch == ')' && top != '(') ||
            (ch == '}' && top != '{') ||
            (ch == ']' && top != '[')) {
          return false;
        }
      }
    }
    return stack.isEmpty();
  }

  public static int[] maxSlidingWindow(int[] nums, int k) {
    Deque<Integer> dq = new ArrayDeque<>();
    int n = nums.length;
    int[] result = new int[n - k + 1];
    int ri = 0;

    for (int i = 0; i < n; i++) {
      if (!dq.isEmpty() && dq.peekFirst() == i - k) {
        dq.pollFirst();
      }

      while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
        dq.pollLast();
      }

      dq.offerLast(i);

      if (i >= k - 1) {
        result[ri++] = nums[dq.peekFirst()];
      }
    }
    return result;
  }

  public String minWindow(String s, String t) {
    if (t.length() > s.length())
      return "";

    Map<Character, Integer> tMap = new HashMap<>();
    for (char c : t.toCharArray()) {
      tMap.put(c, tMap.getOrDefault(c, 0) + 1);
    }
    Map<Character, Integer> window = new HashMap<>();

    int have = 0;
    int need = tMap.size();

    int left = 0;
    int minLen = Integer.MAX_VALUE;
    int start = 0;

    for (int i = 0; right < s.length(); right++) {
      char c = s.charAt(right);
      window.put(c, window.getOrDefault(c, 0) + 1);

      if (tMap.containsKey(c) &&
          window.get(c).intValue() == tMap.get(c).intValue()) {
        have++;
      }

      while (have == need) {
        if ((right - left + 1) < minLen) {
          minLen = right - left + 1;
          start = left;
        }
        char remove = s.charAt(left);
        window.put(remove, window.get(remove) - 1);
        if (tMap.containsKey(remove) &&
            window.get(remove) < tMap.get(remove)) {
          have--;
        }
        left++;
      }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);

  }

  public static boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length())
      return false;

    int[] freq1 = new int[26];
    int[] freq2 = new int[26];

    for (char c : s1.toCharArray()) {
      freq1[c - 'a']++;
    }
    int window = s1.length();

    for (int i = 0; i < window; i++) {
      freq2[s2.charAt(i) - 'a']++;
    }
    if (matches(freq1, freq2))
      return true;

    for (int i = window; i < s2.length(); i++) {
      freq2[s2.charAt(i) - 'a']++;
      freq2[s2.charAt(i - window) - 'a']--;

      if (matches(freq1, freq2))
        return true;
    }
    return false;
  }

  public int characterReplacement(String s, int k) {
    int[] freq = new int[26];
    int left = 0;
    int maxCount = 0;
    int maxLength = 0;

    for (int right = 0; right < s.length(); right++) {
      int index = s.charAt(right) - 'A';
      freq[index]++;

      maxCount = Math.max(maxCount, freq[index]);

      while ((right - left + 1) - maxCount > k) {
        freq[s.charAt(left) - 'A']--;
        left++;
      }
      maxLength = Math.max(maxLength, right - left + 1);
    }
    return maxLength;
  }

  public int lengthOfLongestSubstring(String s) {
    HashSet<Character> set = new HashSet<>();
    int left = 0;
    int maxLength = 0;

    for (int right = 0; right < s.length(); right++) {
      while (set.contains(s.charAt(right))) {
        set.remove(s.charAt(left));
        left++;
      }
      set.add(s.charAt(right));
      maxLength = Math.max(maxLength, right - left + 1);
    }
    return maxLength;
  }

  public static int maxProfit(int[] prices) {
    if (prices == null || prices.length == 0)
      return 0;

    int minPrice = prices[0];
    int maxProfit = 0;

    for (int i = 0; i < prices.length; i++) {
      int profit = prices[i] - minPrice;
      maxProfit = Math.max(maxProfit, profit);
      minPrice = Math.min(minPrice, prices[i]);
    }
    return maxProfit;
  }

  public static int trap(int[] height) {
    if (height == null || height.length == 0)
      return 0;

    int left = 0, right = height.length - 1;
    int leftMax = 0, rightMax = 0;
    int water = 0;

    while (left < right) {
      if (height[left] < height[right]) {

        if (height[left] >= leftMax) {
          leftMax = height[left];
        } else {
          water += leftMax - height[left];
        }
        left++;
      } else {
        if (height[right] >= rightMax) {
          rightMax = height[right];
        } else {
          water += rightMax - height[righ];
        }
        right--;
      }
    }
    return water;
  }

  public static int maxArea(int[] height) {
    int left = 0;
    int right = height.length - 1;
    int maxWater = 0;

    while (left < right) {
      int width = right - left;
      int currentHeight = Math.min(height[left], height[right]);
      int currentArea = width * currentHeight;

      maxWater = Math.max(maxWater, currentArea);

      if (height[left] < height[right]) {
        left++;
      } else {
        right--;
      }
    }
    return maxWater;
  }

  public static List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);

    for (int i = 0; i < nums.length - 2; i++) {
      if (i > 0 && nums[i] == nums[i - 1])
        continue;

      int left = i + 1;
      int right = nums.length - 1;
      int target = -nums[i];
      if (sum == target) {
        while (left < right) {
          result.add(Arrays.asList(nums[i], nums[left], nums[right]));

          while (left < right && nums[left] == nums[left + 1])
            left++;
          while (left < right && nums[right] == nums[right - 1])
            right--;

          left++;
          right--;
        }
      } else if (sum < target) {
        left++;
      } else {
        right--;
      }
    }
  }

  public int[] twoSumV2(int[] numbers, int target) {
    // Two pointer: left at start, right at end //
    int left = 0;
    int right = numbers.length - 1;

    while (left < right) {
      int currentSum = numbers[left] + numbers[right];

      if (currentSum == target) {
        /** Found the answer! Return 1-indexed positions **/
        return new int[] { left + 1, right + 1 };
      } else if (currentSum < target) {
        left++;
      } else {
        right--;
      }
    }
    /* Should never reach here (problem guarantees a solution) */
    return new int[] { -1, -1 };
  }

  public static boolean isPalindrome(String s) {
    // Two pointers: left start at beginning, right at end
    int left = 0;
    int right = s.length() - 1;

    while (left < right) {
      // Skip non-alphanumeric characters from left
      while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
        left++;
      }

      // Skip non-alphanumeric characters from right
      while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
        right--;
      }
      // Compare characters (case-insensitive)
      if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
        return false;
      }

      // Move both pointers inward
      left++;
      right--;
    }
    return true;
  }

  public static int longestConsecutive(int[] nums) {
    // Edge case: empty array
    if (nums.length == 0) {
      return 0;
    }

    // Step 1: Put all numbers in a HashSet for 0(1) lookup
    Set<Integer> numSet = new HashSet<>();
    for (int num : nums) {
      numSet.add(num);
    }
    int maxLength = 0;

    // Step 2: Iterate through each number
    for (int num : numSet) {
      // Step 3: Only start counting if this is the beginning of a sequence
      // (i.e., num-1 is NOT in the set)
      if (!numSet.contains(num - 1)) {
        int currentNum = num;
        int currentLength = 1;

        // Step 4: Count forward as long as consecutive numbers exits
        while (numSet.contains(currentNum + 1)) {
          currentLength++;
          currentLength++;
        }

        // Step 5: Update the maximum length found
        maxLength = Math.max(maxLength, currentLength);
      }

    }

    return maxLength;
  }

  public boolean isValidSudoku(char[][] board) {
    // Use sets to track seen digits
    Set<String> seen = new HashSet<>();

    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        char current = board[i][j];

        if (current != '.') {
          // Create unique identifiers for row, column, and box
          String row = current + " in row " + i;
          String col = current + " in col " + j;
          String box = current + " in box " + (i / 3) + "-" + (j / 3);

          // If any identifier already exists, we have a duplicate
          if (!seen.add(row) || !seen.add(col) || !seen.add(box)) {
            return false;
          }
        }
      }
    }

    return true;
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
