/**
 * A simple class representing a location in 2D space.
 */
public final class Point {
    public int x;
    public int y;

    public double f;
    public double g;
    public Point priorNode;

    public double getf()
    {
        return this.f;
    }

    public void setf(double f)
    {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public Point getPriorNode() {
        return priorNode;
    }

    public void setPriorNode(Point priorNode) {
        this.priorNode = priorNode;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }

    public int distanceSquared(Point p2) {
        int deltaX = this.x - p2.x;
        int deltaY = this.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

    public boolean adjacent(Point p2) {
        return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) || (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point) other).x == this.x && ((Point) other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }
}
