package com.company;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    int level;
    Point point = null;
    QuadTree northWest = null;
    QuadTree northEast = null;
    QuadTree southWest = null;
    QuadTree southEast = null;
    Boundry boundry;

    QuadTree(int level, Boundry boundry) {
        this.level = level;
        this.boundry = boundry;
    }

    public void dfs(QuadTree tree) {
        if (tree == null)
            return;

        if (tree.point != null) {
            System.out.printf("\nLevel=%d [X1=%d Y1=%d] \t[X2=%d Y2=%d] ",
                    tree.level, tree.boundry.getXMin(), tree.boundry.getYMin(),
                    tree.boundry.getXMax(), tree.boundry.getYMax());
            System.out.printf(" \n\t  x=%d y=%d value=%d", tree.point.getX(),
                    tree.point.getY(), tree.point.getValue());
        }

        dfs(tree.northWest);
        dfs(tree.northEast);
        dfs(tree.southWest);
        dfs(tree.southEast);

    }

    public void split() {
        int xO = this.boundry.getXMin()
                + (this.boundry.getXMax() - this.boundry.getXMin()) / 2;
        int yO = this.boundry.getYMin()
                + (this.boundry.getYMax() - this.boundry.getYMin()) / 2;

        northWest = new QuadTree(this.level + 1, new Boundry(
                this.boundry.getXMin(), this.boundry.getYMin(), xO, yO));
        northEast = new QuadTree(this.level + 1, new Boundry(
                xO, this.boundry.getYMin(), this.boundry.getXMax(), yO));
        southWest = new QuadTree(this.level + 1, new Boundry(
                this.boundry.getXMin(), yO, xO, this.boundry.getYMax()));
        southEast = new QuadTree(this.level + 1, new Boundry(
                xO, yO, this.boundry.getXMax(), this.boundry.getYMax()));

        if (point != null) {
            int x = point.getX();
            int y = point.getY();
            int value = point.getValue();
            if (this.northWest.boundry.inRange(x, y))
                this.northWest.point = new Point(x, y, value);
            else if (this.northEast.boundry.inRange(x, y))
                this.northEast.point = new Point(x, y, value);
            else if (this.southWest.boundry.inRange(x, y))
                this.southWest.point = new Point(x, y, value);
            else if (this.southEast.boundry.inRange(x, y))
                this.southEast.point = new Point(x, y, value);

            point = null;
        }
    }

    public void insert(int x, int y, int value) {
        if (!this.boundry.inRange(x, y)) {
            return;
        }

        if (northWest == null) {
            Point point = new Point(x, y, value);
            if (this.point == null) {
                this.point = point;
                return;
            }

            split();
        }

        if (this.northWest.boundry.inRange(x, y))
            this.northWest.insert(x, y, value);
        else if (this.northEast.boundry.inRange(x, y))
            this.northEast.insert(x, y, value);
        else if (this.southWest.boundry.inRange(x, y))
            this.southWest.insert(x, y, value);
        else if (this.southEast.boundry.inRange(x, y))
            this.southEast.insert(x, y, value);
        else
            System.out.printf("ERROR : Unhandled partition %d %d", x, y);
    }

    public Point findPoint(int x, int y) {
        if (!this.boundry.inRange(x, y)) {
            return null;
        }
        QuadTree quadrantForPoint = getQuadrantForPoint(this, x, y);
        return quadrantForPoint.point;
    }

    private QuadTree getQuadrantForPoint(QuadTree tree, int x, int y) {
        QuadTree quadrantForPoint = null;
        if (tree.northWest != null) {
            if (tree.northWest.boundry.inRange(x, y))
                quadrantForPoint = getQuadrantForPoint(tree.northWest, x, y);
            else if (tree.northEast.boundry.inRange(x, y))
                quadrantForPoint = getQuadrantForPoint(tree.northEast, x, y);
            else if (tree.southWest.boundry.inRange(x, y))
                quadrantForPoint = getQuadrantForPoint(tree.southWest, x, y);
            else if (tree.southEast.boundry.inRange(x, y))
                quadrantForPoint = getQuadrantForPoint(tree.southEast, x, y);
        } else {
            quadrantForPoint = tree;
        }
        return quadrantForPoint;
    }
}
