package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    Node newPivot;
    Quaternion quat;
    Geometry geom;
    float velocidad = 0.1f;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        Box b = new Box(1, 1, 1);
        geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        
        
        Vector3f pivote = geom.getMesh().getBound().getCenter();
        newPivot = new Node();
        newPivot.setLocalTranslation(pivote);
        newPivot.attachChild(geom);

//Reverse the pivot to match the center of the mesh
        geom.setLocalTranslation(new Vector3f(0, -3f, 0));

//Apply the desire angle and axis rotation
        quat = new Quaternion();
        rootNode.attachChild(newPivot);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
        if (quat.getX()<90) {
            quat.fromAngleAxis(0.5f*FastMath.DEG_TO_RAD, Vector3f.UNIT_Z);
        }
        else{
            quat.fromAngleAxis(-0.5f*FastMath.DEG_TO_RAD, Vector3f.UNIT_Z);
        }
        newPivot.rotate(quat);
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
