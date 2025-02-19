package jajagames.compu.juego.main;

//Box box = new Box(10, 0.5f, 10);
//Geometry boxGeom = new Geometry("Floor", box);
//Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
//mat2.setColor("m_Color", ColorRGBA.White);
//boxGeom.setMaterial(mat2);
//rootNode.attachChild(boxGeom);
// 
//boxGeom.setLocalTranslation(0, -3f, 0);

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import jajagames.compu.juego.utils.Figura;
 
/**
 * Example 9 - How to make walls and floors solid.
 * This collision code uses Physics and a custom Action Listener.
 * @author normen, with edits by Zathras
 */
public class Main extends SimpleApplication{
        //implements ActionListener {
    
 
//  private Spatial sceneModel;
//  private BulletAppState bulletAppState;
//  private RigidBodyControl landscape;
//  private CharacterControl player;
//  private Vector3f walkDirection = new Vector3f();
//  private boolean left = false, right = false, up = false, down = false, run=false, shoot=false;
//  private boolean debug = true;
//  private float CHARACTER_SPEED = 0.5f;
//  private int frames=0;
//  private int cansancio=500;
//  private boolean flagCansancio = false;
// 
//  //Temporary vectors used on each frame.
//  //They here to avoid instanciating new vectors on each frame
//  private Vector3f camDir = new Vector3f();
//  private Vector3f camLeft = new Vector3f();
//  private Geometry mark;
 
  public static void main(String[] args) {
    Main app = new Main();
    app.start();
  }
  
    /**
     * A centred plus sign to help the player aim.
     */
//    protected void initCrossHairs() {
//        setDisplayStatView(false);
//        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
//        BitmapText ch = new BitmapText(guiFont, false);
//        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
//        ch.setText("+"); // crosshairs
//        ch.setLocalTranslation( // center
//                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
//        guiNode.attachChild(ch);
//    }
//    
//      /** A red ball that marks the last spot that was "hit" by the "shot". */
//  protected void initMark() {
//    Sphere sphere = new Sphere(30, 30, 0.2f);
//    mark = new Geometry("BOOM!", sphere);
//    Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//    mark_mat.setColor("Color", ColorRGBA.Red);
//    mark.setMaterial(mark_mat);
//  }
 
  public void simpleInitApp() {
    /** Set up Physics */
//    bulletAppState = new BulletAppState();
//   // flyCam.setEnabled(false);
//    stateManager.attach(bulletAppState);
// 
//    // We re-use the flyby camera for rotation, while positioning is handled by physics
//    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
//    flyCam.setMoveSpeed(100);
//    setUpKeys();
//    setUpLight();
// 
//    // We load the scene from the zip file and adjust its size.
//    assetManager.registerLocator("town.zip", ZipLocator.class);
//    sceneModel = assetManager.loadModel("main.scene");
//    sceneModel.setLocalScale(2f);
// 
//    // We set up collision detection for the scene by creating a
//    // compound collision shape and a static RigidBodyControl with mass zero.
//    CollisionShape sceneShape =
//            CollisionShapeFactory.createMeshShape((Node) sceneModel);
//    landscape = new RigidBodyControl(sceneShape, 0);
//    sceneModel.addControl(landscape);
// 
//    // We set up collision detection for the player by creating
//    // a capsule collision shape and a CharacterControl.
//    // The CharacterControl offers extra settings for
//    // size, stepheight, jumping, falling, and gravity.
//    // We also put the player in its starting position.
//    CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
//    player = new CharacterControl(capsuleShape, 0.05f);
//    player.setJumpSpeed(20);
//    player.setFallSpeed(30);
//    player.setGravity(30);
//    player.setPhysicsLocation(new Vector3f(0, 10, 0));
// 
//    // We attach the scene and the player to the rootnode and the physics space,
//    // to make them appear in the game world.
//    rootNode.attachChild(sceneModel);
//    bulletAppState.getPhysicsSpace().add(landscape);
//    bulletAppState.getPhysicsSpace().add(player);
//    if(debug) {
//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
//    }
//    
//    initCrossHairs();
//    initMark();
    Node pared = Figura.unaPared(Vector3f.ZERO, ColorRGBA.Blue, assetManager);
                //(Vector3f.ZERO, ColorRGBA.Blue, assetManager); 
         rootNode.attachChild(pared);
  }
 
