package com.jasu.designpattern.decorator;

/*****************************************
 * @author hjs
 * @date 2020-02-26 22:25
 *****************************************/
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("R");
    }
}
