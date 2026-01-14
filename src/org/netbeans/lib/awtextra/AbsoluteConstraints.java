package org.netbeans.lib.awtextra;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class AbsoluteConstraints implements Serializable {
    public int x;
    public int y;
    public int width;
    public int height;

    public AbsoluteConstraints(int x, int y) {
        this(x, y, -1, -1);
    }

    public AbsoluteConstraints(Point p) {
        this(p.x, p.y, -1, -1);
    }

    public AbsoluteConstraints(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public AbsoluteConstraints(Point p, Dimension size) {
        this(p.x, p.y, size.width, size.height);
    }

    public AbsoluteConstraints(Rectangle r) {
        this(r.x, r.y, r.width, r.height);
    }
}
