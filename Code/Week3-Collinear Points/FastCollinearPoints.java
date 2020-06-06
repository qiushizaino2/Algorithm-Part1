/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private final int size;
    private final LineSegment[] segmentsRes;

    public FastCollinearPoints(
            Point[] points) {
        // exception
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
        Point[] pPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pPoints[i] = points[i];
        }

        Arrays.sort(pPoints);
        int indexSeg = 0;
        Point[] segMin = new Point[1];
        Point[] segMax = new Point[1];

        for (int i = 0; i < pPoints.length; i++) {

            Point origin = pPoints[i];
            Point[] temp = new Point[pPoints.length - 1];
            int tempIndex = 0;
            for (int j = 0; j < pPoints.length; j++) {
                if (j == i) continue;
                temp[tempIndex++] = pPoints[j];
            }


            Arrays.sort(temp, origin.slopeOrder());
            int lo = 0;
            int hi = 0;
            int coliNum = 1;
            int skipindex = -1;

            for (int j = 1; j < temp.length; j++) {
                if (j <= skipindex) continue;
                if (Double.compare(origin.slopeTo(temp[j]), origin.slopeTo(temp[lo])) == 0)
                    coliNum++;

                if (coliNum < 3
                        && Double.compare(origin.slopeTo(temp[j]), origin.slopeTo(temp[lo])) != 0) {
                    lo = j;
                    coliNum = 1;
                }

                if (coliNum == 3) {
                    hi = j;

                    for (int z = j + 1; z < temp.length; z++) {
                        if (Double.compare(origin.slopeTo(temp[z]), origin.slopeTo(temp[lo]))
                                == 0) {
                            hi = z;
                            skipindex = hi;
                        }
                        else {
                            break;
                        }
                    }

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


                    if (origin.compareTo(temp[lo]) < 0) {
                        segMin[indexSeg] = origin;
                        segMax[indexSeg] = temp[hi];
                        indexSeg++;
                    }

                    lo = hi + 1;
                    coliNum = 0;
                }
            }
        }
        size = indexSeg;
        segmentsRes = new LineSegment[size];
        for (int i = 0; i < size; i++) {
            segmentsRes[i] = new LineSegment(segMin[i], segMax[i]);
        }
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return size;
    }        // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[size];
        for (int i = 0; i < size; i++)
            ret[i] = segmentsRes[i];
        return ret;
    }                // the line segments

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

//
// public class FastCollinearPoints {
//     private final int size;
//     private final LineSegment[] segmentsRes;
//
//     public FastCollinearPoints(
//             Point[] points) {
//         //exception
//         if (points == null) throw new IllegalArgumentException();
//         else {
//
//             for (int i = 0; i < points.length; i++) {
//                 if (points[i] == null) throw new IllegalArgumentException();
//
//                 if (i < points.length - 1)
//                     for (int j = i + 1; j < points.length; j++) {
//                         if (points[j] == null) throw new IllegalArgumentException();
//                         if (points[i].compareTo(points[j]) == 0)
//                             throw new IllegalArgumentException();
//                     }
//             }
//         }
//
//         int indexSeg = 0;
//         Point[] segMin = new Point[1];
//         Point[] segMax = new Point[1];
//
//         for (int i = 0; i < points.length; i++) {
//             Point origin = points[i];
//             Point[] temp = new Point[points.length - 1];
//             int tempIndex = 0;
//
//             //sort by Slope
//             for (int j = 0; j < points.length; j++) {
//                 if (j == i) continue;
//                 temp[tempIndex] = points[j];
//                 tempIndex++;
//             }
//             Arrays.sort(temp, origin.slopeOrder());
//
//             for (int j = 0; j < temp.length - 2; j++) {
//                 // if (temp[j].compareTo(temp[j + 3]) == 0) {
//                 if (Double.compare(origin.slopeTo(temp[j]), origin.slopeTo(temp[j + 2])) == 0) {
//                     int latterIndex = j + 2;
//                     for (int k = j + 3; k < temp.length; k++) {
//                         if (Double.compare(origin.slopeTo(temp[j]), origin.slopeTo(temp[k])) != 0) {
//                             break;
//                         }
//                         latterIndex = k;
//                     }
//                     Point max = origin;
//                     Point min = origin;
//                     for (int k = j; k <= latterIndex; k++) {
//                         if (max.compareTo(temp[k]) < 0) max = temp[k];
//                         if (min.compareTo(temp[k]) > 0) min = temp[k];
//                     }
//
//
//                     if (indexSeg == segMin.length) {
//                         Point[] segMintemp = new Point[segMin.length * 2];
//                         Point[] segMaxtemp = new Point[segMin.length * 2];
//                         for (int q = 0; q < indexSeg; q++) {
//                             segMintemp[q] = segMin[q];
//                             segMaxtemp[q] = segMax[q];
//                         }
//                         segMin = segMintemp;
//                         segMax = segMaxtemp;
//                     }
//
//
//                     int duplicateFlag = 0;
//                     if (indexSeg != 0)
//                         for (int z = 0; z < indexSeg; z++)
//                             if (segMin[z].compareTo(min) == 0 && segMax[z].compareTo(max) == 0) {
//                                 duplicateFlag = 1;
//                                 break;
//                             }
//
//                     if (duplicateFlag != 1) {
//                         segMin[indexSeg] = min;
//                         segMax[indexSeg] = max;
//                         indexSeg++;
//                     }
//
//                     j = latterIndex;
//                 }
//             }
//         }
//
//         size = indexSeg;
//         segmentsRes = new LineSegment[size];
//         for (int i = 0; i < size; i++) {
//             segmentsRes[i] = new LineSegment(segMin[i], segMax[i]);
//         }
//     }    // finds all line segments containing 4 or more points
//
//     public int numberOfSegments() {
//         return size;
//     }        // the number of line segments
//
//     public LineSegment[] segments() {
//         LineSegment[] ret = new LineSegment[size];
//         for (int i = 0; i < size; i++)
//             ret[i] = segmentsRes[i];
//         return ret;
//     }                // the line segments
//
//     public static void main(String[] args) {
//
//         // read the n points from a file
//         In in = new In(args[0]);
//         int n = in.readInt();
//         Point[] points = new Point[n];
//         for (int i = 0; i < n; i++) {
//             int x = in.readInt();
//             int y = in.readInt();
//             points[i] = new Point(x, y);
//         }
//
//         // draw the points
//         StdDraw.enableDoubleBuffering();
//         StdDraw.setXscale(0, 32768);
//         StdDraw.setYscale(0, 32768);
//         for (Point p : points) {
//             p.draw();
//         }
//         StdDraw.show();
//
//         // print and draw the line segments
//         FastCollinearPoints collinear = new FastCollinearPoints(points);
//         StdOut.print(collinear.segments().length);
//         for (LineSegment segment : collinear.segments()) {
//             StdOut.println(segment);
//             segment.draw();
//         }
//         StdDraw.show();
//     }
// }