  private void setUpLight() {
    // We add light so we see the scene
//    AmbientLight al = new AmbientLight();
//    al.setColor(ColorRGBA.White.mult(1.3f));
//    rootNode.addLight(al);
// 
//    DirectionalLight dl = new DirectionalLight();
//    dl.setColor(ColorRGBA.White);
//    dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
//    rootNode.addLight(dl);
  }
 
  /** We over-write some navigational key mappings here, so we can
   * add physics-controlled walking and jumping: */
//  private void setUpKeys() {
//    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
//    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
//    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
//    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
//    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
//    inputManager.addMapping("Run", new KeyTrigger(KeyInput.KEY_LSHIFT));
//    inputManager.addListener(this, "Left");
//    inputManager.addListener(this, "Right");
//    inputManager.addListener(this, "Up");
//    inputManager.addListener(this, "Down");
//    inputManager.addListener(this, "Jump");
//    inputManager.addListener(this, "Run");
//    
//    inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
//    inputManager.addListener(this, "Shoot");
//  }
// 
//  /** These are our custom actions triggered by key presses.
//   * We do not walk yet, we just keep track of the direction the user pressed. */
//  public void onAction(String binding, boolean isPressed, float tpf) {
//
//    if (binding.equals("Shoot")) {
//      shoot = isPressed;
//    } else if (binding.equals("Left")) {
//      left = isPressed;
//    } else if (binding.equals("Right")) {
//      right= isPressed;
//    } else if (binding.equals("Up")) {
//      up = isPressed;
//    } else if (binding.equals("Down")) {
//      down = isPressed;
//    } else if (binding.equals("Run")) {
//        if(!flagCansancio) {
//            run = isPressed;
//            if(!run){
//                flagCansancio = false;
//            }
//        }
//        if(frames > cansancio) {
//            flagCansancio = true;
//            frames = 0;
//        }
//    } else if (binding.equals("Jump")) {
//      if (isPressed) { player.jump(); }
//    }
//  }
// 
//  public void procesarDisparo(){
//      // 1. Reset results list.
//        CollisionResults results = new CollisionResults();
//        // 2. Aim the ray from cam loc to cam direction.
//        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
//        // 3. Collect intersections between Ray and Shootables in results list.
//        rootNode.collideWith(ray, results);
//        // 4. Print the results
//        System.out.println("----- Collisions? " + results.size() + "-----");
//        for (int i = 0; i < results.size(); i++) {
//          // For each hit, we know distance, impact point, name of geometry.
//          float dist = results.getCollision(i).getDistance();
//          System.out.println("Distance="+dist);
//          Vector3f pt = results.getCollision(i).getContactPoint();
//          String hit = results.getCollision(i).getGeometry().getName();
//          System.out.println("* Collision #" + i);
//          System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
//        }
//        // 5. Use the results (we mark the hit object)
//        if (results.size() > 0) {
//          // The closest collision point is what was truly hit:
//          CollisionResult closest = results.getClosestCollision();
//          // Let's interact - we mark the hit with a red dot.
//          mark.setLocalTranslation(closest.getContactPoint());
//          rootNode.attachChild(mark);
//        } else {
//          // No hits? Then remove the red mark.
//          rootNode.detachChild(mark);
//        }
//  }
  
  /**
   * This is the main event loop--walking happens here.
   * We check in which direction the player is walking by interpreting
   * the camera direction forward (camDir) and to the side (camLeft).
   * The setWalkDirection() command is what lets a physics-controlled player walk.
   * We also make sure here that the camera moves with player.
   */
  @Override
    public void simpleUpdate(float tpf) {
//        camDir.set(cam.getDirection()).multLocal(CHARACTER_SPEED);
//       
//        camDir.setY(0);
//        
//        camLeft.set(cam.getLeft()).multLocal(CHARACTER_SPEED);
//        walkDirection.set(0, 0, 0);
//        if (left) {
//            walkDirection.addLocal(camLeft);
//        }
//        if (right) {
//            walkDirection.addLocal(camLeft.negate());
//        }
//        if (up) {
//            if(run && !flagCansancio){
//                walkDirection.addLocal(camDir.mult(4));
//            } else {
//                walkDirection.addLocal(camDir);
//            }
//        }
//        if (down) {
//            walkDirection.addLocal(camDir.negate());
//        }
//        player.setWalkDirection(walkDirection);
//        cam.setLocation(player.getPhysicsLocation());
//        frames++;
//        
//        if(shoot){
//            procesarDisparo();
//        }
    }
}

       
