package com.jasu.concurrent.jcia.chapter4;

import javax.annotation.concurrent.Immutable;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-13 22:04
 *****************************************/
@Immutable
public class Point {
    private final int x,y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
