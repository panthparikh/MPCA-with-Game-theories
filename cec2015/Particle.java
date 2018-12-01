/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cec2015;

import static cec2015.Particle.fitu;
import java.util.Random;

/**
 *
 * @author Panth
 */
public class Particle {

    static double[] p1 = new double[10];
    static double[] p2 = new double[10];
    static double[] p3 = new double[10];
    static double[] p4 = new double[10];
    static double[] p5 = new double[10];
    static double[] p6 = new double[10];
    static double[] p7 = new double[10];
    static double[] p8 = new double[10];
    static double[] p9 = new double[10];
    static double[] p10 = new double[10];
    static double[] b_p1 = new double[10];
    static double[] b_p2 = new double[10];
    static double[] b_p3 = new double[10];
    static double[] b_p4 = new double[10];
    static double[] b_p5 = new double[10];
    static double[] b_p6 = new double[10];
    static double[] b_p7 = new double[10];
    static double[] b_p8 = new double[10];
    static double[] b_p9 = new double[10];
    static double[] b_p10 = new double[10];
    static int pop_size = 10;
    static double[] results = new double[10];
    static double[] fitness = new double[10];
    static double[] global_best = new double[10];
    static double[] fit_p1 = new double[10];
    static double[] fit_p2 = new double[10];
    static double[] fit_p3 = new double[10];
    static double[] fit_p4 = new double[10];
    static double[] fit_p5 = new double[10];
    static double[] fit_p6 = new double[10];
    static double[] fit_p7 = new double[10];
    static double[] fit_p8 = new double[10];
    static double[] fit_p9 = new double[10];
    static double[] fit_p10 = new double[10];
    static double[] velo_p1 = new double [10];
    static double[] velo_p2 = new double [10];
    static double[] velo_p3 = new double [10];
    static double[] velo_p4 = new double [10];
    static double[] velo_p5 = new double [10];
    static double[] velo_p6 = new double [10];
    static double[] velo_p7 = new double [10];
    static double[] velo_p8 = new double [10];
    static double[] velo_p9 = new double [10];
    static double[] velo_p10 = new double [10];
    static double[] weight = new double[10];
    static double c1 = 1.49445, c2 = 1.49445;
    static double r1 = 0, r2 = 0;
    static double[] fitu = new double[10];

