package com.jasu.designpattern.InterceptingFilter;

/**
 * @author @Jasu
 * @date 2018-10-10 11:35
 */
public class Target {

    public void execute(String request) {
        System.out.println("Executing request: "+request);
    }
}
