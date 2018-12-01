
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.sqrt;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Panth
 */
public class Final_solution {
    public static void main(String args[]){
        int function=0;
        for(function=1 ; function< 16;function++){
        String pathname ="C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\GA\\GA_10D\\solution_GA_function_"+function+".txt";
        List<Double> numbers = new ArrayList<>();
        double sum = 0, mean=0, median =0 , standard_deviation = 0;
        try {
            for (String line : Files.readAllLines(Paths.get(pathname))) {
                for (String part : line.split("\\s+")) {
                    Double i = Double.valueOf(part);
                    numbers.add(i);
                    
                }
            }
            
            for(int i=0;i < numbers.size() ;i++){
            
                sum = sum + numbers.get(i);
            }
            int a ;
            mean = sum/numbers.size();
            Collections.sort(numbers);
            if(numbers.size()% 2==0){
                int b;
                a= numbers.size()/2;
                b= a-1;
                median = (numbers.get(b)+ numbers.get(a))/2;
            }else{
                a = numbers.size()/2;
                median = numbers.get(a-1);
            }
            double summation =0;
            for(int i=0; i< numbers.size();i++){
                summation = summation + ((numbers.get(i) - mean)*(numbers.get(i) - mean)) ;
            }
            standard_deviation = sqrt(summation/numbers.size());
            File f = new File("C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\GA\\GA_10D\\finalresult_GA_function_"+function+".txt");
            if(f.exists() && !f.isDirectory())
            {
                FileWriter fw = new FileWriter(f, true);
                fw.append("Mean:"+mean +"  ");
                fw.append("Median:"+median +"  ");
                fw.append("Standard deviation:"+standard_deviation +"  ");
                fw.close();
            }
            else{
            PrintWriter writer = new PrintWriter("GA\\GA_10D\\finalresult_GA_function_"+function+".txt","UTF-8");
            writer.append("Mean:"+mean+"  ");
            writer.append("Median:"+median +"  ");
            writer.append("Standard deviation:"+standard_deviation +"  ");
            writer.close();
            }
        
        } catch (IOException ex) {
            Logger.getLogger(Read_output.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
}
