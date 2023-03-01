package puzzles.crossing;

import puzzles.common.solver.Configuration;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * CrossingConfig creates the neighbors of each configuration
 *
 * @author Bryan Smith
 */


public class CrossingConfig implements Configuration{
    /** left pups */
    private int leftPups;
    /** left wolf */
    private int leftWolfs;
    /** right pups */
    private int rightPups;
    /** right wolfs */
    private int rightWolfs;
    /** boat */
    private boolean boat;

    /**
     * construct a constructor to initialize the pups and wolfs
     * @param pups current pups
     * @param wolfs current wolves
     */
    public CrossingConfig(int pups, int wolfs){
        leftPups = pups;
        leftWolfs = wolfs;
        rightPups = 0;
        rightWolfs = 0;
        boat = false;

    }

    /**
     * checks if all the pups and wolves are on the right side and not the left
     * @return true or false
     */
    @Override
    public boolean isSolution() {
        if(leftPups == 0  && leftWolfs == 0){
            return true;
        }
        return false;
    }

    /**
     * the to string for the crossing
     * @return the amount of pups and wolves on each side during each step
     */
    @Override
    public String toString(){
        String contents = "left=[" + leftPups + ", " + leftWolfs + "], " +
                "right=[" + rightPups + ", " + rightWolfs + "]";
        String b = " (BOAT) ";

        if(!boat){
            return b + contents;
        } else {
            return "        " + contents + b;
        }

    }

    /**
     * checks if the objects are equal to each other
     * @param o other object
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrossingConfig that = (CrossingConfig) o;
        return leftPups == that.leftPups && leftWolfs == that.leftWolfs && rightPups == that.rightPups && rightWolfs
                == that.rightWolfs && boat == that.boat;
    }

    /**
     * the hashcode of the crossing config
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(leftPups, leftWolfs, rightPups, rightWolfs, boat);
    }

    /**
     * adds neighbors to a linked list based on the amount of pups and wolves on each side
     * @return the neighbors of each new configuration
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        LinkedList<Configuration> neighbors = new LinkedList<>();

        // boat false = left side
        // boat true = right side

        if(boat == false){

            if (leftPups >=2){
                CrossingConfig c = new CrossingConfig(leftPups, leftWolfs);
                c.leftPups = c.leftPups - 2;
                c.rightPups = rightPups + 2;
                c.rightWolfs = rightWolfs;
                c.boat = true;
                neighbors.add(c);
            }

            if (leftPups >= 1){
                CrossingConfig c = new CrossingConfig(leftPups, leftWolfs);
                c.leftPups--;
                c.rightPups = rightPups + 1;
                c.rightWolfs = rightWolfs;
                c.boat = true;
                neighbors.add(c);
            }

            if (leftWolfs >=1 ){
                CrossingConfig c = new CrossingConfig(leftPups, leftWolfs);
                c.leftWolfs--;
                c.rightWolfs = rightWolfs + 1;
                c.rightPups = rightPups;
                c.boat = true;
                neighbors.add(c);
            }


        } else if (boat == true){


            if (rightPups >=2){
                CrossingConfig c = new CrossingConfig(leftPups, leftWolfs);
                c.leftPups = leftPups + 2;
                c.rightPups = rightPups - 2;
                c.rightWolfs = rightWolfs;
                c.boat = false;
                neighbors.add(c);
            }
            if (rightPups >= 1){
                CrossingConfig c = new CrossingConfig(leftPups, leftWolfs);
                c.leftPups = leftPups + 1 ;
                c.rightPups = rightPups - 1;
                c.rightWolfs = rightWolfs;
                c.boat = false;
                neighbors.add(c);
            }

            if (rightWolfs >=1 ){
                CrossingConfig c = new CrossingConfig(leftPups, leftWolfs);
                c.leftWolfs = leftWolfs + 1;
                c.rightWolfs = rightWolfs - 1;
                c.rightPups = rightPups;
                c.boat = false;
                neighbors.add(c);
            }
        }



        return neighbors;
    }
}
