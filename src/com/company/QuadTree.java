package com.company;

public class QuadTree {
    private int level;
    private Point point = null;
    private QuadTree parent;
    private QuadTree northWest = null;
    private QuadTree northEast = null;
    private QuadTree southWest = null;
    private QuadTree southEast = null;
    private Boundry boundry;

    QuadTree(int level, Boundry boundry, QuadTree parent) {
        this.level = level;
        this.boundry = boundry;
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public QuadTree getParent() {
        return parent;
    }

    public void setParent(QuadTree parent) {
        this.parent = parent;
    }

    public QuadTree getNorthWest() {
        return northWest;
    }

    public void setNorthWest(QuadTree northWest) {
        this.northWest = northWest;
    }

    public QuadTree getNorthEast() {
        return northEast;
    }

    public void setNorthEast(QuadTree northEast) {
        this.northEast = northEast;
    }

    public QuadTree getSouthWest() {
        return southWest;
    }

    public void setSouthWest(QuadTree southWest) {
        this.southWest = southWest;
    }

    public QuadTree getSouthEast() {
        return southEast;
    }

    public void setSouthEast(QuadTree southEast) {
        this.southEast = southEast;
    }

    public Boundry getBoundry() {
        return boundry;
    }

    public void setBoundry(Boundry boundry) {
        this.boundry = boundry;
    }

    public void split() {
        int xO = this.boundry.getXMin()
                + (this.boundry.getXMax() - this.boundry.getXMin()) / 2;
        int yO = this.boundry.getYMin()
                + (this.boundry.getYMax() - this.boundry.getYMin()) / 2;

        northWest = new QuadTree(this.level + 1, new Boundry(
                this.boundry.getXMin(), this.boundry.getYMin(), xO, yO), this);
        northEast = new QuadTree(this.level + 1, new Boundry(
                xO, this.boundry.getYMin(), this.boundry.getXMax(), yO), this);
        southWest = new QuadTree(this.level + 1, new Boundry(
                this.boundry.getXMin(), yO, xO, this.boundry.getYMax()), this);
        southEast = new QuadTree(this.level + 1, new Boundry(
                xO, yO, this.boundry.getXMax(), this.boundry.getYMax()), this);

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
    }

    public Point findPoint(int x, int y) {
        if (!this.boundry.inRange(x, y)) {
            return null;
        }
        QuadTree quadrantForPoint = findQuadrantForPoint(this, x, y);
        Point point = quadrantForPoint.point;
        if (point == null) {
            return null;
        } else {
            int pointX = point.getX();
            int pointY = point.getY();
            if ((pointX == x) && (pointY == y)) {
                return point;
            } else {
                return null;
            }
        }
    }

    private QuadTree findQuadrantForPoint(QuadTree tree, int x, int y) {
        QuadTree quadrantForPoint = null;
        if (tree.northWest != null) {
            if (tree.northWest.boundry.inRange(x, y))
                quadrantForPoint = findQuadrantForPoint(tree.northWest, x, y);
            else if (tree.northEast.boundry.inRange(x, y))
                quadrantForPoint = findQuadrantForPoint(tree.northEast, x, y);
            else if (tree.southWest.boundry.inRange(x, y))
                quadrantForPoint = findQuadrantForPoint(tree.southWest, x, y);
            else if (tree.southEast.boundry.inRange(x, y))
                quadrantForPoint = findQuadrantForPoint(tree.southEast, x, y);
        } else {
            quadrantForPoint = tree;
        }
        return quadrantForPoint;
    }

    public void delete(int x, int y) {
        QuadTree quadrantForPoint = findQuadrantForPoint(this, x, y);
        if (quadrantForPoint.point != null) {
            quadrantForPoint.point = null;
            balance(quadrantForPoint.parent);
        }
    }

    private void balance(QuadTree tree) {
        if (tree.northWest != null) {
            int countOfEmptyQuadrant = 0;
            Point point = null;

            if (isQuadrantEmpty(tree.northWest)) {
                countOfEmptyQuadrant++;
            } else {
                point = tree.northWest.point;
            }

            if (isQuadrantEmpty(tree.northEast)) {
                countOfEmptyQuadrant++;
            } else {
                point = tree.northEast.point;
            }

            if (isQuadrantEmpty(tree.southWest)) {
                countOfEmptyQuadrant++;
            } else {
                point = tree.southWest.point;
            }

            if (isQuadrantEmpty(tree.southEast)) {
                countOfEmptyQuadrant++;
            } else {
                point = tree.southEast.point;
            }

            if (countOfEmptyQuadrant == 4) {
                tree.northWest = null;
                tree.northEast = null;
                tree.southWest = null;
                tree.southEast = null;
            }

            if ((countOfEmptyQuadrant == 3) && (point != null)) {
                tree.northWest = null;
                tree.northEast = null;
                tree.southWest = null;
                tree.southEast = null;
                tree.point = point;
            }
        }
    }

    private boolean isQuadrantEmpty(QuadTree tree) {
        Point point = tree.point;
        QuadTree northWest = tree.northWest;
        return (point == null) && (northWest == null);
    }
}
