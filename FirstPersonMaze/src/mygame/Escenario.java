package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Escenario {
    
 
  private static Geometry sceneModel; /*modelo del escenario*/
  private static RigidBodyControl landscape /*para cuerpos rigidos, si la masa es cero, es inamovible*/;
  
              /*agregamos el escenario de un zip, y lo escalamos*/
    public static void setUpScene(AssetManager assetManager, Node rootNode, BulletAppState bulletAppState) {
        
    sceneModel = (Geometry) assetManager.loadModel("Scenes/test_level.j3o");
   
    CollisionShape sceneShape = CollisionShapeFactory.createMeshShape(sceneModel);
    landscape = new RigidBodyControl(sceneShape, 0); /*masa 0 = objeto inamovible*/
    sceneModel.addControl(landscape); 
    
       
    rootNode.attachChild(sceneModel);
    bulletAppState.getPhysicsSpace().add(sceneModel);
    
    }
  
   
}
