package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.uao.game.cargar.CargadorNivel;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        flyCam.setMoveSpeed(5.0f);
//        Box b = new Box(1, 1, 1);
//        Geometry geom = new Geometry("Box", b);
//
//        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", ColorRGBA.Blue);
//        geom.setMaterial(mat);
//
//        rootNode.attachChild(geom);
        CargadorNivel.cargarNivel(assetManager, rootNode, "/Models/Laberinto/untitled.j3o");
        CargadorNivel.cargarLuz(rootNode, new Vector3f(0,0.5f,0), ColorRGBA.Red.mult(3), 5000.0f);
        CargadorNivel.cargarLuzAmbiente(rootNode, ColorRGBA.White.mult(2.0f));
    }

    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
