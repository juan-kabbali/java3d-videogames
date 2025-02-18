package edu.uao.compu2.mars.utilities;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/*
 * @author Juan pablo aguirre
 */
public class SceneUtilities {

    public static void agregarEscenario(Node rootNode, BulletAppState bulletAppState, AssetManager assetManager) {
        Node escenario = (Node) assetManager.loadModel("Scenes/Scene1.j3o");

        CollisionShape shapeEscenario = CollisionShapeFactory.createMeshShape(escenario);
        RigidBodyControl controlEscenario = new RigidBodyControl(shapeEscenario, 0);

        escenario.addControl(controlEscenario);
        rootNode.attachChild(escenario);
        bulletAppState.getPhysicsSpace().add(escenario);
    }

    public static void agregarNave(Node rootNode, BulletAppState bulletAppState, AssetManager assetManager, Vector3f posicion) {
        Spatial nave = assetManager.loadModel("Models/Enterprise/Modeldatei/USSEnterprise.j3o");
        nave.setLocalScale(8f);
        nave.setLocalTranslation(posicion);
        CollisionShape shapeNave = CollisionShapeFactory.createMeshShape(nave);
        RigidBodyControl controlnave = new RigidBodyControl(shapeNave, 0);
        nave.addControl(controlnave);
        rootNode.attachChild(nave);
    }

}
