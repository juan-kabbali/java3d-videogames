package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class Jugador implements ActionListener {
    
  public static CharacterControl player  ; /*objeto que tiene metodos definidos que permiten hacer un mapa de eventos que son capturados por un listener, ademas tiene metodos que controlan la gravedad, paso, salto ect... */
  public static Vector3f walkDirection = new Vector3f(); /*vector que obtiene las coordenadas de la camara y las asigna a la direccion de movimiento del personaje */ 
  public static boolean left = false, right = false, up = false, down = false, run=false, shoot=false;/*estados logicos que indican si una accion del personaje se esta ejecutando*/
  public static float CHARACTER_SPEED = 0.5f;
  public static int cansancio=500;
  public static boolean flagCansancio = false;
  private static Geometry mark; /*esfera que indica la colision del rayo lanzado por el cursor*/ 
     public static void setUpPlayerControlsAndCollision() {
    /*se crea una capsula para detectar colisiones del personaje ''CharacterControl*/
    CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
    Jugador.player = new CharacterControl(capsuleShape, 0.05f); /*malla de colision y magnitud que indica la capacidad de subir pendientes o escaleras*/ 
    Jugador.player.setJumpSpeed(15);
    Jugador.player.setFallSpeed(20);
    Jugador.player.setGravity(30);
    Jugador.player.setPhysicsLocation(new Vector3f(0, 1.5f, 0)); /*posicion inicial del personaje*/
    }
         
    static void setUpKeysJugador(InputManager inputManager) {
    /*se crea un mapa de acciones de teclado, izquierda,derecha,arriba,abajo,saltar*/
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addMapping("Run", new KeyTrigger(KeyInput.KEY_LSHIFT));
    /*se agrega el mapa de acciones a un listener que capture los eventos*/
    inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    agregarInputs(inputManager, new ActionListener() { public void onAction(String binding, boolean isPressed, float tpf) {
            inPutOnAction(binding, isPressed, tpf);
        }});    
  }
    public void onAction(String name, boolean isPressed, float tpf) {
        
        
    }
    private static void agregarInputs(InputManager inputManager,ActionListener a ){
    
    inputManager.addListener(a, "Left");
    inputManager.addListener(a, "Right");
    inputManager.addListener(a, "Up");
    inputManager.addListener(a, "Down");
    inputManager.addListener(a, "Jump");
    inputManager.addListener(a, "Run");
    inputManager.addListener(a, "Shoot");
    
    }
    private static void inPutOnAction(String binding, boolean isPressed, float tpf){
        
                  if (binding.equals("Shoot")) {
      Jugador.shoot = isPressed;
    } else if (binding.equals("Left")) {
      Jugador.left = isPressed;
    } else if (binding.equals("Right")) {
      Jugador.right= isPressed;
    } else if (binding.equals("Up")) {
      Jugador.up = isPressed;
    } else if (binding.equals("Down")) {
      Jugador.down = isPressed;
    } else if (binding.equals("Run")) { 
        
        if(!Jugador.flagCansancio) {
            Jugador.run = isPressed;
            if(!Jugador.run){
                Jugador.flagCansancio = false;
            }
        }
        if(Aplicacion.frames > Jugador.cansancio) {
            Jugador.flagCansancio = true;
            Aplicacion.frames = 0;
        }
    } else if (binding.equals("Jump")) {
      if (isPressed) { Jugador.player.jump(); }
    }
    
    }
     
      public static void procesarDisparo(Camera cam, Node rootNode){
        CollisionResults results = new CollisionResults(); /*objeto que es un tipo lista que devuelve un vector  */
        Ray ray = new Ray(cam.getLocation(), cam.getDirection()); /*se lanza el rayo desde la posicion y direccion de la camara*/
        rootNode.collideWith(ray, results); /* se añade al root el rayo y los results que permite detectar en el entorno 3d la distancia, el punto(direccion) y nombre de la geometria */

        System.out.println("-------Numero de colisiones " + results.size() + "-------------");/*retorna el numero de objetos con los que el rayo colisiono*/
        
        for (int i = 0; i < results.size(); i++) { /*para cada colision, pinta en consola la distancia, el punto de impacto y el nombre de la geometria*/
     
          float dist = results.getCollision(i).getDistance();
          System.out.println("Distance="+dist);
          Vector3f pt = results.getCollision(i).getContactPoint();
          String hit = results.getCollision(i).getGeometry().getName();
          System.out.println("* Collision #" + i);
          System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
        }
       
        
        /*condicional que valida si hay o no colisiones para agregar el MARK (esfera azul) al sceneGraph*/
        if (results.size() > 0) {
          CollisionResult closest = results.getClosestCollision();
          mark.setLocalTranslation(closest.getContactPoint());
          rootNode.attachChild(mark);
          } else {
            rootNode.detachChild(mark);
        }
  }
      
        public static void initMark(AssetManager assetManager) {
    Sphere sphere = new Sphere(30, 30, 0.2f);
    mark = new Geometry("BOOM!", sphere);
    Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mark_mat.setColor("Color", ColorRGBA.Blue);
    mark.setMaterial(mark_mat);
  }
}
