/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HMPCA;

import cec2015.Cec2015;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Panth
 */
public class HMPCA {

    double[] localbf = new double[100];

    double[] globalbf = new double[100];
    static int localNoCA = 10, d = 10, pop_size = 100, local_size, g = 0; // local CA's in the algo
    // d: dimension , pop_size: population
    static Particleh[] pop = new Particleh[pop_size];
    static globalbf gb = new globalbf();
    static int change = 0;
    static int fitness_func = 11;

    public static void main(String args[]) {
         //int run = 1; // function number and run number
        for (int run = 20; run < 21 ; run++) {

            double[] fit = new double[10];
            double mmfit = 0;
            double[] lbs = new double[10];
            local_size = pop_size / localNoCA;
            localCA[] lca = new localCA[localNoCA];
            double a = 0;
            Random rs = new Random();
            for (int i = 0; i < localNoCA; i++) {
                lca[i] = new localCA();
                lca[i].size = local_size;
                lca[i].strategy = rs.nextInt(3);
                for (int j = 0; j < local_size; j++) {
                    lca[i].noparticle[j] = a;
                    //lca[1].noparticle[0] = a;
                    a++;
                }
            }
            //population initialization
            for (int i = 0; i < pop_size; i++) {
                pop[i] = new Particleh();
                pop[i].pop_init(d);
            }
           Cec2015 c = new Cec2015();
          //  OneMax m = new OneMax();
            //generation loop
            while (g < 500) {
                //calculating fitness
                for (int i = 0; i < pop_size; i++) {
                    fit = c.eval(pop[i].solution, d, 1, fitness_func);
                    pop[i].fitness = fit;
                }
                
           //     for (int i = 0; i < pop_size; i++) {
             //       mmfit = m.get(pop[i].solution,d);
              //      pop[i].fitness[0] = mmfit;
               // }
                
                // updating local belief space
                for (int i = 0; i < localNoCA; i++) {
                    updatelocalbf(lca[i], lca[i].size);
                }
                // updating global belief space

                for (int i = 0; i < localNoCA; i++) {
                    updategbf(lca[i], i);
                }
                //mutation
                Random r = new Random();
                double f = 0.5 + r.nextDouble() * 2.0;
                for (int i = 0; i < localNoCA; i++) {
                    mutation(lca[i], lca[i].size, f);
                }

                // crossover
                for (int i = 0; i < localNoCA; i++) {
                    // double cross_p;
                    //cross_p = Math.random();
                    crossover(lca[i], lca[i].size, 0.5);
                }

                // generation offspring
                offspring();

                // setting the best solution
                gb.set_best(localNoCA);
                // average fitness
                for (int i = 0; i < localNoCA; i++) {
                    averagefitness(lca[i], i, lca[i].size);
                }
               // results(fitness_func, run, 0);
                
                 // region random migration
                 //--------------------------------------------------------------------------------------------------------------------------------
             /*    if (g % 10 == 0) {
                 int best = random_migration();
                 Random rand = new Random();
                 int migrate = rand.nextInt(lca[best].size);
                 //int migrate =lca[best].best;
                 int destination;
                 do {
                 destination = rand.nextInt(localNoCA);
                 } while (destination == best);
                 random_migration1(lca[best], lca[destination], migrate);
                 }
                 results(fitness_func, run, 1);*/
                 //end region random migration
                 //---------------------------------------------------------------------------------------------------------------------------------
                 // region prisoner's dilemma strategy
                 //--------------------------------------------------------------------------------------------------------
                if (g % 10 == 0) {
                 int bestlca = random_migration();
                 Random rand = new Random();
                 int migrate = rand.nextInt(lca[bestlca].size);
                 int changes = 0;
                 int r_migrate = 0;
                 do {
                 r_migrate = rand.nextInt(lca[bestlca].size);
                 } while (migrate == r_migrate);
                 int t_mig = (int) lca[bestlca].noparticle[migrate];
                 int pd = (int) lca[bestlca].noparticle[r_migrate];
                 if (pop[t_mig].fitness[0] > pop[pd].fitness[0]) {
                 pop[t_mig].strategy = pop[pd].strategy;
                 changes =1;
                 }
                 if (pop[t_mig].strategy == 1) {
                 double bestca = gb.get_best();
                 int b=0;
                 for(int i=0;i<localNoCA;i++){
                 if(gb.gbf[i] == bestca){
                 b=i;
                 }
                 }
                 if (b == bestlca) {
                 double[] select = new double[localNoCA];
                 select = (gb.gbf).clone();
                 double high1 = 0;
                 double high2 = 0;
                 int h1 = 0, h2 = 0;
                 for (int i = 0; i < localNoCA; i++) {
                 if (select[i] > high1) {
                 high2 = high1;
                                    
                 high1 = select[i];
                 } else if (select[i] > high2) {
                 high2 = select[i];
                                    
                 }
                 }
                 for(int i=0;i<localNoCA;i++){
                 if(gb.avg_fitness[i] == high2)
                 h2 = i;
                 }
                 int m_lca = h2;
                 random_migration1(lca[bestlca], lca[h2], migrate);
                 } else {
                 for (int i = 0; i < localNoCA; i++) {
                 if (gb.best == gb.gbf[i]) {
                 random_migration1(lca[bestlca], lca[i], migrate);
                 }
                 }
                 }
                 } else if (pop[t_mig].strategy == 0) {
                        
                 
                 int w=0;
                 if(changes == 0){
                     double worst = gb.avg_fitness[0];
                 for(int i=0; i<localNoCA;i++){
                 if(gb.avg_fitness[i] > worst){
                 worst = gb.avg_fitness[i];
                 w=i;
                 }
                 }
                 }
                 if(changes == 1){
                 double [] changed = new double[localNoCA];
                 int mno = (int)lca[bestlca].noparticle[migrate];
                 int y=0;
                 double mfit = pop[mno].fitness[0];
                 for(int i=0 ; i<localNoCA; i++){
                 if(gb.avg_fitness[i] > mfit){
                 changed[y] = gb.avg_fitness[i];
                 y++;
                 }
                               
                 }
                 Arrays.sort(changed);
                 if(changed.length == 0){
                 changed = gb.avg_fitness.clone();
                 Arrays.sort(changed);
                 for(int i=0 ;i < localNoCA;i++){
                 if(changed[1] == gb.avg_fitness[i])
                 {
                 w=i;
                 }
                 }
                 }else{
                 mfit = changed[0];
                 for(int i=0 ;i < localNoCA;i++){
                 if(mfit == gb.avg_fitness[i])
                 {
                 w=i;
                 }
                 }
                 }
                 }
                        
                 random_migration1(lca[bestlca], lca[w], migrate);
                 }
                 }
                 results(fitness_func, run, 2);

                g++;

            }
            g = 0;
       }

    }