    public static void main(String[] args) {

        int range, max_range, min_range;
        double fit = 0;
        max_range = 30;
        min_range = 0;
        range = max_range - min_range;
        Random rand = new Random();
        Algo al = new Algo();
        Cec2015 ce = new Cec2015();
// POPULATION INITIALISATION
        p1 = al.init_pop(p1, pop_size, max_range, min_range, velo_p1);
        p2 = al.init_pop(p2, pop_size, max_range, min_range, velo_p2);
        p3 = al.init_pop(p3, pop_size, max_range, min_range, velo_p3);
        p4 = al.init_pop(p4, pop_size, max_range, min_range, velo_p4);
        p5 = al.init_pop(p5, pop_size, max_range, min_range, velo_p5);
        p6 = al.init_pop(p6, pop_size, max_range, min_range, velo_p6);
        p7 = al.init_pop(p7, pop_size, max_range, min_range, velo_p7);
        p8 = al.init_pop(p8, pop_size, max_range, min_range, velo_p8);
        p9 = al.init_pop(p9, pop_size, max_range, min_range, velo_p9);
        p10 = al.init_pop(p10, pop_size, max_range, min_range, velo_p10);
//CLONING OF POPULATION
        b_p1 = p1.clone();
        b_p2 = p2.clone();
        b_p3 = p3.clone();
        b_p4 = p4.clone();
        b_p5 = p5.clone();
        b_p6 = p6.clone();
        b_p7 = p7.clone();
        b_p8 = p8.clone();
        b_p9 = p9.clone();
        b_p10 = p10.clone();

       /* fitness[0] = fit_p1[0];
        fitness[1] = fit_p2[0];
        fitness[2] = fit_p3[0];
        fitness[3] = fit_p4[0];
        fitness[4] = fit_p5[0];
        fitness[5] = fit_p6[0];
        fitness[6] = fit_p7[0];
        fitness[7] = fit_p8[0];
        fitness[8] = fit_p9[0];
        fitness[9] = fit_p10[0];
*/
        //ASSISGNING OF WEIGHT
        for (int i = 0; i < pop_size; i++) {

            double randw;
            randw = 0.5 + (0.55) * rand.nextDouble();
            weight[i] = randw;
        }
        /* al.bubble_srt(p1);
         al.bubble_srt(p2);
         al.bubble_srt(p3);
         al.bubble_srt(p4);
         al.bubble_srt(p5);
         al.bubble_srt(p6);
         al.bubble_srt(p7);
         al.bubble_srt(p8);
         al.bubble_srt(p9);
         al.bubble_srt(p10);
         */
        //double fittest = al.max_number(fitness, pop_size);
        int generations = 1;
        int[] fit_index = new int[10];
        double[] fit_array = new double[10];
        double wd = 0;
        //START OF ALGO
        while (generations <= 5) {
            fitu = ce.eval(p1, 10, 1, 1);
            fit_p1[generations - 1] = fitu[0];

            fitu = ce.eval(p2, 10, 1, 1);
            fit_p2[generations - 1] = fitu[0];
            fitu = ce.eval(p3, 10, 1, 1);
            fit_p3[generations - 1] = fitu[0];
            fitu = ce.eval(p4, 10, 1, 1);
            fit_p4[generations - 1] = fitu[0];
            fitu = ce.eval(p5, 10, 1, 1);
            fit_p5[generations - 1] = fitu[0];
            fitu = ce.eval(p6, 10, 1, 1);
            fit_p6[generations - 1] = fitu[0];
            fitu = ce.eval(p7, 10, 1, 1);
            fit_p7[generations - 1] = fitu[0];
            fitu = ce.eval(p8, 10, 1, 1);
            fit_p8[generations - 1] = fitu[0];
            fitu = ce.eval(p9, 10, 1, 1);
            fit_p9[generations - 1] = fitu[0];
            fitu = ce.eval(p10, 10, 1, 1);
            fit_p10[generations - 1] = fitu[0];
            if (generations > 1) {
                if (fit_p1[generations - 2] < fit_p1[generations-1]) {
                    b_p1 = p1.clone();
                }
                if (fit_p2[generations - 2] < fit_p2[generations-1]) {
                    b_p2 = p2.clone();
                }
                if (fit_p3[generations - 2] < fit_p3[generations-1]) {
                    b_p3 = p3.clone();
                }
                if (fit_p4[generations - 2] < fit_p4[generations-1]) {
                    b_p4 = p4.clone();
                }
                if (fit_p5[generations - 2] < fit_p5[generations-1]) {
                    b_p5 = p5.clone();
                }
                if (fit_p6[generations - 2] < fit_p6[generations-1]) {
                    b_p6 = p6.clone();
                }
                if (fit_p7[generations - 2] < fit_p7[generations-1]) {
                    b_p7 = p7.clone();
                }
                if (fit_p8[generations - 2] < fit_p8[generations-1]) {
                    b_p8 = p8.clone();
                }
                if (fit_p9[generations - 2] < fit_p9[generations-1]) {
                    b_p9 = p9.clone();
                }
                if (fit_p10[generations-2] > fit_p10[generations - 1]) {
                    b_p10 = p10.clone();
                }

            }
            fitness[0] = fit_p1[generations-1];
            fitness[1] = fit_p2[generations-1];
            fitness[2] = fit_p3[generations-1];
            fitness[3] = fit_p4[generations-1];
            fitness[4] = fit_p5[generations-1];
            fitness[5] = fit_p6[generations-1];
            fitness[6] = fit_p7[generations-1];
            fitness[7] = fit_p8[generations-1];
            fitness[8] = fit_p9[generations-1];
            fitness[9] = fit_p10[generations-1];
            fit_array = al.bubble_srt(fitness);
            fit_index = al.getfintess_array();
            wd = 0.55 / generations;
            switch (fit_index[0]) {
                case 1:
                    weight[0] = weight[0] + wd;
                    for (int j = 0; j < pop_size; j++) {
                        velo_p1[j] = weight[0] * velo_p1[j];
                    }
                    break;
                case 2:
                    weight[1] = weight[1] + wd;
                    for (int j = 0; j < pop_size; j++) {
                        velo_p2[j] = weight[1] * velo_p2[j];
                    }
                    break;
                case 3:
                    weight[2] = weight[2] + wd;
                    for (int j = 0; j < pop_size; j++) {
                        velo_p3[j] = weight[2] * velo_p3[j];
                    }
                    break;
                case 4:
                    weight[3] = weight[3] + wd;
                    for (int j = 0; j < pop_size; j++) {
                        velo_p4[j] = weight[3] * velo_p4[j];
                    }
                    break;
                case 5:
                    weight[4] = weight[4] + wd;
                    for (int j = 0; j < pop_size; j++) {
                        velo_p5[j] = weight[4] * velo_p5[j];
                    }
                    break;
                case 6:
                    weight[5] = weight[5] + wd;
                    for (int j = 0; j < pop_size; j++) {
                        velo_p6[j] = weight[5] * velo_p6[j];
                    }
                    break;
                case 7:
                    weight[6] = weight[6] + wd;
                    for (int j = 0; j < pop_size; j++) {
                        velo_p7[j] = weight[6] * velo_p7[j];
                    }
                    break;
                case 8:
                    weight[7] = weight[7] + wd;
                    for (int j = 0; j < pop_size; j++) {
                        velo_p8[j] = weight[7] * velo_p8[j];
                    }
                    break;
                case 9:
                    weight[8] = weight[8] + wd;
                    for (int j = 0; j < pop_size; j++) {
                        velo_p9[j] = weight[8] * velo_p9[j];
                    }
                    break;
                case 10:
                    weight[9] = weight[9] + wd;
                    for (int j = 0; j < pop_size; j++) {
                        velo_p10[j] = weight[9] * velo_p10[j];
                    }
                    break;

            }
            for (int i = 0; i < pop_size; i++) {
                if (fit_index[0] != i) {
                    weight[i] = weight[i] - (0.55 / generations);
                }
            }
            particle_info(fit_index[1]);
            double[] s_points = new double[10]; // second best
            double[] t_points = new double[10]; // third best
            double[] fo_points = new double[10]; // fourth best
            double[] fi_points = new double[10]; // best
            double[] c_points = new double[10];
            double[] l_points = new double[10]; // least
            double[] ls_points = new double[10]; // second last
            double[] u_velo = new double[10];
            double[] o_velo = new double[10];
            double[] p_best = new double[10];
            s_points = getparticle();
            particle_info(fit_index[2]);
            t_points = getparticle();
            particle_info(fit_index[3]);
            fo_points = getparticle();
            particle_info(fit_index[0]);
            fi_points = getparticle();
            particle_info(fit_index[9]);
            l_points = getparticle();
            particle_info(fit_index[8]);
            ls_points = getparticle();
            for (int i = 0; i < pop_size; i++) {
                c_points[i] = (s_points[i] + t_points[i] + fo_points[i]) / 3;
            }
            particle_info(fit_index[9]);
            o_velo = getvelo();
            for (int i = 0; i < pop_size; i++) {
                r1 = rand.nextDouble();
                r2 = rand.nextDouble();
                double a = c1 * r1;
                double b = c2 * r2;
                u_velo[i] = (weight[fit_index[9]] * o_velo[i]) + a * (c_points[i] - l_points[i]) + b * (fi_points[i] - l_points[i]);
            }
            update_velo(u_velo, fit_index[9]);
            particle_info(fit_index[8]);
            o_velo = getvelo();
            for (int i = 0; i < pop_size; i++) {
                r1 = rand.nextDouble();
                r2 = rand.nextDouble();
                double a = c1 * r1;
                double b = c2 * r2;
                u_velo[i] = (weight[fit_index[9]] * o_velo[i]) + a * (c_points[i] - l_points[i]) + b * (fi_points[i] - l_points[i]);
            }
            update_velo(u_velo, fit_index[8]);
            double a = rand.nextDouble();

            for (int i = 1; i < pop_size - 2; i++) {
                particle_info(fit_index[i]);
                o_velo = getvelo();
                p_best = getpersonalbest();
                s_points = getparticle();

                for (int j = 0; j < pop_size; j++) {
                    r1 = rand.nextDouble();
                    r2 = rand.nextDouble();
                    if (a > 0.5) {
                        u_velo[j] = weight[fit_index[i]] * o_velo[j] + ((c1 * r1) * (p_best[j] - s_points[j])) + ((c2 * r2) * (fi_points[j] - s_points[j]));
                    } else {
                        u_velo[j] = weight[fit_index[i]] * o_velo[j] + ((c1 * r1) * (p_best[j] - s_points[j]));
                    }

                }
                update_velo(u_velo, fit_index[i]);
            }
            for (int i = 0; i < pop_size; i++) {
                particle_info(i);
                o_velo = getvelo();
                s_points = getparticle();
                for (int j = 0; j < pop_size; j++) {
                    t_points[j] = s_points[j] + o_velo[j];
                }
                update_position(t_points, i);
            }
            generations++;
        }
        display_result(fit_p1, generations);
        display_result(fit_p2, generations);
        display_result(fit_p3, generations);
        display_result(fit_p4, generations);
        display_result(fit_p5, generations);
        display_result(fit_p6, generations);
        display_result(fit_p7, generations);
        display_result(fit_p8, generations);
        display_result(fit_p9, generations);
        display_result(fit_p10, generations);

    }
    static double[] particle = new double[10];
    static double[] p_velo = new double[10];
    static double p_weight = 0;
    static double p_fitness = 0;
    static double[] p_best = new double[10];

