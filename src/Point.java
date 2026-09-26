/**
 * An immutable point in the 2D integer plane.
 *
 * Deliberately does NOT override equals()/hashCode()!
 * Every Point used by the closest-pair algorithms is a
 * distinct object, and the divide-and-conquer implementation
 * relies on telling two points with identical coordinates apart
 * by object identity, not by value.
 * Default Object identity semantics are exactly what's needed here.
 */
public final class Point {

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Squared Euclidean distance.
     *  Avoids a sqrt() call.
     *  long arithmetic avoids int overflow.
     */
    public long squaredDistanceTo(Point other) {
        long dx = (long) x - other.x;
        long dy = (long) y - other.y;
        return dx * dx + dy * dy;
    }

    public double distanceTo(Point other) {
        return Math.sqrt(squaredDistanceTo(other));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
