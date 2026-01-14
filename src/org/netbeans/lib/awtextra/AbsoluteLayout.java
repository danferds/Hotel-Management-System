package org.netbeans.lib.awtextra;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AbsoluteLayout implements LayoutManager2, Serializable {
    private final Map<Component, AbsoluteConstraints> constraints = new HashMap<>();

    @Override
    public void addLayoutComponent(Component comp, Object constraint) {
        if (constraint == null) {
            constraints.put(comp, new AbsoluteConstraints(0, 0, -1, -1));
        } else if (constraint instanceof AbsoluteConstraints) {
            constraints.put(comp, (AbsoluteConstraints) constraint);
        } else {
            throw new IllegalArgumentException("Constraint must be AbsoluteConstraints");
        }
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        constraints.put(comp, new AbsoluteConstraints(0, 0, -1, -1));
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        constraints.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return layoutSize(parent, LayoutSizeType.PREFERRED);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return layoutSize(parent, LayoutSizeType.MINIMUM);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return layoutSize(target, LayoutSizeType.MAXIMUM);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        for (Component comp : parent.getComponents()) {
            AbsoluteConstraints c = constraints.get(comp);
            Dimension size = comp.getPreferredSize();
            int x = 0;
            int y = 0;
            int width = size.width;
            int height = size.height;
            if (c != null) {
                x = c.x;
                y = c.y;
                if (c.width != -1) {
                    width = c.width;
                }
                if (c.height != -1) {
                    height = c.height;
                }
            } else {
                x = comp.getX();
                y = comp.getY();
            }
            comp.setBounds(insets.left + x, insets.top + y, width, height);
        }
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    private enum LayoutSizeType {
        PREFERRED,
        MINIMUM,
        MAXIMUM
    }

    private Dimension layoutSize(Container parent, LayoutSizeType type) {
        Insets insets = parent.getInsets();
        int maxWidth = 0;
        int maxHeight = 0;
        for (Component comp : parent.getComponents()) {
            AbsoluteConstraints c = constraints.get(comp);
            Dimension size;
            if (type == LayoutSizeType.MINIMUM) {
                size = comp.getMinimumSize();
            } else if (type == LayoutSizeType.MAXIMUM) {
                size = comp.getMaximumSize();
            } else {
                size = comp.getPreferredSize();
            }
            int x = 0;
            int y = 0;
            int width = size.width;
            int height = size.height;
            if (c != null) {
                x = c.x;
                y = c.y;
                if (c.width != -1) {
                    width = c.width;
                }
                if (c.height != -1) {
                    height = c.height;
                }
            }
            maxWidth = Math.max(maxWidth, x + width);
            maxHeight = Math.max(maxHeight, y + height);
        }
        return new Dimension(maxWidth + insets.left + insets.right, maxHeight + insets.top + insets.bottom);
    }
}