    public static void particle_info(int index) {

        switch (index) {
            case 1:
                for (int i = 0; i < pop_size; i++) {
                    particle[i] = p1[i];
                    p_velo[i] = velo_p1[i];
                   
                }
                
                p_weight = weight[0];
                p_best = b_p1.clone();
                break;

            case 2:
                for (int i = 0; i < pop_size; i++) {
                    particle[i] = p2[i];
                    p_velo[i] = velo_p2[i];
                   
                }
                 p_weight = weight[1];
                 
                p_best = b_p2.clone();
                break;
            case 3:
                for (int i = 0; i < pop_size; i++) {
                    particle[i] = p3[i];
                    p_velo[i] = velo_p3[i];
                    p_weight = weight[2];
                    
                }
                p_best = b_p3.clone();
                break;
            case 4:
                for (int i = 0; i < pop_size; i++) {
                    particle[i] = p4[i];
                    p_velo[i] = velo_p4[i];
                    p_weight = weight[3];
                    
                }
                p_best = b_p4.clone();
                break;
            case 5:
                for (int i = 0; i < pop_size; i++) {
                    particle[i] = p5[i];
                    p_velo[i] = velo_p5[i];
                    p_weight = weight[4];
                    
                }
                p_best = b_p5.clone();
                break;
            case 6:
                for (int i = 0; i < pop_size; i++) {
                    particle[i] = p6[i];
                    p_velo[i] = velo_p6[i];
                    p_weight = weight[5];
                    
                }
                p_best = b_p6.clone();
                break;
            case 7:
                for (int i = 0; i < pop_size; i++) {
                    particle[i] = p7[i];
                    p_velo[i] = velo_p7[i];
                    p_weight = weight[6];
                    
                }
                p_best = b_p7.clone();
                break;
            case 8:
                for (int i = 0; i < pop_size; i++) {
                    particle[i] = p8[i];
                    p_velo[i] = velo_p8[i];
                    p_weight = weight[7];
                    
                }
                p_best = b_p8.clone();
                break;
            case 9:
                for (int i = 0; i < pop_size; i++) {
                    particle[i] = p9[i];
                    p_velo[i] = velo_p9[i];
                    p_weight = weight[8];
                    
                }
                p_best = b_p9.clone();
                break;
            case 10:
                for (int i = 0; i < pop_size; i++) {
                    particle[i] = p10[i];
                    p_velo[i] = velo_p10[i];
                    p_weight = weight[9];
                    
                }
                p_best = b_p10.clone();
                break;
        }

    }

