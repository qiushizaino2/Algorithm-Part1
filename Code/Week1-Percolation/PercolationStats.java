import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double meanRes;
    private final double stdDev;
    private final double confidenceLo;
    private final double confidenceHi;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("Error: n<=0");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("Error: trials<=0");
        }

        double[] opensitesFractions = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perco = new Percolation(n);
            while (!perco.percolates()) {
                int row = StdRandom.uniform(0, n) + 1;
                int col = StdRandom.uniform(0, n) + 1;
                perco.open(row, col);
            }
            opensitesFractions[i] = perco.numberOfOpenSites() * 1.0 / (n * n);
        }

        meanRes = StdStats.mean(opensitesFractions);
        stdDev = StdStats.stddev(opensitesFractions);
        confidenceLo = (meanRes - CONFIDENCE_95 * stdDev / Math.sqrt(trials));
        confidenceHi = (meanRes + CONFIDENCE_95 * stdDev / Math.sqrt(trials));
    }

    // sample mean of percolation threshold
    public double mean() {
        return meanRes;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        // StdOut.printf("n                        = %d\n", n);
        // StdOut.printf("trials                   = %d\n", trials);

        PercolationStats test;
        test = new PercolationStats(n, trials);

        // StdOut.println("mean() = " + test.mean());
        // StdOut.println("stddev() = " + test.stddev());
        // StdOut.println(
        //         "95%% confidence interval = [" + test.confidenceLo() + "," + test.confidenceHi()
        //                 + "]");
        StdOut.printf("%-25s %s %f\n", "mean", "=", test.mean());
        StdOut.printf("%-25s %s %f\n", "stddev", "=", test.stddev());
        StdOut.printf("%-25s %s%f%s%f%s\n", "95% confidence interval", "= [", test.confidenceLo(),
                      ", ", test.confidenceHi(), "]");

    }

}
