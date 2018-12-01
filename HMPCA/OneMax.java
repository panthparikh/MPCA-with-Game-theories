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
public class OneMax {
   
  // arrayboady is solution and colmax is dimension
  public double get(int[] arrayBody, int colMax) {
    //Let's try an easy one. The more ones, the better
    int intFit = 0;
    for(int i=0; i<colMax; i++) {
      if(arrayBody[i]==1) {intFit+= arrayBody[i];}
    }
    //normalise and return
    return ((double)intFit);
  }

}
    
