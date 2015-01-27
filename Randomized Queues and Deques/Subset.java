/*----------------------------------------------------------------
 *  Author:        Marco Di Dionisio
 *
 *  Compilation:   javac -cp ../stdlib.jar:../algs4.jar:. Subset.java
 *  Execution:     % echo A B C D E F G H I | java -cp ../stdlib.jar:../algs4.jar:. Subset 3
 *
 *  Tests the percolation as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 *----------------------------------------------------------------*/
/**
 *
 * @author marco
 */
public class Subset {

    public static void main(String[] args) {
        Integer k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (StdIn.hasNextLine() && !StdIn.isEmpty()) {
           queue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
          StdOut.println(queue.dequeue());  
        }

    }
}

