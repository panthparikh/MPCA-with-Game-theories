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
public class globalbf {

    double[] gbf = new double[100];
    double[] pno = new double[100];// particle no
    double best;
    double[] avg_fitness = new double[10];// average fitness of all local CA

    public void set_best(int size) {
        best = gbf[0];
        for (int i = 0; i < size; i++) {
            if (gbf[i] < best) {
                best = gbf[i];
            }
        }
    }

    public double get_best() {
        return best;
    }
}
