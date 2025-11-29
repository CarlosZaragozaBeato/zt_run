from typing import List
from collections import defaultdict


def main():
    pass


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
