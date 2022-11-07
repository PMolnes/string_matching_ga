package dev.molnes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

  private static final int MAX_GENERATIONS = 1000;
  private static final double CROSSOVER_PROBABILITY = 1.0;
  private static Population population = new Population("Petter Molnes", 0.01, 1000, CROSSOVER_PROBABILITY);

  public static void main(String[] args) {

    try {
      File file = new File("./generationResults.txt");
      if (file.createNewFile()) {
        System.out.println("File created");
      }
      FileWriter writer = new FileWriter(file);

    while (!population.getFinished() && population.getCycles() < MAX_GENERATIONS) {
      population.calculateFitnessForAll();

      System.out.println(population.allPhrases());

      writer.write(population.logBestChromosome());

      population.naturalSelection();

      population.cycle();

    }
    writer.close();
    System.out.println();
    System.out.println("Best final string: " + population.getBestString());
    System.out.println("Generations: " + population.getCycles());
  } catch(IOException e) {
    System.out.println(e.getMessage());
  }
  }
}
