package com.jasu.designpattern.InterceptingFilter;

/**
 * @author @Jasu
 * @date 2018-10-09 15:26
 */
public class DebugFilter implements Filter {
    @Override
    public void execute(String request) {
        System.out.println("Request log: "+request);
    }
}
