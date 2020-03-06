package com.jasu.algorithm.extra;

import java.util.*;

/*****************************************
 * @author hjs
 * @date 2020-03-04 17:43
 *****************************************/
public class Extra_3_MN的01矩阵最大行 {
    public static void main(String[] args) {

        byte[] i1 = new byte[]{0, 0, 0, 1, 1, 1};
        byte[] i2 = new byte[]{0, 0, 1, 1, 1, 1};
        byte[] i3 = new byte[]{0, 1, 1, 1, 1, 1};
        byte[] i4 = new byte[]{1, 1, 1, 1, 1, 1};
        byte[] i6 = new byte[]{1, 0, 1, 1, 1, 1};



        byte[][] nums = new byte[6][4];
        nums[0] = i1;
        nums[1] = i2;
        nums[2] = i3;
        nums[3] = i4;

        System.out.println(max(nums));
        for (Byte aByte : sort(i6)) {
            System.out.print(aByte + " ");
        }

    }

    public static Byte[] sort(byte[] nums) {
        Deque<Byte> deque = new LinkedList<>();
        for (byte num : nums) {
            if (num == 0) {
                deque.addFirst(num);
            } else {
                deque.addLast(num);
            }
        }
        Byte[] b = new Byte[nums.length];
        return deque.toArray(b);
    }

    public static int max(byte[][] nums) {
        byte[] row1 = nums[0];
        int num = row1.length-1;
        int rowMax = nums.length;
        for (int i = 0; i < row1.length; i++) {
            if (row1[i] == 1) {
                num = i;
                break;
            }
        }
        if (num == row1.length - 1) {
            return 0;
        }
        for (int i = 1; i < nums.length; i++) {
            if (nums[i][0] == 1) {
                return i;
            }
            if (nums[i][num] == 1) {
                while (num >= 1) {
                    num--;
                    if (nums[i][num] == 0) {
                        rowMax = i;
                        break;
                    }
                }

            }
        }

        return rowMax;


    }
}
