/*----------------------------------------------------------------
 *  Author:        Marco Di Dionisio
 *
 *  Compilation:   javac -cp ../stdlib.jar:../algs4.jar:. fast.java
 *  Execution:     java -cp ../stdlib.jar:../algs4.jar:. Fast ./collinear-input/input.txt
 *
 *  Tests the percolation as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 *----------------------------------------------------------------*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author marco
 */
public class Fast {

    private static void printPoints(Point pointArray[], int N, In inputFile) {
        for (int i = 0; i < N; i++) {
            int x = inputFile.readInt();
            int y = inputFile.readInt();
            pointArray[i] = new Point(x, y);
            pointArray[i].draw();
        }
    }

    private static boolean checkSubSegments(List<Point> discoveredStartSegments, List<Point> collinearPoints, double actualSlope) {
        boolean subSegment = false;
        if (!discoveredStartSegments.isEmpty()) {
            for (Point point : discoveredStartSegments) {
                if (point.slopeTo(collinearPoints.get(0)) == actualSlope) {
                    subSegment = true;
                }
            }
        }

        return subSegment;
    }

    private static void printDiscoveredSegments(boolean subSegment, List<Point> collinearPoints) {

        if (!subSegment) {
            int size = collinearPoints.size();
            for (int k = 0; k < size - 1; k++) {
                StdOut.print(collinearPoints.get(k));
                StdOut.print(" -> ");
            }
            StdOut.println(collinearPoints.get(size - 1) + "\n");
            collinearPoints.get(0).drawTo(collinearPoints.get(size - 1));
        }
    }

    public static void main(String[] args) {

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        // make the points a bit larger
        //StdDraw.setPenRadius(0.01);
        Point pointArray[];

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        pointArray = new Point[N];

        printPoints(pointArray, N, in);

        List<Point> discoveredStartSegments = new ArrayList<>();

        double time;
        StopwatchCPU stopWatch = new StopwatchCPU();

        //Arrays.sort(pointArray);
        for (int i = 0; i < N; i++) {

            //Arrays.sort(pointArray);
            Point p = pointArray[i];
            Arrays.sort(pointArray, i + 1, N, p.SLOPE_ORDER);
            //Arrays.sort(pointArray, i + 1, N);
            //double prevSlope;
            double actualSlope;
            boolean subSegment;
            List<Point> collinearPoints;
            int j = i + 1;
            while (j < N) {
                actualSlope = pointArray[i].slopeTo(pointArray[j]);
                collinearPoints = new ArrayList<>();
                collinearPoints.add(pointArray[i]);
                collinearPoints.add(pointArray[j]);

                j = j + 1;
                if (j < N && actualSlope == pointArray[i].slopeTo(pointArray[j])) {
                    collinearPoints.add(pointArray[j]);
                    j = j + 1;
                    if (j < N && actualSlope == pointArray[i].slopeTo(pointArray[j])) {
                        collinearPoints.add(pointArray[j]);
                        j = j + 1;
                        int scanIncr = j;
                        for (int k = scanIncr; k < N; k++) {
                            if (actualSlope == pointArray[i].slopeTo(pointArray[k])) {
                                collinearPoints.add(pointArray[k]);
                                j = k;
                            } else {
                                break;
                            }
                        }

                        subSegment = checkSubSegments(discoveredStartSegments, collinearPoints, actualSlope);

                        discoveredStartSegments.add(collinearPoints.get(0));
                        Collections.sort(collinearPoints);

                        printDiscoveredSegments(subSegment, collinearPoints);
                    }
                }
            }
        }

        time = stopWatch.elapsedTime();
        StdOut.println("\nTIME: " + time + " Seconds\n");

    }

}

