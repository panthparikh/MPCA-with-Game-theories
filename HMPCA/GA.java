/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HMPCA;

import static HMPCA.HMPCA.gb;
import static HMPCA.HMPCA.pop_size;
import cec2015.Cec2015;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Panth
 */
public class GA {

    static int pop_size = 100, d = 30, g = 1, fitness_func = 1, parent_percent = 10;
    static Particleh[] pop = new Particleh[pop_size];

    public static void main(String[] args) {
        for (int run = 1; run < 21; run++) {
            double[] fit = new double[10];
            int p = (parent_percent * 100) / pop_size;
            double[] parent = new double[20];
            double[] child = new double[pop_size - p];
            double[] fitness = new double[pop_size];
            double uniform_cross = 0.5, mutation_rate = 0.5;
            //population initialization
            for (int i = 0; i < pop_size; i++) {
                pop[i] = new Particleh();
                pop[i].pop_init(d);
            }

            Cec2015 c = new Cec2015();

            while (g < 101) {
                // calculating fitness
                for (int i = 0; i < pop_size; i++) {
                    fit = c.eval(pop[i].solution, d, 1, fitness_func);
                    pop[i].fitness = fit;
                }

                // selection process
                for (int i = 0; i < pop_size; i++) {
                    fitness[i] = pop[i].fitness[0];
                }
                int l = 0;
                Arrays.sort(fitness);
                int k = 0, m = 0;
                for (int i = 0; i < p; i++) {
                    l = 0;
                    for (int j = 0; j < pop_size; j++) {

                        if (fitness[i] == pop[j].fitness[0]) {
                            parent[k] = j;
                            k++;
                            l = 1;
                        }
                    }
                    if (l == 0) {
                        child[m] = i;
                        m++;
                    }
                }

                // crossover
                Random r = new Random();
                int parent1 = r.nextInt(p);
                int parent2 = 0;
                do {
                    parent2 = r.nextInt(p);
                } while (parent1 == parent2);
                int p1 = (int) parent[parent1];
                int p2 = (int) parent[parent2];
                for (int i = 0; i < m; i++) {
                    int q = (int) child[i];
                    for (int j = 0; j < d; j++) {

                        if (Math.random() <= uniform_cross) {
                            pop[q].solution[j] = pop[p1].solution[j];
                        } else {
                            pop[q].solution[j] = pop[p2].solution[j];
                        }

                    }

                }

                // Mutation
                for (int i = 0; i < pop_size; i++) {
                    double rand = Math.random();
                    int q = (int) child[i];
                    for (int j = 0; j < d; j++) {
                        int a = 0;
                        if (rand > mutation_rate) {
                            do {
                                a = r.nextInt(d);
                            } while (a == j);
                            double temp = pop[i].solution[j];
                            pop[i].solution[j] = pop[i].solution[a];
                            pop[a].solution[j] = temp;
                        }
                    }
                }

                for (int i = 0; i < pop_size; i++) {
                    fit = c.eval(pop[i].solution, d, 1, fitness_func);
                    pop[i].fitness = fit;
                }

                for (int i = 0; i < pop_size; i++) {
                    fitness[i] = pop[i].fitness[0];
                }
                Arrays.sort(fitness);

                results(fitness_func, run, fitness[0]);

                g++;
            }
            g=0;
        }
    }

    public static void results(int a, int r, double fitness) {
        try {
            String s = null, ss = null;

            s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\GA\\GA_" + d + "D\\output_GA_function_";
            ss = "GA\\GA_" + d + "D\\output_GA_function_";

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
}
