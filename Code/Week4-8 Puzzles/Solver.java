import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private final Board initial;
    private final Board initialTwin;
    private int solvable = -1;
    private int leastmove = -1;
    private ArrayList<Board> sequence = null;
    private GameTreeNode goal = null;

    private class GameTreeNode implements Comparable<GameTreeNode> {
        private final GameTreeNode parent;
        private final int priority;
        private final int distance;
        private final int moves;
        private final Board data;


        GameTreeNode(Board input, int mv, GameTreeNode par) {
            data = input;
            moves = mv;
            distance = data.manhattan();
            priority = moves + distance;
            parent = par;
        }

        int moveTimes() {
            return moves;
        }

        public int compareTo(GameTreeNode node) {
            if (priority == node.priority) {
                return Integer.compare(distance, node.distance);
            }
            else {
                return Integer.compare(priority, node.priority);
            }
        }


        public boolean equals(Object node) {
            if (node == null) {
                return false;
            }
            if (this == node) {
                return true;
            }
            if (node.getClass() != this.getClass()) {
                return false;
            }
            GameTreeNode that = (GameTreeNode) node;
            return data.equals(that.data);
        }

        public int hashCode() {
            return 1;
        }

        public Iterable<Board> getNeighBoard() {
            return data.neighbors();
        }

        public Board getBoard() {
            return data;
        }

        public GameTreeNode getParent() {
            return parent;
        }

        public int getMoves() {
            return moves;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        this.initial = initial;
        this.initialTwin = initial.twin();
        running();
    }

    private void running() {
        MinPQ<GameTreeNode> gameTree = new MinPQ<GameTreeNode>();
        MinPQ<GameTreeNode> gameTreeTwin = new MinPQ<GameTreeNode>();
        GameTreeNode init = new GameTreeNode(initial, 0, null);
        GameTreeNode initTwin = new GameTreeNode(initialTwin, 0, null);
        boolean initGoal = false;
        if (init.data.isGoal()) {
            leastmove = init.getMoves();
            solvable = 1;
            goal = init;
            initGoal = true;
        }
        else if (initTwin.data.isGoal()) {
            return;
        }

        Iterable<Board> iter = init.getNeighBoard();
        Iterable<Board> iterTwin = initTwin.getNeighBoard();
        for (Board i : iter) {
            gameTree.insert(new GameTreeNode(i, 1, init));
        }
        for (Board i : iterTwin) {
            gameTreeTwin.insert(new GameTreeNode(i, 1, initTwin));
        }

        while (!gameTree.isEmpty() && (!initGoal)) {
            GameTreeNode deleNode = gameTree.delMin();
            GameTreeNode deleNodeTwin = gameTreeTwin.delMin();
            if (deleNode.data.isGoal()) {
                leastmove = deleNode.getMoves();
                solvable = 1;
                goal = deleNode;
                break;
            }
            else if (deleNodeTwin.data.isGoal()) {
                StdOut.print(deleNodeTwin.data.toString());
                return;
            }


            Iterable<Board> iters = deleNode.getNeighBoard();
            for (Board i : iters) {
                if (!i.equals(deleNode.getParent().getBoard()))
                    gameTree.insert(new GameTreeNode(i, deleNode.moveTimes() + 1, deleNode));
            }
            Iterable<Board> itersTwin = deleNodeTwin.getNeighBoard();
            for (Board i : itersTwin) {
                if (!i.equals(deleNodeTwin.getParent().getBoard()))
                    gameTreeTwin.insert(new GameTreeNode(i, deleNodeTwin.moveTimes() + 1,
                                                         deleNodeTwin));
            }
        }
        if (solvable == 1) {
            sequence = new ArrayList<>(leastmove + 1);
            GameTreeNode temp = goal;
            for (int i = 0; i < leastmove + 1; i++) {
                sequence.add(0, temp.getBoard());
                temp = temp.getParent();
            }
        }

    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return (solvable != -1);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return leastmove;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return sequence;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
