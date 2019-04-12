package com.jasu.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author @Jasu
 * @date 2019-04-12 10:52
 */
public class _1_TwoSum {
    public static void main(String[] args) {
        int[] arr = {2, 7, 11, 23};
        int target = 9;
        int[] ints = new _1_TwoSum().twoSum(arr, target);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(i);
        }
    }

    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("no solution");
    }


    public int[] twoSum1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[]{i, map.get(complement)};
            }
        }
        throw new IllegalArgumentException("no solution");
    }
    //One-pass
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
