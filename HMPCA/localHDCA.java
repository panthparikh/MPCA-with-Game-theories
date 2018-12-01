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
public class localHDCA {
    
    double localbf = 0;                         
    double[] particle = new double[100]; //  particle dimensional solution
    double[] fitness = new double[100]; // fitness of particle 
    double [] noparticle = new double [100];
    int best;
    int size =0;
    double avg_fitness = 0; // average fitness of the local CA
    public void setlocalbf(double bf){
    if(localbf > bf)
        localbf = bf;
}
    
}
