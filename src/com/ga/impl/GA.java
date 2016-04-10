package com.ga.impl;

import com.ga.*;

import java.util.Random;

public class GA extends ExamplePanel {
    final static int FIELD_SIZE = 20;
    final static int POPULATION_SIZE = 5;
    final static int SHAPES_AMOUNT = 50;

    public Population population = new Population();
    public Field field = new Field(FIELD_SIZE);

    public static void main(String[] args) {
        GA ga = new GA();
        ga.generatePopulation().calcFitness();


        System.out.println(ga.generateSpecies().field.toString());
//        ga.field.placeShape(new Shape(new Point(5, 5), 90, Type.TRIANGLE));
        System.out.println(ga.field);
        System.out.println(ga.field.getFreeRows());

        ga.showInFrame(ga.field.shapes);
    }

    GA generatePopulation() {
        for (int i = 0; i <= POPULATION_SIZE; i++) {
            population.addSpecies(generateSpecies());
        }
        return this;
    }

    private Species generateSpecies() {
        for (int i = 0; i < SHAPES_AMOUNT; i++) {
            try {
                Shape s = generateGene();
                field.placeShape(s);
            } catch (Exception ignored) {
            }
        }
        return new Species(field);
    }

    public Shape generateGene() {
        Random r = new Random();
        Random r1 = new Random();
        Random r2 = new Random();

        Point p = new Point(r.nextInt(FIELD_SIZE - 1) + 1, r1.nextInt(FIELD_SIZE - 1) + 1);
        return new Shape(p, r2.nextInt(4) * 90, Shape.Type.TRIANGLE);
    }

    public GA calcFitness() {
        population.stream().forEach(species -> {
            population.fitness += (double) species.field.shapes.size();
            species.setFitness(species.field.shapes.size());
        });
        return this;
    }

    public GA selection() {
        double selProb = new Random().nextDouble();
        population.stream().forEach(species -> {

                }
        );
        return this;
    }
}
