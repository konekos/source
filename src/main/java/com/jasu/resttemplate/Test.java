package com.jasu.resttemplate;

public class Test {

    public static void main(String[] args) {
        String x = "ab";
        change(x);
        System.out.println(x);
    }

    public static void change(String x) {
        x = "cd" ;
    }
}
