package co.InteractiveMusic;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;

public class Jugador {
 
    
    
  public static CharacterControl player  ; /*objeto que tiene metodos definidos que permiten hacer un mapa de eventos que son capturados por un listener, ademas tiene metodos que controlan la gravedad, paso, salto ect... */
  public static Vector3f walkDirection = new Vector3f(); /*vector que obtiene las coordenadas de la camara y las asigna a la direccion de movimiento del personaje */ 
  public static boolean left = false, right = false, up = false, down = false, run=false, rayoInstrument=false;/*estados logicos que indican si una accion del personaje se esta ejecutando*/
  public static float CHARACTER_SPEED = 0.2f;
  public static CapsuleCollisionShape capsuleShape;
  
  
  
     public static void setUpPlayerControlsAndCollision() {
    /*se crea una capsula para detectar colisiones del personaje ''CharacterControl*/
    capsuleShape = new CapsuleCollisionShape(1.5f, 5f, 1);
    player = new CharacterControl(capsuleShape, 0.05f); /*malla de colision y magnitud que indica la capacidad de subir pendientes o escaleras*/ 
    player.setJumpSpeed(15);
    player.setFallSpeed(20);
    player.setGravity(30);
    player.setPhysicsLocation(new Vector3f(5, 5, 10)); /*posicion inicial del personaje*/
    }

}
