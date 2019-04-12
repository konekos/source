package com.jasu.algorithm.leetcode;

/**
 * @author @Jasu
 * @date 2019-03-26 14:07
 */
public class LeetCode136 {
    private int num;

    public int getNum() {
        return num;
    }

    public static int singleNumber(int A[], int n) {

            //异或
            int elem = 0;
            for(int i = 0; i < n ; i++) {
                elem = elem ^ A[i];
            }

            return elem;
        }

    public static void main(String[] args) {
        int[] array = {2, 3, 4, 5, 56, 567, 56, 567, 3, 4, 5};
        System.out.println(singleNumber(array, array.length));

    }
}