    public static void updatelocalbf(localCA l, int size) {
        double[] a = new double[size];
        int no, ne;
      //  int[] f1 = new int[10];
      //  int[] f2 = new int[10];
        a = l.noparticle.clone();
        ne = (int) a[0];
        l.best = (int) ne;
        l.particle = (pop[ne].solution).clone();
        l.localbf = pop[ne].fitness[0];
        for (int i = 0; i < size; i++) {
            double c, b;
            no = (int) a[i];
            if (pop[no].fitness[0] < l.localbf) {

                l.localbf = pop[no].fitness[0];
                l.particle = pop[no].solution.clone();
                l.best = (int) no;
            }
        }
    }

    public static void updategbf(localCA l, int i) {
        gb.gbf[i] = l.localbf;
        gb.pno[i] = l.best;
    }

    public static void mutation(localCA l, int size, double f) {
        double[] p = new double[d]; // best solution of CA
        double[] p1 = new double[d]; //first random solution
        double[] p2 = new double[d]; // second random solution
        int b = l.best;
        double[] no = new double[size];
        no = (l.noparticle).clone();
        p = (pop[b].solution).clone();
        int one, two;
        Random r = new Random();

        for (int i = 0; i < size; i++) {
            do {
                one = r.nextInt(l.size);
                two = r.nextInt(l.size);
            } while (one == b || two == b);
            int num = (int) no[one];
            int num1 = (int) no[two];
            p1 = (pop[num].solution).clone();
            p2 = (pop[num1].solution).clone();
            int a = (int) no[i];
            for (int j = 0; j < d; j++) {
                pop[a].mutation[j] = (int)(p[j] + f * (p1[j] - p2[j]));
            }
        }
    }

