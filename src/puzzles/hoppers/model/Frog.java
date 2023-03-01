package puzzles.hoppers.model;

public class Frog
{
    private boolean isRed;
    private int Row;
    private int Col;

    public Frog(int Row, int Col, boolean isRed)
    {
        this.isRed = isRed;
        this.Row = Row;
        this.Col = Col;


    }

    public int getRow()
    {
        return Row;
    }

    public int getCol()
    {
        return Col;
    }

    public boolean isRed()
    {
        return this.isRed;
    }


}
