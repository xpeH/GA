package com.ga.impl;

import com.ga.*;
import com.ga.Point;
import com.ga.Shape;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.ui.InteractivePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GA extends JFrame {
    final static int FIELD_SIZE = 20;
    final static int POPULATION_SIZE = 5;
    final static int SHAPES_AMOUNT = 9;

    Population population;
    double fitness;
    Field field;

    public GA() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
    }

    void generatePopulation() {
        for (int i = 0; i <= POPULATION_SIZE; i++) {
            population.addSpecies(generateSpecies());
        }

    }

    public Species generateSpecies() {
        Field f = new Field(FIELD_SIZE);
        for (int i = 0; i < SHAPES_AMOUNT; i++) {
            boolean exception = true;
            while (exception) {
                try {
                    Shape s = generateChromosome();
                    if (f.fitShape(s)) {
                        f.placeShape(s);
                        exception = false;
                    } else {
                        exception = true;
                    }
                } catch (Exception ignored) {
                    exception = true;
                }
            }
        }
        plot(f);

        return new Species(f);
    }

    private void plot(Field f) {
        field = f;
        DataTable data = new DataTable(Double.class, Double.class);

        for (int i = 0; i < f.getSize(); i++) {
            for (int j = 0; j < f.getSize(); j++) {
                Point p = new Point(j, i);
                Shape s = f.getShapeByPoint(p);
                if (s != null) {
                    data.add((double) j, (double) i);
                }
            }
        }

        XYPlot plot = new XYPlot(data);
        getContentPane().add(new InteractivePanel(plot));
//        LineRenderer lines = new DefaultLineRenderer2D();
//        plot.getPointRenderers(data);
    }

    public Shape generateChromosome() {
        Random r = new Random();
        Random r1 = new Random();
        Random r2 = new Random();
        Point p = new Point(r.nextInt(FIELD_SIZE - 1) + 1, r1.nextInt(FIELD_SIZE - 1) + 1);
        return new Shape(p, r2.nextInt(4) * 90, Shape.Type.TRIANGLE);
    }

    public void calcFitness(Species spec) {

    }
}
