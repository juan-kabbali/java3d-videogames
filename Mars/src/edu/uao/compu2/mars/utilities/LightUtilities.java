package edu.uao.compu2.mars.utilities;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/*
 * @author Juan pablo aguirre
 */
public class LightUtilities {

    public static void luzAmbiental(Node rootNode) {
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(al);
    }

    public static void luzDireccional(Node rootNode, Vector3f direccion) {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(direccion.normalizeLocal());
        rootNode.addLight(sun);
    }

}
