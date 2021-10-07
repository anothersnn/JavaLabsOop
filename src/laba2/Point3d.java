package laba2;

public class Point3d extends Point2d {
    double zCoord;
    public Point3d(double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    public Point3d() {
        this(0, 0,0);
    }
    public double getZ() { //Возвращение координаты z
        return zCoord;
    }
    public void setZ(double val) { //Установка значения координаты z
        zCoord = val;
    }
    public boolean equals(Point3d point) { //Сравнение двух объектов в Point3d
        //Возвращает true, если точки находятся в одном месте, false в ином случае
        if (this.getX() == point.getX()) {
            if (this.getY() == point.getY()) {
                if (this.getZ() == point.getZ())
                    return true;
            }
        }
        return false;
    }
    public double distanceTo(Point3d point) {
        //Возвращает расстояние между точками
        double d = Math.sqrt(Math.pow(this.getX() - point.getX(), 2) + Math.pow(this.getY() - point.getY(), 2) + Math.pow(this.getZ() - point.getZ(), 2));
        return Math.round(d * 100)/100.0;
    }
}
