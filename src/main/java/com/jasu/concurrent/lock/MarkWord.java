package com.jasu.concurrent.lock;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author huangjiashu
 * @date 2020/1/2
 **/
public class MarkWord {
    public static void main(String[] args) {
        A a = new A();
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }

    static class A {

    }
}
