package com.jasu.algorithm.leetcode;

/**
 * @author @Jasu
 * @date 2019-04-12 15:40
 */
public class _206_ReverseLinkedList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    class Solution {
        public ListNode reverseList(ListNode head) {
            ListNode prev = null;
            ListNode curr = head;
            while (curr != null) {
                ListNode nextTemp = head.next;
                curr.next = prev;
                prev = curr;
                curr = nextTemp;
            }
            return prev;
        }

        /**
         * The recursive version is slightly trickier and the key is to work backwards. Assume that the rest of the list had already been reversed, now how do I reverse the front part? Let's assume the list is: n1 → … → nk-1 → nk → nk+1 → … → nm → Ø
         *
         * Assume from node nk+1 to nm had been reversed and you are at node nk.
         *
         * n1 → … → nk-1 → nk → nk+1 ← … ← nm
         *
         * We want nk+1’s next node to point to nk.
         *
         * So,
         *
         * nk.next.next = nk;
         *
         * Be very careful that n1's next must point to Ø. If you forget about this, your linked list has a cycle in it. This bug could be caught if you test your code with a linked list of size 2.
         * @param head
         * @return
         */
        public ListNode reverseList1(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode p = reverseList(head.next);
            head.next.next = head;
            head.next = null;
            return p;
        }

    }
}
