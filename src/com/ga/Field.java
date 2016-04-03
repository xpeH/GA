package com.ga;

public class Field {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    public Shape[][] field;
    private int fieldSize;

    public Field(int size) {
        field = new Shape[size][size];
        fieldSize = field.length - 1;
    }

    public int getSize() {
        return field.length;
    }

    public void placeShape(Shape shape) {
        shape.getShapeVertices().forEach(point -> field[point.getX()][point.getY()] = shape);
    }

    public boolean fitShape(Shape shape) {
//        try {
//            if (getShapeByPoint(point).getShapeVertices().stream().filter(shapePoint -> shapePoint.equals(point)).findFirst().get() != null) {
//                return false;
//            }
//        } catch (Exception ignored) {
//        }
//        List<Point> innerPoints = getShapeByPoint(point).getPoints(Point.Type.INNER);
//        return !innerPoints.stream().anyMatch(innerPoint -> shape.getShapeVertices().contains(innerPoint));

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
        StringBuilder s = new StringBuilder();

        for (int i = fieldSize; i >= 0; i--) {
            for (int j = 0; j <= fieldSize; j++) {

                Point p = new Point(j, i);
                if (field[j][i] != null) {
                    final Point finalP = p;
                    p = field[j][i].getShapeVertices()
                            .stream()
                            .filter(point -> point.getX() == finalP.getX() && point.getY() == finalP.getY())
                            .findFirst()
                            .get();
                    if (p.equals(field[j][i].getCenter())) {
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
}
