package com.jasu.kafka.chapter4.internal;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2019-07-10 22:34
 *****************************************/

/**
 * 4 bytes
 * [4],[4].[10],[4],[20]
 * [4],[10],[20]
 *
 * String可变长的。
 */
public class User {
    private int age;
    private String name;
    private String address;

    public User() {
    }

    public User(int age, String name, String address) {
        this.age = age;
        this.name = name;
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
