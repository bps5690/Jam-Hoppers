package puzzles.hoppers.model;

import puzzles.common.solver.Configuration;
import puzzles.jam.model.Car;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

// TODO: implement your HoppersConfig for the common solver

public class HoppersConfig implements Configuration
{

    private int greenFrogs;
    private final static char GREEN = 'G';
    private final static char EMPTY = '.';
    private final static char RED = 'R';
    private final static char INVALID = '*';
    private int dimR;
    private int dimC;
    private char[][] board;


    public HoppersConfig(String filename) throws IOException
    {
        try (BufferedReader file = new BufferedReader(new FileReader(filename)))
        {
            String[] dimensions = file.readLine().split("\\s+");
            dimR = Integer.parseInt(dimensions[0]);
            dimC = Integer.parseInt(dimensions[1]);

            this.board = new char[dimR][dimC];

            for (int r = 0; r < this.dimR; r++)
            {
                for (int c = 0; c < this.dimC; c++)
                {
                    this.board[r][c] = EMPTY;
                }
            }

            int greenFrogAccum = 0;
            for (int r = 0; r < dimR; r++)
            {
                String[] in = file.readLine().split("\\s+");
                for (int c = 0; c < dimC; c++)
                {
                    String p = in[c];
                    char pos = p.charAt(0);
                    if (pos == GREEN)
                    {
                        greenFrogAccum += 1;
                    }
                    this.board[r][c] = pos;
                }
            }
            this.greenFrogs = greenFrogAccum;

        }

    }

    public char[][] getBoard()
    {
        return this.board;
    }

    private HoppersConfig(HoppersConfig other, Frog frog, int removeRow, int removeCol, int startRow, int startCol, int endRow, int endCol)
    {
        this.dimR = other.dimR;
        this.dimC = other.dimC;
        this.greenFrogs = other.greenFrogs - 1;
        this.board = new char[dimR][dimC];
        for (int r = 0; r < dimR; r++)
        {
            System.arraycopy(other.board[r], 0, this.board[r], 0, dimC);
        }
        this.board[removeRow][removeCol] = EMPTY;
        this.board[startRow][startCol] = EMPTY;
        if (frog.isRed())
        {
            this.board[endRow][endCol] = RED;
        } else
        {
            this.board[endRow][endCol] = GREEN;
        }

    }

    public int getDimR()
    {
        return this.dimR;
    }

    public int getDimC()
    {
        return this.dimC;
    }

    public char getValue(int row, int col)
    {
        return this.board[row][col];
    }

    public int getGreenFrogs()
    {
        return this.greenFrogs;
    }

    public HoppersConfig moveFrog(int startRow, int startCol, int endRow, int endCol, String direction)
    {
        if (direction.equals("L"))
        {
            HoppersConfig Left;
            if (this.board[startRow][startCol] == GREEN)
            {
                Frog jump = new Frog(endRow, endCol, false);
                Left = new HoppersConfig(this, jump, startRow, startCol - 2, startRow, startCol, endRow, endCol);
                return Left;
            } else
            {
                Frog jump = new Frog(endRow, endCol, true);
                Left = new HoppersConfig(this, jump, startRow, startCol - 2, startRow, startCol, endRow, endCol);
                return Left;
            }

        } else if (direction.equals("R"))
        {
            HoppersConfig Right;
            if (this.board[startRow][startCol] == GREEN)
            {
                Frog jump = new Frog(endRow, endCol, false);
                Right = new HoppersConfig(this, jump, startRow, startCol + 2, startRow, startCol, endRow, endCol);
                return Right;
            } else
            {
                Frog jump = new Frog(endRow, endCol, true);
                Right = new HoppersConfig(this, jump, startRow, startCol + 2, startRow, startCol, endRow, endCol);
                return Right;
            }

        } else if (direction.equals("U"))
        {
            HoppersConfig Up;
            if (this.board[startRow][startCol] == GREEN)
            {
                Frog jump = new Frog(endRow, endCol, false);
                Up = new HoppersConfig(this, jump, startRow - 2, startCol, startRow, startCol, endRow, endCol);
                return Up;
            } else
            {
                Frog jump = new Frog(endRow, endCol, true);
                Up = new HoppersConfig(this, jump, startRow - 2, startCol, startRow, startCol, endRow, endCol);
                return Up;
            }

        } else if (direction.equals("D"))
        {
            HoppersConfig Down;
            if (this.board[startRow][startCol] == GREEN)
            {
                Frog jump = new Frog(endRow, endCol, false);
                Down = new HoppersConfig(this, jump, startRow + 2, startCol, startRow, startCol, endRow, endCol);
                return Down;
            } else
            {
                Frog jump = new Frog(endRow, endCol, true);
                Down = new HoppersConfig(this, jump, startRow + 2, startCol, startRow, startCol, endRow, endCol);
                return Down;
            }

        } else if (direction.equals("UR"))
        {
            HoppersConfig upRight;
            if (this.board[startRow][startCol] == GREEN)
            {
                Frog jump = new Frog(endRow, endCol, false);
                upRight = new HoppersConfig(this, jump, startRow - 1, startCol + 1, startRow, startCol, endRow, endCol);
                return upRight;
            } else
            {
                Frog jump = new Frog(endRow, endCol, true);
                upRight = new HoppersConfig(this, jump, startRow - 1, startCol + 1, startRow, startCol, endRow, endCol);
                return upRight;
            }

        } else if (direction.equals("DR"))
        {
            HoppersConfig downRight;
            if (this.board[startRow][startCol] == GREEN)
            {
                Frog jump = new Frog(endRow, endCol, false);
                downRight = new HoppersConfig(this, jump, startRow + 1, startCol + 1, startRow, startCol, endRow, endCol);
                return downRight;
            } else
            {
                Frog jump = new Frog(endRow, endCol, true);
                downRight = new HoppersConfig(this, jump, startRow + 1, startCol + 1, startRow, startCol, endRow, endCol);
                return downRight;
            }

        } else if (direction.equals("DL"))
        {
            HoppersConfig downLeft;
            if (this.board[startRow][startCol] == GREEN)
            {
                Frog jump = new Frog(endRow, endCol, false);
                downLeft = new HoppersConfig(this, jump, startRow + 1, startCol - 1, startRow, startCol, endRow, endCol);
                return downLeft;
            } else
            {
                Frog jump = new Frog(endRow, endCol, true);
                downLeft = new HoppersConfig(this, jump, startRow + 1, startCol - 1, startRow, startCol, endRow, endCol);
                return downLeft;
            }

        } else if (direction.equals("UL"))
        {
            HoppersConfig upLeft;
            if (this.board[startRow][startCol] == GREEN)
            {
                Frog jump = new Frog(endRow, endCol, false);
                upLeft = new HoppersConfig(this, jump, startRow - 1, startCol - 1, startRow, startCol, endRow, endCol);
                return upLeft;
            } else
            {
                Frog jump = new Frog(endRow, endCol, true);
                upLeft = new HoppersConfig(this, jump, startRow - 1, startCol - 1, startRow, startCol, endRow, endCol);
                return upLeft;
            }

        }
        return this;
    }

