from typing import List
from collections import deque, defaultdict, Counter
import heapq


def main():
    pass

    def isValidParentheses(s: str) -> bool:
        stack = []

        for ch in s:
            if ch in "([{":
                stack.append(ch)
            else:
                if not stack:
                    return False
                top = stack.pop()
                if (
                    (ch == ")" and top != "(")
                    or (ch == "}" and top != "{")
                    or (ch == "]" and top != "[")
                ):
                    return False
        return not stack


def maxSlidingWindow(nums, k):
    dq = deque()
    result = []

    for i in range(len(nums)):
        if dq and dq[0] == i - k:
            dq.popleft()

        while dq and nums[dq[-1]] < nums[i]:
            dq.pop()

        dq.append(i)

        if i >= k - 1:
            result.append(nums[dq[0]])

    return result


def minWindow(s: str, t: str) -> str:
    if t == "" or len(t) > len(s):
        return ""

    t_freq = Counter(t)
    window = {}

    have = 0
    need = len(t_freq)

    res = [-1, -1]
    res_len = float("inf")

    left = 0

    for right in range(len(s)):
        c = s[right]
        window[c] = window.get(c, 0) + 1

        if c in t_freq and window[c] == t_freq[c]:
            have += 1

        while have == need:
            if (right - left + 1) < res_len:
                res = [left, right]
                res_len = right - left + 1

            window[s[left]] -= 1
            if s[left] in t_freq and window[s[left]] < t_freq[s[left]]:
                have -= 1
            left += 1
    l, r = res
    return s[l : r + 1] if res_len != float("inf") else ""


def checkInclusion(s1: str, s2: str) -> bool:
    if len(s1) > len(s2):
        return False

    freq1 = [0] * 26
    freq2 = [0] * 26

    for ch in s1:
        freq1[ord(ch) - ord("a")] += 1

    window = len(s1)

    for i in range(window):
        freq2[ord(s2[i]) - ord("a")] += 1

    if freq1 == freq2:
        return True

    for i in range(window, len(s2)):
        freq2[ord(s2[i]) - ord("a")] += 1
        freq2[ord(s2[i - window]) - ord("a")] -= 1

        if freq1 == freq2:
            return True

    return False


def character_replacement(s, k):
    freq = [0] * 26
    left = 0
    max_count = 0
    max_length = 0

    for right in range(len(s)):
        index = ord(s[right]) - ord("A")
        freq[index] += 1

        max_count = max(max_count, freq[index])
        while (right - left + 1) - max_count > k:
            freq[ord(s[left]) - ord("A")] -= 1
            left += 1
        max_length = max(max_length, right - left + 1)
    return max_length


def length_of_longest_substring(s: str) -> int:
    char_set = set()
    left = 0
    max_length = 0

    for right in range(len(s)):
        while s[right] in char_set:
            char_set.remove(s[left])
            left += 1
        char_set.add(s[right])
        max_length = max(max_length, right - left + 1)
    return max_length


def maxProfit(prices):
    if not prices:
        return 0

    min_price = prices[0]
    max_profit = 0
    for price in prices[1:]:
        profit = price - min_price
        max_profit = max(max_profit, profit)

        min_price = min(min_price, price)
    return max_profit


def maxProfitAlternative(prices):
    min_price = float("inf")
    max_profit = 0
    for price in prices:
        min_price = min(min_price, price)
        max_profit = max(max_profit, price - min_price)
    return max_profit


def trap(height):
    if not height:
        return 0

    left, right = 0, len(height) - 1
    left_max, right_max = 0, 0
    water = 0
    while left < right:
        if height[left] < height[right]:
            if height[left] >= left_max:
                left_max = height[left]
            else:
                water += left_max - height[left]
            left += 1
        else:
            if height[right] >= right_max:
                right_max = height[right]
            else:
                water += right_max - height[right]
            right -= 1
    return water


def trapAlternative(height):
    if not height:
        return 0

    n = len(height)
    left_max = [0] * n
    right_max = [0] * n

    left_max[0] = height[0]
    for i in range(1, n):
        left_max[i] = max(left_max[i - 1], height[i])

    right_max[n - 1] = height[n - 1]
    for i in range(n - 2, -1, -1):
        right_max[i] = max(right_max[i + 1], height[i])

    water = 0
    for i in range(n):
        water += min(left_max[i], right_max[i]) - height[i]
    return water


