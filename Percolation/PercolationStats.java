/*----------------------------------------------------------------
 *  Author:        Marco Di Dionisio
 *
 *  Compilation:   javac -cp ../stdlib.jar:../algs4.jar:. PercolationStats.java
 *  Execution:     java -cp ../stdlib.jar:../algs4.jar:. PercolationStats 200 100
 *
 *  Tests the percolation as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 *----------------------------------------------------------------*/
/**
 *
 * @author marco
 */
public class PercolationStats {

    private double mean;
    private double stddev;
    //private Percolation perc;
    //private double openCount;
    private double[] opensFractionArray;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(final int N, final int T) {
        if (N <= 0 | T <= 0) {
            throw new IllegalArgumentException("N and T must be > 0 !!!!");
        } else {
            opensFractionArray = testsRunner(N, T);
            mean = StdStats.mean(opensFractionArray);
            stddev = StdStats.stddev(opensFractionArray);
            confidenceLo = (mean - ((1.96 * stddev) / Math.sqrt(T)));
            confidenceHi = (mean + ((1.96 * stddev) / Math.sqrt(T)));

        }
    }

    private double[] testsRunner(final int N, final int T) {
        double[] testsResults = new double[T];
        for (int i = 0; i < T; i++) {
            testsResults[i] = (singleTest(N)) / (N * N);
        }
        return testsResults;
    }

    private double singleTest(final int N) {
        Percolation perc = new Percolation(N);
        double openCount = 0;
        while (!perc.percolates()) {
            int x = StdRandom.uniform(1, N + 1);
            int y = StdRandom.uniform(1, N + 1);
            if (!perc.isOpen(x, y)) {
                perc.open(x, y);
                openCount++;
            }
        }
        return openCount;
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {

        int N = StdIn.readInt();
        int T = StdIn.readInt();

        PercolationStats percStats = new PercolationStats(N, T);

        StdOut.println("\nmean                    = " + percStats.mean());
        StdOut.println("stdev                   = " + percStats.stddev());
        StdOut.println("95% confidence interval = " + percStats.confidenceLo + ", " + percStats.confidenceHi);

    }
}

