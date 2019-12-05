import java.util.Objects;

public class Point {
    private final int x;
    private final int y;
    private final int steps;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.steps = 0;
    }

    public Point(Point p,int x, int y) {
        this.x = p.x + x;
        this.y = p.y + y;
        this.steps = p.steps + 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
