package laba6;

import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    //Установка начальных условий
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }

    // Вернуть количество итераций для конкретной точки
    public int numIterations(double x, double y) {
        int iteration = 0;
        double zR = 0;
        double zIm = 0;
        while (zR * zR + zIm * zIm <= 4 && iteration < MAX_ITERATIONS) {
            double zRUpdated = zR * zR - zIm * zIm + x;
            double zImUpdated = 2 * Math.abs(zR) * Math.abs(zIm) + y;
            zR = zRUpdated;
            zIm = zImUpdated;
            iteration++;
        }

        if (iteration == MAX_ITERATIONS) return -1;
        return iteration;
    }

    public String toString() {
        return "BurningShip";
    }
}

