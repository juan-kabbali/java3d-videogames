package edu.uao.compu.planeta3D.utils;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
 
public class CubeUtils3D {

    public static Geometry crearVoxel(ColorRGBA color, AssetManager assetManagerRef) {
        Box b = new Box(0.5f, 0.5f, 0.5f);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManagerRef, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);

        return geom;
    }

    public static Geometry CrearVoxel(
        Vector3f posicion, ColorRGBA color, AssetManager assetManagerRef) {
        Geometry voxel = crearVoxel(color, assetManagerRef);
        voxel.setLocalTranslation(posicion);
       
        return voxel;
    }
}
