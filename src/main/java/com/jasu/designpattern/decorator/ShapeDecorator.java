package com.jasu.designpattern.decorator;

/*****************************************
 * @author hjs
 * @date 2020-02-26 22:26
 *****************************************/
abstract public class ShapeDecorator implements Shape {
    protected Shape shape;

    public ShapeDecorator(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void draw() {
        shape.draw();
    }
}
