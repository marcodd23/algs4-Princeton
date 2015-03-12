import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author marco
 */
public class Board {

    private final int N;
    private int manhattan;
    private int hamming;
    private final short[][] blocks;
    private int emptyRow;
    private int emptyClm;

    public Board(int[][] blocks) {
        N = blocks[0].length;
        manhattan = 0;
        hamming = 0;
        this.blocks = new short[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                short val = (short) blocks[i][j];
                this.blocks[i][j] = val;
                manhattan(val, i, j);
                hamming(val, i, j);
                if (val == 0) {
                    emptyRow = i;
                    emptyClm = j;
                }
            }
        }
    }

    private Board(short[][] blocks, int N, int manhattanVal, int hammingVal, int emptyRow, int emptyClm) {
        this.blocks = blocks;
        this.N = N;
        this.manhattan = manhattanVal;
        this.hamming = hammingVal;
        this.emptyRow = emptyRow;
        this.emptyClm = emptyClm;
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        return hamming;

    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return (manhattan == 0 && hamming == 0);
    }

    public Board twin() {
        short[][] twinBlocks = new short[N][N];
        copyBlocksArray(blocks, twinBlocks);
        int row = (N / 2) - 1;
        int newManhattan = manhattan;
        int newHamming = hamming;
        int[] manhattanAndHammingUpdate;
        if (twinBlocks[row][0] != 0 && twinBlocks[row][1] != 0) {
            manhattanAndHammingUpdate = swapBlocks(twinBlocks, row, 0, row, 1, newManhattan, newHamming);
        } else {
            row++;
            manhattanAndHammingUpdate = swapBlocks(twinBlocks, row, 0, row, 1, newManhattan, newHamming);
        }
        newManhattan = manhattanAndHammingUpdate[0];
        newHamming = manhattanAndHammingUpdate[1];
        return new Board(twinBlocks, N, newManhattan, newHamming, emptyRow, emptyClm);
    }

    public boolean equals(Object x) {
        if (this == x) {
            return true;
        }
        if (x == null) {
            return false;
        }
        if (this.getClass() != x.getClass()) {
            return false;
        }
        Board that = (Board) x;
        if (this.dimension() != that.dimension()) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        short[][] newghborsBlocks;
        int[] manhAndHamUpdate;
        Board neighBor;
        Queue<Board> queue = new ArrayDeque<>();

        //Up shift of Empty tile
        if (emptyRow > 0) {
            newghborsBlocks = new short[N][N];
            copyBlocksArray(blocks, newghborsBlocks);
            manhAndHamUpdate = swapBlocks(newghborsBlocks, emptyRow, emptyClm, emptyRow - 1, emptyClm, manhattan, hamming);
            neighBor = new Board(newghborsBlocks, N, manhAndHamUpdate[0], manhAndHamUpdate[1], emptyRow - 1, emptyClm);
            queue.add(neighBor);
        }
        //Down shift of Empty tile
        if (emptyRow < N - 1) {
            newghborsBlocks = new short[N][N];
            copyBlocksArray(blocks, newghborsBlocks);
            manhAndHamUpdate = swapBlocks(newghborsBlocks, emptyRow, emptyClm, emptyRow + 1, emptyClm, manhattan, hamming);
            neighBor = new Board(newghborsBlocks, N, manhAndHamUpdate[0], manhAndHamUpdate[1], emptyRow + 1, emptyClm);
            queue.add(neighBor);
        }
        //left shift of Empty tile
        if (emptyClm > 0) {
            newghborsBlocks = new short[N][N];
            copyBlocksArray(blocks, newghborsBlocks);
            manhAndHamUpdate = swapBlocks(newghborsBlocks, emptyRow, emptyClm, emptyRow, emptyClm - 1, manhattan, hamming);
            neighBor = new Board(newghborsBlocks, N, manhAndHamUpdate[0], manhAndHamUpdate[1], emptyRow, emptyClm - 1);
            queue.add(neighBor);
        }
        //Right shift of Empty tile
        if (emptyClm < N - 1) {
            newghborsBlocks = new short[N][N];
            copyBlocksArray(blocks, newghborsBlocks);
            manhAndHamUpdate = swapBlocks(newghborsBlocks, emptyRow, emptyClm, emptyRow, emptyClm + 1, manhattan, hamming);
            neighBor = new Board(newghborsBlocks, N, manhAndHamUpdate[0], manhAndHamUpdate[1], emptyRow, emptyClm + 1);
            queue.add(neighBor);
        }

        return queue;

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%-2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();

    }

    private void manhattan(short val, int row, int clm) {
        if (val != 0) {
            int rowTarget;
            int clmTarget;
            int[] indexsFromValue = getIndexesFromValue(val);
            rowTarget = indexsFromValue[0];
            clmTarget = indexsFromValue[1];
            if (rowTarget != row || clmTarget != clm) {
                int rowDiff = Math.abs(rowTarget - row);
                int clmDiff = Math.abs(clmTarget - clm);
                manhattan += rowDiff + clmDiff;
            }
        }
    }

    private void hamming(short val, int row, int clm) {
        if (val != 0) {
            int targetValue = (N * row) + clm + 1;
            if (val != targetValue) {
                hamming++;
            } else {
                return;
            }
        }
    }

    private int[] getIndexesFromValue(int val) {
        int[] indexes = new int[2];
        int rowTarget;
        int clmTarget;
        if (val % N != 0) {
            rowTarget = val / (N);
        } else {
            rowTarget = val / (N + 1);
        }
        clmTarget = val - 1 - (N * rowTarget);
        indexes[0] = rowTarget;
        indexes[1] = clmTarget;
        return indexes;
    }

    /**
     * Return an int[] manhattanAndHammingUpdate of 2 elements:
     * <br>
     * element 0 ==> updated manhattan value
     * <br>
     * element 1 ==> updated hamming value
     *
     * @param blocks
     * @param row1
     * @param clm1
     * @param row2
     * @param clm2
     * @param newManhattan
     * @param newHamming
     * @return manhattanAndHammingUpdate
     */
    private int[] swapBlocks(short[][] blocksMatrix, int row1, int clm1, int row2, int clm2, int newManhattan, int newHamming) {
        short temp;
        int manhattanUpdate = manhattanUpdate(blocksMatrix, row1, clm1, row2, clm2);
        int hammingUpdate = hammingUpdate(blocksMatrix, row1, clm1, row2, clm2);
        temp = blocksMatrix[row1][clm1];
        blocksMatrix[row1][clm1] = blocksMatrix[row2][clm2];
        blocksMatrix[row2][clm2] = temp;
        int[] manhattanAndHammingUpdate = new int[2];
        manhattanAndHammingUpdate[0] = manhattanUpdate;
        manhattanAndHammingUpdate[1] = hammingUpdate;
        return manhattanAndHammingUpdate;
    }

    private int manhattanUpdate(short[][] blocksMatrix, int row1, int clm1, int row2, int clm2) {
        int correctRow1;
        int correctRow2;
        int correctClm1;
        int correctClm2;
        int newManhattan = manhattan;
        int val1 = blocksMatrix[row1][clm1];
        int val2 = blocksMatrix[row2][clm2];

        int[] correctIndexes1 = getIndexesFromValue(val1);
        int[] correctIndexes2 = getIndexesFromValue(val2);

        correctRow1 = correctIndexes1[0];
        correctClm1 = correctIndexes1[1];
        correctRow2 = correctIndexes2[0];
        correctClm2 = correctIndexes2[1];

        //Orizzontal swap
        if (row1 == row2) {
            if (val1 != 0) {
                //Update Manhattan for first block;
                if (Math.abs(clm1 - correctClm1) < Math.abs(clm2 - correctClm1)) {
                    newManhattan++;
                } else {
                    newManhattan--;
                }
            }
            if (val2 != 0) {
                //Update Manhattan for second block;
                if (Math.abs(clm2 - correctClm2) < Math.abs(clm1 - correctClm2)) {
                    newManhattan++;
                } else {
                    newManhattan--;
                }
            }
        } else {
                //Update Manhattan for first block;
                if (val1 != 0) {
                    if (Math.abs(row1 - correctRow1) < Math.abs(row2 - correctRow1)) {
                        newManhattan++;
                    } else {
                        newManhattan--;
                    }
                }

            if (val2 != 0) {
                //Update Manhattan for second block;
                if (Math.abs(row2 - correctRow2) < Math.abs(row1 - correctRow2)) {
                    newManhattan++;
                } else {
                    newManhattan--;
                }
            }
        }

        return newManhattan;

    }

    private int hammingUpdate(short[][] blocksMatrix, int row1, int clm1, int row2, int clm2) {
        int correctRow1;
        int correctRow2;
        int correctClm1;
        int correctClm2;
        int newHamming = hamming;
        int val1 = blocksMatrix[row1][clm1];
        int val2 = blocksMatrix[row2][clm2];

        int[] correctIndexes1 = getIndexesFromValue(val1);
        int[] correctIndexes2 = getIndexesFromValue(val2);

        correctRow1 = correctIndexes1[0];
        correctClm1 = correctIndexes1[1];
        correctRow2 = correctIndexes2[0];
        correctClm2 = correctIndexes2[1];

        if (row1 == correctRow1 && clm1 == correctClm1) {
            newHamming++;
        } else if (row2 == correctRow1 && clm2 == correctClm1) {
            newHamming--;
        }
        
        if (row2 == correctRow2 && clm2 == correctClm2) {
            newHamming++;
        } else if (row1 == correctRow2 && clm1 == correctClm2) {
            newHamming--;
        }

        return newHamming;
    }

    private void copyBlocksArray(short[][] src, short[][] dest) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dest[i][j] = src[i][j];
            }
        }
    }

    public static void main(String[] args) {

    }

}
