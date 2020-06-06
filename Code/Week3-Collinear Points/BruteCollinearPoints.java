/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final int size;
    private final LineSegment[] segmentsRes;


    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        else {

            for (int i = 0; i < points.length; i++) {
                if (points[i] == null) throw new IllegalArgumentException();

                if (i < points.length - 1)
                    for (int j = i + 1; j < points.length; j++) {
                        if (points[j] == null) throw new IllegalArgumentException();
                        if (points[i].compareTo(points[j]) == 0)
                            throw new IllegalArgumentException();
                    }
            }

        }


        Point[] segMin = new Point[1];
        Point[] segMax = new Point[1];
        int indexSeg = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double slopeRef = points[i].slopeTo(points[j]);
                int coLiPo = 2;

                Point max;
                Point min;
                if (points[i].compareTo(points[j]) > 0) {
                    max = points[i];
                    min = points[j];
                }
                else {
                    max = points[j];
                    min = points[i];
                }


                for (int k = 0; k < points.length; k++) {
                    if (k == i || k == j) continue;
                    double s = points[i].slopeTo(points[k]);
                    if (Double.compare(s, slopeRef) == 0) {
                        coLiPo++;
                        if (points[k].compareTo(max) > 0) {
                            max = points[k];
                        }
                        else if (points[k].compareTo(min) < 0) {
                            min = points[k];
                        }


                    }
                }

                if (coLiPo >= 4) {

                    if (indexSeg == segMin.length) {
                        Point[] segMintemp = new Point[segMin.length * 2];
                        Point[] segMaxtemp = new Point[segMin.length * 2];
                        for (int q = 0; q < indexSeg; q++) {
                            segMintemp[q] = segMin[q];
                            segMaxtemp[q] = segMax[q];
                        }
                        segMin = segMintemp;
                        segMax = segMaxtemp;
                    }

                    int duplicateFlag = 0;
                    for (int z = 0; z < indexSeg; z++)
                        if (segMin[z].compareTo(min) == 0 && segMax[z].compareTo(max) == 0) {
                            duplicateFlag = 1;
                            break;
                        }

                    if (duplicateFlag != 1) {
                        segMin[indexSeg] = min;
                        segMax[indexSeg] = max;
                        indexSeg++;
                    }
                }


            }
        }

        size = indexSeg;
        segmentsRes = new LineSegment[size];
        for (int i = 0; i < size; i++) {
            segmentsRes[i] = new LineSegment(segMin[i], segMax[i]);
        }
    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return size;
    }       // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[size];
        for (int i = 0; i < size; i++)
            ret[i] = segmentsRes[i];
        return ret;
    }               // the line segments


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.print(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
