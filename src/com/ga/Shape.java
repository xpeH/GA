package com.ga;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.List;

public class Shape {

    enum Type {
        TRIANGLE,
        L_SHAPE
    }


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
                vertices.add(center.getCoordinates().moveCoordsBydXdY(-2, -2));
                vertices.add(center.getCoordinates().moveCoordsBydXdY(-2, 2));
                vertices.add(center.getCoordinates().moveCoordsBydXdY(2, -2));
                break;

            case L_SHAPE:
                vertices.add(center.moveCoordsBydXdY(0, 2));
                vertices.add(center.moveCoordsBydXdY(-2, 2));
                vertices.add(center.moveCoordsBydXdY(-2, 2));

                vertices.add(center.moveCoordsBydXdY(2, -2));
                vertices.add(center.moveCoordsBydXdY(2, 0));
                break;
        }

        vertices.forEach(point -> calcPointRotation(point, angle));
    }

    private void calcPointRotation(Point point, int angle){
        SimpleMatrix src = new SimpleMatrix(1, 2, false, point.getX(), point.getY());
        SimpleMatrix ang = new SimpleMatrix(2, 2, true, Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)), -Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)));

        SimpleMatrix res = src.mult(ang);

        point.setX((int)(float)res.get(0));  // double 1.9999 -> float 2.00 -> int 2
        point.setY((int)(float)res.get(1));
    }

    @Override
    public String toString() {
        return "Shape{" +
                "vertices=" + vertices +
                '}';
    }

    public static void main(String[] args) {

        Shape sh = new Shape(new Point(), -90, Type.TRIANGLE);

    }


}
