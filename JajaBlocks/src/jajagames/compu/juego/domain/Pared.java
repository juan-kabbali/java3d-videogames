
package jajagames.compu.juego.domain;


    public abstract class Pared {

    protected String nombre;
    protected int[][] muro;
    public int ancho = 10; 
    public int alto = 13;

    public abstract void inicializarPared();
    
    public int[][] obtenerMatrizDeLaPared() {
        return muro;
    }

    public String obtenerNombre() {
        return nombre;
    }
}
    

