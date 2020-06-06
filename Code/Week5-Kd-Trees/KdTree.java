/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private Node<Point2D> root = null;
    private int size = 0;
    private double tempMinDistance = Double.POSITIVE_INFINITY;

    private class Node<Point2D> {
        private final Point2D point;
        private final RectHV rectHV;
        private Node<Point2D> pointLeft;
        private Node<Point2D> pointRight;
        private final boolean flag; // 0-y, 1-x

        public Node(Point2D p, Node<Point2D> lft, Node<Point2D> rgt, RectHV rect, boolean axis) {
            point = p;
            rectHV = rect;
            pointLeft = lft;
            pointRight = rgt;
            flag = axis;
        }

        public boolean dimension() {
            return flag;
        }

        public Node<Point2D> leftChid() {
            return pointLeft;
        }

        public Node<Point2D> rightChid() {
            return pointRight;
        }

        public void assignLeftChid(Node<Point2D> that) {
            pointLeft = that;
        }

        public void assignRightChid(Node<Point2D> that) {
            pointRight = that;
        }

        public Point2D getPoint() {
            return point;
        }

        public RectHV getRect() {
            return rectHV;
        }

    }

    public KdTree() {
    }

    public boolean isEmpty() {
        return size == 0;
    }             // is the set empty?

    public int size() {
        return size;
    }             // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (size == 0) {
            root = new Node<Point2D>(p, null, null, new RectHV(0, 0, 1, 1), false);
            size++;
            return;
        }
        if (contains(p)) return;
        Node<Point2D> pointer = root;
        while (pointer != null) {
            Point2D temp = pointer.getPoint();
            RectHV tempRect = pointer.getRect();
            Node<Point2D> next = null;

            int lr = -1;
            if (!pointer.dimension()) {
                if (p.x() <= temp.x()) {
                    next = pointer.leftChid();
                }
                else {
                    next = pointer.rightChid();
                    lr = 1;
                }
            }
            else {
                if (p.y() <= temp.y()) {
                    next = pointer.leftChid();
                }
                else {
                    next = pointer.rightChid();
                    lr = 1;
                }
            }

            if (next == null) {
                double xMin = tempRect.xmin();
                double xMax = tempRect.xmax();
                double yMin = tempRect.ymin();
                double yMax = tempRect.ymax();
                RectHV insertRect;
                if (lr == -1) {
                    if (!pointer.dimension()) {
                        insertRect = new RectHV(xMin, yMin, temp.x(), yMax);
                    }
                    else {
                        insertRect = new RectHV(xMin, yMin, xMax, temp.y());
                    }
                    pointer.assignLeftChid(
                            new Node<Point2D>(p, null, null, insertRect,
                                              !pointer.dimension()));
                }
                else {
                    if (!pointer.dimension()) {
                        insertRect = new RectHV(temp.x(), yMin, xMax, yMax);
                    }
                    else {
                        insertRect = new RectHV(xMin, temp.y(), xMax, yMax);
                    }
                    pointer.assignRightChid(
                            new Node<Point2D>(p, null, null, insertRect, !pointer.dimension()));
                }
                size++;
                break;
            }
            pointer = next;
        }
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (size == 0) return false;

        Node<Point2D> pointer = root;
        while (pointer != null) {
            Point2D temp = pointer.getPoint();

            if (temp.equals(p)) return true;

            if (!pointer.dimension()) {
                if (p.x() <= temp.x()) {
                    pointer = pointer.leftChid();
                }
                else {
                    pointer = pointer.rightChid();
                }
            }
            else {
                if (p.y() <= temp.y()) {
                    pointer = pointer.leftChid();
                }
                else {
                    pointer = pointer.rightChid();
                }
            }
        }

        return false;
    }     // does the kdtrees contain point p?

    public void draw() {
        drawRunning(root);
    }                     // draw all points to standard draw


    private void drawRunning(Node<Point2D> np) {
        if (np == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        np.getPoint().draw();
        StdDraw.setPenRadius();
        if (!np.dimension()) {
            StdDraw.setPenColor(StdDraw.RED);
            Point2D lo = new Point2D(np.getPoint().x(),
                                     np.getRect().ymin());
            Point2D hi = new Point2D(np.getPoint().x(),
                                     np.getRect().ymax());
            lo.drawTo(hi);

        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D lo = new Point2D(np.getRect().xmin(),
                                     np.getPoint().y());
            Point2D hi = new Point2D(np.getRect().xmax(),
                                     np.getPoint().y());
            lo.drawTo(hi);
        }

        drawRunning(np.leftChid());
        drawRunning(np.rightChid());
    }

    public Iterable<Point2D> range(
            RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        ArrayList<Point2D> rangInPoints = new ArrayList<Point2D>();
        rangeRunning(root, rect, rangInPoints);

        return rangInPoints;
    }      // all points that are inside the rectangle (or on the boundary)

    private void rangeRunning(Node<Point2D> np, RectHV rect, ArrayList<Point2D> arrayRange) {
        if (np == null) return;

        Point2D temp = np.getPoint();

        if (rect.contains(temp)) arrayRange.add(temp);

        if (np.leftChid() != null && np.leftChid().getRect().intersects(rect))
            rangeRunning(np.leftChid(), rect, arrayRange);
        if (np.rightChid() != null && np.rightChid().getRect().intersects(rect))
            rangeRunning(np.rightChid(), rect, arrayRange);
    }


    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        tempMinDistance = Double.POSITIVE_INFINITY;
        return nearestRunning(root, p);
    }    // a nearest neighbor in the set to point p; null if the set is empty

    private Point2D nearestRunning(Node<Point2D> np, Point2D p) {
        Point2D lftP = null;
        Point2D rgtP = null;
        Point2D temp = np.getPoint();
        double distance = temp.distanceSquaredTo(p);
        if (tempMinDistance > distance)
            tempMinDistance = distance;

        double distanceLmin = Double.POSITIVE_INFINITY;
        double distanceRmin = Double.POSITIVE_INFINITY;

        double rectDisL = Double.POSITIVE_INFINITY;
        double rectDisR = Double.POSITIVE_INFINITY;
        if (np.leftChid() != null)
            rectDisL = np.leftChid().getRect().distanceSquaredTo(p);
        if (np.rightChid() != null)
            rectDisR = np.rightChid().getRect().distanceSquaredTo(p);

        if (rectDisL <= rectDisR) {
            if (rectDisL < tempMinDistance) {
                lftP = nearestRunning(np.leftChid(), p);
                distanceLmin = lftP.distanceSquaredTo(p);
                if (tempMinDistance > distanceLmin)
                    tempMinDistance = distanceLmin;
            }
            if (rectDisR < tempMinDistance) {
                rgtP = nearestRunning(np.rightChid(), p);
                distanceRmin = rgtP.distanceSquaredTo(p);
                if (tempMinDistance > distanceRmin)
                    tempMinDistance = distanceRmin;
            }

        }
        else {
            if (rectDisR < tempMinDistance) {
                rgtP = nearestRunning(np.rightChid(), p);
                distanceRmin = rgtP.distanceSquaredTo(p);
                if (tempMinDistance > distanceRmin)
                    tempMinDistance = distanceRmin;
            }
            if (rectDisL < tempMinDistance) {
                lftP = nearestRunning(np.leftChid(), p);
                distanceLmin = lftP.distanceSquaredTo(p);
                if (tempMinDistance > distanceLmin)
                    tempMinDistance = distanceLmin;
            }
        }

        Point2D nearest;
        if (distanceLmin <= distanceRmin && distanceLmin <= distance) nearest = lftP;
        else if (distanceRmin <= distanceLmin && distanceRmin <= distance) nearest = rgtP;
        else nearest = temp;
        return nearest;
    }

    public static void main(
            String[] args) {
        // this is a comment.
    }            // unit testing of the methods (optional)
}
