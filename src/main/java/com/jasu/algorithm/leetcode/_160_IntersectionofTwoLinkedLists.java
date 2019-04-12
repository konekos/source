package com.jasu.algorithm.leetcode;

/**
 * @author @Jasu
 * @date 2019-04-12 17:38
 */
public class _160_IntersectionofTwoLinkedLists {

    public static void main(String[] args) {

    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public class Solution {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            ListNode first = headA, second = headB;

            while (first != second) {
                //不能用first.next判断  无交集同时reach null 跳出循环
                first = (first != null) ? first.next : headB;
                second = (second != null) ? second.next : headA;
            }
            return first;
        }
    }
}
