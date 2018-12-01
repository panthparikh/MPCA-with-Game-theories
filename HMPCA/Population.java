/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HMPCA;

/**
 *
 * @author Panth
 */
import static HMPCA.GA.d;
import static HMPCA.GA.results;
import cec2015.Cec2015;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Population {
    final static int ELITISM_K = 5;
    final static int POP_SIZE = 100 + ELITISM_K;  // population size
    final static int MAX_ITER = 100;             // max number of iterations
    final static double MUTATION_RATE = 0.05;     // probability of mutation
    final static double CROSSOVER_RATE = 0.7;     // probability of crossover
    static int ft_fn = 15;
    private static Random m_rand = new Random();  // random-number generator
    private Individual[] m_population;
    private double totalFitness;

    public Population() {
        m_population = new Individual[POP_SIZE];

        // init population
        for (int i = 0; i < POP_SIZE; i++) {
            m_population[i] = new Individual();
            m_population[i].randGenes();
        }

        // evaluate current population
        this.evaluate();
    }

    public void setPopulation(Individual[] newPop) {
        // this.m_population = newPop;
        System.arraycopy(newPop, 0, this.m_population, 0, POP_SIZE);
    }

    public Individual[] getPopulation() {
        return this.m_population;
    }

    public double evaluate() {
        Cec2015 c= new Cec2015();
        double[] pop = new double[10];
         double[] fit = new double[3];
        this.totalFitness = 0.0;
        for (int i = 0; i < POP_SIZE; i++) {
           // this.totalFitness += m_population[i].evaluate();
            for(int j=0;j< 10;j++)
            {
                pop[j] = (double) m_population[i].getGene(j);
            }
           
            fit = c.eval(pop, 10, 1, ft_fn);
            m_population[i].setFitnessValue(fit[0]);
            this.totalFitness += fit[0];
        }
        return this.totalFitness;
    }

    public Individual rouletteWheelSelection() {
        double randNum = m_rand.nextDouble() * this.totalFitness;
        int idx;
        for (idx=0; idx<POP_SIZE && randNum>0; ++idx) {
            randNum -= m_population[idx].getFitnessValue();
        }
        return m_population[idx-1];
    }

    public Individual findBestIndividual() {
        int idxMax = 0, idxMin = 0;
        double currentMax = 0.0;
        double currentMin = 1.0;
        double currentVal;

        for (int idx=0; idx<POP_SIZE; ++idx) {
            currentVal = m_population[idx].getFitnessValue();
            if (currentMax < currentMin) {
                currentMax = currentMin = currentVal;
                idxMax = idxMin = idx;
            }
            if (currentVal < currentMax) {
                currentMax = currentVal;
                idxMax = idx;
            }
            if (currentVal > currentMin) {
                currentMin = currentVal;
                idxMin = idx;
            }
        }

        //return m_population[idxMin];      // minimization
        return m_population[idxMax];        // maximization
    }

    public static Individual[] crossover(Individual indiv1,Individual indiv2) {
        Individual[] newIndiv = new Individual[2];
        newIndiv[0] = new Individual();
        newIndiv[1] = new Individual();

        int randPoint = m_rand.nextInt(Individual.SIZE);
        int i;
        for (i=0; i<randPoint; ++i) {
            newIndiv[0].setGene(i, indiv1.getGene(i));
            newIndiv[1].setGene(i, indiv2.getGene(i));
        }
        for (; i<Individual.SIZE; ++i) {
            newIndiv[0].setGene(i, indiv2.getGene(i));
            newIndiv[1].setGene(i, indiv1.getGene(i));
        }

        return newIndiv;
    }

public static void results(int a, int r, double fitness) {
        try {
            String s = null, ss = null;

            s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\GA\\GA_" + 10 + "D\\output_GA_function_";
            ss = "GA\\GA_" + 10 + "D\\output_GA_function_";

            File f = new File(s + a + "run_" + r + ".txt");
            if (f.exists() && !f.isDirectory()) {
                FileWriter fw = new FileWriter(f, true);
                fw.append(fitness + "  ");
                //fw.append(" Change "+change +"  ");
                fw.close();
            } else {
                PrintWriter writer = new PrintWriter(ss + a + "run_" + r + ".txt", "UTF-8");
                writer.append(fitness + "  ");
                writer.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(HMPCA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    public static void main(String[] args) {
        for(int run =1 ;run < 21;run++){
        Population pop = new Population();
        Individual[] newPop = new Individual[POP_SIZE];
        Individual[] indiv = new Individual[2];

        // current population
        System.out.print("Total Fitness = " + pop.totalFitness);
        System.out.println(" ; Best Fitness = " + 
            pop.findBestIndividual().getFitnessValue());
        double fittest = pop.findBestIndividual().getFitnessValue();
        results(ft_fn, run, fittest);
        // main loop
        int count;
        for (int iter = 0; iter < MAX_ITER; iter++) {
            count = 0;

            // Elitism
            for (int i=0; i<ELITISM_K; ++i) {
                newPop[count] = pop.findBestIndividual();
                count++;
            }

            // build new Population
            while (count < POP_SIZE) {
                // Selection
                indiv[0] = pop.rouletteWheelSelection();
                indiv[1] = pop.rouletteWheelSelection();

                // Crossover
                if ( m_rand.nextDouble() < CROSSOVER_RATE ) {
                    indiv = crossover(indiv[0], indiv[1]);
                }

                // Mutation
                if ( m_rand.nextDouble() < MUTATION_RATE ) {
                    indiv[0].mutate();
                }
                if ( m_rand.nextDouble() < MUTATION_RATE ) {
                    indiv[1].mutate();
                }

                // add to new population
                newPop[count] = indiv[0];
                newPop[count+1] = indiv[1];
                count += 2;
            }
            pop.setPopulation(newPop);

            // reevaluate current population
            pop.evaluate();
            System.out.print("Total Fitness = " + pop.totalFitness);
            System.out.println(" ; Best Fitness = " +
                pop.findBestIndividual().getFitnessValue()); 
            double fittest1 = pop.findBestIndividual().getFitnessValue();
        results(ft_fn, run, fittest1);
        }

        // best indiv
        Individual bestIndiv = pop.findBestIndividual();
        }
    }
}
    
