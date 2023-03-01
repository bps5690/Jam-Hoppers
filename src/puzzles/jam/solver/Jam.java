package puzzles.jam.solver;

import puzzles.common.solver.Solver;
import puzzles.jam.model.JamConfig;
import puzzles.common.solver.Configuration;

import java.io.IOException;
import java.util.Collection;

public class Jam {

    private static int num = 0;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Jam filename");
        } else{

            JamConfig jam = new JamConfig(args[0]);
            Solver solve = new Solver();
            System.out.println("Filename: " + args[0]);
            System.out.println(jam);

            Collection<Configuration> path = solve.getPath(jam);
//            System.out.println(path.size());

            System.out.println("Total Configs: " + solve.getTotalConfigs());
            System.out.println("Unique Configs: "+ solve.getUniqueConfigs());

            if(path.size() != 0){
                for(Configuration n : path){
                    System.out.println("Step " + num + ":" );
                    System.out.println(n);
                    System.out.println("");
                    num++;
                }
            } else{
                System.out.println("No solution!");
            }
        }
    }
}