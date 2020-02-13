package com.jasu.concurrent.jcia.chapter4;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.HashSet;
import java.util.Set;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-13 21:31
 *****************************************/
@ThreadSafe
public class PersonSet {
    @GuardedBy("this")
    private final Set<Person> mySet = new HashSet<>();

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }

    private class Person{

    }
}
