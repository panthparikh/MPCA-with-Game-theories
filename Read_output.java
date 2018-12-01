import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
public class Read_output {

    public static void main(String args[]) {
        //int function = 1, run = 20;
        double best= Double.MAX_VALUE;
        double bestb = Double.MAX_VALUE;
        int gene = 0;
        int avg_g = 0;
        int a =0;
        for (int j = 1; j < 16; j++) {
            for (int k = 1; k < 21; k++) {
                String pathname = "C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\HDCA\\HDCA_30D\\output_HDCA_function_" + j + "run_" + k + ".txt";
                List<Double> numbers = new ArrayList<>();
                double sum = 0, mean = 0;
                
                try {
                    for (String line : Files.readAllLines(Paths.get(pathname))) {
                        for (String part : line.split("\\s+")) {
                            
                            Double i = Double.valueOf(part);
                            numbers.add(i);
                            

                        }
                    }
                       
                    for (int i = 0; i < numbers.size(); i++) {

                        sum = sum + numbers.get(i);
                        if(numbers.get(i) < best){
                            best = numbers.get(i);
                        }
                    }
                    
                    for(int i=0;i< 100;i++){
                        if(bestb > numbers.get(i))
                        {
                            bestb = numbers.get(i);
                            gene = i;
                        }
                        
                    }
                    avg_g = avg_g + gene;
                    
                    mean = sum / numbers.size();

           /*        File f = new File("C:\\Users\\Panth\\Documents\\NetBeansProjects\\cec2015\\cec2015\\GA\\GA_10D\\solution_GA_function_" + j + ".txt");
                    if (f.exists() && !f.isDirectory()) {
                        FileWriter fw = new FileWriter(f, true);
                        fw.append(mean + "  ");
                        fw.close();
                    } else {
                        PrintWriter writer = new PrintWriter("GA\\GA_10D\\solution_GA_function_" + j + ".txt", "UTF-8");
                        writer.append(mean + "  ");
                        writer.close();
                    }
             */       

                } catch (IOException ex) {
                    Logger.getLogger(Read_output.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
            a = avg_g/20;
            System.out.printf(" Average_for_function:_"+j+"_is:"+ a);
            //System.out.printf("Best for function:"+j+" is :" + best);
            best = Double.MAX_VALUE;
            bestb = Double.MAX_VALUE;
            avg_g = 0;
        }
    }
}
