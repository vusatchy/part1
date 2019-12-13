import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final double mean;

    private final double dev;

    private final double high;

    private final double low;

    public PercolationStats(int n, int t) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        if (t <= 0) {
            throw new IllegalArgumentException();
        }
        double[] possibilities = new double[t];
        Percolation percolation;
        for (int ti = 0; ti < t; ti++) {
            double timesOpened = 0;
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int i = getRandomNumberInRange(1, n);
                int j = getRandomNumberInRange(1, n);
                if (!percolation.isOpen(i, j)) {
                    timesOpened++;
                    percolation.open(i, j);
                }
            }
            possibilities[ti] = (timesOpened / (n * n));
        }
        this.mean = StdStats.mean(possibilities);
        this.dev = StdStats.stddev(possibilities);
        this.high = mean + (CONFIDENCE_95 * dev) / Math.sqrt(t);
        this.low = mean - (CONFIDENCE_95 * dev) / Math.sqrt(t);

    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return dev;
    }

    public double confidenceLo() {
        return low;
    }

    public double confidenceHi() {
        return high;
    }

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", "
                                   + percolationStats.confidenceHi() + "]");
    }
}