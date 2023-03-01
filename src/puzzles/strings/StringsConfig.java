package puzzles.strings;

import puzzles.common.solver.Configuration;
import java.util.Collection;
import java.util.LinkedList;

/**
 * StringCongif creates the neighbors of each configuration
 *
 * @author Bryan Smith
 */

public class StringsConfig implements Configuration {
    /** starting string */
    public String start;
    /** ending string */
    private static String end;


    public StringsConfig(String startStr){
        this.start = startStr;
    }

    /**
     * sets the end to the end string
     * @param str
     */
    public static void setEnd(String str){
        end = str;
    }

    /**
     * checks if the string is the end string
     * @return true or false
     */
    @Override
    public boolean isSolution() {
        if (this.start.equals(end)) {
            return true;
        } else {
            return false;
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
        StringsConfig config = (StringsConfig) o;
        return start.equals(config.start);
    }

    /**
     * hashcode of the strings
     * @return the hashcode
     */
    @Override
    public int hashCode(){
        return this.start.hashCode();
    }

    /**
     * the string
     * @return the string
     */
    @Override
    public String toString(){
        return this.start;
    }

    /**
     * gets the previous and next neighbor based on the current letter that is being changed
     * @return the neighbors of the string
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        LinkedList<Configuration> neighbors = new LinkedList<>();
        for (int j = 0; j < this.start.length(); j++) {
            if(this.start.charAt(j) == 'A'){
                String beg = this.start.substring(0, j);
                String end = this.start.substring(j + 1);

                String prev = beg + 'Z' + end;
                String next = beg + 'B' + end;

                StringsConfig prevStart = new StringsConfig(prev);
                StringsConfig nextStart = new StringsConfig(next);

                neighbors.add(prevStart);
                neighbors.add(nextStart);


            }else if(this.start.charAt(j) == 'Z'){
                String beg = this.start.substring(0, j);
                String end = this.start.substring(j + 1);

                String prev = beg + "Y" + end;
                String next = beg + "A" + end;

                StringsConfig prevStart = new StringsConfig(prev);
                StringsConfig nextStart = new StringsConfig(next);

                neighbors.add(prevStart);
                neighbors.add(nextStart);


            } else{
                String beg = this.start.substring(0, j);
                String end = this.start.substring(j + 1);

                char prevN = (char) ((int) start.charAt(j) - 1);
                char nextN = (char) ((int) start.charAt(j) + 1);

                String prev = beg + prevN + end;
                String next = beg + nextN + end;

                StringsConfig prevStart = new StringsConfig(prev);
                StringsConfig nextStart = new StringsConfig(next);

                neighbors.add(prevStart);
                neighbors.add(nextStart);

            }

        }
        return neighbors;
    }






}

