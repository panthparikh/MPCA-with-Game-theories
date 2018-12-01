/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cec2015;

/**
 *
 * @author Panth
 */
import java.util.Random;
public class Algo{
    
    public double[] init_pop(double[] pop, int size,int max_range, int min_range,double[] velo)
{
    Random rand = new Random();
    double velo_max= 0.1* (max_range-min_range);
    for (int i= 0; i< size; i++)
    {
        double randomd = min_range + (max_range - min_range)* rand.nextDouble();
        pop[i] = randomd;
        double randvelo;
        randvelo = 0 + (velo_max - 0)* rand.nextDouble();
        velo[i] = (double)randvelo;
    }
    return pop;
}
    static int index_fit [] = {0,1,2,3,4,5,6,7,8,9};
public double[] bubble_srt(double array[]) {
        int n = array.length;
        int k= 0;
        double temp = 0;
               
                for(int i=0; i < n; i++){
                        for(int j=1; j < (n-i); j++){
                               
                                if(array[j-1] < array[j]){
                                        //swap the elements!
                                        temp = array[j-1];
                                        k= index_fit[j-1];
                                        array[j-1] = array[j];
                                        index_fit[j-1] = index_fit[j];
                                        array[j] = temp;
                                        index_fit[j] = k;
                                }
                               
                        }
        
    }
                return array;
}
  
    /*int index =0;
    public double max_number(double [] a, int size){
        double max=a[0];
        for(int i=0; i< size; i++)
        {
            if(a[i]> max)
            {
                max = a[i];
                index= i;
            }
        }
        return max;
    }
    
    public int getfitness_index()
    {
        return index+1;
    }*/
    
    public int[] getfintess_array()
    {
        return index_fit;
    }
    
}
