package com.ga;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Population implements Iterable {
    public double fitness;
    List<Species> population = new ArrayList<>();

    public void addSpecies(Species s) {
        population.add(s);
    }

    public int getSize() {
        return population.size();
    }

    @Override
    public Iterator<Species> iterator() {
        return population.iterator();
    }

    public Stream<Species> stream() {
        return population.stream();
    }

}
