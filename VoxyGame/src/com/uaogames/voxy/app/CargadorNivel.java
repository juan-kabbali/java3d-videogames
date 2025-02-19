/*
 * UNIVERSIDAD AUTONOMA DE OCCIDENTE
 * 2015 - COMPUTACION GRAFICA II
 * VOXYGAME - TALLER DE PRACTICA
 */
package com.uaogames.voxy.app;

import com.jme3.asset.AssetManager;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Este es el cargador del nivel.
 * @author gisler
 */
public class CargadorNivel {
    /**
     * Carga un nivel agrega algunas luces
     * y le da la escala deseada.
     * @param assetManager  Referencia del assetManager necesaria para cargar el modelo.
     * @param rootNode      Referencia del assetManager para atachar el nivel.
     * @param level         Ruta del modelo
     * @param scale         Escala del nivel
     */
    public static void cargarNivel(AssetManager assetManager,Node rootNode, String level, float scale) {
        Spatial levelModel = assetManager.loadModel(level);
        
        cargarLuz(rootNode,Vector3f.ZERO,ColorRGBA.White);
        cargarLuz(rootNode,new Vector3f(0,-10.0f,0),ColorRGBA.Blue);
        cargarLuz(rootNode,new Vector3f(10.0f,0,0),ColorRGBA.Red);
        cargarLuz(rootNode,new Vector3f(10.0f,10.0f,0),ColorRGBA.Green);
        
        levelModel.setLocalScale(scale);
        rootNode.attachChild(levelModel);
        
    }
    
    /**
     * Carga una luz del color y la posicion dados.
     * @param rootNode  Referencia del rootNode
     * @param posicion  Posicion de la luz
     * @param color     Color de la luz.
     */
    private static void cargarLuz(Node rootNode,Vector3f posicion,ColorRGBA color){
        PointLight pointLight = new PointLight();
        pointLight.setColor(color);
        pointLight.setRadius(200.0f);
        pointLight.setPosition(posicion);
        rootNode.addLight(pointLight);
    }
}
