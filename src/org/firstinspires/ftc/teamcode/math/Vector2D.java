package org.firstinspires.ftc.teamcode.math;

public class Vector2D implements Comparable {

    public double x = 0;
    public double y = 0;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D rotated(double angle) {
        double sina = Math.sin(angle);
        double cosa = MathUtil.cosFromSin(sina, angle);
        double newX = x * cosa - y * sina;
        double newY = x * sina + y * cosa;
        return new Vector2D(newX, newY);
    }

    public Vector2D rotatedCW(double angle) {
        double sina = Math.sin(angle);
        double cosa = MathUtil.cosFromSin(sina, angle);
        double newX = x * cosa + y * sina;
        double newY = -x * sina + y * cosa;
        return new Vector2D(newX, newY);
    }

    public Vector2D normalize(){
        double r = radius();
        if(radius()!=0) {
            return new Vector2D(x /= radius(), y /= radius());
        }
        return this;
    }

    public Vector2D multiply(double d) {return new Vector2D(this.x*d, this.y*d); }

    public Vector2D add(Vector2D p) {
        return new Vector2D(this.x + p.x, this.y + p.y);
    }

    public Vector2D minus(Vector2D p) {
        return new Vector2D(this.x - p.x, this.y - p.y);
    }

    public double atan() {
        return Math.atan2(y, x);
    }

    public double acot() {
        return MathUtil.angleWrap(Math.PI / 2 - Math.atan2(y, x));
    }

    public double radius() {
        return Math.sqrt(x * x + y * y);
    }

    public double distance(Vector2D p) {
        return minus(p).radius();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        boolean toReturn = (MathUtil.approxEquals(vector2D.x, x) &&
                MathUtil.approxEquals(vector2D.y, y));
                System.out.println("WILL RETURN " + toReturn);
        return toReturn;

    }

    @Override
    public int hashCode() {
        return Double.valueOf(x).hashCode() ^ Double.valueOf(y).hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)", x, y);
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return -1;
        Vector2D p = (Vector2D) o;
        return Integer.compare(this.hashCode(), o.hashCode());
    }

}