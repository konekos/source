package com.jasu.algorithm.extra;

/**
 * @author huangjiashu
 * @date 2019/12/12
 **/
public class Extra_2_消除连续的3字母 {
    public static void main(String[] args) {
        String s = "acbbcccbccddd";

        for (int i = 0; i < s.length()-1; i++) {
            if (s.charAt(i) == s.charAt(i + 1) && s.charAt(i + 1) == s.charAt(i + 2)) {
                s = s.substring(0, i) + s.substring(i + 3);
                i = 0;
            }
        }

        System.out.println(s);
    }

}