    @Override
    public boolean isSolution()
    {
        if (this.greenFrogs == 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public Collection<Configuration> getNeighbors()
    {
        LinkedList<Configuration> neighbors = new LinkedList<>();

        for (int r = 0; r < dimR; r++)
        {

            for (int c = 0; c < dimC; c++)
            {
                if (this.board[r][c] == GREEN || this.board[r][c] == RED)
                {
                    if (r + 2 < dimR && c + 2 < dimC)
                    {

                        if (this.board[r + 1][c + 1] == GREEN) // Down Diagonal Right
                        {
                            if (this.board[r + 2][c + 2] == EMPTY)
                            {
                                if (this.board[r][c] == GREEN)
                                {
                                    Frog jump = new Frog(r + 2, c + 2, false);
                                    HoppersConfig downRight = new HoppersConfig(this, jump, r + 1, c + 1, r, c, r + 2, c + 2);
                                    neighbors.add(downRight);
                                } else
                                {
                                    Frog jump = new Frog(r + 2, c + 2, true);
                                    HoppersConfig downRight = new HoppersConfig(this, jump, r + 1, c + 1, r, c, r + 2, c + 2);
                                    neighbors.add(downRight);
                                }

                            }
                        }
                    }

                    if (r % 2 == 0 && c % 2 == 0)
                    {
                        if (r < dimR && c + 4 < dimC)
                        {
                            if (this.board[r][c + 2] == GREEN) // Right
                            {
                                if (this.board[r][c + 4] == EMPTY)
                                {
                                    if (this.board[r][c] == GREEN)
                                    {
                                        Frog jump = new Frog(r, c + 4, false);
                                        HoppersConfig Right = new HoppersConfig(this, jump, r, c + 2, r, c, r, c + 4);
                                        neighbors.add(Right);
                                    } else
                                    {
                                        Frog jump = new Frog(r, c + 4, true);
                                        HoppersConfig Right = new HoppersConfig(this, jump, r, c + 2, r, c, r, c + 4);
                                        neighbors.add(Right);
                                    }

                                }
                            }
                        }
                    }

                    if (r - 2 >= 0 && c + 2 < dimC)
                    {
                        if (this.board[r - 1][c + 1] == GREEN) // Up Right
                        {
                            if (this.board[r - 2][c + 2] == EMPTY)
                            {
                                if (this.board[r][c] == GREEN)
                                {
                                    Frog jump = new Frog(r - 2, c + 2, false);
                                    HoppersConfig upRight = new HoppersConfig(this, jump, r - 1, c + 1, r, c, r - 2, c + 2);
                                    neighbors.add(upRight);
                                } else
                                {
                                    Frog jump = new Frog(r - 2, c + 2, true);
                                    HoppersConfig upRight = new HoppersConfig(this, jump, r - 1, c + 1, r, c, r - 2, c + 2);
                                    neighbors.add(upRight);
                                }

                            }
                        }
                    }

                    if (r % 2 == 0 && c % 2 == 0)
                    {
                        if (r - 4 >= 0 && c < dimC)
                        {
                            if (this.board[r - 2][c] == GREEN) // Up
                            {
                                if (this.board[r - 4][c] == EMPTY)
                                {
                                    if (this.board[r][c] == GREEN)
                                    {
                                        Frog jump = new Frog(r - 4, c, false);
                                        HoppersConfig up = new HoppersConfig(this, jump, r - 2, c, r, c, r - 4, c);
                                        neighbors.add(up);
                                    } else
                                    {
                                        Frog jump = new Frog(r - 2, c + 2, true);
                                        HoppersConfig up = new HoppersConfig(this, jump, r - 2, c, r, c, r - 4, c);
                                        neighbors.add(up);
                                    }

                                }
                            }
                        }
                    }

                    if (r - 2 >= 0 && c - 2 >= 0)
                    {
                        if (this.board[r - 1][c - 1] == GREEN) // Up Left
                        {
                            if (this.board[r - 2][c - 2] == EMPTY)
                            {
                                if (this.board[r][c] == GREEN)
                                {
                                    Frog jump = new Frog(r - 2, c - 2, false);
                                    HoppersConfig upLeft = new HoppersConfig(this, jump, r - 1, c - 1, r, c, r - 2, c - 2);
                                    neighbors.add(upLeft);
                                } else
                                {
                                    Frog jump = new Frog(r - 2, c + 2, true);
                                    HoppersConfig upLeft = new HoppersConfig(this, jump, r - 1, c - 1, r, c, r - 2, c - 2);
                                    neighbors.add(upLeft);
                                }

                            }
                        }
                    }

                    if (r % 2 == 0 && c % 2 == 0)
                    {
                        if (r >= 0 && c - 4 >= 0)
                        {
                            if (this.board[r][c - 2] == GREEN) // Left
                            {
                                if (this.board[r][c - 4] == EMPTY)
                                {
                                    if (this.board[r][c] == GREEN)
                                    {
                                        Frog jump = new Frog(r, c - 4, false);
                                        HoppersConfig left = new HoppersConfig(this, jump, r, c - 2, r, c, r, c - 4);
                                        neighbors.add(left);
                                    } else
                                    {
                                        Frog jump = new Frog(r - 2, c + 2, true);
                                        HoppersConfig left = new HoppersConfig(this, jump, r, c - 2, r, c, r, c - 4);
                                        neighbors.add(left);
                                    }

                                }
                            }
                        }
                    }

                    if (r + 2 < dimR && c - 2 >= 0)
                    {
                        if (this.board[r + 1][c - 1] == GREEN) // down left
                        {
                            if (this.board[r + 2][c - 2] == EMPTY)
                            {
                                if (this.board[r][c] == GREEN)
                                {
                                    Frog jump = new Frog(r + 2, c - 2, false);
                                    HoppersConfig downLeft = new HoppersConfig(this, jump, r + 1, c - 1, r, c, r + 2, c - 2);
                                    neighbors.add(downLeft);
                                } else
                                {
                                    Frog jump = new Frog(r - 2, c + 2, true);
                                    HoppersConfig downLeft = new HoppersConfig(this, jump, r + 1, c - 1, r, c, r + 2, c - 2);
                                    neighbors.add(downLeft);
                                }

                            }
                        }
                    }

                    if (r % 2 == 0 && c % 2 == 0)
                    {
                        if (r + 4 < dimR && c < dimC)
                        {
                            if (this.board[r + 2][c] == GREEN) // Down
                            {
                                if (this.board[r + 4][c] == EMPTY)
                                {
                                    if (this.board[r][c] == GREEN)
                                    {
                                        Frog jump = new Frog(r + 4, c, false);
                                        HoppersConfig down = new HoppersConfig(this, jump, r + 2, c, r, c, r + 4, c);
                                        neighbors.add(down);
                                    } else
                                    {
                                        Frog jump = new Frog(r - 2, c + 2, true);
                                        HoppersConfig down = new HoppersConfig(this, jump, r + 2, c, r, c, r + 4, c);
                                        neighbors.add(down);
                                    }

                                }
                            }
                        }
                    }
                }

            }
        }


        return neighbors;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoppersConfig that = (HoppersConfig) o;
        return greenFrogs == that.greenFrogs && dimR == that.dimR && dimC == that.dimC && Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(greenFrogs, dimR, dimC);
        result = 31 * result + Arrays.deepHashCode(board);
        return result;
    }

    @Override
    public String toString()
    {
        String str = "";
        for (int r = 0; r < dimR; r++)
        {
            str += r + "| ";
            for (int c = 0; c < dimC; c++)
            {

                str += this.board[r][c] + " ";

            }
            str += "\n";
        }
        return str;
    }


}
