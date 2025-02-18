
package edu.uao.compu.planeta3D.domain;


    public abstract class Dismensiones {

    protected String nombre;
    protected int[][] mapa;
    public int ancho = 30; 
    public int alto = 30;

    public abstract void inicializarPlaneta();
    
    public int[][] obtenerMatrizDelPlaneta() {
        return mapa;
    }

    public String obtenerNombre() {
        return nombre;
    }
}
    

