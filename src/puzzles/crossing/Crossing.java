package puzzles.crossing;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.strings.StringsConfig;

import java.util.Collection;

/**
 * The crossing class displays the unique and total configurations of the crossing puzzle
 *
 * @author Bryan Smith
 */

public class Crossing {
    /** the number of steps */
    private static int num = 0;

    /**
     * Creates a crossing configuration and finds the best path to the solution
     * @param args the number of starting pups and wolves
     */
    public static void main(String[] args) {


        if (args.length < 2) {
            System.out.println(("Usage: java Crossing pups wolves"));
        } else {

            CrossingConfig boat = new CrossingConfig(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            Solver solve = new Solver();
            System.out.println("Pups: " + args[0] + ", Wolves: " + args[1]);

            Collection<Configuration> path = solve.getPath(boat);

            System.out.println("Total configs: " + solve.getTotalConfigs());
            System.out.println("Unique configs: " + solve.getUniqueConfigs());

            if(path.size() != 0){
                for(Configuration n : path){
                    System.out.println("Step " + num + ":" + n);
                    num++;
                }
            } else{
                System.out.println("No solution!");

            }
            // TODO
        }
    }
}