def maxAreaAlternative(height: List[int]) -> int:
    left, right = 0, len(height) - 1
    max_water = 0

    while left < right:
        max_water = max(max_water, min(height[left], height[right]) * (right - left))
        if height[left] < height[right]:
            left -= 1
        else:
            right += 1
    return max_water


def maxArea(height: List[int]) -> int:
    left = 0
    right = len(height) - 1
    max_water = 0

    while left < right:
        width = right - left
        current_height = min(height[left], height[right])
        current_area = width * current_height

        max_water = max(max_water, current_area)

        if height[left] < height[right]:
            left += 1
        else:
            right -= 1

    return max_water


def threeSum(nums: List[int]) -> List[List[int]]:
    result = []
    nums.sort()

    for i in range(len(nums) - 2):
        if i > 0 and nums[i] == nums[i - 1]:
            continue

        left = i + 1
        right = len(nums) - 1
        target = -nums[i]

        while left < right:
            current_sum = nums[left] + nums[right]
            if current_sum == target:
                result.append([nums[i], nums[left], nums[right]])

                while left < right and nums[left] == nums[left + 1]:
                    left += 1

                while left < right and nums[right] == nums[right - 1]:
                    right -= 1

                left += 1
                right -= 1
            elif current_sum < target:
                left += 1
            else:
                right -= 1
    return result


def twoSumsV2(numbers: List[int], target: int) -> List[int]:
    # Two pointers: left at start, right at end
    left = 0
    right = len(numbers) - 1

    while left < right:
        current_sum = numbers[left] + numbers[right]

        if current_sum == target:
            # Fount the answer! Return 1-indexed position
            return [left + 1, right + 1]
        elif current_sum < target:
            # Sum too small, need larger number
            left += 1
        else:
            # Sum too large, need smaller number
            right -= 1
    # Should never reach here (problem guarantees a solution)
    return [-1, -1]


def isPalindrome(s: str) -> bool:
    # Two pointers: left starts at beginning, right at end
    left = 0
    right = len(s) - 1

    while left < right:
        # Skip non-alphanumeric characters from left
        while left < right and not s[left].isalnum():
            left += 1

        # Skip non-alphanumeric characters from right
        while left < right and not s[right].isalnum():
            right -= 1

        # Compare characters (case - insensitive)
        if s[left].lower() != s[right].lower():
            return False
        # Move both pointers inwards
        left += 1
        right -= 1
    return True


def longestConsecutive(nums: List[int]) -> int:
    # Edge case: empty array
    if not nums:
        return 0

    # Step 1: Put all the numbers in a set for 0(1) lookup
    num_set = set(nums)
    max_length = 0

    # Step 2: Iterate through each number
    for num in num_set:
        # Step 3: Only start counting if this is the beginning of a sequence
        # (i.e., num-1 is NOT in the set)
        if num - 1 not in num_set:
            current_num = num
            current_length = 1

            # Step 4: Count forward as long consecutive numbers exists
            while current_num + 1 in num_set:
                current_num += 1
                current_length = 1

                # Step 5: Update the maximum length found
                max_length = max(max_length, current_length)
    return max_length


def isValidSudoku(board: List[List[str]]) -> bool:
    # Use sets to track seen digits
    seen = set()
    for i in range(9):
        for j in range(9):
            current = board[i][j]
            if current != ".":
                # Create unique identifiers for row,column and box
                row_check = f"{current} in row {j}"
                col_check = f"{current} in col {j}"
                box_check = f"{current} in box {i // 3}-{j // 3}"
                # If any identifiers already exists, we have a duplicate
                if row_check in seen or col_check in seen or box_check in seen:
                    return False
                seen.add(row_check)
                seen.add(col_check)
                seen.add(box_check)
    return True


def product_except_self(nums):
    """
    Returns array where output[i] is product of all elements except nums[i]
    Time: 0(n), Space: 0(1) excluding output array
    Args:
        nums: List of integers
    Returns:
        List of integers representing products
    """
    n = len(nums)
    output = [1] * n

    # Step 1: Calculate left products (prefix products)
    # Output[i] contains product of all elements to the left of i
    left_product = 1
    for i in range(n):
        output[i] = left_product
        left_product *= nums[i]
    # Step 2: Calculate right products (suffix products) and multiply
    # right_product tracks product of all element to the right
    right_product = 1
    for i in range(n - 1, -1, -1):
        output[i] *= right_product
        right_product *= nums[i]
    return output


