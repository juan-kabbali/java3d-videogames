/*
 * UNIVERSIDAD AUTONOMA DE OCCIDENTE
 * 2015 - COMPUTACION GRAFICA II
 * VOXYGAME - TALLER DE PRACTICA
 */
package com.uaogames.voxy.app;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 * WoodBox es una caja de madera que oscila
 * desde una posicion inicial hasta una final,
 * se le puede dar una velocidad.
 * @author gisler
 */
public class CajaMadera {
    private float posicionInicial = 0.0f;
    private float posicionActualCaja = 0.0f;
    private float posicionFinal = 0;
    private float velocidadCaja = 0.5f;
    private float posInicialX = 0.0f;
    private Geometry geom;
    
    /**
     * Este es el constructor de WoodBox,
     * permite crear la geometria y crear un material
     * con una textura de madera.
     * @param assetManager  Referencia del assetManager
     */
    public CajaMadera(AssetManager assetManager, int posInicialX, float posicionInicial,float posicionFinal) {
        Box b = new Box(1, 1, 1);
        geom = new Geometry("Box", b);
        this.posInicialX = posInicialX;
        this.posicionInicial = posicionInicial;
        this.posicionFinal = posicionFinal;
        this.posicionActualCaja = posicionInicial;

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = assetManager.loadTexture("Textures/madera.jpg");
        mat.setTexture("ColorMap", textura);
        geom.setMaterial(mat);
    }

    /**
     * Retorna la geometria de WoodBox,
     * esta geometria se atachara luego al rootnode.
     * @return 
     */
    public Spatial getGeom() {
        return geom;
    }
    
    /**
     * Desplaza la caja a la posicion deseada.
     * @param nuevaPosicion Vector con la posicion a la que se desplazara la caja.
     */
    public void desplazarse(Vector3f nuevaPosicion){
        geom.setLocalTranslation(nuevaPosicion);
    }

    /**
     * Actualiza la posicion de la caja por cada frame,
     * cuando alcanza el limite este cambia la direccion
     * multiplicando la velocidad por -1, esto permite
     * un movimiento oscilatorio.
     */
    public void update() {
        posicionActualCaja = posicionActualCaja + velocidadCaja;
        if(posicionActualCaja>=posicionFinal) {
            velocidadCaja = velocidadCaja * -1;
        } else {
            if(posicionActualCaja<=posicionInicial) {
               velocidadCaja = velocidadCaja * -1; 
            }
        }
        desplazarse(new Vector3f(this.posInicialX,-5.0f,posicionActualCaja));
    }
    
}
