package tictactoe;

import java.util.Scanner;
import java.util.Arrays;

public class Main {
    private static final Scanner scn = new Scanner(System.in);

    // tic-tac-toe grid is 3x3 matrix (i.e. 2D array)
    private static final int GRID_LENGTH = 3;
    private static final char FIRST_USER_MOVE = 'X';
    private static final char SECOND_USER_MOVE = 'O';
    private static final char NO_USER_MOVE = '_'; // or, EMPTY_CELL

    // wcc: winning cell combinations (contains cell position (not coordinate indices))
    private static final int[][] WINNING_CELL_COMBOS = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    // move: the number of the user move in the grid
    // starting "move" from 1 (i.e. first move) i.e. initialized to 1.
    private static int move = 1;
    // xCount: count of no. of X's in the grid (so far). Initialized to 0.
    private static int xCount = 0;
    // yCount: count of no. of Y's in the grid (so far). Initialized to 0.
    private static int oCount = 0;

    private static boolean endOfGame = false;

    public static void main(String[] args) {
        char[][] grid = new char[GRID_LENGTH][GRID_LENGTH];

        initialize(grid);
        play(grid);
    }

    private static void initialize(char[][] grid) {
        for (char[] row : grid) {
            Arrays.fill(row, NO_USER_MOVE);
        }
    }

    private static void display(char[][] grid) {
        System.out.println("---------");
        for (char[] row : grid) {
            System.out.print("| ");
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static void play(char[][] grid) {
        // Will play until we reach the "end of game" i.e. until endOfGame becomes true.
        // This will happen only when someone either "Wins the game", or there is a "Draw"
        while(!endOfGame) {
            display(grid);
            getValidUserMove(grid);
            analyze(grid);
        }
    }

    private static void getValidUserMove(char[][] grid) {
        boolean validUserMove = false;

        do {

            System.out.print("Enter the coordinates: ");
            String[] coordinates = scn.nextLine().split(" ");

            try {
                // Coordinates of User Move (initialized with invalid values)
                int row = Integer.parseInt(coordinates[0]);
                int col = Integer.parseInt(coordinates[1]);

                // 0-based indexing in grid
                if (grid[row - 1][col - 1] == '_') {
                    if (move % 2 == 1) {
                        grid[row - 1][col - 1] = FIRST_USER_MOVE;
                        xCount++;
                    } else {
                        grid[row - 1][col - 1] = SECOND_USER_MOVE;
                        oCount++;
                    }

                    validUserMove = true;
                    move++;
                } else {
                    throw new IllegalArgumentException("This cell is occupied! Choose another one!");
                }
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Coordinates should be from 1 to 3!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        } while(!validUserMove);
    }

    /**
     * Analyses the "grid" after each user move. And finally, prints the final result.
     * Final result: "Someone wins the game" or "Draw".
     * @param grid tic-tac-toe grid
     */
    private static void analyze(char[][] grid) {

        // After each user move, finding the count of valid winning combinations for 'X'.
        // That is, for current grid state.

        // xwc: 'X' winning combinations (combos) i.e. count of valid winning combinations for 'X'
        int xwc = 0;
        for (int[] wc: WINNING_CELL_COMBOS) {
            // wc: winning combination (combo)

            // vsf: valid so far i.e. the winning combination is valid so far
            // i.e. the character at the position in "wc" is 'X' i.e. it matches "XXX" (so far)
            boolean vsf = true;

            for (int cellNo : wc) {
                int row = cellNo / GRID_LENGTH;
                int col = cellNo % GRID_LENGTH;

                if ('X' != grid[row][col]) {
                    vsf = false;
                    break;
                }
            }

            if(vsf) {
                xwc++;
            }
        }

        // After each user move, finding the count of valid winning combinations for 'O'.
        // That is, for current grid state.
        // owc: 'O' winning combinations (combos) i.e. count of valid winning combinations for 'O'
        int owc = 0;
        for (int[] wc: WINNING_CELL_COMBOS) {
            // wc: winning combination (combo)

            // vsf: valid so far i.e. the winning combination is valid so far
            // i.e. the character at the position in "wc" is 'O' i.e. it matches "OOO" (so far)
            boolean vsf = true;

            for (int cellNo : wc) {
                int row = cellNo / GRID_LENGTH;
                int col = cellNo % GRID_LENGTH;

                if ('O' != grid[row][col]) {
                    vsf = false;
                    break;
                }
            }

            if(vsf) {
                owc++;
            }
        }

        if (Math.abs(xCount - oCount) > 1) {
            // Impossible case: when the moves of either 'X' or 'O', is "2 or more" greater than the other
            System.out.println("Impossible");
        } else if (xwc > 0 && owc > 0) {
            // Impossible case: when both 'X' win and 'O' wins
            System.out.println("Impossible");
        } else if (xwc > 0) {
            // 'X' has at least 1 winning combination (i.e. 3 X's in a row)
            display(grid);
            System.out.println("X wins");
            endOfGame = true;
        } else if (owc > 0) {
            // 'O' has at least 1 winning combination (i.e. 3 O's in a row)
            display(grid);
            System.out.println("O wins");
            endOfGame = true;
        } else if (xCount + oCount == 9) {
            // Not "Impossible Case" and neither side has won.
            // But the grid has been filled (because the count of 'X' and 'O' total is 9 (total cells))
            // So, it is a draw
            display(grid);
            System.out.println("Draw");
            endOfGame = true;
        }
//        else {
//            // Not "Impossible Case" and neither side has won.
//            // But the grid is not completely filled i.e. the game still continues
//            System.out.println("Game not finished");
//        }
    }
}
