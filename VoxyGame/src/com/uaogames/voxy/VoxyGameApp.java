/*
 * UNIVERSIDAD AUTONOMA DE OCCIDENTE
 * 2015 - COMPUTACION GRAFICA II
 * VOXYGAME - TALLER DE PRACTICA
 */
package com.uaogames.voxy;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.uaogames.voxy.app.CargadorNivel;
import com.uaogames.voxy.app.CajaMadera;
import java.util.ArrayList;

/**
 * Prueba en clase de las transformaciones basicas.
 * @author gisler
 */
public class VoxyGameApp extends SimpleApplication {
    private ArrayList<CajaMadera> cajas;

        
    public static void main(String[] args) {
        VoxyGameApp app = new VoxyGameApp();
        app.start();
    }
    
    private void crearTodasLasCajas(){
        for(int i=0; i< cajas.size(); i++){
            CajaMadera caja = cajas.get(i);
            caja.desplazarse(new Vector3f(0,0,0));
            rootNode.attachChild(caja.getGeom());
        }
    }
    
    private void updateAllCajas(){
        for(int i=0; i< cajas.size(); i++){
            cajas.get(i).update();
        }
    }

    @Override
    public void simpleInitApp() {
        //Creamos las cajas
        cajas = new ArrayList<CajaMadera>();
        cajas.add(new CajaMadera(assetManager,0,0.0f,-20.0f));
        cajas.add(new CajaMadera(assetManager,5,-20.0f,0));
        cajas.add(new CajaMadera(assetManager,10,-6.0f,6.0f));
        cajas.add(new CajaMadera(assetManager,15,-4.0f,3.0f));
        
        crearTodasLasCajas();
        
        //Cargamos un nivel de pruebas
        CargadorNivel.cargarNivel(assetManager, rootNode, "/Models/test_level.j3o",7.0f);
        
        //Aumentamos la velocidad de la camara por que el nivel es mas grande
        flyCam.setMoveSpeed(18.0f);
    }

    @Override
    public void simpleUpdate(float tpf) {
        updateAllCajas();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