    public static void update_velo(double[] v, int index) {
        switch (index) {
            case 1:
                for (int k = 0; k < pop_size; k++) {
                    velo_p1[k] = v[k];
                }
                break;
            case 2:
                for (int k = 0; k < pop_size; k++) {
                    velo_p2[k] = v[k];
                }
                break;
            case 3:
                for (int k = 0; k < pop_size; k++) {
                    velo_p3[k] = v[k];
                }
                break;
            case 4:
                for (int k = 0; k < pop_size; k++) {
                    velo_p4[k] = v[k];
                }
                break;
            case 5:
                for (int k = 0; k < pop_size; k++) {
                    velo_p5[k] = v[k];
                }
                break;
            case 6:
                for (int k = 0; k < pop_size; k++) {
                    velo_p6[k] = v[k];
                }
                break;
            case 7:
                for (int k = 0; k < pop_size; k++) {
                    velo_p7[k] = v[k];
                }
                break;
            case 8:
                for (int k = 0; k < pop_size; k++) {
                    velo_p8[k] = v[k];
                }
                break;
            case 9:
                for (int k = 0; k < pop_size; k++) {
                    velo_p9[k] = v[k];
                }
                break;
            case 10:
                for (int k = 0; k < pop_size; k++) {
                    velo_p10[k] = v[k];
                }
                break;
        }
    }

    public static void update_position(double[] pos, int i) {
        switch (i) {
            case 1:
                p1 = pos.clone();
                break;
            case 2:
                p2 = pos.clone();
                break;

            case 3:
                p3 = pos.clone();
                break;

            case 4:
                p4 = pos.clone();
                break;

            case 5:
                p5 = pos.clone();
                break;

            case 6:
                p6 = pos.clone();
                break;

            case 7:
                p7 = pos.clone();
                break;

            case 8:
                p8 = pos.clone();
                break;

            case 9:
                p9 = pos.clone();
                break;
            case 10:
                p10 = pos.clone();
                break;

        }
    }

    public static double[] getparticle() {
        return particle;
    }

    public static double[] getvelo() {
        return p_velo;
    }

    public static double getweight() {
        return p_weight;
    }

    public static double getfitness() {
        return p_fitness;
    }

    public static double[] getpersonalbest() {
        return p_best;
    }

    public static void display_result(double[] f, int s) {

        for (int i = 0; i < s; i++) {
            System.out.println("Fitness of particle:" + i + " " + f[i]);
        }
    }

}