    public static void crossover(localCA l, int size, double cross_p) {
        double[] no = new double[size];
        double r;
        no = (l.noparticle).clone();

        Random rand = new Random();
        for (int i = 0; i < size; i++) {

            int b = (int) no[i];
            int a = rand.nextInt(d);
            for (int j = 0; j < d; j++) {
                r = Math.random();
                if (r <= cross_p || pop[b].mutation[j] == pop[b].mutation[a]) {
                    pop[b].mutation[j] = pop[b].mutation[j];
                } else {
                    pop[b].mutation[j] = pop[b].solution[j];
                }
            }
        }
    }

    public static void offspring() {
        double[] fitness = new double[1];
        double fit = 0;
        Cec2015 c = new Cec2015();
      //  OneMax m = new OneMax();
        
       for (int i = 0; i < pop_size; i++) {
            fitness = c.eval(pop[i].mutation, d, 1, fitness_func);
            if (fitness[0] < pop[i].fitness[0]) {
                pop[i].solution = (pop[i].mutation).clone();
                change++;
            }
        }
        
        //for (int i = 0; i < pop_size; i++) {
          //  fit = m.get(pop[i].mutation, d);
           // if (fit > pop[i].fitness[0]) {
             //   pop[i].solution = (pop[i].mutation).clone();
               // change++;
           // }
       // }
    }

    public static void averagefitness(localCA l, int i, int size) {
        double avg_fit = 0, sum = 0;
        double[] no = new double[size];
        no = (l.noparticle).clone();
        for (int j = 0; j < l.size; j++) {
            int a = (int) no[j];
            sum = sum + pop[a].fitness[0];
        }
        avg_fit = sum / l.size;
        gb.avg_fitness[i] = avg_fit;
        l.avg_fitness = avg_fit;
    }
// returns the best performing local CA in context of average fitness

    public static int random_migration() {

        double best = gb.avg_fitness[0];
        int best_number = 0;
        for (int i = 0; i < localNoCA; i++) {
            if (gb.avg_fitness[i] < best) {
                best = gb.avg_fitness[i];
                best_number = i;
            }
        }
        return best_number;
    }

    public static int second_best() {
        double best = gb.avg_fitness[0];
        int x = 0, y = 0;
        double high1 = Double.MAX_VALUE, high2 = Double.MAX_VALUE;
        for (int i = 0; i < localNoCA; i++) {
            if (gb.avg_fitness[i] > high1) {
                y = x;
                high2 = high1;
                x = i;
                high1 = gb.avg_fitness[i];
            } else if (gb.avg_fitness[i] > high2) {
                y = i;
                high2 = gb.avg_fitness[i];
            }
        }
        return y;
    }

    public static void random_migration1(localCA m, localCA d, int migrate) {
        int pop = 0;
        //pop = (int)m.best;
        //int x =0;
        //for(int i=0 ;i< m.size;i++){
        pop = (int) m.noparticle[migrate];
            //int x= (int)m.noparticle[i];
        //if(m.best == (int)m.noparticle[i]){
        //  x = i;
        //}

        //}
        d.noparticle[d.size] = pop;
        d.size++;
        for (int i = migrate; i < m.size; i++) {
            m.noparticle[i] = m.noparticle[i + 1];
        }
        m.size--;

    }

    public static void duopoly_migration(localCA m, localCA d, int migrate) {
        int pop = 0;
        //pop = (int)m.best;
        //int x =0;
        //for(int i=0 ;i< m.size;i++){
        pop = (int) m.noparticle[migrate];
            //int x= (int)m.noparticle[i];
        //if(m.best == (int)m.noparticle[i]){
        //  x = i;
        //}

        //}
        d.noparticle[d.size] = pop;
        d.size++;
        for (int i = migrate; i < m.size; i++) {
            m.noparticle[i] = m.noparticle[i + 1];
        }
        m.size--;
        int rpop = d.best;
        m.noparticle[m.size] = rpop;
        m.size++;
        int a = 0;
        for (int i = 0; i < d.size; i++) {
            if ((int) d.noparticle[i] == rpop) {
                a = i;
            }
        }
        for (int i = a; i < d.size; i++) {
            d.noparticle[i] = d.noparticle[i + 1];
        }
        d.size--;

    }

