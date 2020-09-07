
package tictactoe;

import java.util.Scanner;

public class TicTacToe {
    private final static int SIDE = 3;
    private char[][] gameField;
    private Scanner sc;
    private boolean isStopped, isXWin, isOWin, moveTurn;

    private int countX, countO;

    public TicTacToe() {
        gameField = new char[SIDE][SIDE];
        createEmptyField();
        isStopped = false;
        isXWin = false;
        isOWin = false;
        moveTurn = true;
        countX = 0;
        countO = 0;
        sc = new Scanner(System.in);
        printState();
    }

    public void play() {
        while (!isStopped) {
            move();
            printState();
            check();
        }
    }

    private void printState() {
        printLine();

        for (int y = 0; y < SIDE; y++) {
            System.out.print("|");
            for (int x = 0; x < SIDE; x++) {
                System.out.print(String.format(" %c", gameField[y][x]));
            }
            System.out.println(" |");
        }

        printLine();
    }

    private void createEmptyField() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                gameField[y][x] = ' ';
            }
        }
    }

    private void readField() {
        System.out.println("Enter cells:");
        String moves = sc.nextLine();
        int counter = 0;


        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                if (counter < moves.length()) {
                    char currentChar = moves.charAt(counter);
                    gameField[y][x] = currentChar == '_' ? ' ' : currentChar;

                    if (currentChar == 'X') {
                        countX++;
                    } else if (currentChar == 'O') {
                        countO++;
                    }
                    counter++;
                }
            }
        }
    }

    private void move() {
        System.out.println("Enter the coordinates");
        makeMove(getValidCoordinates());
    }

    private int[] getValidCoordinates() {
        int[] coordinates = new int[2];

        while (true) {
            String line = sc.nextLine();

            if (line.length() == 3) {
                int x = Character.getNumericValue(line.charAt(0)) - 1;
                int y = SIDE - Character.getNumericValue(line.charAt(2));

                if (x >= 0 && x < SIDE && y >= 0 && y < SIDE) {
                    if (gameField[y][x] == ' ') {
                        coordinates[0] = x;
                        coordinates[1] = y;
                        return coordinates;
                    } else {
                        System.out.println("This cell is occupied! Choose another one!");
                    }
                } else {
                    System.out.println("You should enter valid numbers!");
                }
            }
        }
    }

    private void makeMove(int[] coordinates) {
        gameField[coordinates[1]][coordinates[0]] = moveTurn ? 'X' : 'O';

        if (moveTurn) {
            countX++;
        } else {
            countO++;
        }

        moveTurn = !moveTurn;
    }

    private void check() {
        checkLines();
        checkRows();
        checkDiagonals();


        if (isXWin && isOWin || Math.abs(countO - countX) > 1) {
            isStopped = true;
            System.out.println("Impossible");
        } else if (isXWin || isOWin) {
            isStopped = true;
            System.out.println(String.format("%s wins", isXWin ? "X" : "O"));
        } else if (countX + countO == SIDE * SIDE) {
            isStopped = true;
            System.out.println("Draw");
        } else if (!isStopped) {
            System.out.println("Game not finished");
        }
    }

    private void checkLines() {
        for (int y = 0; y < SIDE; y++) {
            char currentChar = gameField[y][0];
            if (currentChar != '_' && currentChar != ' ') {
                if (currentChar == gameField[y][1] && currentChar == gameField[y][2]) {
                    if (currentChar == 'X') {
                        isXWin = true;
                    } else if (currentChar == 'O') {
                        isOWin = true;
                    }
                }
            }
        }
    }

    private void checkRows() {
        for (int x = 0; x < SIDE; x++) {
            char currentChar = gameField[0][x];
            if (currentChar == gameField[1][x] && currentChar == gameField[2][x]) {
                if (currentChar == 'X') {
                    isXWin = true;
                } else if (currentChar == 'O') {
                    isOWin = true;
                }
            }
        }
    }

    private void checkDiagonals() {
        char center = gameField[1][1];
        if ((center == gameField[0][0] && center == gameField[2][2]) ||
                center == gameField[2][0] && center == gameField[0][2]) {
            if (center == 'X') {
                isXWin = true;
            } else if (center == 'O') {
                isOWin = true;
            }
        }
    }

    private static void printLine() {
        System.out.println("---------");
    }
}
