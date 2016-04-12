package com.ga.impl;

import com.ga.*;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GA extends ExamplePanel {
    final static int FIELD_SIZE = 20;
    final static int POPULATION_SIZE = 5;
    final static int SHAPES_AMOUNT = 50;
    final static double CROSSOVER_PROB = 0.3;

    public Population population = new Population();
    public Population newPopulation = new Population();

    public static void main(String[] args) {
        GA ga = new GA();
        ga.generatePopulation().calcFitness().crossOver();


//        System.out.println(ga.generateSpecies().field.toString());
////        ga.field.placeShape(new Shape(new Point(5, 5), 90, Type.TRIANGLE));
//        System.out.println(ga.field);
//        System.out.println(ga.field.getFreeRows());
//
        ga.showInFrame(ga.newPopulation.getSpecies(0).field.shapes);
    }

    GA generatePopulation() {
        for (int i = 0; i <= POPULATION_SIZE; i++) {
            population.addSpecies(generateSpecies());
        }
        return this;
    }

    private Species generateSpecies() {
        Field f = new Field(FIELD_SIZE);
        for (int i = 0; i < SHAPES_AMOUNT; i++) {
            try {
                Shape s = generateGene();
                f.placeShape(s);
            } catch (Exception ignored) {
            }
        }
        return new Species(f);
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
        population.sort();
        return this;
    }

    public GA selection() {
        double selProb = new Random().nextDouble();
        population.stream().forEach(species -> {

                }
        );
        return this;
    }

    public GA crossOver() {
        List<Species> crossOverCandidates = population.stream().filter(species -> species.getFitnessValue() / population.fitness < CROSSOVER_PROB).collect(Collectors.toList());

        for (Iterator<Species> iterator = crossOverCandidates.iterator(); iterator.hasNext(); ) {
            Species parent1 = iterator.next();
            Species parent2 = iterator.next();

            getChild(parent1, parent2);

            newPopulation.addSpecies(parent1);
            newPopulation.addSpecies(parent2);
        }
        return this;
    }

    private void getChild(Species parent1, Species parent2) {
        parent1.field.switchWithShapes(parent2.field);
    }
}
