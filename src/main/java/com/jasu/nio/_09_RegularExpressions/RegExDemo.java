package com.jasu.nio._09_RegularExpressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author @Jasu
 * @date 2018-08-14 11:17
 */
public class RegExDemo {
    public static void main(String[] args) {
        String regex = "ox: ";
        String input = "324324 ox: ips \r\n" +
                "3423";

        Pattern pattern = Pattern.compile("(ox).(.*)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            System.out.println(matcher.group(2));
        }
    }
}
