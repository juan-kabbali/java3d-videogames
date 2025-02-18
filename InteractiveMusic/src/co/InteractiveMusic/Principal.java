package co.InteractiveMusic;

import co.InteractiveMusic.Objetos.OSvalidator;
import co.InteractiveMusic.Utilities.Escenario;
import co.InteractiveMusic.Utilities.Gui2d;
import co.InteractiveMusic.Utilities.Luz;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;

public class Principal extends SimpleApplication implements ActionListener{
 
    public RigidBodyControl landscape /*para cuerpos rigidos, si la masa es cero, es inamovible*/;
    private BulletAppState bulletAppState /*en donde se realizan los calculos fisicos*/;
    private boolean debug = false; /*variable logica que indica la visualisacion del mundo fisico y visual simultaneamente*/

    public static AudioNode ambientAudio;
    private static Vector3f camDir = new Vector3f();  //Temporary vectors used on each frame.
    private static Vector3f camLeft = new Vector3f(); //They here to avoid instanciating new vectors on each frame
    private static int frames = 0;

    @Override
    public void simpleInitApp() {

        setUpKeys();  /*se inicializan los mapas de eventos y su respectivo listener*/
        Jugador.setUpPlayerControlsAndCollision();/*inicializacion de valores del character control y se crea la malla de colision*/
        setUpapp(); /*inicializa la app*/
        setUpSounds();
        System.out.println(OSvalidator.getOSRawName());

    }

    @Override
    public void simpleUpdate(float tpf) {

        desplazarse();
        trocarAudios();
    }

    @Override
    public void simpleRender(RenderManager rm) {
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
    }

