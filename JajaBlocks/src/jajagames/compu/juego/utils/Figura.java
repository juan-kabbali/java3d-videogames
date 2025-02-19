package jajagames.compu.juego.utils;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import jajagames.compu.juego.domain.Pared;
import jajagames.compu.juego.muros.Muro_1Figura;
import jajagames.compu.juego.muros.Muro_2Figura;

public class Figura {

    private static AssetManager assetManager;

    public static Node unaPared(Vector3f posicion, ColorRGBA color, AssetManager assetManager) {
        Pared pared = new Muro_1Figura();
        Node laPared = new Node("pared");
        pared.inicializarPared();
        Geometry ladrillos;
        Geometry espacioFigura;


        for (int[] filas : pared.obtenerMatrizDeLaPared()) {

            for (int j = 0; j < filas.length; j++) {
                int[][] mapa = pared.obtenerMatrizDeLaPared();
                for (int i = 0; i < mapa.length; i++) {

                    if(mapa[i][j] == 1) {
                            ladrillos = CubeUtils3D.CrearVoxel(
                                    posicion.add(new Vector3f(i, -j, 0)), ColorRGBA.White, assetManager);
                            laPared.attachChild(ladrillos);
                    }
                      if(mapa[i][j] == 2) {
                            espacioFigura = CubeUtils3D.CrearVoxel(
                                    posicion.add(new Vector3f(i, -j, 0)), new ColorRGBA(0, 1, 0, 0.3f), assetManager);
                           // espacioFigura.setCullHint(CullHint.Never); 
                            laPared.attachChild(espacioFigura);
//                          Box cube2Mesh = new Box( 0.5f,0.5f,0.5f);
//    Geometry cube2Geo = new Geometry("window frame", cube2Mesh);
//    Material cube2Mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//    cube2Mat.setTexture("ColorMap", assetManager.loadTexture("Textures/ColoredTex/Monkey.png"));
//    cube2Mat.setTexture("ColorMap", assetManager.loadTexture("Textures/empty.png"));
//    cube2Mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);  // activate transparency
//    cube2Geo.setLocalTranslation(new Vector3f(i, -j, 0));
//    cube2Geo.setQueueBucket(RenderQueue.Bucket.Transparent);
//    cube2Geo.setMaterial(cube2Mat);
//    laPared.attachChild(cube2Geo);
                      }
                      
                    }


               }
        }
        return laPared;
    }
}
