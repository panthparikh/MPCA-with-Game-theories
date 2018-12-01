/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HMPCA;

import static HMPCA.DE.d;
import static HMPCA.DE.fitness_func;
import static HMPCA.DE.results;
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
public class HDCA {

    double[] localbf = new double[100];
    static int d = 30, pop_size = 100, local_size, g = 0; // local CA's in the algo
    static int localNoCA = d;
    // d: dimension , pop_size: population
    static Particleh[] pop = new Particleh[pop_size];
    static int fitness_func = 15,run;

    public static void main(String[] args) {
        for (run = 1; run < 21; run++) {
            
            double[] fit = new double[10];
            Random r = new Random();
            local_size = pop_size / localNoCA;
            localHDCA[] lca = new localHDCA[localNoCA];
            double a = 0;

            for (int i = 0; i < localNoCA; i++) {
                lca[i] = new localHDCA();
                lca[i].size = local_size;
                for (int j = 0; j < local_size; j++) {
                    lca[i].noparticle[j] = a;
                    //lca[1].noparticle[0] = a;
                    a++;
                }
            }

            //population initialization
            int k = 0;

            for (int i = 0; i < local_size; i++) {
                for (int j = 0; j < localNoCA; j++) {
                    pop[k] = new Particleh();
                    pop[k].pop_init(d);
                    pop[k].heritage[i][0] = 1.0;
                    pop[k].heritage[i][1] = 1.0;
                    k++;
                }
            }

            Cec2015 c = new Cec2015();

            //generation loop
            while (g < 100) {
                //calculating fitness
                for (int i = 0; i < local_size*d; i++) {
                    fit = c.eval(pop[i].solution, d, 1, fitness_func);
                    pop[i].fitness = fit;
                }

                // updating local belief space
                for (int i = 0; i < localNoCA; i++) {
                    updatelocalbf(lca[i], lca[i].size, i);
                }
                for (int i = 0; i < d; i++) {
                    for (int j = 0; j < local_size; j++) {
                        lca[i].noparticle[j] = 0;
                    }
                    lca[i].noparticle[0] = lca[i].best;
                    lca[i].size = 1;
                }

                //generating new offspring
                int parents[] = new int[d];
                int no = 0;
                for (int i = 0; i < d; i++) {
                    parents[i] = lca[i].best;
                }
                for (int i = 0; i < local_size*d; i++) {
                    for (int j = 0; j < d; j++) {
                        if (i == parents[j]) {
                            no = 1;
                        }
                    }
                    if (no == 0) {
                        int p1 = r.nextInt(d);
                        int p2 = r.nextInt(d);

                        int parent1 = lca[p1].best;
                        int parent2 = lca[p2].best;

                        int l = r.nextInt(d);
                        lca[l].noparticle[lca[l].size] = l;
                        lca[l].size++;
                        generateoffspring(parent1, parent2, p1, p2, i, l);

                        mutation(i);

                    }
                    no = 0;
                }

                for (int i = 0; i < local_size*d; i++) {
                    fit = c.eval(pop[i].solution, d, 1, fitness_func);
                    pop[i].fitness = fit;
                }
                double fittest = Double.MAX_VALUE;

                for (int i = 0; i < local_size*d; i++) {
                    if (pop[i].fitness[0] < fittest) {
                        fittest = pop[i].fitness[0];
                    }
                }
                results(fitness_func, run, fittest);

                g++;
            }
            g=0;
        }
    }

    public static void results(int a, int r, double fitness) {
        try {
            String s = null, ss = null;

            s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\HDCA\\HDCA_" + d + "D\\output_HDCA_function_";
            ss = "HDCA\\HDCA_" + d + "D\\output_HDCA_function_";

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
            Logger.getLogger(HMPCA.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updatelocalbf(localHDCA l, int size, int lca_no) {
        double[] a = new double[size];
        int no, ne;

        a = l.noparticle.clone();
        ne = (int) a[0];
        l.best = (int) ne;
        l.localbf = pop[ne].fitness[0];
        for (int i = 0; i < size; i++) {
            double c, b;
            no = (int) a[i];
            if (pop[no].fitness[0] < l.localbf) {

                l.localbf = pop[no].fitness[0];
                l.best = (int) no;
            }
        }
        int j = 0;
        for (int i = 0; i < local_size*d; i++) {

            if (pop[i].heritage[lca_no][0] != 0) {
                l.particle[j] = pop[i].solution[lca_no];
                l.fitness[j] = pop[i].fitness[0];
                j++;

            }
        }
    }

    public static void generateoffspring(int p1, int p2, int d1, int d2, int c, int l) {
        Random r = new Random();
        for (int i = 0; i < d; i++) {
            if (i == d1) {
                pop[c].solution[i] = pop[p1].solution[i];
                pop[c].heritage[i][0] = pop[p1].heritage[i][0] / 2;
            } else if (i == d2) {
                pop[c].solution[i] = pop[p2].solution[i];
                pop[c].heritage[i][0] = pop[p2].heritage[i][0] / 2;
            } else {
                for (int j = 0; j < d; j++) {
                    if (pop[p1].heritage[i][1] != 0 && pop[p2].heritage[i][1] != 0) {
                        if (pop[p1].heritage[i][1] > pop[p2].heritage[i][1]) {
                            pop[c].solution[i] = pop[p1].solution[i];
                            pop[c].heritage[i][0] = pop[p1].heritage[i][0] / 2;
                        } else {
                            pop[c].solution[i] = pop[p2].solution[i];
                            pop[c].heritage[i][0] = pop[p2].heritage[i][0] / 2;
                        }
                    } else if (pop[p1].heritage[i][1] != 0) {
                        pop[c].solution[i] = pop[p1].solution[i];
                        pop[c].heritage[i][0] = pop[p1].heritage[i][0] / 2;
                    } else if (pop[p2].heritage[i][1] != 0) {
                        pop[c].solution[i] = pop[p2].solution[i];
                        pop[c].heritage[i][0] = pop[p2].heritage[i][0] / 2;
                    } else {
                        pop[c].solution[i] = (double) r.nextInt(201) - 100;
                        pop[c].heritage[i][0] = 0;
                    }

                }
            }
        }
        pop[c].heritage[l][0] += 0.5;
        double influence = 0.0;
        for (int i = 0; i < d; i++) {
            influence += pop[c].heritage[i][0];
        }
        for (int i = 0; i < d; i++) {
            pop[c].heritage[i][1] = pop[c].heritage[i][0] / influence;
        }

    }

    public static void mutation(int c) {
        Random r = new Random();
        for (int i = 0; i < d; i++) {

            if (Math.random() > 0.5) {
                pop[c].solution[i] = pop[c].solution[i] + (double) r.nextInt(5);
            }

        }

    }

}
