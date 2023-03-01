package puzzles.jam.model;

import javafx.geometry.Orientation;

public class Car {

    private char label;
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;
    private int size;
    private Orientation orientation;

    public Car(char label, int startRow, int startCol, int endRow, int endCol){
        this.label = label;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;

        if (startRow == endRow){
            orientation = Orientation.HORIZONTAL;
        }else if(startCol == endCol){
            orientation = Orientation.VERTICAL;
        }

    }

    public char getLabel() {
        return label;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getSize(Car car){
        int size1 = (car.endRow - car.startRow) + (car.getEndCol() - car.getStartCol()) + 1;
        return size1;
    }







}
