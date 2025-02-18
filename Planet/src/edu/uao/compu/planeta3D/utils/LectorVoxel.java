package edu.uao.compu.planeta3D.utils;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import edu.uao.compu.planeta3D.domain.Dismensiones;
import edu.uao.compu.planeta3D.planetas.Laberinto;

public class LectorVoxel {

    private static AssetManager assetManager;

    public static Node unPlaneta(Vector3f posicion, ColorRGBA color, AssetManager assetManager) {
        Dismensiones planeta = new Laberinto();
        Node elPlaneta = new Node("planeta");
        planeta.inicializarPlaneta();
        Geometry elementoDelPlaneta;
        Geometry elementoDelPlaneta2;
        Geometry elementoDelPlaneta3;


        for (int[] filas : planeta.obtenerMatrizDelPlaneta()) {

            for (int j = 0; j < filas.length; j++) {
                int[][] mapa = planeta.obtenerMatrizDelPlaneta();
                for (int i = 0; i < mapa.length; i++) {

                    if(mapa[i][j] == 1) {
                            Geometry elementoDelPlaneta1 = CubeUtils3D.CrearVoxel(
                                    posicion.add(new Vector3f(i, j, -50)), ColorRGBA.Yellow, assetManager);
                            elPlaneta.attachChild(elementoDelPlaneta1)
                                    ;
                    }
                      if(mapa[i][j] == 2) {
                            elementoDelPlaneta2 = CubeUtils3D.CrearVoxel(
                                    posicion.add(new Vector3f(i, j, -50)), ColorRGBA.Red, assetManager);
                            elPlaneta.attachChild(elementoDelPlaneta2);
                      }
                      if(mapa[i][j] == 3) {
                            elementoDelPlaneta3 = CubeUtils3D.CrearVoxel(
                                    posicion.add(new Vector3f(i, j, -50)), ColorRGBA.Blue, assetManager);
                            elPlaneta.attachChild(elementoDelPlaneta3);
                      }
                    }


               }
        }
        return elPlaneta;
    }
}
