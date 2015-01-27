/*----------------------------------------------------------------
 *  Author:        Marco Di Dionisio
 *
 *  Compilation:   javac -cp ../stdlib.jar:../algs4.jar:. Point.java
 *
 *  Tests the percolation as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 *----------------------------------------------------------------*/

import java.util.Comparator;

/**
 *
 * @author marco
 */
public class Point implements Comparable<Point> {

    public final int x;
    public final int y;
    public final Comparator<Point> SLOPE_ORDER;

//    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
//
//        @Override
//        public int compare(Point point1, Point point2) {
//            
//            
//            return 0;
//        }
//    };
    
    // compare points by slope to this point
    private class SlopeOrderComparator implements Comparator<Point> {

        @Override
        public int compare(Point p1, Point p2) {
            
            double slopeToP1 = slopeTo(p1);
            double slopeToP2 = slopeTo(p2);
            
            if(slopeTo(p1) > slopeTo(p2)){
                return 1;
            }else if(slopeTo(p1) < slopeTo(p2)){
                return -1;
            }else {
                return 0;
            }
        }

    }

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
        SLOPE_ORDER = new SlopeOrderComparator();
    }

    // draw this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Check if this point is lexicographically smaller than that point 
    @Override
    public int compareTo(final Point that) {
        if (y < that.y || (y == that.y && x < that.x)) {
            return -1;
        }
        if (y > that.y || (y == that.y && x > that.x)) {
            return 1;
        }
        return 0;
    }

    // the slope between this point and that point
    public double slopeTo(final Point that) {
        if (that == null) {
            throw new NullPointerException();
        }
        if (x == that.x) {
            if (y == that.y) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        if (y == that.y){
            return 0.0;
        }
        return ((double) (that.y - y)) / (that.x - x);
    }

    public static void main(String[] args) {
        double a = 5.0;
        double x = (a) / 0;
        double y = (-a) / 0;
        System.out.println(a < Double.POSITIVE_INFINITY);

        System.out.println(a > Double.NEGATIVE_INFINITY);

        double t = 1.000001;
        double b = 0.000001;
        double c = t - b;
        System.out.println(c);
        System.out.println(Math.abs(c - 1.0));
        System.out.println(Math.abs(c - 1.0) <= 0.000001);
        
         System.out.println("\n ////////////////////////////////////\n");
         
         
        int d = 9;
        int f = 2;
        System.out.println("-->" + ((double)d)/f + "\n" );
        
        
        
    }

}

