package dev.molnes;

import java.util.ArrayList;
import java.util.Random;

public class Population {
  private Random random;

  private Chromosome[] population;

  private String target;

  private double mutationRate = 0.01;

  private ArrayList<Chromosome> matingPool;

  private int cycles;

  private boolean finished;

  private int perfectScore;

  private String bestGeneratedString;

  private double crossoverProbability;

  public Population(String target, double mutationRate, int populationSize, double crossoverProbability) {
    random = new Random();
    this.target = target;
    this.mutationRate = mutationRate;
    this.population = new Chromosome[populationSize];
    this.crossoverProbability = crossoverProbability;

    for (int i = 0; i < populationSize; i++) {
      this.population[i] = new Chromosome(target.length());
    }

    calculateFitnessForAll();
    matingPool = new ArrayList<Chromosome>();
    finished = false;
    cycles = 0;
    perfectScore = 1;
  }

  public void calculateFitnessForAll() {
    for (int i = 0; i < population.length; i++) {
      this.population[i].calculateFitness(target);
    }
  }

  public void naturalSelection() {
    matingPool.clear();

    float maxFitness = 0;

    for (int i = 0; i < population.length; i++) {
      if (population[i].getFitness() > maxFitness) {
        maxFitness = population[i].getFitness();
      }
    }

    for (int i = 0; i < population.length; i++) {
      float fitness = 0;
      if (maxFitness != 0) {
        fitness = population[i].getFitness() * (1 / maxFitness);
      }

      int numberOfAppearances = (int) (fitness * 100);
      for (int j = 0; j <= numberOfAppearances; j++) {
        matingPool.add(population[i]);
      }
    }
  }

  public void cycle() {
    bestGeneratedString = getBest().getPhrase();
    if (!bestGeneratedString.equals(target)) {

      for (int i = 0; i < population.length; i++) {
        int randomParentAIndex = random.nextInt(matingPool.size());
        int randomParentBIndex = random.nextInt(matingPool.size());
        Chromosome parentA = matingPool.get(randomParentAIndex);
        Chromosome parentB = matingPool.get(randomParentBIndex);

        if (random.nextDouble() <= crossoverProbability) {
          Chromosome child = parentA.crossover(parentB);
          child.mutate(mutationRate);
          population[i] = child;
        }
      }
      cycles++;
    }
  }

  public Chromosome getBest() {
    double currentBest = 0.0;
    int index = 0;
    for (int i = 0; i < population.length; i++) {
      if (population[i].getFitness() > currentBest) {
        index = i;
        currentBest = population[i].getFitness();
      }
    }
    if (currentBest == perfectScore) {
      finished = true;
    }
    return population[index];
  }

  public String allPhrases() {
    StringBuilder allChromosomes = new StringBuilder();
    for (int i = 0; i < population.length; i++) {
      allChromosomes.append(population[i].getPhrase() + "\n");
    }
    return allChromosomes.toString();
  }

  public boolean getFinished() {
    return finished;
  }

  public String getBestString() {
    return bestGeneratedString;
  }

  public int getCycles() {
    return cycles;
  }

  public String logBestChromosome() {
    return "(" + getBest().getFitness() + "," + getCycles() + "),\n";
  }

}
