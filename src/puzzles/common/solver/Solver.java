package puzzles.common.solver;


import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The common solver for the crossing and strings puzzle. Gets the shortest path to the solution
 *
 * @author Bryan Smith
 */

public class Solver{
    /** number of unique configurations */
    private int uniqueConfigs = 1;
    /** number of total configurations */
    private int totalConfigs = 1;

    public Solver() {
    }

    /**
     * Uses a breadth first search
     * @param start the starting word of the puzzle
     * @return the path of the puzzle
     */
    public List<Configuration> getPath(Configuration start){
        List<Configuration> queue = new LinkedList<>();
        queue.add(start);

        HashMap<Configuration, Configuration> pred = new HashMap<>();
        pred.put(start, start);


        Configuration end = null;

        while(!queue.isEmpty()) {
            Configuration current = queue.remove(0);
            if(current.isSolution()){
                end = current;
                break;
            }

            for (Configuration neighbors : current.getNeighbors()){
                totalConfigs++;
                if(!pred.containsKey(neighbors)){
                    uniqueConfigs++;
                    pred.put(neighbors, current);
                    queue.add(neighbors);
                }
            }
        }
        List<Configuration> path = new LinkedList<>();
        if(pred.containsKey(end)){
            Configuration currWord = end;
            while(currWord != start){
                path.add(0, currWord);
                currWord = pred.get(currWord);
            }
            path.add(0, start);


        }
        return path;
    }

    /**
     * the unique configurations
     * @return unique configurations
     */
    public int getUniqueConfigs(){
        return uniqueConfigs;
    }

    /**
     * the total configurations
     * @return total configurations
     */
    public int getTotalConfigs(){
        return totalConfigs;
    }

}

