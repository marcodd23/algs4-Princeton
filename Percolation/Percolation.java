/*----------------------------------------------------------------
 *  Author:        Marco Di Dionisio
 *
 *  Compilation:   javac -cp ../stdlib.jar:../algs4.jar:. Percolation.java
 *  Execution:     java -cp ../stdlib.jar:../algs4.jar:. Percolation.java
 *
 *  Tests the percolation as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 *----------------------------------------------------------------*/

/**
 *             25 (virtual top site)
 * 
 *  _|_1___2___3___4___5___
 *   | 
 * 1 | 0   1   2   3   4
 *   |
 * 2 | 5   6   7   8   9
 *   |
 * 3 | 10  11  12  13  14
 *   | 
 * 4 | 15  16  17  18  19
 *   | 
 * 5 | 20  21  22  23  24
 *   |
 *             26 (virtual bottom site)
 * 
 */

/**
 *
 * @author marco
 */
public class Percolation {

    private final int rowSize;
    private final int gridSize;
    private boolean[] grid;
    private WeightedQuickUnionUF ufPercolation;
    private WeightedQuickUnionUF ufFullness;
    private int topIndex;
    private int bottomIndex;
    private boolean percolated;

    /**
     * Creates N-by-N grid, with all sites blocked (false). We consider a matrix
     * where row and column indices are between 1 and N. We pass to UnionFind an
     * N*N+1 sites , because we consider one virtual site top(N*N index), and
     * one virtual site bottom (N*N+1 index)
     */
    public Percolation(final int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Dimension N must be > 0");
        } else {
            percolated = false;
            rowSize = N;
            gridSize = N * N;
            topIndex = gridSize;
            bottomIndex = gridSize + 1;
            grid = new boolean[gridSize];
            ufPercolation = new WeightedQuickUnionUF(gridSize + 2);
            ufFullness = new WeightedQuickUnionUF(gridSize + 2);
        }
    }

    public void open(final int i, final int j) {
        checkIndexInput(i, j);

        int index = getGridIndex(i, j);
        if (!isOpen(index)) {
            grid[index] = true;

            if (gridSize == 1) {
                ufPercolation.union(index, topIndex);
                ufPercolation.union(index, bottomIndex);
                ufFullness.union(index, topIndex);
                percolated = true;
            }

            if (i == 1) {
                ufPercolation.union(index, topIndex);
                ufFullness.union(index, topIndex);
            }

            if (i == rowSize) {
             ufPercolation.union(index, bottomIndex);
             }
            
            //Up
            if (i > 1 && isOpen(i - 1, j)) {
                ufPercolation.union(index, getGridIndex(i - 1, j));
                ufFullness.union(index, getGridIndex(i - 1, j));
            }
            //Down
            if (i < rowSize && isOpen(i + 1, j)) {
                ufPercolation.union(index, getGridIndex(i + 1, j));
                ufFullness.union(index, getGridIndex(i + 1, j));
            }
            //Left
            if (j > 1 && isOpen(i, j - 1)) {
                ufPercolation.union(index, getGridIndex(i, j - 1));
                ufFullness.union(index, getGridIndex(i, j - 1));
            }
            //Right
            if (j < rowSize && isOpen(i, j + 1)) {
                ufPercolation.union(index, getGridIndex(i, j + 1));
                ufFullness.union(index, getGridIndex(i, j + 1));
            }
        }
    }

    public boolean isOpen(final int i, final int j) {
        checkIndexInput(i, j);
        return isOpen(getGridIndex(i, j));
    }

    private boolean isOpen(int index) {
        return grid[index];
    }

    public boolean isFull(final int i, final int j) {
        checkIndexInput(i, j);
        if (isOpen(i, j)) {
            return ufFullness.connected(topIndex, getGridIndex(i, j));
        } else {
            return false;
        }

    }

    public boolean percolates() {
        if (!percolated) {
            percolated = ufPercolation.connected(topIndex, bottomIndex);
        }
        return percolated;
    }

    /**
     * Map from a 2-dimensional (row, column) pair to a 1-dimensional union find
     * object index
     */
    private int getGridIndex(final int i, final int j) {
        // Change i and j indexes to start at 0, not 1
        return (rowSize * (i - 1)) + (j - 1);
    }

    private void checkIndexInput(final int i, final int j) {
        if ((i < 1 || i > rowSize) || (j < 1 || j > rowSize)) {
            throw new IndexOutOfBoundsException("Indexes must be between 1 and " + rowSize);
        }
    }

    public static void main(String[] args) {

        final int N = StdIn.readInt();
        Percolation perc = new Percolation(N);
        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            perc.open(x, y);
        }
        
        String outputMatrix;
        outputMatrix = new String();
        for (int i = 0; i < N; i++) {
            outputMatrix = "";
            for (int j = 0; j < N; j++) {
                if (perc.isOpen(i + 1, j + 1)) {
                    outputMatrix += "0 ";
                } else {
                    outputMatrix += "* ";
                }
            }
            StdOut.println(outputMatrix);
        }
        StdOut.println("Does the system percolates ?" + perc.percolates());


    }
}

