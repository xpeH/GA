package com.ga;

public class Species {
    public Field field;
    double fitnessValue;

    public Species(Field field) {
        this.field = field;
    }

    public void setFitness(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }
}
