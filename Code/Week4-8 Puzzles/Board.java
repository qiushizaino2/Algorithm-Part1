/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private static final int[][] NO_FILES = { };
    private final int size;
    private final int[][] tilseObej;
    private final int zeroIndexI;
    private final int zeroIndexJ;
    private final int hamMing;
    private final int manHattan;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        size = tiles.length;
        tilseObej = new int[size][size];

        int[] tempIndex = new int[2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tilseObej[i][j] = tiles[i][j];
                if (tilseObej[i][j] == 0) {
                    tempIndex[0] = i;
                    tempIndex[1] = j;
                }
            }
        }
        zeroIndexI = tempIndex[0];
        zeroIndexJ = tempIndex[1];

        int temph = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tilseObej[i][j] != i * size + j + 1 && tilseObej[i][j] != 0) {
                    temph++;
                }
            }
        }
        hamMing = temph;

        int tempM = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tilseObej[i][j] != i * size + j + 1 && tilseObej[i][j] != 0) {
                    int goali = (tilseObej[i][j] - 1) / size;
                    int goalj = (tilseObej[i][j] - 1) % size;
                    if (i >= goali) tempM += (i - goali);
                    else tempM += -(i - goali);
                    if (j >= goalj) tempM += (j - goalj);
                    else tempM += -(j - goalj);
                }
            }
        }
        manHattan = tempM;
    }


    // string representation of this board
    public String toString() {
        String present = String.format("%d\n", size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j != size - 1)
                    present = present.concat(String.format("%d ", tilseObej[i][j]));
                else if (i != size - 1)
                    present = present.concat(String.format("%d\n", tilseObej[i][j]));
                else
                    present = present.concat(String.format("%d", tilseObej[i][j]));
            }
        }
        return present;
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        return hamMing;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manHattan;

    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamMing == 0 || manHattan == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (!(y.getClass() == this.getClass())) return false;
        if (this == y) return true;
        Board obj = (Board) y;
        if (size != obj.dimension()) return false;
        return Arrays.deepEquals(tilseObej, obj.tilseObej);
    }


    private int[][] swap(int x, int y, int xx, int yy) {
        if (size == 0) return NO_FILES;
        if (x >= size || y >= size || x < 0 || y < 0 || xx >= size || yy >= size || xx < 0
                || yy < 0)
            return NO_FILES;
        int[][] newTiles = new int[size][size];
        if (!isvalid(xx, yy)) return NO_FILES;
        for (int i = 0; i < size; i++) {
            System.arraycopy(tilseObej[i], 0, newTiles[i], 0, size);
        }
        int tmp = newTiles[x][y];
        newTiles[x][y] = newTiles[xx][yy];
        newTiles[xx][yy] = tmp;
        return newTiles;
    }

    private boolean isvalid(int x, int y) {
        return !(x > size - 1 || x < 0 || y > size - 1 || y < 0);
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighTemp = new ArrayList<>();
        int[] offset = { -1, 1, 0, 0, 0, 0, -1, 1 };
        for (int i = 0; i < offset.length / 2; i++) {
            int[][] temp = swap(zeroIndexI, zeroIndexJ, zeroIndexI + offset[i],
                                zeroIndexJ + offset[i + 4]);
            if (temp.length != 0)
                neighTemp.add(new Board(
                        temp));
        }

        return neighTemp;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTwinAr = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newTwinAr[i][j] = tilseObej[i][j];
            }
        }
        int swapFlag = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j += 2) {
                if ((zeroIndexI + i >= 0 && zeroIndexI + i < size - 1) && (zeroIndexJ + j >= 0
                        && zeroIndexJ + j < size)) {
                    int temp = newTwinAr[zeroIndexI + i][zeroIndexJ + j];
                    newTwinAr[zeroIndexI + i][zeroIndexJ + j] = newTwinAr[zeroIndexI + i + 1][
                            zeroIndexJ + j];
                    newTwinAr[zeroIndexI + i + 1][zeroIndexJ + j] = temp;
                    swapFlag = 1;
                    break;
                }
            }
            if (swapFlag == 1) break;
        }
        return new Board(newTwinAr);

    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] t = { { 2, 0, 3, 4 }, { 1, 10, 6, 8 }, { 1, 10, 6, 8 }, { 1, 10, 6, 8 } };
        // int[][] t = { { 1, 2, 3 }, { 4, 5, 0 }, { 8, 7, 6 } };
        Board b = new Board(t);
        StdOut.println(b.toString());
        // StdOut.println(b.dimension());
        // StdOut.println(b);
        // StdOut.println(b.hamming());
        // StdOut.println(b.manhattan());
        // StdOut.println(b.isGoal());
        // StdOut.println((b.twin()).toString());
        // StdOut.println(b.equals(b.twin()));
        // for (Board bb : b.neighbors()) {
        //     StdOut.println(bb);
        // }

    }

}
