from typing import List
from collections import defaultdict
import heapq


def main():
    pass


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
