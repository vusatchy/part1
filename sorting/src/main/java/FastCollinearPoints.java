import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;


    public FastCollinearPoints(Point[] points) {
        Point[] copy;
        lineSegments = new LineSegment[0];
        int count = 0;
        for (int i = 0; i < points.length; i++) {
            Point p0 = points[0];
            swap(points, 0, i);
            Comparator<Point> pointComparator = p0.slopeOrder();
            copy = Arrays.copyOf(points, points.length);
            Arrays.sort(copy, 1, points.length, pointComparator);
            for (int j = 1; j < copy.length - 2; j++) {
                Point p1 = copy[j];
                Point p2 = copy[j + 1];
                Point p3 = copy[j + 2];
                if (arrayEqual(p0.slopeTo(p1), p0.slopeTo(p2), p0.slopeTo(p3))) {
                    LineSegment lineSegment = findNewLineSegment(p0, p1, p2, p3);
                    if (count == lineSegments.length) {
                        lineSegments = resize(lineSegments, 2 * lineSegments.length + 1);
                    }
                    lineSegments[count++] = lineSegment;
                }
            }
        }
        this.lineSegments = postProcess(lineSegments);
    }   // finds all line segments containing 4 or more points

    private LineSegment[] resize(LineSegment[] array, int newSize) {
        return Arrays.copyOfRange(array, 0, newSize);
    }

    private LineSegment[] postProcess(LineSegment[] lineSegments) {
        LineSegment[] unique = new LineSegment[lineSegments.length];
        int counter = 0;
        for (int i = 0; i < lineSegments.length; i++) {
            LineSegment lineSegment = lineSegments[i];
            if (lineSegment != null && !contains(unique, lineSegment)) {
                unique[counter++] = lineSegment;
            }
        }
        int countNotNull = countNotNull(unique);
        LineSegment[] trimed = new LineSegment[countNotNull];
        counter = 0;
        for (LineSegment lineSegment : unique) {
            if (lineSegment != null) {
                trimed[counter++] = lineSegment;
            }
        }
        return trimed;
    }

    private int countNotNull(LineSegment[] unique) {
        int counter = 0;
        for (LineSegment lineSegment : unique) {
            if (lineSegment != null) {
                counter++;
            }
        }
        return counter;
    }

    private boolean contains(LineSegment[] segments, LineSegment segment) {
        for (LineSegment lineSegment : segments) {
            if (lineSegment != null && lineSegment.toString().equals(segment.toString())) {
                return true;
            }
        }
        return false;
    }


    private LineSegment findNewLineSegment(Point... points) {
        Point max = points[0];
        Point min = max;
        for (Point point : points) {
            if (point.compareTo(max) >= 0) {
                max = point;
            } else if (point.compareTo(min) <= 0) {
                min = point;
            }
        }
        return new LineSegment(min, max);
    }

    private static boolean arrayEqual(double... vars) {
        double prev = vars[0];
        for (int i = 1; i < vars.length; i++) {
            if (prev != vars[i]) {
                return false;
            }
        }
        return true;
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }  // the number of line segments

    public LineSegment[] segments() {
        return lineSegments;
    }             // the line segments


    private static final <T> void swap(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}