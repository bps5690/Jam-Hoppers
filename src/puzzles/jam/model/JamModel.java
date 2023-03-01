package puzzles.jam.model;

import javafx.scene.paint.Color;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class JamModel {
    /**
     * the collection of observers of this model
     */
    private final List<Observer<JamModel, String>> observers = new LinkedList<>();

    /**
     * the current configuration
     */
    private JamConfig currentConfig;

    private JamConfig startingConfig;

    private JamConfig loadedConfig;

    private final static char EMPTY = '.';

    private boolean isFirstSelect = true;

    private int selectedRow;

    private int selectedCol;

    private Car firstSelectCar;

    private Car.Orientation orientation;


    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<JamModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);
        }
    }


    public JamConfig getCurrentConfig() {
        return currentConfig;
    }

    public JamModel(String filename) throws IOException {
        currentConfig = new JamConfig(filename);
        startingConfig = new JamConfig(filename);
        this.firstSelectCar = null;
        this.orientation = null;
        this.selectedRow = -1;
        this.selectedCol = -1;
    }


    public void reset() {
        currentConfig = startingConfig;
        this.selectedCol = -1;
        this.selectedRow = -1;
        alertObservers("Puzzle reset!");
    }

    public void load(String filename) {
        try {
            currentConfig = new JamConfig(filename);
            startingConfig = new JamConfig(filename);
            File filee = new File(filename);

            alertObservers("> Loaded: " + filee.getName());
        } catch (FileNotFoundException fnf){
            alertObservers("File not found");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void quit(){
        System.exit(0);
    }

    public void hint() {
        Solver solve = new Solver();
        List<Configuration> path = solve.getPath(currentConfig);
        if (path.size() == 1 ) {
            alertObservers("Already Solved!");

        } else if(path.size() == 0){
            alertObservers("No solution!");
        }
        else {
            currentConfig = (JamConfig) path.get(1);
            alertObservers("> Next step!");
        }

    }

    public void select(int row, int column) {


        char[][] gridModel = currentConfig.getGrid();
        char label = gridModel[row][column];
        HashMap<Character, Car> map = currentConfig.getMapCars();
        Car car = map.get(label);

        if (isFirstSelect) {
            if (label != EMPTY) {
                selectedRow = row;
                selectedCol = column;
                this.firstSelectCar = new Car(car.getLabel(), car.getStartRow(), car.getStartCol(), car.getEndRow(), car.getEndCol());
                this.orientation = this.firstSelectCar.getOrientation();
                isFirstSelect = false;
                alertObservers("> Selected (" + row + "," + column + ")");
            } else {

                alertObservers("> No Car at (" + row + "," + column + ")");
            }
        } else {
            if (label == EMPTY) {
                isFirstSelect = true;
                if (orientation == Car.Orientation.HORIZONTAL) {
                    if (selectedCol > column) {
                        //move left
                        if (firstSelectCar.getStartCol() > column && firstSelectCar.getStartCol() - 1 == column
                                && selectedRow == row && row == firstSelectCar.getStartRow()) {
                            char left = 'L';
                            currentConfig.moveCar(firstSelectCar, left);
                            alertObservers("> Moved from (" + selectedRow + "," + selectedCol + ") to ("
                                    + row + "," + column + ")");
                        } else {
                            alertObservers("> Can't move from (" + selectedRow + "," + selectedCol + ") to ("
                                    + row + "," + column + ")");
                        }

                    } else if (selectedCol < column) {
                        // move right
                        if (firstSelectCar.getEndCol() < column && firstSelectCar.getEndCol() + 1 == column
                                && selectedRow == row && row == firstSelectCar.getStartRow()) {
                            char right = 'R';
                            currentConfig.moveCar(firstSelectCar, right);

                            alertObservers("> Moved from (" + selectedRow + "," + selectedCol + ") to ("
                                    + row + "," + column + ")");
                        } else {

                            alertObservers("> Can't move from (" + selectedRow + "," + selectedCol + ") to ("
                                    + row + "," + column + ")");
                        }
                    }

                } else if (orientation == Car.Orientation.VERTICAL) {
                    if (selectedRow > row) {
                        // move up
                        if (firstSelectCar.getStartRow() > row && firstSelectCar.getStartRow() - 1 == row
                                && selectedCol == column && column == firstSelectCar.getStartCol()) {
                            char up = 'U';
                            currentConfig.moveCar(firstSelectCar, up);
                            alertObservers("> Moved from (" + selectedRow + "," + selectedCol + ") to ("
                                    + row + "," + column + ")");
                        } else {
                            alertObservers("> Can't move from (" + selectedRow + "," + selectedCol + ") to ("
                                    + row + "," + column + ")");
                        }

                    } else if (selectedRow < row) {
                        // move down
                        if (firstSelectCar.getEndRow() < row && firstSelectCar.getEndRow() + 1 == row
                                && selectedCol == column && column == firstSelectCar.getStartCol()) {
                            char down = 'D';
                            currentConfig.moveCar(firstSelectCar, down);

                            alertObservers("> Moved from (" + selectedRow + "," + selectedCol + ") to ("
                                    + row + "," + column + ")");
                        } else {
                            alertObservers("> Can't move from (" + row + "," + column + ") to (");
                        }
                    }
                }
            } else {
                isFirstSelect = true;
                alertObservers("> Can't move from (" + selectedRow + "," + selectedCol + ") to ("
                        + row + "," + column + ")");
            }
        }
    }
}
