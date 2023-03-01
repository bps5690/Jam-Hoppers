package puzzles.jam.model;

import puzzles.common.solver.Configuration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// TODO: implement your JamConfig for the common solver



public class JamConfig implements Configuration {

    private final static char EMPTY = '.';
    private int rows;
    private int columns;
    private int numCars;
    private HashMap<Character, Car> mapCars;
    private char[][] grid; 



    public JamConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {


            String[] fields = in.readLine().split("\\s+");
            rows = Integer.parseInt(fields[0]);
            columns = Integer.parseInt(fields[1]);

            mapCars = new HashMap<>();
            grid = new char[rows][columns];

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    grid[r][c] = EMPTY;
                }
            }
            
            String[] numOfCars = in.readLine().split("\\s+");
            numCars = Integer.parseInt(numOfCars[0]);

            for (int i = 0; i < numCars; i++) {
                String[] carDes = in.readLine().split("\\s+");
                char label = carDes[0].charAt(0);
                Car newCar = new Car(label, Integer.parseInt(carDes[1]), Integer.parseInt(carDes[2]),
                                    Integer.parseInt(carDes[3]), Integer.parseInt(carDes[4]));
                mapCars.put(label, newCar);


                if(newCar.getOrientation() == Car.Orientation.VERTICAL){
                    for (int r = newCar.getStartRow(); r < newCar.getEndRow() + 1; r++) {
                        grid[r][newCar.getStartCol()] = newCar.getLabel();
                    }
                }
                else if(newCar.getOrientation() == Car.Orientation.HORIZONTAL){
                    for (int c = newCar.getStartCol(); c < newCar.getEndCol() + 1; c++) {
                        grid[newCar.getStartRow()][c] = newCar.getLabel();
                    }
                }
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getNumCars() {
        return numCars;
    }

    private JamConfig(JamConfig other, Car car, Character label){
        this.rows = other.rows;
        this.columns = other.columns;
        this.numCars = other.numCars;
        this.grid = new char[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                this.grid[r][c] = EMPTY;
            }
        }
        this.mapCars = new HashMap<>(other.mapCars);
        this.mapCars.remove(label);
        this.mapCars.put(car.getLabel(), car);

        for(Car newCar : mapCars.values()) {

            if(newCar.getOrientation() == Car.Orientation.VERTICAL){
                for (int r = newCar.getStartRow(); r < newCar.getEndRow() + 1; r++) {
                    grid[r][newCar.getStartCol()] = newCar.getLabel();
                }
            } else if(newCar.getOrientation() == Car.Orientation.HORIZONTAL){
                for (int c = newCar.getStartCol(); c < newCar.getEndCol() + 1; c++) {
                    grid[newCar.getStartRow()][c] = newCar.getLabel();
                }
            }
        }
    }

    @Override
    public boolean isSolution(){
        Car curr = mapCars.get('X');
        if(curr.getEndCol() == columns - 1){
            return true;
        }
        return false;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        // if horizontal / and first column move to right || and last column move to left || middle of board, left or right 
        // if vertical   / and first row move down || and last row move up || middle of board, up or down
        // check for collisions

        LinkedList<Configuration> neighbors = new LinkedList<>();

        for (Car checkCar : mapCars.values()) {

            if (checkCar.getOrientation() == Car.Orientation.HORIZONTAL) {

                Car moveCarRight = new Car(checkCar.getLabel(), checkCar.getStartRow(),
                        checkCar.getStartCol() + 1, checkCar.getEndRow(),
                        checkCar.getEndCol() + 1);

                Car moveCarLeft = new Car(checkCar.getLabel(), checkCar.getStartRow(),
                        checkCar.getStartCol() - 1, checkCar.getEndRow(),
                        checkCar.getEndCol() - 1);

                if (checkCar.getStartCol() > 0) {
                    if (grid[checkCar.getStartRow()][checkCar.getStartCol() - 1] == EMPTY) {
                        JamConfig moveLeft = new JamConfig(this, moveCarLeft, checkCar.getLabel());
                        neighbors.add(moveLeft);
                    }

                }  if (checkCar.getEndCol() < columns - 1) {
                    if (grid[checkCar.getStartRow()][checkCar.getEndCol() + 1] == EMPTY) {
                        JamConfig moveRight = new JamConfig(this, moveCarRight, checkCar.getLabel());
                        neighbors.add(moveRight);
                    }
                }

            } else if (checkCar.getOrientation() == Car.Orientation.VERTICAL) {

                Car moveCarUp = new Car(checkCar.getLabel(), checkCar.getStartRow() - 1,
                        checkCar.getStartCol(), checkCar.getEndRow() - 1,
                        checkCar.getEndCol());

                Car moveCarDown = new Car(checkCar.getLabel(), checkCar.getStartRow() + 1,
                        checkCar.getStartCol(), checkCar.getEndRow() + 1,
                        checkCar.getEndCol());

                if (checkCar.getStartRow() > 0) {
                    if (grid[checkCar.getStartRow() - 1][checkCar.getEndCol()] == EMPTY) {
                        JamConfig moveUp = new JamConfig(this, moveCarUp, checkCar.getLabel());
                        neighbors.add(moveUp);
                    }
                }  if (checkCar.getEndRow() < rows - 1) {
                    if (grid[checkCar.getEndRow() + 1][checkCar.getEndCol()] == EMPTY) {
                        JamConfig moveDown = new JamConfig(this, moveCarDown, checkCar.getLabel());
                        neighbors.add(moveDown);
                    }
                }
            }
        }
        return neighbors;
    }


    @Override
    public String toString() {
        String[] line = new String[rows];
        for (int r = 0; r < rows; r++) {
            line[r] = r + "| ";
            for (int c = 0; c < columns; c++) {
                line[r] = line[r] + grid[r][c] + " ";
            }
        }


        return String.join("\n", line);
    }

    @Override
    public boolean equals(Object other){
        if (this == other){
            return true;
        }
        JamConfig jamConfig = (JamConfig) other;
        if( Arrays.deepEquals(this.grid, jamConfig.grid)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() { return Arrays.deepHashCode(grid); }

    public char[][] getGrid() {
        return grid;
    }



    public HashMap<Character, Car> getMapCars() {
        return mapCars;
    }

    public void moveCar(Car car, char direction){
        // check direction

        if(direction == 'L'){
            mapCars.remove(car.getLabel());
            Car moveCarLeft = new Car(car.getLabel(), car.getStartRow(),
                    car.getStartCol() - 1, car.getEndRow(),
                    car.getEndCol() - 1);
            mapCars.put(moveCarLeft.getLabel(), moveCarLeft);

        } else if (direction == 'R'){
            mapCars.remove(car.getLabel());
            Car moveCarRight = new Car(car.getLabel(), car.getStartRow(),
                    car.getStartCol() + 1, car.getEndRow(),
                    car.getEndCol() + 1);
            mapCars.put(moveCarRight.getLabel(), moveCarRight);


        } else if (direction == 'U'){
            mapCars.remove(car.getLabel());
            Car moveCarUp = new Car(car.getLabel(), car.getStartRow() - 1,
                    car.getStartCol(), car.getEndRow() - 1,
                    car.getEndCol());
            mapCars.put(moveCarUp.getLabel(), moveCarUp);

        } else if (direction == 'D'){
            mapCars.remove(car.getLabel());
            Car moveCarDown = new Car(car.getLabel(), car.getStartRow() + 1,
                    car.getStartCol(), car.getEndRow() + 1,
                    car.getEndCol());
            mapCars.put(moveCarDown.getLabel(), moveCarDown);
        }


        this.grid = new char[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                this.grid[r][c] = EMPTY;
            }
        }
        for(Car newCar : mapCars.values()) {

            if(newCar.getOrientation() == Car.Orientation.VERTICAL){
                for (int r = newCar.getStartRow(); r < newCar.getEndRow() + 1; r++) {
                    grid[r][newCar.getStartCol()] = newCar.getLabel();
                }
            } else if(newCar.getOrientation() == Car.Orientation.HORIZONTAL){
                for (int c = newCar.getStartCol(); c < newCar.getEndCol() + 1; c++) {
                    grid[newCar.getStartRow()][c] = newCar.getLabel();
                }
            }
        }
    }


}
