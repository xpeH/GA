package com.ga;

import com.ga.impl.GA;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Shape {

    private static final int SIZE = 2;
    Point center;
    int angle;
    Type type;
    List<Point> vertices = new ArrayList<>();

    public Shape(Point center, int angle, Type type) {
        this.center = center;
        this.angle = angle;
        this.type = type;

        switch (type) {
            case TRIANGLE:
                newTriangle(center);
                break;

            case L_SHAPE:
                newLShape(center);
                break;
        }

        vertices.forEach(point -> calcPointRotation(point, angle));
    }

    public static void main(String[] args) {


        GA f = new GA();
//        f.generateSpecies();
        System.out.println(f.generateSpecies().field.toString());
        System.out.println(f.generateSpecies().field.getFreeRows());
        f.setVisible(true);

//        System.out.println(f.shapes.size());
//        System.out.println(f.shapes);

//        Field f = new Field(20);
//
//        f.placeShape(new Shape(new Point(5, 5), 0, Type.TRIANGLE));
//        f.placeShape(new Shape(new Point(9, 5), 0, Type.TRIANGLE));
//        f.placeShape(new Shape(new Point(9, 15), 0, Type.TRIANGLE));
//        f.placeShape(new Shape(new Point(9, 10), 0, Type.TRIANGLE));
//        f.placeShape(new Shape(new Point(9, 2), 0, Type.TRIANGLE));
//
//        System.out.println(f.toString());
//        System.out.println(f.getFreeRows());
    }

    private void newLShape(Point center) {

        vertices.add(center.getCoordinates().moveCoordsBydXdY(0, SIZE));
        vertices.add(center.getCoordinates().moveCoordsBydXdY(-SIZE, SIZE));
        vertices.add(center.getCoordinates().moveCoordsBydXdY(-SIZE, SIZE));
        vertices.add(center.getCoordinates().moveCoordsBydXdY(SIZE, -SIZE));
        vertices.add(center.getCoordinates().moveCoordsBydXdY(SIZE, 0));
    }

    private void newTriangle(Point center) {
        Point p3 = null, p1, p2;

        p1 = p2 = p3;

        p1 = new Point(center.getCoordinates().moveCoordsBydXdY(-SIZE, SIZE));
        p2 = new Point(center.getCoordinates().moveCoordsBydXdY(-SIZE, -SIZE));
        p3 = new Point(center.getCoordinates().moveCoordsBydXdY(SIZE, -SIZE));

        for (int i = p1.getY(); i >= p2.getY(); i--) {
            vertices.add(new Point(p1.getX(), i, Point.Type.OUTER));
        }
//
        for (int i = p2.getX(); i <= p3.getX(); i++) {
            vertices.add(new Point(i, p2.getY(), Point.Type.OUTER));
        }

        for (int i = 0; i <= SIZE * 2; i++) {
            vertices.add(p3.getCoordinates().setType(Point.Type.OUTER).moveCoordsBydXdY(-i, i));
        }


        Point maxXPoint;
        for (int y = p1.getY(); y >= p2.getY(); y--) {
            final int currentY = y;
            maxXPoint = vertices.stream()
                    .distinct()
                    .filter(point -> point.getY() == currentY)
                    .max(Comparator.comparing(Point::getX))
                    .get();

            for (int x = p1.getX(); x < maxXPoint.getX(); x++) {
                Point p = new Point(x, y, Point.Type.INNER);
                if (!vertices.contains(p)) {
                    vertices.add(p);
                }
            }
        }

        Collections.reverse(vertices.stream().distinct().collect(Collectors.toList()));
    }

    public List<Point> getShapeVertices() {
        return vertices;
    }

    public List<Point> getPoints(Point.Type type) {
        return vertices.stream().filter(point -> point.getType() == type).collect(Collectors.toList());
    }

    public Point getCenter() {
        return center;
    }

    private void calcPointRotation(Point Point, int angle) {
        if (angle == 0)
            return;

        SimpleMatrix src = new SimpleMatrix(1, 3, false, Point.getX(), Point.getY(), 1);
        SimpleMatrix ang = new SimpleMatrix(
                3, 3, true,
                Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)), 0,
                -Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), 0,
                0, 0, 1
        );

        SimpleMatrix pnt = new SimpleMatrix(
                3, 3, true,
                1, 0, 0,
                0, 1, 0,
                center.getX(), center.getY(), 1
        );

        SimpleMatrix pnt_ = new SimpleMatrix(
                3, 3, true,
                1, 0, 0,
                0, 1, 0,
                -center.getX(), -center.getY(), 1
        );

        SimpleMatrix res = src.mult(pnt_).mult(ang).mult(pnt);

        Point.setX((int) (float) res.get(0));  // double 1.9999 -> float 2.00 -> int 2
        Point.setY((int) (float) res.get(1));
    }

    @Override
    public String toString() {
        return "Shape{" +
                "vertices=" + vertices +
                '}';
    }

    public enum Type {
        TRIANGLE,
        L_SHAPE
    }
}
