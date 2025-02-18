package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class Aplicacion extends SimpleApplication/* implements ActionListener */{

  private BulletAppState bulletAppState /*en donde se realizan los calculos fisicos*/;
  private boolean debug = true; /*variable logica que indica la visualisacion del mundo fisico y visual simultaneamente*/
  public static int frames=0;
 
  public void simpleInitApp() {
      
    Jugador.setUpPlayerControlsAndCollision();  /*se inicializan los mapas de eventos y su respectivo listener*/
    Jugador.setUpKeysJugador(inputManager);
    setUpApp();
    Light.setUpLight(rootNode); /*se agregan dos luces para poder ver el escenario, ambient y directional ligth*/
    Escenario.setUpScene(assetManager, rootNode, bulletAppState);  // We load the scene from the zip file and adjust its size.
 
  }
   @Override
  public void simpleUpdate(float tpf) {
      
       Cam.caminar(cam);
       if(Jugador.shoot){Jugador.procesarDisparo(cam,rootNode);
       
        }
       
    }
  protected void initCrossHairs() {
       
      setDisplayStatView(false);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
        
    } /* A centred plus sign to help the player aim/mira en el centro de la pantalla*/
  private void setUpApp() {
      
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    //flyCam.setEnabled(false); /*se desabilida la camara, ya el mouse no mueve la pantalla*/
    viewPort.setBackgroundColor(new ColorRGBA(20f, 20f, 1f, 1f)); /*view port es el fondo del escenario. BackgroundColor*/
    initCrossHairs(); /*mira en el centro de la pantalla '+'*/
    Jugador.initMark(assetManager); /* esfera azul que permite visualizar las colisiones del rayo*/
    flyCam.setMoveSpeed(100);
     bulletAppState.getPhysicsSpace().add(Jugador.player);
      // if(debug) {bulletAppState.getPhysicsSpace().enableDebug(assetManager);} /*permite visualizar el mundo fisico y visual simultaneamente*/
    }

 
}