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

    private Shape[][] field = new Shape[20][20];


    public void placeShape(Shape shape) {
        shape.getShapeVertices().forEach(point -> field[point.getX()][point.getY()] = shape);
    }

    public boolean fitShape(Shape shape, Point Point) {
        boolean fits = false;


        return fits;
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

//        for (int i = 19; i > 0; i--) {
//            for (int j = 19; j > 0; j--) {

        for (int i = 19; i > 0; i--) {
            for (int j = 19; j > 0; j--) {


                Point p = new Point(j, i);
                if (field[j][i] != null) {
                    if (p.equals(field[j][i].getCenter())) {
                        s.append(ANSI_YELLOW + "o" + ANSI_RESET);
                    } else {
                        s.append(ANSI_RED + "o" + ANSI_RESET);
                    }
                } else {
                    s.append("o");
                }
            }
            s.append("\n");
        }

        return s.toString();
    }
}
