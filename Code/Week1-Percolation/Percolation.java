import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] siteStat; // array to indicate site status(Opened or not)
    private final boolean[] topConnect; // array to indicate connection with top
    private final boolean[] bottomConnect; // array to indicate connection with bottom
    private boolean percolateFlag = false; // flag to indicate Percolation
    private final int size; // size of the block
    private int openNum; // Numbers of opend site
    private final WeightedQuickUnionUF uf; // UF for the datastructure

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Error: n<1");
        }
        size = n;
        openNum = 0;
        siteStat = new boolean[n * n];
        topConnect = new boolean[n * n];
        bottomConnect = new boolean[n * n];

        for (int i = 0; i < size * size; i++) {
            siteStat[i] = false;
            topConnect[i] = false;
            bottomConnect[i] = false;
        }

        uf = new WeightedQuickUnionUF(n * n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        row = row - 1;
        col = col - 1;
        validate(row, col);

        if (siteStat[mapping(row, col)]) return;

        siteStat[mapping(row, col)] = true;
        boolean top = false;
        boolean bottom = false;


        // at 1st row, union with upper point
        if (row == 0) {
            top = true;
        }
        // at (size-1)th row, union with lower point
        if (row == size - 1) {
            bottom = true;
        }

        // union with adjacent opened site
        if (row - 1 >= 0) {
            if (siteStat[mapping(row - 1, col)]) {
                if (topConnect[uf.find(mapping(row - 1, col))])
                    top = true;
                if (bottomConnect[uf.find(mapping(row - 1, col))])
                    bottom = true;
                uf.union(mapping(row - 1, col), mapping(row, col));
            }
        }

        if (row + 1 <= size - 1) {
            if (siteStat[(row + 1) * size + col]) {
                if (topConnect[uf.find(mapping(row + 1, col))])
                    top = true;
                if (bottomConnect[uf.find(mapping(row + 1, col))])
                    bottom = true;
                uf.union(mapping(row + 1, col), mapping(row, col));
            }
        }

        if (col + 1 <= size - 1) {
            if (siteStat[mapping(row, col + 1)]) {
                if (topConnect[uf.find(mapping(row, col + 1))])
                    top = true;
                if (bottomConnect[uf.find(mapping(row, col + 1))])
                    bottom = true;
                uf.union(mapping(row, col + 1), mapping(row, col));
            }
        }

        if (col - 1 >= 0) {
            if (siteStat[mapping(row, col - 1)]) {
                if (topConnect[uf.find(mapping(row, col - 1))])
                    top = true;
                if (bottomConnect[uf.find(mapping(row, col - 1))])
                    bottom = true;
                uf.union(mapping(row, col - 1), mapping(row, col));
            }
        }
        topConnect[uf.find(mapping(row, col))] = top;
        bottomConnect[uf.find(mapping(row, col))] = bottom;
        openNum++;

        if (topConnect[uf.find(mapping(row, col))] && bottomConnect[uf.find(mapping(row, col))])
            percolateFlag = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row = row - 1;
        col = col - 1;
        validate(row, col);
        return (this.siteStat[row * size + col]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row = row - 1;
        col = col - 1;
        validate(row, col);
        return (topConnect[uf.find(row * size + col)]);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolateFlag;
    }

    // validate the row and column indices
    private void validate(int row, int col) {
        if (row > size - 1 || row < 0 || col > size - 1 || col < 0) {
            throw new IllegalArgumentException("Error: row/col exceeds border");
        }
    }

    // mapping 2D to 1D
    private int mapping(int row, int col) {
        return row * size + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Percolation test = new Percolation(10);
        test.open(2, 2);
        System.out.println(test.isOpen(2, 2));
        System.out.println(test.isFull(-1, -1));
        System.out.println(test.numberOfOpenSites());
        System.out.println(test.percolates());
    }
}
