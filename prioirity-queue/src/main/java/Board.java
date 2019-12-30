import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {

    private final int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.board = tiles;
    }

    // string representation of this board
    public String toString() {
        String table = board.length + System.lineSeparator();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                table += " " + board[i][j];
            }
            table += System.lineSeparator();
        }
        return table;
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        int index = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int element = board[i][j];
                if (element != index) {
                    count += 1;
                }
                index++;
            }
        }
        return count - 1; // for zero
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int element = board[i][j];
                if (element == 0) continue;
                int exp_i = calcExpectedI(element - 1);
                int exp_j = calcExpectedJ(element - 1);
                int diff = Math.abs(exp_j - j) + Math.abs(exp_i - i);
                sum += diff;
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0 && hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y instanceof Board) {
            return Arrays.deepEquals(board, ((Board) y).board);
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zero_i = 0;
        int zero_j = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int element = board[i][j];
                if (element == 0) {
                    zero_i = i;
                    zero_j = j;
                }
            }
        }
        Queue<Board> queue = new Queue<>();
        Board boardUp = calcNextBoard(zero_i, zero_j, -1, 0);
        if (boardUp != null) {
            queue.enqueue(boardUp);
        }
        Board boardDown = calcNextBoard(zero_i, zero_j, 1, 0);
        if (boardDown != null) {
            queue.enqueue(boardDown);
        }
        Board boardLeft = calcNextBoard(zero_i, zero_j, 0, -1);
        if (boardLeft != null) {
            queue.enqueue(boardLeft);
        }
        Board boardRight = calcNextBoard(zero_i, zero_j, 0, 1);
        if (boardRight != null) {
            queue.enqueue(boardRight);
        }
        return queue;
    }

    private Board calcNextBoard(int zero_i, int zero_j, int move_i, int move_j) {
        int new_i = zero_i + move_i;
        int new_j = zero_j + move_j;
        if (new_i >= dimension() || new_i < 0) {
            return null;
        }
        if (new_j >= dimension() || new_j < 0) {
            return null;
        }
        int[][] new_board = copy(board);
        int tmp = new_board[zero_i][zero_j];
        new_board[zero_i][zero_j] = new_board[new_i][new_j];
        new_board[new_i][new_j] = tmp;
        return new Board(new_board);
    }

    private int[][] copy(int[][] board) {
        int[][] copy = new int[board.length][];
        for (int i = 0; i < copy.length; i++) {
            int[] aMatrix = board[i];
            int aLength = aMatrix.length;
            copy[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, copy[i], 0, aLength);
        }
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    private int calcExpectedI(int value) {
        return (value - value % dimension()) / dimension();
    }

    private int calcExpectedJ(int value) {
        return value % dimension();
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In("puzzle3x3-00.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        System.out.println(initial);
        System.out.println(initial.hamming());
        System.out.println(initial.manhattan());


        tiles = new int[][]{new int[]{1, 0, 3},
                new int[]{4, 2, 5},
                new int[]{7, 8, 6}};
        initial = new Board(tiles);
        System.out.println(initial);
        System.out.println(initial.hamming());
        System.out.println(initial.manhattan());
        System.out.println("--------------------------------");
        for (Board board : initial.neighbors()) {
            System.out.println(board);
        }
    }

}