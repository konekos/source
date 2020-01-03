package com.jasu.algorithm;

/**
 * 1 3 5 7 9 11 13     1 2 3 4 5   6   7 8 9 11 13
 *  2 4 6 8
 *
 * @author huangjiashu
 * @date 2020/1/3
 **/
public class 两个数组的中位数 {

    public static double findMid(int[] arrA, int[] arrB) {
        int m = arrA.length;
        int n = arrB.length;

        if (m > n) {
            int[] temp = arrA;
            int tmp = m;
            arrA = arrB;
            arrB = temp;
            n = m;
            m = tmp;
        }

        int start = 0;
        int end = m;

        int mid = (m + n + 1) / 2;
        while (start <= end) {
            int i = (start + end) / 2;
            int j = mid - i;
            if (i < end && arrB[j - 1] > arrA[i]) {
                //i要右移
                start = i + 1;
            } else if (i > start && arrA[i - 1] > arrB[j]) {
                //i要左移
                end = i - 1;
            } else {
                //i合适或达到数组边界
                int maxLeft;
                if (i == 0) {
                    maxLeft = arrB[j - 1];
                } else if (j == 0) {
                    maxLeft = arrA[i - 1];
                }else {
                    maxLeft = Math.max(arrA[i - 1], arrB[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }
                int minRight;
                if (i == m) {
                    minRight = arrB[j];
                } else if (j == n) {
                    minRight = arrA[i];
                } else {
                    minRight = Math.min(arrA[i], arrB[j]);
                }
                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0;
    }

}
