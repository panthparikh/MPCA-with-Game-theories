/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HMPCA;

import java.util.Random;

/**
 *
 * @author Panth
 */
public class Particleh {

    double[] solution = new double[100];
    double[] fitness = new double[10];
    double[] mutation = new double[100];
    double[] crossover = new double[100];
    double[][] heritage = new double[30][2];
   int strategy =0;
    Random r = new Random();

    public void pop_init(int a) {
        int max_range = 100, min_range = -100;
        for (int i = 0; i < a; i++) {
            double rand = (double) r.nextInt((max_range - min_range) + 1) + min_range;
            solution[i] = rand;
         //  for(int j=0; j < 10;j++){
           //     heritage[j][0] = 0.0;
          //      heritage[j][1] =0.0;
          //  }
        }
        double random = Math.random()*1;
         strategy = (int)Math.round(random);// 0 will cooperate and 1 will defect
    }

}
