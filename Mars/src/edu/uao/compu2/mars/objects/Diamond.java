package edu.uao.compu2.mars.objects;

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
public class Diamond {

    private Spatial diamante;
    private CollisionShape shapeDiamante;
    private RigidBodyControl controlDiamante;

    public Diamond(AssetManager assetManager, String spatialName, Vector3f posicion) {

        diamante = assetManager.loadModel("Models/Diamond/diamante.j3o");
        shapeDiamante = CollisionShapeFactory.createBoxShape(diamante);
        diamante.setName(spatialName);
        diamante.setLocalTranslation(posicion);

        controlDiamante = new RigidBodyControl(shapeDiamante, 5);

        diamante.addControl(controlDiamante);

    }

    public Spatial getDiamante() {
        return diamante;
    }

    public void agregarAlJuego(Node rootNode, BulletAppState bulletAppState) {
        rootNode.attachChild(diamante);
        bulletAppState.getPhysicsSpace().add(diamante);
    }
}
