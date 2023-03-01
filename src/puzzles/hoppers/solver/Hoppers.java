package puzzles.hoppers.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.IOException;
import java.util.Collection;

public class Hoppers
{

    private static int num = 0;

    public static void main(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            System.out.println("Usage: java Hoppers filename");
        }else{
            HoppersConfig hop = new HoppersConfig(args[0]);
            Solver hops = new Solver();
            System.out.println("FILENAME" + args[0]); //todo

            System.out.println(hop);

            Collection<Configuration> path = hops.getPath(hop);

            System.out.println("Total configs: " + hops.getTotalConfigs());
            System.out.println("Unique configs: " + hops.getUniqueConfigs());

            if(path.size() != 0){
                for(Configuration n : path){
                    System.out.println("Step " + num + ":");
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