    private void setUpKeys() {
        /*se crea un mapa de acciones de teclado, izquierda,derecha,arriba,abajo,saltar*/
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Run", new KeyTrigger(KeyInput.KEY_LSHIFT));
        /*se agrega el mapa de acciones a un listener que capture los eventos*/
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
        inputManager.addListener(this, "Run");

        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "Shoot");
    }

    public void setUpapp() {

        bulletAppState = new BulletAppState(); /*setUpfisics*/
        stateManager.attach(bulletAppState);
        viewPort.setBackgroundColor(new ColorRGBA(100f, 100f, 200f, 10f)); /*view port es el fondo del escenario. BackgroundColor*/
        flyCam.setMoveSpeed(100);
        bulletAppState.getPhysicsSpace().add(Jugador.player);
        if (debug) {bulletAppState.getPhysicsSpace().enableDebug(assetManager); }
        Luz.luzAmbiental(rootNode);
        Escenario.agregarEscenario(rootNode, bulletAppState, assetManager, "Scenes/p.j3o", "Scenes/mfAcustico/mf.j3o", "Scenes/complemento.j3o");  // salonAcustico
        Escenario.agregarEscenarioCasa(rootNode, bulletAppState, assetManager, "Scenes/casaVisual.j3o", "Scenes/MundoFisicoCasa.j3o");  // casa
        Luz.luzSun(rootNode, new Vector3f(0.5f, 0.5f, 0.5f));
        Luz.luzSun(rootNode, new Vector3f(-0.5f, -0.5f, -0.5f));
        initCrossHairs(); /*mira en el centro de la pantalla '+'*/
//        Escenario.crearAguaMaterial(assetManager, rootNode);
    }

    public void desplazarse() {
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
            if (Jugador.run) {
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

        if (Jugador.rayoInstrument) {
            procesarDisparo();
        }
    }

    public void onAction(String binding, boolean isPressed, float tpf) {

        if (binding.equals("Shoot")) {
            Jugador.rayoInstrument = isPressed;
        } else if (binding.equals("Left")) {
            Jugador.left = isPressed;
        } else if (binding.equals("Right")) {
            Jugador.right = isPressed;
        } else if (binding.equals("Up")) {
            Jugador.up = isPressed;
        } else if (binding.equals("Down")) {
            Jugador.down = isPressed;
        } else if (binding.equals("Run")) {
        } else if (binding.equals("Jump")) {
            if (isPressed) {
                Jugador.player.jump();
            }
        }

    }

    public void procesarDisparo() {

        CollisionResults results = new CollisionResults(); /*objeto que es un tipo lista que devuelve un vector  */
        Ray ray = new Ray(cam.getLocation(), cam.getDirection()); /*se lanza el rayo desde la posicion y direccion de la camara*/
        rootNode.collideWith(ray, results); /* se añade al root el rayo y los results que permite detectar en el entorno 3d la distancia, el punto(direccion) y nombre de la geometria */

        if (results.size() > 0) {         /*condicional que valida si hay o no colisiones para agregar el MARK (esfera azul) al sceneGraph*/
            CollisionResult closest = results.getClosestCollision();
            System.out.println("geometria: " + closest.getGeometry().getName());
            interpretarDisparo(closest);
        }
    }

    public void interpretarDisparo(CollisionResult closest) {
        
        System.out.println(Jugador.player.getPhysicsLocation());
        identificarInstrumento(closest, "p-geom-22", "assets/Interface/saxofon.jpg");
        identificarInstrumento(closest, "p-geom-17", "assets/Interface/acordeon.jpg");identificarInstrumento(closest, "p-geom-28", "assets/Interface/acordeon.jpg");identificarInstrumento(closest, "p-geom-29", "assets/Interface/acordeon.jpg");
        identificarInstrumento(closest, "casaVisual-geom-4", "assets/Interface/enfermeria.jpg"); identificarInstrumento(closest, "casaVisual-geom-11", "assets/Interface/enfermeria.jpg");
        identificarInstrumento(closest, "casaVisual-geom-12", "assets/Interface/talleres.jpg"); identificarInstrumento(closest, "casaVisual-geom-7", "assets/Interface/talleres.jpg");
        identificarInstrumento(closest, "p-geom-37", "assets/Interface/trompeta.jpg");
        identificarInstrumento(closest, "p-geom-27", "assets/Interface/horarios.jpg");
        identificarInstrumento(closest, "p-geom-34", "assets/Interface/bombo.jpg");identificarInstrumento(closest, "p-geom-32", "assets/Interface/bombo.jpg");identificarInstrumento(closest, "p-geom-19", "assets/Interface/bombo.jpg");identificarInstrumento(closest, "p-geom-14", "assets/Interface/bombo.jpg");
        identificarInstrumento(closest, "p-geom-24", "assets/Interface/electrica.jpg"); identificarInstrumento(closest, "p-geom-8", "assets/Interface/electrica.jpg");
        identificarInstrumento(closest, "p-geom-0", "assets/Interface/acustica.jpg");identificarInstrumento(closest, "p-geom-12", "assets/Interface/acustica.jpg"); identificarInstrumento(closest, "p-geom-2", "assets/Interface/acustica.jpg");
        identificarInstrumento(closest, "p-geom-36", "assets/Interface/bateria.jpg");identificarInstrumento(closest, "p-geom-31", "assets/Interface/bateria.jpg"); identificarInstrumento(closest, "p-geom-39", "assets/Interface/bateria.jpg"); identificarInstrumento(closest, "p-geom-15", "assets/Interface/bateria.jpg");
        identificarInstrumento(closest, "p-geom-35", "assets/Interface/piano.jpg"); identificarInstrumento(closest, "p-geom-4", "assets/Interface/piano.jpg"); identificarInstrumento(closest, "p-geom-3", "assets/Interface/piano.jpg");
        identificarInstrumento(closest, "p-geom-20", "assets/Interface/congas.jpg");identificarInstrumento(closest, "p-geom-7", "assets/Interface/congas.jpg"); identificarInstrumento(closest, "p-geom-11", "assets/Interface/congas.jpg");
        identificarInstrumento(closest, "p-geom-30", "assets/Interface/cencerro.jpg"); identificarInstrumento(closest, "p-geom-26", "assets/Interface/cencerro.jpg");
        cambiarEscena(closest);

    }

    private void setUpSounds() {

        ambientAudio = new AudioNode(assetManager, "Sounds/ambiente.ogg", false);
        ambientAudio.setPositional(false);
        ambientAudio.setLooping(true);
        ambientAudio.setVolume(1.2f);
        rootNode.attachChild(ambientAudio);
        ambientAudio.play();


    }

    public void trocarAudios() {

        if (Gui2d.estoyVisible == false) {

            ambientAudio.play();


            if (Gui2d.sonidoInstrumento == null) {
            } else {
                Gui2d.sonidoInstrumento.stop();
            }

        } else {
            ambientAudio.pause();
        }


        if (Gui2d.isPlus == true) {
            Gui2d.getSonidoInstrumento().stop();
        }

    }

    public void identificarInstrumento(CollisionResult closest, String nombreGeometria, String rutaPantalla) {

        if (closest.getGeometry().getName().equals(nombreGeometria)) {
            Gui2d interfaz2d = new Gui2d(rootNode, assetManager, rutaPantalla);
            interfaz2d.setVisible(true);
            ambientAudio.pause();
        }


    }

    public void cambiarEscena(CollisionResult closest) {

        if (closest.getGeometry().getName().equals("casaVisual-geom-2") || closest.getGeometry().getName().equals("casaVisual-geom-18")) {

            System.out.println(Jugador.player.getPhysicsLocation());
            Jugador.player.setPhysicsLocation(new Vector3f(1f, 6f, -485f));
            Jugador.player.setCollisionShape(new CapsuleCollisionShape(1.5f, 13f, 1));

        }
        if (closest.getGeometry().getName().equals("complemento-geom-3")) {

            System.out.println(Jugador.player.getPhysicsLocation());
            Jugador.player.setPhysicsLocation(new Vector3f(61, 5f, 7f));
            Jugador.player.setCollisionShape(new CapsuleCollisionShape(1.5f, 5f, 1));

        }
    }
}
