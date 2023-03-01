package puzzles.strings;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;

/**
 * The strings class dislays the amount of steps, unique and total configurations to get to the solution
 *
 * @author Bryan Smith
 */

public class Strings {
    /** the number of steps */
    private static int num = 0;

    /**
     * Creates a strings configuration and displays the shortest path
     * @param args starting and ending string
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
        } else {

            StringsConfig start = new StringsConfig(args[0]);
            String end = args[1];
            StringsConfig.setEnd(end);

            Solver solve = new Solver();
            System.out.println("start: " + start + ", finish: " + end);


            Collection<Configuration> path = solve.getPath(start);

            System.out.println("Total configs: " + solve.getTotalConfigs());
            System.out.println("Unique configs: " + solve.getUniqueConfigs());

            if(path.size() != 0){
                for(Configuration n : path){
                    System.out.println("Step " + num + ": " + n);
                    num++;
                }
            } else{
                System.out.println("There is no path");

            }

            // TODO
        }
    }
}
