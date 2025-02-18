/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uao.game.cargar;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author juan_pab.aguirre
 */
public class CargadorNivel {
    public static void cargarNivel(AssetManager assetManager,Node rootNode, String ruta ){
    Spatial level = assetManager.loadModel(ruta);
    rootNode.attachChild(level);
                
                    }
    
    public static void cargarLuz(Node rootNode, Vector3f posicion, ColorRGBA color,float radius ){
    PointLight pointLight = new PointLight();
    pointLight.setPosition(posicion);
    pointLight.setColor(color);
    pointLight.setRadius(radius);
    
    rootNode.addLight(pointLight);
   
    }
    
     
    
    public static void cargarLuzAmbiente(Node rootNode, ColorRGBA color){
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(color);
        rootNode.addLight(ambient);
        
        
        
    
    }
    
}
