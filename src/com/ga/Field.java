package com.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Field {
    private static final int WINDOW_SIZE = 5;

    public Shape[][] field;
    public List<Shape> shapes = new ArrayList<>();
    private int fieldSize;
    private Point windowPosition;
    private int removedShapesAmount = 0;

    public Field(int size) {
        field = new Shape[size][size];
        fieldSize = field.length - 1;
        genWindowCoords();
    }

    public void resetRemovedShapesAmount() {
        removedShapesAmount = 0;
    }

    public void genWindowCoords() {
        Random r = new Random();
        int low = WINDOW_SIZE / 2;
        int high = WINDOW_SIZE - low + 1; // to be inclusive in random
        windowPosition = new Point(r.nextInt(high - low) + low, r.nextInt(high - low) + low);
    }

    private boolean windowsNotCross(Point otherWindowCoordinates) {
        return (Math.abs(windowPosition.getX() - otherWindowCoordinates.getX()) >= WINDOW_SIZE) && (Math.abs(windowPosition.getY() - otherWindowCoordinates.getY()) >= WINDOW_SIZE);
    }

    public int getSize() {
        return field.length;
    }

    public void placeShape(Shape shape) {
        if (fitShape(shape)) {
            shape.getShapeVertices().forEach(point -> field[point.getX()][point.getY()] = shape);
            shapes.add(shape);
        }
    }

    public void removeShape(Shape shape, boolean increment) {
        shape.getShapeVertices().forEach(point -> field[point.getX()][point.getY()] = null);
        shapes = shapes.stream().filter(shape::equals).collect(Collectors.toList());
        if (increment)
            removedShapesAmount++;
    }

    public boolean fitShape(Shape shape) {

        return shape.getShapeVertices().stream().noneMatch(p -> getShapeByPoint(p) != null);
    }

    public boolean pointFree(Point point) {
        Shape shape = getShapeByPoint(point);
        return shape == null || shape.getCenter().equals(point);
    }

    /**
     * Returns shape by Point
     *
     * @param p Point
     * @return Shape or Null
     */
    public Shape getShapeByPoint(Point p) {
        return field[p.getX()][p.getY()];
    }

    @Override
    public String toString() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLACK = "\u001B[30m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_WHITE = "\u001B[37m";

        StringBuilder s = new StringBuilder();

        for (int y = fieldSize; y >= 0; y--) {
            for (int x = 0; x < this.getSize(); x++) {
                Point p = new Point(x, y);
                if (field[x][y] != null) {
                    final Point finalP = p;
                    p = field[x][y].getShapeVertices()
                            .stream()
                            .filter(point -> point.getX() == finalP.getX() && point.getY() == finalP.getY())
                            .findFirst()
                            .get();
                    if (p.equals(field[x][y].getCenter())) {
                        s.append(ANSI_YELLOW + "\u23FA" + ANSI_RESET);
                    } else if (p.getType() == Point.Type.INNER) {
                        s.append(ANSI_GREEN + "\u23FA" + ANSI_RESET);
                    } else {
                        s.append(ANSI_RED + "\u23FA" + ANSI_RESET);
                    }
                } else {
                    s.append("\u23FA");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int getFreeRows() {
        int freeRows = 0;
        for (int i = 0; i <= fieldSize; i++) {
            boolean rowFree = true;
            for (int j = 0; j <= fieldSize; j++) {

                if (field[j][i] != null) {
                    rowFree = false;
                    break;
                }
            }

            if (rowFree)
                freeRows++;
        }
        return freeRows;
    }

    public Point getWindowPosition() {
        return windowPosition;
    }

    public void switchWithShapes(Field otherField) {
        while (windowsNotCross(otherField.getWindowPosition())) {
            genWindowCoords();
            otherField.genWindowCoords();
        }
        List<Shape> otherWinFullShapes = otherField.getShapesInWindow().stream().filter(shape ->
                shape.getShapeVertices().stream().allMatch(
                        point ->
                        {
                            boolean inWin = Math.abs(otherField.getWindowPosition().getX() - point.getX()) <= (WINDOW_SIZE / 2)
                                    &&
                                    Math.abs(otherField.getWindowPosition().getY() - point.getY()) <= (WINDOW_SIZE / 2);
                            if (!inWin) {
                                otherField.removeShape(shape, true);
                            }
                            return inWin;
                        }
                )
        ).collect(Collectors.toList());

        List<Shape> winFullShapes = this.getShapesInWindow().stream().filter(shape ->
                shape.getShapeVertices().stream().allMatch(
                        point ->
                        {
                            boolean inWin = (Math.abs(this.getWindowPosition().getX() - point.getX()) > (WINDOW_SIZE / 2)
                                    &&
                                    Math.abs(this.getWindowPosition().getY() - point.getY()) > WINDOW_SIZE / 2);
                            if (!inWin) {
                                this.removeShape(shape, true);
                            }
                            return inWin;
                        }
                )
        ).collect(Collectors.toList());

        winFullShapes.stream().forEach(shape -> {
                    this.removeShape(shape, false);
                    otherField.placeShape(shape);
                }
        );
        otherWinFullShapes.stream().forEach(shape -> {
                    otherField.removeShape(shape, false);
                    this.placeShape(shape);
                }
        );
    }

    public List<Shape> getShapesInWindow() {
        Point win = this.getWindowPosition();
        List<Shape> shapes = new ArrayList<>();
        for (int y = win.getY() - WINDOW_SIZE / 2; y < win.getY() + WINDOW_SIZE / 2; y++) {
            for (int x = win.getX() - WINDOW_SIZE / 2; x < win.getX() + WINDOW_SIZE / 2; x++) {
                shapes.add(this.getShapeByPoint(new Point(x, y)));
            }
        }
        return shapes.stream().distinct().collect(Collectors.toList());
    }
}
