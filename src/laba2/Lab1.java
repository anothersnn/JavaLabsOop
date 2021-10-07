package laba2;

import java.util.Scanner;

public class Lab1 {
    public static void main(String[] args) {
        Point3d a = createPoint();
        Point3d b = createPoint();
        Point3d c = createPoint();
        if (a.equals(b) || a.equals(c) || b.equals(c)) {
            System.out.println("Значения некоторых точек совпадают, поэтому невозможно вычислить площадь треугольника");
            return;
        }
        System.out.println("Площадь треугольника равна: ");
        System.out.println(computeArea(a, b, c));
    }
    public static Point3d createPoint() {
        //Создаёт экземпляр класс Point3d
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите координаты точки: ");
        String input = scanner.nextLine();
        String[] xyz = input.split("\\s");
        double x = Double.parseDouble(xyz[0]);
        double y = Double.parseDouble(xyz[1]);
        double z = Double.parseDouble(xyz[2]);
        return new Point3d(x, y, z);
    }
    public static double computeArea(Point3d a, Point3d b, Point3d c) {
        double AB = a.distanceTo(b);
        double AC = a.distanceTo(c);
        double BC = b.distanceTo(c);
        double p = (AB+AC+BC)/2;
        return Math.sqrt(p*(p-AB)*(p-AC)*(p-BC));
    }
}