/**
 *
 * @author marco
 */
public class Solver {

    private final MinPQ<SearchNode> puzzleQueue;
    private final MinPQ<SearchNode> twinQueue;
    private SearchNode solution;

    public Solver(Board root) {
        SearchNode rootNode = new SearchNode(root, null);
        puzzleQueue = new MinPQ<>();
        puzzleQueue.insert(rootNode);

        Board twin = root.twin();
        SearchNode twinNode = new SearchNode(twin, null);
        twinQueue = new MinPQ<>();
        twinQueue.insert(twinNode);

        solve(puzzleQueue, twinQueue);
    }

    private void solve(MinPQ<SearchNode> puzzle, MinPQ<SearchNode> twin) {

        while (!puzzle.isEmpty() || !twin.isEmpty()) {
            if (computeSolution(puzzle) != null) {
                return;
            }
            if (computeSolution(twin) != null) {
                solution = null;
                return;
            }
        }
    }

    private SearchNode computeSolution(MinPQ<SearchNode> queue) {

        SearchNode minNode = queue.delMin();
        if (minNode.isGoal()) {
            solution = minNode;
            return solution;
        }
        Iterable<Board> neighbors = minNode.neighborFilter();
        for (Board neighbor : neighbors) {
            SearchNode node = new SearchNode(neighbor, minNode);
            queue.insert(node);
        }
        return null;
    }

    public boolean isSolvable() {
        return solution != null;

    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return solution.moves;
    }

    public Iterable<Board> solution() {
        if (solution == null) {
            return null;
        }
        Stack<Board> solutionsTrace = new Stack<>();
        SearchNode node = solution;
        while (node != null) {
            Board board = node.board;
            solutionsTrace.push(board);
            node = node.prev;
        }
        return solutionsTrace;
    }

    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;
        private final SearchNode prev;
        private final int manhattan;
        private final int hamming;
        private int moves;
        private final int priority;

        public SearchNode(Board board, SearchNode prev) {
            this.board = board;
            this.prev = prev;
            if (prev == null) {
                moves = 0;
            } else {
                moves = prev.moves + 1;
            }
            this.manhattan = board.manhattan();
            this.hamming = board.hamming();
            this.priority = manhattan + moves;
        }

        private Iterable<Board> neighborFilter() {
            Iterable<Board> neighbors = board.neighbors();
            Queue<Board> neighborsFiltered = new Queue<>();
            if (prev != null) {
                for (Board neighbor : neighbors) {
                    if (neighbor.equals(prev.board)) {
                        continue;
                    }
                    neighborsFiltered.enqueue(neighbor);
                }
                return neighborsFiltered;
            }
            return neighbors;
        }

        public boolean isGoal() {
            return this.board.isGoal();
        }

        @Override
        public int compareTo(final SearchNode that) {

            if (this.priority > that.priority) {
                return 1;
            }
            if (this.priority < that.priority) {
                return -1;
            }
            if (this.manhattan > that.manhattan) {
             return 1;
             }
             if (this.manhattan < that.manhattan) {
             return -1;
             }
            return 0;
        }

    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Stopwatch watch = new Stopwatch();
        
        Solver solver = new Solver(initial);

        double elapsedTime = watch.elapsedTime();
        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Time: " + elapsedTime + "s ");
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
