package com.jasu.designpattern.decorator;

/*****************************************
 * @author hjs
 * @date 2020-02-26 22:29
 *****************************************/
public class DecoratorDemo {
    public static void main(String[] args) {
        Shape circle = new Circle();

        RedShapeDecorator redCircle = new RedShapeDecorator(new Circle());
        RedShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());

        circle.draw();
        redCircle.draw();
        redRectangle.draw();
    }
}
