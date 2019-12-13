/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;

    private final boolean[][] map;

    private final int[][] indexes;

    private static final int ABOVE_ROOT_INDEX = 0;

    private final int beloveRootIndex;

    private int openSites = 0;

    /**
     * 0 - would be root of above corner 1 to  n - our elements n + 1 elemnt - below corner
     **/
    private final WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        beloveRootIndex = n * n + 1;
        map = new boolean[n][];
        indexes = new int[n][];
        uf = new WeightedQuickUnionUF(n * n + 2);
        int count = 0;
        for (int i = 0; i < n; i++) {
            map[i] = new boolean[n];
            indexes[i] = new int[n];
            for (int j = 0; j < n; j++) {
                map[i][j] = false;
                indexes[i][j] = ++count;
            }
        }
    }

    public boolean percolates() {
        return uf.connected(ABOVE_ROOT_INDEX, beloveRootIndex);
    }

    public boolean isOpen(int i, int j) {
        if (i <= 0 || j <= 0 || i > n || j > n) {
            throw new IllegalArgumentException();
        }
        return map[i - 1][j - 1];
    }

    public void open(int i, int j) {
        if (i <= 0 || j <= 0 || i > n || j > n) {
            throw new IllegalArgumentException();
        }

        if (!map[i - 1][j - 1]) {
            map[i - 1][j - 1] = true;
            openSites++;
        }
        int index = calcIndex(i, j);
        if (isAbove(index)) {
            uf.union(index, ABOVE_ROOT_INDEX);
        }
        else if (isBelow(index)) {
            uf.union(index, beloveRootIndex);
        }

        if (isOpenAndValid(i + 1, j)) uf.union(index, calcIndex(i + 1, j));
        if (isOpenAndValid(i - 1, j)) uf.union(index, calcIndex(i - 1, j));
        if (isOpenAndValid(i, j + 1)) uf.union(index, calcIndex(i, j + 1));
        if (isOpenAndValid(i, j - 1)) uf.union(index, calcIndex(i, j - 1));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    private boolean isOpenAndValid(int i, int j) {
        i = i - 1;
        j = j - 1;
        return 0 <= i && i <= n - 1 &&
                0 <= j && j <= n - 1 &&
                map[i][j];
    }

    private boolean isBelow(int index) {
        return index > (n * n) - n;
    }

    private boolean isAbove(int index) {
        return index <= n;
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException();
        }
        int index = calcIndex(row, col);
        return uf.connected(index, ABOVE_ROOT_INDEX);
    }

    private int calcIndex(int i, int j) {
        return indexes[i - 1][j - 1];
    }

    /* public static void main(String[] args) {
        int n = 3;
        Percolation p = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.println(p.calcIndex(i, j));
            }
        }

    } */
}
