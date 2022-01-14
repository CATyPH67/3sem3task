package com.company;

public class Utils {
    public static void printQuadTree(QuadTree tree) {
        if (tree == null)
            return;

        System.out.printf("\nLevel=%d [X1=%d Y1=%d] \t[X2=%d Y2=%d] ",
                tree.getLevel(), tree.getBoundry().getXMin(), tree.getBoundry().getYMin(),
                tree.getBoundry().getXMax(), tree.getBoundry().getYMax());

        if (tree.getPoint() != null) {
            System.out.printf(" \n\t x=%d y=%d value=%d", tree.getPoint().getX(),
                    tree.getPoint().getY(), tree.getPoint().getValue());
        } else {
            if (tree.getNorthWest() == null) {
                System.out.print("\n\t empty");
            } else {
                System.out.print("\n\t leaf");
            }
        }

        printQuadTree(tree.getNorthWest());
        printQuadTree(tree.getNorthEast());
        printQuadTree(tree.getSouthWest());
        printQuadTree(tree.getSouthEast());
    }
}
