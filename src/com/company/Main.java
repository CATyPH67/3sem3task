package com.company;

public class Main {

    public static void main(String[] args) {
        QuadTree anySpace = new QuadTree(1, new Boundry(0, 0, 20, 20), null);
        anySpace.insert(5, 5, 1);
        anySpace.insert(12, 18, 2);
        anySpace.insert(16, 11, 3);
        anySpace.insert(16, 13, 4);

        Utils.printQuadTree(anySpace);
        Point point = anySpace.findPoint(12, 3);
        if (point != null) {
            System.out.println("\n\t" + point.getValue());
        } else {
            System.out.println("\n\t\n this point does not exist");
        }

        anySpace.delete(16, 11);
        Utils.printQuadTree(anySpace);
        point = anySpace.findPoint(16, 11);
        if (point != null) {
            System.out.println("\n\t" + point.getValue());
        } else {
            System.out.println("\n\t\n this point does not exist");
        }

        anySpace.delete(16, 13);
        Utils.printQuadTree(anySpace);
        point = anySpace.findPoint(16, 13);
        if (point != null) {
            System.out.println("\n\t" + point.getValue());
        } else {
            System.out.println("\n\t\n this point does not exist");
        }
    }
}
