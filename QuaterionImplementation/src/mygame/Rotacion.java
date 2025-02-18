
package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author juan_pab.aguirre
 */
public class Rotacion {
 
    
      public double velocidadAngular = 0.1f;
      public double posicionAngular = 0.1f;
      public double anguloLimite1= 0.5f;
      public int anguloLimite2= 0;
      
      

      
      Quaternion q1 = new  Quaternion();
      
     public Quaternion aplicarQuat(){
                        
           
     if(posicionAngular < 0.5){
         
          velocidadAngular = velocidadAngular + 0.1;        
          q1.fromAngleAxis((float) velocidadAngular, Vector3f.UNIT_Y);     
          
     }
     
     else{
     
         velocidadAngular = velocidadAngular*-1;
     }
    
     
     return q1;
    
    }

    public double getVelocidadAngular() {
        return velocidadAngular;
    }

    public void setVelocidadAngular(int velocidadAngular) {
        this.velocidadAngular = velocidadAngular;
    }

    public double getAnguloLimite1() {
        return anguloLimite1;
    }

    public void setAnguloLimite1(int anguloLimite1) {
        this.anguloLimite1 = anguloLimite1;
    }

    public int getAnguloLimite2() {
        return anguloLimite2;
    }

    public void setAnguloLimite2(int anguloLimite2) {
        this.anguloLimite2 = anguloLimite2;
    }

    public Quaternion getQ1() {
        return q1;
    }

    public void setQ1(Quaternion q1) {
        this.q1 = q1;
    }

  
    
}
