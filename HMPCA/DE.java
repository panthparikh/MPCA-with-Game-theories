/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HMPCA;

import static HMPCA.GA.g;
import static HMPCA.GA.results;
import static HMPCA.HMPCA.pop;
import static HMPCA.HMPCA.pop_size;
import cec2015.Cec2015;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Panth
 */
public class DE {

    static int pop_size = 100, d = 10, g = 1, fitness_func = 13;
    static Particleh[] pop = new Particleh[pop_size];

    public static void main(String[] args) {
        for(int run = 1 ; run < 21; run++){
        double[] fit = new double[10];
        double best_individual = 0, cross_p = 0.5;
        int best = 0;
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
            best_individual = Double.MAX_VALUE;
            for (int i = 0; i < pop_size; i++) {
                if (best_individual > pop[i].fitness[0]) {
                    best_individual = pop[i].fitness[0];
                    best = i;
                }
            }
            //mutation
            Random r = new Random();

            double f = 0.5 + r.nextDouble() * 2.0;
            for (int i = 0; i < pop_size; i++) {
                int x1 = r.nextInt(pop_size);
                int x2 = 0;
                do {
                    x2 = r.nextInt(pop_size);
                } while (x2 == x1);
                for (int j = 0; j < d; j++) {
                    pop[i].mutation[j] = (int) (pop[best].solution[j] + f * (pop[x1].solution[j] - pop[x2].solution[j]));
                }
            }

            //crossover
            for (int i = 0; i < pop_size; i++) {

                int a = r.nextInt(d);
                for (int j = 0; j < d; j++) {
                    double ran = Math.random();
                    if (ran <= cross_p || pop[i].mutation[j] == pop[i].mutation[a]) {
                        pop[i].mutation[j] = pop[i].mutation[j];
                    } else {
                        pop[i].mutation[j] = pop[i].solution[j];
                    }
                }
            }

            //generating offspring
            double[] fitness = new double[1];
            double n_fit = 0;

            for (int i = 0; i < pop_size; i++) {
                fitness = c.eval(pop[i].mutation, d, 1, fitness_func);
                if (fitness[0] < pop[i].fitness[0]) {
                    pop[i].solution = (pop[i].mutation).clone();
                }

            }
            best_individual = Double.MAX_VALUE;
            for (int i = 0; i < pop_size; i++) {
                if (best_individual > pop[i].fitness[0]) {
                    best_individual = pop[i].fitness[0];
                    best = i;
                }
            }

            results(fitness_func, run, best_individual);

            g++;
        }
        g=0;
        }
    }

    public static void results(int a, int r, double fitness) {
        try {
            String s = null, ss = null;

            s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\DE\\DE_"+d+"D\\output_DE_function_";
            ss = "DE\\DE_"+d+"D\\output_DE_function_";

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
