package com.ga;

import java.util.ArrayList;
import java.util.List;

public class Population {
    List<Species> population = new ArrayList<>();

    public void addSpecies(Species s) {
        population.add(s);
    }
}
