/*
 * GRAL: GRAphing Library for Java(R)
 *
 * (C) Copyright 2009-2016 Erich Seifert <dev[at]erichseifert.de>,
 * Michael Seifert <mseifert[at]error-reports.org>
 *
 * This file is part of GRAL.
 *
 * GRAL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GRAL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GRAL.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ga;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.SizeablePointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Random;

/**
 * Abstract base class for all visual examples.
 */
public abstract class ExamplePanel extends JPanel {
    /**
     * First corporate color used for normal coloring.
     */
    protected static final Color COLOR1 = new Color(55, 170, 200);
    /**
     * Second corporate color used as signal color
     */
    protected static final Color COLOR2 = new Color(200, 80, 75);
    /**
     * Version id for serialization.
     */
    private static final long serialVersionUID = 8221256658243821951L;

    /**
     * Performs basic initialization of an example,
     * like setting a default size.
     */
    public ExamplePanel() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
    }

    protected JFrame showInFrame(List<Shape> shapes) {
        JFrame frame = new JFrame(getTitle());
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(getPreferredSize());
        plot(shapes);
        frame.setVisible(true);
        return frame;
    }

    private void plot(List<Shape> shapes) {
        XYPlot plot = new XYPlot();
        shapes.forEach(shape -> {
            DataTable data = new DataTable(Double.class, Double.class, Double.class);
            shape.getShapeVertices().forEach(point -> data.add((double) point.getX(), (double) point.getY(), 3.0));
            plot.add(data);
            setColor(data, plot);
        });
        this.add(new InteractivePanel(plot));
    }

    private void setColor(DataTable data, XYPlot plot) {
        Color color = getColor();
        SizeablePointRenderer pointRenderer = new SizeablePointRenderer();
        pointRenderer.setShape(new Ellipse2D.Double(-2, -2, 2, 2));  // shape of data points
        pointRenderer.setColor(color);  // color of data points
        pointRenderer.setColumn(2);  // data column which determines the scaling of data point shapes
        plot.setPointRenderers(data, pointRenderer);

        LineRenderer line = new DefaultLineRenderer2D();
        line.setColor(color);
        line.setGap(3.0);
        line.setGapRounded(true);
        plot.setLineRenderers(data, line);
    }

    private Color getColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return GraphicsUtils.deriveWithAlpha(new Color(r, g, b), 96);
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public String getTitle() {
        return "Stacked plots";
    }

    public String getDescription() {
        return "An area and a line plot with synchronized actions.";
    }

}
