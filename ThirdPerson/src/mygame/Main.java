package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;


public class Main extends SimpleApplication implements AnalogListener, ActionListener {

  private Geometry teaGeom;
  private ChaseCamera chaseCam;


  public void simpleInitApp() {
      
    setUpTeaGeomANDfloor();
    setUpCams();
  


    
    //Uncomment this to invert the camera's vertical rotation Axis                                      //chaseCam.setInvertVerticalAxis(true);
    //Uncomment this to invert the camera's horizontal rotation Axis                                   //chaseCam.setInvertHorizontalAxis(true);
    //Comment this to disable smooth camera motion                                                    //chaseCam.setSmoothMotion(true);
    //Uncomment this to disable trailing of the camera                                               //chaseCam.setTrailingEnabled(false);
    //Uncomment this to look 3 world units above the target                                         //chaseCam.setLookAtOffset(Vector3f.UNIT_Y.mult(30));
    //Uncomment this to enable rotation when the middle mouse button is pressed (like Blender)     //chaseCam.setToggleRotationTrigger(new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));

    //Uncomment this to set mutiple triggers to enable rotation of the cam                      //chaseCam.setToggleRotationTrigger(new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE),new KeyTrigger(KeyInput.KEY_SPACE));
    registerInput();

  }
  
  

  public void registerInput() {
    inputManager.addMapping("moveForward", new KeyTrigger(keyInput.KEY_UP), new KeyTrigger(keyInput.KEY_W));
    inputManager.addMapping("moveBackward", new KeyTrigger(keyInput.KEY_DOWN), new KeyTrigger(keyInput.KEY_S));
    inputManager.addMapping("moveRight", new KeyTrigger(keyInput.KEY_RIGHT), new KeyTrigger(keyInput.KEY_D));
    inputManager.addMapping("moveLeft", new KeyTrigger(keyInput.KEY_LEFT), new KeyTrigger(keyInput.KEY_A));
    inputManager.addMapping("displayPosition", new KeyTrigger(keyInput.KEY_P));
    inputManager.addListener(this, "moveForward", "moveBackward", "moveRight", "moveLeft");
    inputManager.addListener(this, "displayPosition");
  }

  public void onAnalog(String name, float value, float tpf) {
    if (name.equals("moveForward")) {
      teaGeom.move(0, 0, -5 * tpf);
    }
    if (name.equals("moveBackward")) {
      teaGeom.move(0, 0, 5 * tpf);
    }
    if (name.equals("moveRight")) {
      teaGeom.move(5 * tpf, 0, 0);
    }
    if (name.equals("moveLeft")) {
      teaGeom.move(-5 * tpf, 0, 0);

    }

  }

  public void onAction(String name, boolean keyPressed, float tpf) {
    if (name.equals("displayPosition") && keyPressed) {
      teaGeom.move(10, 10, 10);

    }
  }

  @Override
  public void simpleUpdate(float tpf) {
    super.simpleUpdate(tpf);

    //  teaGeom.move(new Vector3f(0.001f, 0, 0));
    // pivot.rotate(0, 0.00001f, 0);
    //   rootNode.updateGeometricState();
  }
  
//  
//    public void update() {
//        super.update();
//// render the viewports
//        float tpf = timer.getTimePerFrame();
//        state.getRootNode().rotate(0, 0.000001f, 0);
//        stateManager.update(tpf);
//        stateManager.render(renderManager);
//        renderManager.render(tpf);
//    }

    private void setUpTeaGeomANDfloor() {
        
    teaGeom = (Geometry) assetManager.loadModel("Models/Teapot/Teapot.obj");
    Material mat_tea = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    teaGeom.setMaterial(mat_tea);
    rootNode.attachChild(teaGeom);
    
      // Load a floor model
    Material mat_ground = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat_ground.setTexture("ColorMap", assetManager.loadTexture("Interface/Logo/Monkey.jpg"));
    Geometry ground = new Geometry("ground", new Quad(50, 50));
    ground.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
    ground.setLocalTranslation(-25, -1, 25);
    ground.setMaterial(mat_ground);
    rootNode.attachChild(ground);
    }

    private void setUpCams() {
        
    flyCam.setEnabled(false);    // Disable the default first-person cam!
    chaseCam = new ChaseCamera(cam, teaGeom, inputManager);     // Enable a chase cam
    
    chaseCam.setMinDistance(4.0f);
    chaseCam.setMaxDistance(8.0f);
    }
}