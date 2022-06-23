package tictactoe;

import java.util.Scanner;

public class Main {
    private static Scanner scn = new Scanner(System.in);

    private static final char USER_MOVE = 'X';

    public static void main(String[] args) {
        System.out.print("Enter cells: ");
        String input = scn.nextLine();

        char[][] grid = new char[3][3];

        initialize(input, grid);
        display(grid);

        updateByUserMove(grid);
        display(grid);

//        analyzeAndPrintResult(input);
    }

    private static void initialize(String input, char[][] grid) {
        for (int i = 0; i < input.length(); i++) {
            char move = input.charAt(i);

            int row = i / grid.length;
            int col = i % grid[0].length;
            grid[row][col] = move;
        }
    }

    private static void display(char[][] grid) {
        System.out.println("---------");
        for (int i = 0; i < grid.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static void updateByUserMove(char[][] grid) {
        boolean validUserMove = false;

        // Coordinates of User Move (initialized with invalid values)
        int row = -1;
        int col = -1;

        do {
            try {

                System.out.print("Enter the coordinates: ");
                String[] coordinates = scn.nextLine().split(" ");

                row = Integer.parseInt(coordinates[0]);
                col = Integer.parseInt(coordinates[1]);

                // 0-based indexing in grid
                if (grid[row - 1][col - 1] == '_') {
                    grid[row - 1][col - 1] = USER_MOVE;
                    validUserMove = true;
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

    private static void analyzeAndPrintResult(String input) {
        // x: count of no. of X's
        int x = 0;
        // o: count of no. of O's
        int o = 0;
        // blanks: count of no. of _'s (i.e. blanks)
//        int blanks = 0;

        for (int i = 0; i < input.length(); i++) {
            char move = input.charAt(i);

            switch (move) {
                case 'X':
                    x++;
                    break;

                case 'O':
                    o++;
                    break;

//                case '_':
//                    blanks++;
//                    break;
            }
        }

        // wcc: winning cell combinations (indices)
        int[][] wcc = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}
        };

        // finding the count of valid winning combinations, for 'X' and 'O' each
        // xc: count of valid winning combinations for 'X'
        int xc = 0;
        for (int[] wc: wcc) {
            // wc: winning combination

            // len: length of any side of tic-tac-toe grid
            // = cells.length = cells[0].length (i.e. rows and cols of this grid are of same length)
//            int len = 3;

            // vsf: valid so far i.e. the winning combination is valid so far
            // i.e. the character at the position in "wc" is 'X' i.e. it matches "XXX" (so far)
            boolean vsf = true;

            for (int cellNo : wc) {
//                int row = cellNo / len;
//                int col = cellNo % len;
//
//                if ('X' != cells[row][col]) {
//                    vsf = false;
//                    break;
//                }
                if ('X' != input.charAt(cellNo)) {
                    vsf = false;
                    break;
                }
            }

            if(vsf) {
                xc++;
            }
        }

        // oc: count of valid winning combinations for "O"
        int oc = 0;
        for (int[] wc: wcc) {
            // wc: winning combination

            // len: length of any side of tic-tac-toe grid
            // = cells.length = cells[0].length (i.e. rows and cols of this grid are of same length)
//            int len = 3;

            // vsf: valid so far i.e. the winning combination is valid so far
            // i.e. the character at the position in "wc" is 'O' i.e. it matches "OOO" (so far)
            boolean vsf = true;

            for (int cellNo : wc) {
                if ('O' != input.charAt(cellNo)) {
                    vsf = false;
                    break;
                }
            }

            if(vsf) {
                oc++;
            }
        }

        if (Math.abs(x - o) > 1) {
            // Impossible case: when the moves of either 'X' or 'O', is "2 or more" greater than the other
            System.out.println("Impossible");
        } else if (xc > 0 && oc > 0) {
            // Impossible case: when both 'X' win and 'O' wins
            System.out.println("Impossible");
        } else if (xc > 0) {
            // 'X' has at least 1 winning combination (i.e. 3 X's in a row)
            System.out.println("X wins");
        } else if (oc > 0) {
            // 'O' has at least 1 winning combination (i.e. 3 O's in a row)
            System.out.println("O wins");
        } else if (x + o == 9) {
            // Not "Impossible Case" and neither side has won.
            // But the grid has been filled (because the count of 'X' and 'O' total is 9 (total cells))
            // So, it is a draw
            System.out.println("Draw");
        } else {
            // Not "Impossible Case" and neither side has won.
            // But the grid is not completely filled i.e. the game still continues
            System.out.println("Game not finished");
        }
    }
}
