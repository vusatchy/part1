import java.util.Arrays;

public class BruteCollinearPoints {


    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        LineSegment[] lineSegments = new LineSegment[2];
        int count = 0;
        for (int a = 0; a < points.length; a++) {
            for (int b = a + 1; b < points.length; b++) {
                for (int c = b + 1; c < points.length; c++) {
                    for (int d = c + 1; d < points.length; d++) {
                        double AB = points[a].slopeTo(points[b]);
                        double AD = points[a].slopeTo(points[d]);
                        double AC = points[a].slopeTo(points[c]);

                        double BC = points[b].slopeTo(points[c]);
                        double BD = points[b].slopeTo(points[d]);

                        double CD = points[c].slopeTo(points[d]);
                        if (arrayEqual(AB, BC, AC, BD, AD, CD)) {
                            LineSegment lineSegment = findNewLineSegment(points[a], points[b],
                                    points[c], points[d]);
                            if (count == lineSegments.length) {
                                lineSegments = resize(lineSegments, 2 * lineSegments.length + 1);
                            }
                            lineSegments[count++] = lineSegment;
                        }
                    }
                }
            }
        }
        this.lineSegments = postProcess(lineSegments);
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
            if (lineSegments != null && !lineSegment.equals(segment)) {
                return true;
            }
        }
        return false;
    }

    private LineSegment[] resize(LineSegment[] array, int newSize) {
        return Arrays.copyOfRange(array, 0, newSize);
    }

    private LineSegment findNewLineSegment(Point... points) {
        Point max = points[0];
        Point min = max;
        for (Point point : points) {
            if (point.compareTo(max) > 0) {
                max = point;
            } else if (point.compareTo(min) < 0) {
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
    }

    public LineSegment[] segments() {
        return lineSegments;
    }
}