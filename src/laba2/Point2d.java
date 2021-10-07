package laba2;

public class Point2d {  //Двумерный класс точки
    double xCoord;
    double yCoord;
    public Point2d (double x, double y) {   //Конструктор инициализации
        xCoord = x;
        yCoord = y;
    }
    public Point2d() {  //Конструктор по умолчанию
        this(0, 0);
    }
    public double getX() {  //Возвращение координаты х
        return xCoord;
    }
    public double getY() {  //Возвращение координаты у
        return yCoord;
    }
    public void setX(double val) {  //Установка значения координаты х
        xCoord = val;
    }
    public void setY(double val) {  //Установка значения координаты у
        yCoord = val;
    }
}
