package co.InteractiveMusic.Utilities;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.texture.Texture2D;
import com.jme3.water.WaterFilter;

public class Escenario {

    public static Node acustico;
    public static Node escenarioRepuesto;
    public static Geometry acusticoFisico;
    public static Node escenarioCasa;
    public static Geometry casaFisico;
    public static WaterFilter water;
    private static Vector3f LIGHT_DIR = new Vector3f(-4.9f, -1.3f, 5.9f);

    public static void agregarEscenario(Node rootNode, BulletAppState bulletAppState, AssetManager assetManager, String visual, String fisico, String repuesto) {


        acustico = (Node) assetManager.loadModel(visual);
        acusticoFisico = (Geometry) assetManager.loadModel(fisico);
        escenarioRepuesto = (Node) assetManager.loadModel(repuesto);
        acustico.setLocalTranslation(new Vector3f(0, 0, -500));
        escenarioRepuesto.setLocalTranslation(new Vector3f(0, 0, -500));
        acusticoFisico.setLocalTranslation(new Vector3f(0, 0, -500));
        CollisionShape shapeEscenario = CollisionShapeFactory.createMeshShape(acusticoFisico);
        RigidBodyControl controlEscenario = new RigidBodyControl(shapeEscenario, 0);
        acustico.addControl(controlEscenario);
        rootNode.attachChild(acustico);
        rootNode.attachChild(escenarioRepuesto);
        bulletAppState.getPhysicsSpace().add(acustico);




    }

    public static void agregarEscenarioCasa(Node rootNode, BulletAppState bulletAppState, AssetManager assetManager, String scenescasaj3o, String scenesMundoFisicoCasaj3o) {

        escenarioCasa = (Node) assetManager.loadModel(scenescasaj3o);
        casaFisico = (Geometry) assetManager.loadModel(scenesMundoFisicoCasaj3o);
        Node pared1 = (Node) assetManager.loadModel("Scenes/pared1.j3o");
        Node pared2 = (Node) assetManager.loadModel("Scenes/pared2.j3o");
        escenarioCasa.setLocalScale(10f, 10f, 10f);
        casaFisico.setLocalScale(10f, 10f, 10f);
        pared1.setLocalScale(10f, 10f, 10f);
        pared2.setLocalScale(10f, 10f, 10f);

        CollisionShape shapeEscenario = CollisionShapeFactory.createMeshShape(casaFisico);
        RigidBodyControl controlEscenario = new RigidBodyControl(shapeEscenario, 0);

        escenarioCasa.addControl(controlEscenario);
        rootNode.attachChild(escenarioCasa);
        rootNode.attachChild(pared1);
        rootNode.attachChild(pared2);

        bulletAppState.getPhysicsSpace().add(escenarioCasa);
    }

    public static void crearAgua(AssetManager assetManager, Node rootNode) {

        ParticleEmitter fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        fire.move(52f, 4f, -32.f);
        Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture("Interface/agua.png"));
        fire.scale(0.5f);
        fire.setMaterial(mat_red);
        fire.setImagesX(25);
        fire.setImagesY(10);
        fire.setEndColor(new ColorRGBA(ColorRGBA.Blue));   // red
        fire.setStartColor(new ColorRGBA(ColorRGBA.Cyan)); // yellow
        fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0.5f, 0));
        fire.setGravity(0, -9.8f, 0);
        rootNode.attachChild(fire);
    }

    public static void crearAguaMaterial(AssetManager assetManager, Node rootNode) {


        water = new WaterFilter(rootNode, LIGHT_DIR);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        water.setWaterHeight(10f);
        water.setWaveScale(0.003f);
        water.setMaxAmplitude(2f);
        water.setRadius(20f);
        water.setCenter(new Vector3f(9716232f, 4.003327f, -6.0738964f));
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));
        fpp.addFilter(water);



    }


  
    
}
