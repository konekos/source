package com.jasu.designpattern.decorator;

/*****************************************
 * @author hjs
 * @date 2020-02-26 22:27
 *****************************************/
public class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        shape.draw();
        setRedBorder(shape);
    }

    private void setRedBorder(Shape shape) {
        System.out.println("BR");
    }

}
