/*************************************************************************
 * Name: Floyd Taylor   
 *
 * Compilation:  javac SolverVisualizer.java
 * Execution:    java SolverVisualizer <input file>
 * Dependencies: stdLib, java.util, Board.class, Solver.class
 *
 * Description: A visualizer for the solution for Solver
 *
 *************************************************************************/
import java.awt.Font;
import java.util.Scanner;

/**
 * <p>
 * This Visualizer displays the output solution() from the Solver class. The
 * output is displayed using the StdDraw grid of 500x500, and each move is drawn
 * from parsing the toString.
 * </p>
 * <p>
 * Delay between displays is calculated based on number of moves to the total
 * display is approximately 10 seconds. Also, the font size is adjusted
 * depending on the size of the puzzle.
 * </p>
 * =
 *
 *
 * @author marco
 */
public class SolverVisualizer {

    /*
     * private method to parse the returned string from Board.toString()
     * only the board is returned, not the dimension
     */
    private static int[][] parseBoard(String s) {

        Scanner in = new Scanner(s);
        int N = in.nextInt();
        int[][] list = new int[N][N];
        int row = 0;
        int col = 0;
        while (in.hasNext()) {
            list[row][col++] = in.nextInt();
            if (col >= N) {
                col = 0;
                row++;
            }
        }
        return list;
    }

    /*
     * private method to display parsed toString for debugging
     */
    private static void printList(int[][] l) {
        for (int i = 0; i < l[0].length; i++) {
            for (int j = 0; j < l[0].length; j++) {
                StdOut.print("" + l[i][j] + " ");
            }
            StdOut.println("");
        }
    }

    /*
     * private method to draw the board, using the parsed [][]
     * Other parameters are used for display and timing
     */
    private static void drawBoard(int[][] b, int delay, int move, int moves,
            String fname) {
        int N = b[0].length;

        StdDraw.show(0);
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-.05 * N, 1.05 * N);
        StdDraw.setYscale(-.05 * N, 1.05 * N);
        StdDraw.filledSquare(N / 2.0, N / 2.0, N / 2.0);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 16));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(.5 * N, N * 1.025, "Visualizer for Puzzle: " + fname);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // StdDraw.setPenColor(colors[nodes[i][j].value]);
                StdDraw.setPenColor(StdDraw.WHITE);
                if (b[i][j] == 0) StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                StdDraw.filledSquare(j + 0.5, N - i - 0.5, 0.45);
                StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 222 / N));
                StdDraw.setPenColor(StdDraw.BLACK);
                if (b[i][j] != 0)
                    StdDraw.text(j + 0.5, N - i - 0.5, "" + b[i][j]);
            }
        }
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 16));
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(.5 * N, -N * .025, "Move " + move + " of " + moves);
        StdDraw.show(delay);
    }

    /**
     * Main routine, with data read from <args[0]>
     * 
     * @param args
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        int[][] l;
        int move = 0;
        int moves;
        if (!solver.isSolvable()) StdOut.println("No solution possible");
        else {
            moves = solver.moves();
            StdOut.println("Solved, number of moves = " + moves);
            int delay = 10000 / moves;
            for (Board board : solver.solution()) {
                l = parseBoard(board.toString());
                // printList(l);
                drawBoard(l, delay, move++, moves, args[0]);
            }
        }
    }
}