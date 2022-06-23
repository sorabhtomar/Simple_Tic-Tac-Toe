package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        System.out.print("Enter cells: ");
        String input = scn.nextLine();

        char[][] cells = new char[3][3];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                int idx = i * cells[i].length + j;
                cells[i][j] = input.charAt(idx);
            }
        }

        System.out.println("---------");
        for (int i = 0; i < cells.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print(cells[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
}
