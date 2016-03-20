package com.ga;

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

        Field f = new Field();


        f.placeShape(new Shape(new Point(7, 5), -180, Shape.Type.TRIANGLE));
        f.placeShape(new Shape(new Point(16, 10), 90, Shape.Type.TRIANGLE));

        System.out.println(f.toString());


    }

    private void newLShape(Point center) {

        vertices.add(center.getCoordinates().moveCoordsBydXdY(0, SIZE));
        vertices.add(center.getCoordinates().moveCoordsBydXdY(-SIZE, SIZE));
        vertices.add(center.getCoordinates().moveCoordsBydXdY(-SIZE, SIZE));
        vertices.add(center.getCoordinates().moveCoordsBydXdY(SIZE, -SIZE));
        vertices.add(center.getCoordinates().moveCoordsBydXdY(SIZE, 0));
    }

    private void newTriangle(Point center) {
        Point p = null, p1, p2;

        p1 = p2 = p;

//        vertices.add(center.getCoordinates().moveCoordsBydXdY(-SIZE, -SIZE));
//        vertices.add(center.getCoordinates().moveCoordsBydXdY(-SIZE, SIZE));
//        vertices.add(center.getCoordinates().moveCoordsBydXdY(SIZE, -SIZE));

        p = center.getCoordinates();
        for (int i = 0; i <= SIZE; i++) {
            vertices.add(p1 = p.getCoordinates().moveCoordsBydXdY(-i, -i));
        }
//        p1 = p;

        p = center.getCoordinates();
        for (int i = 0; i <= SIZE; i++) {
            vertices.add(p2 = p.getCoordinates().moveCoordsBydXdY(-i, i));
        }
//        p2 = p;

        p = center.getCoordinates();
        for (int i = 0; i <= SIZE; i++) {
            vertices.add(p.getCoordinates().moveCoordsBydXdY(i, -i));
        }

        Point maxXPoint;
        for (int y = p1.getY(); y <= p2.getY(); y++) {
            final int finalY = y;
            maxXPoint = vertices.stream()
                    .distinct()
                    .filter(point -> point.getY() == finalY)
                    .max(Comparator.comparing(Point::getX))
                    .get();

            p = p1.getCoordinates();
            for (int x = p.getX(); x < maxXPoint.getX(); x++) {
                vertices.add(new Point(x, y));
            }
        }

        Collections.reverse(vertices.stream().distinct().collect(Collectors.toList()));
    }

    public List<Point> getShapeVertices() {
        return vertices;
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

    enum Type {
        TRIANGLE,
        L_SHAPE
    }


}
