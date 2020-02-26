package com.jasu.designpattern.decorator;

/*****************************************
 * @author hjs
 * @date 2020-02-26 22:25
 *****************************************/
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("C");
    }
}
