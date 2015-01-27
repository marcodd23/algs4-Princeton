/*----------------------------------------------------------------
 *  Author:        Marco Di Dionisio
 *
 *  Compilation:   javac -cp ../stdlib.jar:../algs4.jar:. Brute.java
 *  Execution:     java -cp ../stdlib.jar:../algs4.jar:. Brute ./collinear-input/input.txt
 *
 *  Tests the percolation as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 *----------------------------------------------------------------*/

import java.util.Arrays;

/**
 *
 * @author marco
 */
public class Brute {

    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        //StdDraw.show(0);
        //StdDraw.setPenRadius(0.01);  // make the points a bit larger

        Point pointArray[];

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        pointArray = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            pointArray[i] = new Point(x, y);;
            pointArray[i].draw();
        }
        Point[] discoveredPoint = new Point[4];

        for (int p = 0; p < N; p++) {
            for (int q = p + 1; q < N; q++) {
                double slopeToQ = pointArray[p].slopeTo(pointArray[q]);
                for (int r = q + 1; r < N; r++) {
                    double slopeToR = pointArray[p].slopeTo(pointArray[r]);
                    if (slopeToQ != slopeToR) {
                        continue;
                    }
                    for (int s = r + 1; s < N; s++) {
                        double slopeToS = pointArray[p].slopeTo(pointArray[s]);
                        if (slopeToQ == slopeToS) {
                            
                            discoveredPoint[0] = pointArray[p];
                            discoveredPoint[1] = pointArray[q];
                            discoveredPoint[2] = pointArray[r];
                            discoveredPoint[3] = pointArray[s];
                            Arrays.sort(discoveredPoint); 

                            for (int i = 0; i < 3; i++) {
                                StdOut.print(discoveredPoint[i]);
                                StdOut.print(" -> ");
                            }
                            StdOut.println(discoveredPoint[3] + "\n");

                            discoveredPoint[0].drawTo(discoveredPoint[3]);
                            //Collections.min(discoveredPoint).drawTo(Collections.max(discoveredPoint));
                        }
                    }

                }

            }

        }

    }
}

