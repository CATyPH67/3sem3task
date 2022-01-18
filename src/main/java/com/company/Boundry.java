package com.company;

public class Boundry {
    final int xMin, yMin, xMax, yMax;

    public Boundry(int xMin, int yMin, int xMax, int yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public int getXMin() {
        return xMin;
    }

    public int getYMin() {
        return yMin;
    }

    public int getXMax() {
        return xMax;
    }

    public int getYMax() {
        return yMax;
    }

    public boolean inRange(int x, int y) {
        return (x >= xMin && x <= xMax
                && y >= yMin && y <= yMax);
    }
}
