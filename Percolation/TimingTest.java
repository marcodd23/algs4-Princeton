/*----------------------------------------------------------------
 *  Author:        Marco Di Dionisio
 *
 *  Compilation:   javac -cp ../stdlib.jar:../algs4.jar:. TimingTest.java
 *  Execution:     javac -cp ../stdlib.jar:../algs4.jar:. TimingTest.java
 *
 *  Tests the percolation as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 *----------------------------------------------------------------*/

public class TimingTest {

    public TimingTest() {
    }

    public double singleTest(final int N) {
        Percolation perc = new Percolation(N);
        int x;
        int y;
        double time;
        StopwatchCPU stopWatch = new StopwatchCPU();
        while (!perc.percolates()) {
            x = StdRandom.uniform(1, N + 1);
            y = StdRandom.uniform(1, N + 1);
            if (!perc.isOpen(x, y)) {
                //StdOut.println("Opening " + "(" + x + ", " + y + ")");
                perc.open(x, y);
            }
        }
        time = stopWatch.elapsedTime();
        return time;
    }

    public static void main(String[] args) {
        
        int N = StdIn.readInt();
        
        TimingTest test = new TimingTest();

        double time = test.singleTest(N);
        
        StdOut.println("\nTime: " + time + " Seconds\n");
    }
}

