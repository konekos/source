package com.jasu.nio._04_Streams;

import java.io.Serializable;

/**
 * @author @Jasu
 * @date 2018-07-31 18:00
 */
public class Employee implements Serializable {
    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
