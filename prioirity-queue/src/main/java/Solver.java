import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class Solver {

    private SearchNode node;

    private final Board board;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        board = initial;
        MinPQ<SearchNode> minPQ = new MinPQ<>(initial.dimension() + 10);
        Set<Board> previouses = new HashSet<>(50);
        Board dequeuedBoard = initial;
        Board previous = null;
        SearchNode dequeuedNode = new SearchNode(initial, 0, null);
        Iterable<Board> boards;
        while (!dequeuedBoard.isGoal()) {
            boards = dequeuedBoard.neighbors();
            for (Board board : boards) {
                if (!board.equals(previous) && !previouses.contains(board)) {
                    minPQ.insert(new SearchNode(board, dequeuedNode.moves + 1, dequeuedNode));
                }
            }
            previouses.add(previous);
            previous = dequeuedBoard;
            dequeuedNode = minPQ.delMin();
            dequeuedBoard = dequeuedNode.current;
        }
        this.node = dequeuedNode;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return !board.isGoal();
    }

    // min number of moves to solve initial board
    public int moves() {
        return node.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Queue<Board> queue= new Queue<>();
        queue.enqueue(node.current);
        return queue;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final int priority;
        private final SearchNode previous;
        private final Board current;
        private final int moves;


        public SearchNode(Board current, int moves, SearchNode previous) {
            this.current = current;
            this.previous = previous;
            this.priority = current.hamming() + current.manhattan();
            this.moves = moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            int cmp = this.priority - that.priority;
            return Integer.compare(cmp, 0);
        }

    }

    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        int[][] tiles5 = {{4, 1, 3},
                {0, 2, 6},
                {7, 5, 8}};

        int[][] tiles7 = {{1, 2, 3},
                {0, 7, 6},
                {5, 4, 8}};


        int[][] tiles8 = {{2, 3, 5},
                {1, 0, 4},
                {7, 8, 6}};

        int[][] tiles9 = {{2, 0, 3, 4},
                {1, 10, 6, 8},
                {5, 9, 7, 12},
                {13, 14, 11, 15}};

        int[][] tiles11 = {{1, 0, 2},
                {7, 5, 4},
                {8, 6, 3}};

        int[][] tiles18 = {{5, 6, 2},
                {1, 8, 4},
                {7, 3, 0}};


        // answer will be here, compare w/ other

        int[][] tiles25 = {{2, 8, 5},
                {3, 6, 1},
                {7, 0, 4}};

        int[][] tiles36 = {{5, 3, 1, 4},
                {10, 2, 8, 7},
                {14, 13, 0, 11},
                {6, 9, 15, 12}};


        double start5 = System.currentTimeMillis();
        Board board5 = new Board(tiles5);
        Solver solve5 = new Solver(board5);
        System.out.printf("# of moves = %d && # of actual moves %d & time passed %f\n", solve5.moves(), solve5.moves(), (System.currentTimeMillis() - start5) / 1000);

        double start7 = System.currentTimeMillis();
        Board board7 = new Board(tiles7);
        Solver solve7 = new Solver(board7);
        System.out.printf("# of moves = %d && # of actual moves %d & time passed %f\n", solve7.moves(), solve7.moves(), (System.currentTimeMillis() - start7) / 1000);

        double start8 = System.currentTimeMillis();
        Board board8 = new Board(tiles8);
        Solver solve8 = new Solver(board8);
        System.out.printf("# of moves = %d && # of actual moves %d & time passed %f\n", solve8.moves(), solve8.moves(), (System.currentTimeMillis() - start8) / 1000);

        double start9 = System.currentTimeMillis();
        Board board9 = new Board(tiles9);
        Solver solve9 = new Solver(board9);
        System.out.printf("# of moves = %d && # of actual moves %d & time passed %f\n", solve9.moves(), solve9.moves(), (System.currentTimeMillis() - start9) / 1000);

        double start11 = System.currentTimeMillis();
        Board board11 = new Board(tiles11);
        Solver solve11 = new Solver(board11);
        System.out.printf("# of moves = %d && # of actual moves %d & time passed %f\n", solve11.moves(), solve11.moves(), (System.currentTimeMillis() - start11) / 1000);

        double start18 = System.currentTimeMillis();
        Board board18 = new Board(tiles18);
        Solver solve18 = new Solver(board18);
        System.out.printf("# of moves = %d && # of actual moves %d & time passed %f\n", solve18.moves(), solve18.moves(), (System.currentTimeMillis() - start18) / 1000);
//
        double start25 = System.currentTimeMillis();
        Board board25 = new Board(tiles25);
        Solver solve25 = new Solver(board25);
        System.out.printf("# of moves = %d && # of actual moves %d & time passed %f\n", solve25.moves(), solve25.moves(), (System.currentTimeMillis() - start25) / 1000);

    }

}