/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HMPCA;

import static HMPCA.HMPCA.pop_size;

/**
 *
 * @author Panth
 */
public class localCA {
   // static Particleh[] pop = new Particleh[pop_size/10];
    
    double localbf = 0;                         
    double[] particle = new double[100]; // fitness of the particle
    double [] noparticle = new double [100];
    int best;
    int size =0;
    double avg_fitness = 0; // average fitness of the local CA
    int strategy = 1; // 0 = low; 1= medium; 2= high
    public void setlocalbf(double bf){
    if(localbf > bf)
        localbf = bf;
}
}