    public static void results(int a, int r, int algo) {
        try {
            String s = null, ss = null;
            if (algo == 0) {
                s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\MPCA_results_function1_10D\\output_MPCA_function_";
                ss = "MPCA_results_function1_10D\\output_MPCA_function_";
            } else if (algo == 1) {
                s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\Random_migration_10D\\output_Random_Migration_function_";
                ss = "Random_Migration_10D\\output_Random_Migration_function_";
            } else if (algo == 2) {
                s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\Schwefel\\Schwefel_10D\\PD\\output_PD_function_";
                ss = "Schwefel\\Schwefel_10D\\PD\\output_PD_function_";
            } else if (algo == 3) {
                s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\Schwefel\\Schwefel_10D\\DU\\output_DU_function_";
                ss = "Schwefel\\Schwefel_10D\\DU\\output_DU_function_";
            } else if (algo == 4) {
                s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\Schwefel\\Schwefel_10D\\OG\\output_OG_function_";
                ss = "Schwefel\\Schwefel_10D\\OG\\output_OG_function_";
            } else if (algo == 5) {
                s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\Schwefel\\Schwefel_10D\\FD\\output_FD_function_";
                ss = "Schwefel\\Schwefel_10D\\FD\\output_FD_function_";
            } else if (algo == 6) {
                s = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\Schwefel\\Schwefel_10D\\HB\\output_HB_function_";
                ss = "Schwefel\\Schwefel_10D\\HB\\output_HB_function_";
            }
            double solution = gb.get_best();
            File f = new File(s + a + "run_" + r + ".txt");
            if (f.exists() && !f.isDirectory()) {
                FileWriter fw = new FileWriter(f, true);
                fw.append(solution + "  ");
                //fw.append(" Change "+change +"  ");
                fw.close();
            } else {
                PrintWriter writer = new PrintWriter(ss + a + "run_" + r + ".txt", "UTF-8");
                writer.append(solution + "  ");
                writer.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(HMPCA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int answer(double m, double[] result, int size) {
        for (int i = 0; i < size; i++) {
            if (m == result[i]) {
                return i;
            }
        }
        return 0;
    }

   
}


                 //--------------------------------------------------------------------------------------------------------
                 //end region
                //-----------------------------------------------------------------------------------------------------
                //region Duopoly
                 
             /*  if(g % 10 == 0)
                 {
                 int bestlca = random_migration();
                 int  s_bestlca = second_best();
                 Random rand = new Random();
                 int migrate = rand.nextInt(lca[bestlca].size);
                 int migrate2 = rand.nextInt(lca[s_bestlca].size);
                 int destination,destination2;
                 do {
                 destination = rand.nextInt(localNoCA);
                 } while (destination == bestlca || destination == s_bestlca);
                 duopoly_migration(lca[bestlca], lca[destination], migrate);
                 
                 do {
                 destination2 = rand.nextInt(localNoCA);
                 } while (destination2 == bestlca || destination2 == s_bestlca || destination2 == destination);
                 duopoly_migration(lca[s_bestlca], lca[destination2], migrate2);
                 
                 
                 }
                    
                 results(fitness_func, run, 3);*/
                 
                 //-------------------------------------------------------------------------------------------------------
                //------------------------------------------------------------------------------------------------------
                //region Oligopoly
            /* if(g%10 == 0){
                 double avg_fit[] = new double[10];
                 avg_fit=(gb.avg_fitness).clone();
                 Arrays.sort(avg_fit);
                 double size = (avg_fit.length) * 0.3;
                 int mm = rs.nextInt((int)size);
                 int d = rs.nextInt(10- (int)size )+(int)size;
                 int str1= random_migration() ;
                 int str = lca[str1].strategy;
                 int migrate = answer(avg_fit[mm],gb.avg_fitness,localNoCA);
                 int destination = answer(avg_fit[d], gb.avg_fitness, localNoCA);
                 double[] avf = new double[lca[migrate].size];
                 for(int i=0 ;i< lca[migrate].size;i++){
                 avf[i] = pop[(int)lca[migrate].noparticle[i]].fitness[0];
                 }
                 double[] avf1 = new double[lca[migrate].size];
                 avf1= avf.clone();
                 Arrays.sort(avf1);
                 int tomigrate1= 0;     
                 if(str == 2)
                 {
                 tomigrate1 = rs.nextInt(3);
                 tomigrate1 = answer(avf1[tomigrate1],avf,lca[migrate].size);
                     
                 }
                 else if(str == 1)
                 {
                 tomigrate1 = rs.nextInt(4)+3;
                 tomigrate1 = answer(avf1[tomigrate1],avf,lca[migrate].size);
                    
                 }
                 else if(str == 0)
                 {
                 tomigrate1 = rs.nextInt(3)+7;
                 tomigrate1 = answer(avf1[tomigrate1],avf,lca[migrate].size);
                     
                 }
                 int d_str = answer(avg_fit[3],gb.avg_fitness,localNoCA);
                 str1 = lca[d_str].strategy;
                 for(int i=0 ;i< lca[destination].size;i++){
                 avf[i] = pop[(int)lca[destination].noparticle[i]].fitness[0];
                 }
                 avf1 = new double[lca[destination].size];
                 
                 Arrays.sort(avf1);
                 int tomigrate = 0;
                 if(str1 == 2)
                 {
                 tomigrate = rs.nextInt(3);
                 tomigrate = answer(avf1[tomigrate1],avf,lca[destination].size);
                     
                 }
                 else if(str1 == 1)
                 {
                 tomigrate = rs.nextInt(4)+3;
                 tomigrate = answer(avf1[tomigrate1],avf,lca[destination].size);
                     
                 }
                 else if(str1 == 0)
                 {
                 tomigrate = rs.nextInt(3)+7;
                 tomigrate = answer(avf1[tomigrate1],avf,lca[destination].size);
                 }
                 random_migration1(lca[migrate],lca[destination],(int)lca[migrate].noparticle[tomigrate1]);
                 random_migration1(lca[destination],lca[migrate],(int)lca[migrate].noparticle[tomigrate]);
                 }
                 results(fitness_func, run, 4);*/
                 //End region oligopoly
                 //--------------------------------------------------------------------------------------------------------
                  // region fair division
                //--------------------------------------------------------------------------------------------------------
                
            /*   if(g%10 == 0)
                 {
                 double avg_fit[] = new double[localNoCA];
                 avg_fit=(gb.avg_fitness).clone();
                 Arrays.sort(avg_fit);
                 for(int i=0,k=9; i< localNoCA/2; i++,k--)
                 {
                 int tomigrate = answer(avg_fit[i],gb.avg_fitness,localNoCA);
                 int destination = answer(avg_fit[k],gb.avg_fitness,localNoCA);
                 int agent_migrate = lca[tomigrate].best;
                 random_migration1(lca[tomigrate], lca[destination], agent_migrate);
                         
                 }
                 }
                 results(fitness_func,run,5);*/
                 //End region fair division
                //----------------------------------------------------------------------------------------------------------
                // region Intra household bargaining
           /*     if(g%10 == 0){
                 int migrate_lca = random_migration();
                 int co =0,de=0;
                 int destination ;
                 do {
                 destination = r.nextInt(localNoCA);
                 } while (destination == migrate_lca);
                 double[] m_particle = new double[lca[migrate_lca].size];
                 m_particle = (lca[migrate_lca].noparticle).clone();
                 for(int i=0; i < lca[migrate_lca].size;i++){
                 int j= (int)m_particle[i];
                 if(pop[j].strategy == 0){
                 co++;
                 }
                 else
                 de++;
                 }
                 if(co > de){
                 int mig = lca[migrate_lca].best;
                 random_migration1(lca[migrate_lca], lca[destination], mig);
                 }
                 else {
                 int mig = r.nextInt(lca[migrate_lca].size);
                 int pop_m =(int)lca[migrate_lca].noparticle[mig];
                 if(pop[pop_m].strategy == 0){
                 double worst = gb.avg_fitness[0];
                 int w=0;
                 for(int i=0; i<localNoCA;i++){
                 if(gb.avg_fitness[i] > worst){
                 worst = gb.avg_fitness[i];
                 w=i;
                 }
                 }
                 random_migration1(lca[migrate_lca], lca[w], mig);
                 }
                 else{
                 int des = second_best();
                 random_migration1(lca[migrate_lca], lca[des],mig);
                 }
                      
                 }
                 }
                 results(fitness_func,run,6);*/
                 // end region bargaining 
                 //next generation

                 /*if(g==0 || g==25 || g==50 || g==75 || g==99 )
                 {for(int i=0;i < localNoCA; i++)
                 {
                 System.out.println("Best solution of generation " +g +" is :"+ gb.gbf[i]+ " of particle " +gb.pno[i]+" in local CA"+ i);
                 }}*/