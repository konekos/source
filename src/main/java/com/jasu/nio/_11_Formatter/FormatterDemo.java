package com.jasu.nio._11_Formatter;

import java.util.Formatter;

/**
 * @author @Jasu
 * @date 2018-08-16 11:26
 */
public class FormatterDemo {

    public static void main(String[] args) {

        Formatter formatter = new Formatter();
        System.out.println(formatter.format("%d", 123).toString());
        System.out.println(formatter.format("%x", 123).toString());// Formats the argument as a hexadecimal integer
        System.out.println(formatter.format("%c", 'X').toString());
        System.out.println(formatter.format("%f", 0.1).toString());
        System.out.println(formatter.format("%s%n", "Hello, World").toString());
        System.out.println(formatter.format("%10.2f", 98.375).toString());
        System.out.println(formatter.format("%05d", 123).toString());
        System.out.println(formatter.format("%1$d %1$d", 123).toString());
        System.out.println(formatter.format("%d %d", 123, 123).toString());

        System.out.printf("%04X%n", 478);
        System.out.printf("Current date: %1$tb %1$te, %1$tY%n",
                System.currentTimeMillis());

    }

}
