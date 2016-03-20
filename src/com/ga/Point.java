package com.ga;

public class Point {
    private int x = 0;
    private int y = 0;

    public Point() {
    }

    public Point(Point p) {
        x = p.x;
        y = p.y;
    }

    public Point(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public Point moveCoordsBydXdY(int x, int y) {
        this.x += x;
        this.y += y;

        return this;
    }

    Point getCoordinates() {
        return new Point(this);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;  // type check

        if (obj == null)
            return false;


        if (!(getClass() == obj.getClass()))
            return false;

        Point tmp = (Point) obj;
        return tmp.getX() == this.getX() && tmp.getY() == this.getY();
    }
}
