package co.InteractiveMusic.Utilities;
import java.awt.Point;

/*
 * Class creates by Juan Pablo Aguirre - 2015  multimedia engineer 
 * interpret colissions 
 */

public class Colisiones2d {
    
    public static boolean deteccionBotones(Point m, Point o, int ancho, int largo) {

        boolean vx = entre(o.getX(), o.getX() + largo, m.getX());
        boolean vy = entre(o.getY(), o.getY() + ancho, m.getY());
        return (vx && vy);

    }

     private static boolean entre(double v1, double v2, double v) {
        return v1 < v && v < v2;
    }

 
    private static boolean entre(int v1, int v2, int v) {
        return v1 < v && v < v2;
    }

 
    public static double distancia(Point a, Point b) {
        double d;
        double deltax = a.getX() - b.getX();
        double deltay = a.getY() - b.getY();
        d = Math.sqrt(deltax * deltax + deltay * deltay);
        return d;
    }

    
}
