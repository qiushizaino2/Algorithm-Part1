/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> data;

    public PointSET() {
        data = new SET<Point2D>();
    }                              // construct an empty set of points

    public boolean isEmpty() {
        return data.isEmpty();
    }             // is the set empty?

    public int size() {
        return data.size();
    }             // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p))
            data.add(p);
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return data.contains(p);
    }     // does the set contain point p?

    public void draw() {
        for (Point2D p : data) {
            p.draw();
        }
    }                     // draw all points to standard draw

    public Iterable<Point2D> range(
            RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> rangInPoints = new ArrayList<Point2D>();
        for (Point2D potential : data) {
            if (rect.contains(potential)) {
                rangInPoints.add(potential);
            }
        }
        return rangInPoints;
    }      // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(
            Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D min = null;
        double minDist = Double.POSITIVE_INFINITY;
        for (Point2D potential : data) {
            double tempDist = potential.distanceSquaredTo(p);
            if (Double.compare(tempDist, minDist) < 0) {
                min = potential;
                minDist = tempDist;
            }

        }
        return min;
    }    // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(
            String[] args) {
        // this is a comment.
    }            // unit testing of the methods (optional)
}
