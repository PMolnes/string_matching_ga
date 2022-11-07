package dev.molnes;

import java.util.Random;

public class Chromosome {

  private Random random;
  private char[] genes;
  private float fitness;
  private final String POSSIBLE_GENES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";

  private char generateRandomChar() {
    return POSSIBLE_GENES.charAt(random.nextInt(POSSIBLE_GENES.length()));
  }

  public Chromosome(int num) {
    random = new Random();
    genes = new char[num];
    for (int i = 0; i < genes.length; i++) {
      genes[i] = generateRandomChar();
    }
    fitness = 0;
  }

  public String getPhrase() {
    return (new String(genes)) + " | Fitness: " + getFitness();
  }

  public float getFitness() {
    return fitness;
  }

  public void calculateFitness(String target) {
    int fitness = 0;
    for (int i = 0; i < genes.length; i++) {
      if (genes[i] == target.charAt(i)) {
        fitness++;
      }
    }
    this.fitness = (float) fitness / (float) target.length();
  }

  public Chromosome crossover(Chromosome partner) {
    Chromosome child = new Chromosome(genes.length);
    int splitIndex = random.nextInt(genes.length);

    for (int i = 0; i < genes.length; i++) {
      if (i < splitIndex) {
        child.setGenesAtIndex(getGenes(), i);
      } else {
        child.setGenesAtIndex(partner.getGenes(), i);
      }
    }
    return child;
  }

  public Chromosome doublePointCrossover(Chromosome partner) {
    Chromosome child = new Chromosome(genes.length);
    int firstSplitIndex = random.nextInt(genes.length);
    int secondSplitIndex = random.nextInt(genes.length);

    for (int i = 0; i < genes.length; i++) {
      if (i < firstSplitIndex) {
        child.setGenesAtIndex(getGenes(), i);
      } else if (i < secondSplitIndex) {
        child.setGenesAtIndex(partner.getGenes(), i);
      } else {
        child.setGenesAtIndex(getGenes(), i);
      }
    }
    return child;
  }

  public void mutate(double mutationRate) {
    for (int i = 0; i < genes.length; i++) {
      if (random.nextDouble() < mutationRate) {
        genes[i] = generateRandomChar();
      } 
    }
  }

  public void setGenesAtIndex(char[] genes, int index) {
    this.genes[index] = genes[index];
  }

  private char[] getGenes() {
    return genes;
  }

}
