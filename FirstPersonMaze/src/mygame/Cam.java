package mygame;

import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import static mygame.Aplicacion.frames;

public class Cam {
    
  private static Vector3f camDir = new Vector3f();
  private static Vector3f camLeft = new Vector3f();
  
      static void caminar(Camera cam){
  
       camDir.set(cam.getDirection()).multLocal(Jugador.CHARACTER_SPEED);
       
        camDir.setY(0);
        
        camLeft.set(cam.getLeft()).multLocal(Jugador.CHARACTER_SPEED);
        Jugador.walkDirection.set(0, 0, 0);
        if (Jugador.left) {
            Jugador.walkDirection.addLocal(camLeft);
        }
        if (Jugador.right) {
            Jugador.walkDirection.addLocal(camLeft.negate());
        }
        if (Jugador.up) {
            if(Jugador.run && !Jugador.flagCansancio){
                Jugador.walkDirection.addLocal(camDir.mult(4));
            } else {
                Jugador.walkDirection.addLocal(camDir);
            }
        }
        if (Jugador.down) {
            Jugador.walkDirection.addLocal(camDir.negate());
        }
        Jugador.player.setWalkDirection(Jugador.walkDirection);
        cam.setLocation(Jugador.player.getPhysicsLocation());
        frames++;
        
       
  }
      


  
    
}
