package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.model.JamConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HoppersModel
{
    /**
     * the collection of observers of this model
     */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();

    /**
     * the current configuration
     */
    private HoppersConfig currentConfig;

    private HoppersConfig startConfig;

    private boolean isFirstSelect = true;

    private int selectedRow;

    private int selectedCol;

    private final static char GREEN = 'G';

    private final static char RED = 'R';

    private final static char EMPTY = '.';

    private String move = null;

    //TODO

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, String> observer)
    {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg)
    {
        for (var observer : observers)
        {
            observer.update(this, msg);
        }
    }

    public HoppersConfig getCurrentConfig()
    {
        return currentConfig;
    }

    public HoppersModel(String filename) throws IOException
    {
        currentConfig = new HoppersConfig(filename);
        startConfig = new HoppersConfig(filename);
        this.selectedRow = -1;
        this.selectedCol = -1;
        //TODO
    }

    public void reset()
    {
        currentConfig = startConfig;
        this.selectedCol = -1;
        this.selectedRow = -1;
        alertObservers("Puzzle reset!");
        //TODO
    }

    public void load(String filename)
    {
        try {

            currentConfig = new HoppersConfig(filename);
            startConfig = new HoppersConfig(filename);
            File filee = new File(filename);
            //if (filee == null || filee.length() == 0){
            //    throw new NullPointerException();
            //}

            alertObservers("> Loaded: " + filee.getName());
        } catch (FileNotFoundException fnf){
            alertObservers("File not found");
        } catch (IOException io) {
            io.printStackTrace();
        }catch (NullPointerException n){
            alertObservers("Load Cancelled");
        }
        //TODO
    }

    public void quit()
    {
        System.exit(0);
    }

    public void hint()
    {
        Solver solve = new Solver();
        List<Configuration> path = solve.getPath(currentConfig);
        if (path.size() == 1)
        {
            alertObservers("Already Solved!");

        }else if (path.size() == 0){
            alertObservers("No Solution");
        }
        else
        {
            currentConfig = (HoppersConfig) path.get(1);
            alertObservers("> Next Step!");
        }
        //TODO
    }

    public boolean isValid(int startRow, int startCol, int endRow, int endCol)
    {
        if (endRow < currentConfig.getDimR() && endRow >= 0 && endCol < currentConfig.getDimC() && endCol >= 0)
        {
            if (endRow == startRow - 2 && endCol == startCol + 2) //Up Right
            {
                if (currentConfig.getValue(startRow - 1, startCol + 1) == GREEN)//Check between
                {
                    move = "UR";
                    return true;
                }

            }
            if (endRow == startRow - 2 && endCol == startCol - 2) //Up Left
            {
                if (currentConfig.getValue(startRow - 1, startCol - 1) == GREEN)//Check between
                {
                    move = "UL";
                    return true;
                }
            }
            if (endRow == startRow + 2 && endCol == startCol - 2) //Down Left
            {
                if (currentConfig.getValue(startRow + 1, startCol - 1) == GREEN)//Check between
                {
                    move = "DL";
                    return true;
                }
            }
            if (endRow == startRow + 2 && endCol == startCol + 2) //Down Right
            {
                if (currentConfig.getValue(startRow + 1, startCol + 1) == GREEN)//Check between
                {
                    move = "DR";
                    return true;
                }
            }
            if (startRow % 2 == 0 && startCol % 2 == 0)
            {
                if (endRow == startRow && endCol == startCol + 4) //Right
                {
                    if (currentConfig.getValue(startRow, startCol + 2) == GREEN)//Check between
                    {
                        move = "R";
                        return true;
                    }
                }
                if (endRow == startRow - 4 && endCol == startCol) //Up
                {
                    if (currentConfig.getValue(startRow + 2, startCol) == GREEN)//Check between
                    {
                        move = "U";
                        return true;
                    }
                }
                if (endRow == startRow && endCol == startCol - 4) //Left
                {
                    if (currentConfig.getValue(startRow, startCol - 2) == GREEN)//Check between
                    {
                        move = "L";
                        return true;
                    }
                }
                if (endRow == startRow + 4) //Down
                {
                    if (currentConfig.getValue(startRow + 2, startCol) == GREEN)//Check between
                    {
                        move = "D";
                        return true;
                    }
                }
            }
        }

        return false;

    }


    public void select(int row, int col)
    {

        if (currentConfig.isSolution())
        {
            alertObservers("Puzzle is Solved");
        }

        if (isFirstSelect)
        {
            if (currentConfig.getValue(row, col) == GREEN || currentConfig.getValue(row, col) == RED)
            {
                selectedRow = row;
                selectedCol = col;

                isFirstSelect = false;
                alertObservers("> Selected (" + row + "," + col + ")");
            } else
            {
                alertObservers("> No Frog at (" + row + "," + col + ")");
            }
        } else
        {
            if (currentConfig.getValue(row, col) == EMPTY)
            {
                isFirstSelect = true;
                if (isValid(selectedRow, selectedCol, row, col))
                {
                    currentConfig = currentConfig.moveFrog(selectedRow, selectedCol, row, col, move);
                    alertObservers("> Moved from (" + selectedRow + "," + selectedCol + ") to ("
                            + row + "," + col + ")");
                }else {
                    alertObservers("Illegal Move");
                }


            } else
            {
                isFirstSelect = true;
                alertObservers("> Can't move from (" + selectedRow + "," + selectedCol + ") to ("
                        + row + "," + col + ")");
            }


        }
        //TODO
    }

}












