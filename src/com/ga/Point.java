package com.ga;

public class Point {
    private int x = 0;
    private int y = 0;

    Point moveCoordsBydXdY(int x, int y){
        this.x += x;
        this.y += y;

        return this;
    }

    public Point() {
    }

    public Point(Point p) {
        x = p.x;
        y = p.y;
    }

    Point getCoordinates(){
        return new Point(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
