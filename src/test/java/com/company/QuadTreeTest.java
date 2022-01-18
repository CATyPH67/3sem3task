package com.company;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class QuadTreeTest {

    @Test
    void insert() {
        QuadTree tree = new QuadTree(1, new Boundry(0, 0, 10, 10), null);
        tree.insert(1, 1, 1);
        tree.insert(9, 9, 2);
        QuadTreePoint point1 = tree.findPoint(1, 1);
        QuadTreePoint point2 = tree.findPoint(9, 9);
        Assertions.assertEquals(1, point1.getX());
        Assertions.assertEquals(1, point1.getY());
        Assertions.assertEquals(1, point1.getValue());
        Assertions.assertEquals(9, point2.getX());
        Assertions.assertEquals(9, point2.getY());
        Assertions.assertEquals(2, point2.getValue());
    }

    @Test
    void findPoint() {
        QuadTree tree = new QuadTree(1, new Boundry(0, 0, 10, 10), null);
        tree.insert(1, 1, 1);
        QuadTreePoint point1 = tree.findPoint(1, 1);
        Assertions.assertEquals(1, point1.getX());
        Assertions.assertEquals(1, point1.getY());
        Assertions.assertEquals(1, point1.getValue());
    }

    @Test
    void delete() {
        QuadTree tree = new QuadTree(1, new Boundry(0, 0, 10, 10), null);
        tree.insert(1, 1, 1);
        tree.delete(1,1);
        QuadTreePoint point1 = tree.findPoint(1, 1);
        Assertions.assertNull(point1);
    }
}