public class Vector2i {
    public static final Vector2i EAST = of(1, 0);
    public static final Vector2i SOUTHEAST = of(1, 1);
    public static final Vector2i SOUTH = of(0, 1);
    public static final Vector2i WEST = of(-1, 0);
    public static final Vector2i NORTHWEST = of(-1, -1);
    public static final Vector2i NORTH = of(0, -1);
    public static final Vector2i NORTHEAST = of(1, -1);
    public static final Vector2i SOUTHWEST = of(-1, 1);

    private int x;
    private int y;

    public Vector2i() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2i of(int x, int y) {
        return new Vector2i(x, y);
    }

    public boolean isEmpty() {
        return (x == 0 && y == 0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}