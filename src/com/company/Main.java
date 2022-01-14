package com.company;

public class Main {

    public static void main(String[] args) {
        QuadTree anySpace = new QuadTree(1, new Boundry(0, 0, 20, 20));
        anySpace.insert(5, 5, 1);
        anySpace.insert(12, 18, 2);
        anySpace.insert(16, 11, 3);
        anySpace.insert(16, 13, 4);
        //Traveling the graph
        anySpace.dfs(anySpace);
        System.out.println();
        Point point = anySpace.findPoint(200, 18);
        System.out.println(point.getValue());
    }
}