def product_except_self_v2(nums):
    """Alternative implementation with explicit left/right arrays for clarity"""
    n = len(nums)
    output = [1] * n
    # Build left products
    for i in range(1, n):
        output[i] = output[i - 1] * nums[i - 1]
    # Build right products and multiply
    right_product = 1
    for i in range(n - 1, -1, -1):
        output[i] = output[i] * right_product
        right_product *= nums[i]
    return output


def encode(strs: List[str]) -> str:
    """
    Encodes a list of strings to a single string.
    Format: <length>#<string><length>#<string>...
    """
    encoded = ""
    for s in strs:
        # Append: length + '#' + actual string
        encoded += str(len(s)) + "#" + s
    return encoded


def decode(s: str) -> List[str]:
    """
    Decodes a single string to a list of strings
    """
    decoded = []
    i = 0
    while i < len(s):
        # Find the delimiter '#'
        delimiter_index = s.index("#", i)
        # Extract the length
        length = int(s[i:delimiter_index])
        # Move past the '#'
        i = delimiter_index + 1
        # Extract the string of specified length
        original = s[i : i + length]
        decoded.append(original)

        # Move to the next encoded string
        i += length
    return decoded


def tok_k_frequent(nums, k):
    # Step 1: Count frequencies
    frequency_map = {}
    for num in nums:
        frequency_map[num] = frequency_map.get(num, 0) + 1
    # Step 2: Sort by frequency (descending)
    sorted_nums = sorted(
        frequency_map.keys(), key=lambda x: frequency_map[x], reverse=True
    )
    # Step 3: Return top k
    return sorted_nums[:k]


def top_k_frequent_bucket_sort(nums, k):
    frequency_map = {}
    for num in nums:
        frequency_map[num] = frequency_map.get(num, 0) + 1
    buckets = [[] for _ in range(len(nums) + 1)]
    for num, freq in frequency_map.items():
        buckets[freq].append(num)
    result = []
    for i in range(len(buckets) - 1, -1, -1):
        result.extend(buckets[i])
        if len(result) >= k:
            return result[:k]
    return result


def top_k_frequent_heap(nums, k):
    # Step 1: Count frequencies
    frequency_map = {}
    for num in nums:
        frequency_map[num] = frequency_map.get(num, 0) + 1

    # Step 2: Min heap of size k
    # Python's heapq is a min heap, so we use (frequency, num) tuples
    heap = []
    for num, freq in frequency_map.items():
        heapq.heappush(heap, (freq, num))
        if len(heap) > k:
            heapq.heappop(heap)  # Remove least frequent

    # Step 3: Extract numbers from heap
    return [num for _, num in heap]


def group_anagrams(strs):
    # Step 1: Create a dictionary to store groups
    anagram_groups = {}
    # Step 2: Iterate through each string
    for word in strs:
        # Sort the characters to create a key
        # "eat" become "aet"
        sorted_word = "".join(sorted(word))
        # If key doesn't exists, create a new list
        if sorted_word not in anagram_groups:
            anagram_groups[sorted_word] = []
        # Add the original word to the group
        anagram_groups[sorted_word].append(word)
    return list(anagram_groups.values())


def groupAnagrams(strs):
    anagram_groups = defaultdict(list)
    for word in strs:
        sorted_word = "".join(sorted(word))
        anagram_groups[sorted_word].append(word)
    return list(anagram_groups.values())


def two_sums(nums: List[int], target: int):
    seen = {}

    for i, value in enumerate(nums):
        diff = target - value
        if diff in seen:
            return [seen[diff], i]
        seen[value] = i
    return []


def valid_anagram(s: str, t: str):
    resS = {}
    resT = {}
    if len(s) != len(t):
        return False
    for i in range(len(s)):
        resS[s[i]] = 1 + resS.get(s[i], 0)
        resT[t[i]] = 1 + resT.get(t[i], 0)
    return resS == resT


def contains_duplicates_no_meh(nums: List[int]):
    seen = set()
    for value in nums:
        if value in seen:
            return True
        else:
            seen.add(value)
    return False


def contains_duplicates(nums: List):
    # Meh
    res = {}
    for value in nums:
        if value in res:
            res[value] += 1

            if res[value] > 1:
                return True
        else:
            res[value] = 1
    return False


if __name__ == "__main__":
    main()
