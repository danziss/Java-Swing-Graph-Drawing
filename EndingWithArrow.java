package com.codebin;

import java.awt.geom.Path2D;

public class EndingWithArrow extends Path2D.Double {

    public EndingWithArrow() {
        int size = 10;
        moveTo(0, size);
        lineTo(size / 2, 0);
        lineTo(size, size);
    }

}
