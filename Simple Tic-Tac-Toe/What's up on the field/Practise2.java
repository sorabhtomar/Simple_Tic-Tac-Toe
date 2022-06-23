package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.abs;

class Grid {
    private static void printTopBot() {
        System.out.println("---------");
    }

    private static void printLine(char[] line) {
        System.out.println("| " + line[0] + " " + line[1] + " " + line[2] + " |");
    }

    public static void printGrid(char[][] grid) {
        printTopBot();
        for (char[] line : grid) {
            printLine(line);
        }
        printTopBot();
    }

    public static char[][] createGrid() {
        return new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
    }
}

class GameState {
    private static boolean win(char[][] grid, char player) {
        char[] winCondition = {player, player, player};
        for (int i = 0; i < 3; i++) {
            if (Arrays.equals(grid[i], winCondition)) {
                return true;
            }
            char[] column = new char[3];
            for (int j = 0; j < 3; j++) {
                column[j] = grid[j][i];
            }
            if (Arrays.equals(column, winCondition)) {
                return true;
            }
        }
        if (grid[0][0] == player & grid[1][1] == player & grid[2][2] == player) {
            return true;
        }
        return grid[0][2] == player & grid[1][1] == player & grid[2][0] == player;
    }

    private static boolean game_not_finished(char[][] grid) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[j][i] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    public static String state(char[][] grid) {
        if (win(grid, 'X')) {
            return "X wins";
        }
        if (win(grid, 'O')) {
            return "O wins";
        }
        if (game_not_finished(grid)) {
            return "Game not finished";
        }
        return "Draw";
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] grid = Grid.createGrid();
        Grid.printGrid(grid);

        char player = 'X';
        while (GameState.state(grid).equals("Game not finished")) {

            System.out.print("Enter the coordinates: ");
            String input = scanner.nextLine();

            if (input.length() < 3) {
                System.out.println("Incomplete coordinates.");
                continue;
            }
            if (!Character.isDigit(input.charAt(0)) | !Character.isDigit(input.charAt(2))) {
                System.out.println("You should enter numbers!");
                continue;
            }

            int row = Character.getNumericValue(input.charAt(0)) - 1;
            int column = Character.getNumericValue(input.charAt(2)) - 1;

            if (row < 0 | row > 2 | column < 0 | column > 2) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (grid[row][column] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            grid[row][column] = player;
            Grid.printGrid(grid);
            player = player == 'X' ? 'O' : 'X';
        }

        System.out.println(GameState.state(grid));
    }
}
