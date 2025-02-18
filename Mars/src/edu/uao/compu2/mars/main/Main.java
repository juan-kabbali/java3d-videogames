package edu.uao.compu2.mars.main;

import edu.uao.compu2.mars.utilities.SceneUtilities;
import edu.uao.compu2.mars.utilities.GUIUtilities;
import edu.uao.compu2.mars.utilities.LightUtilities;
import edu.uao.compu2.mars.objects.MyCamera;
import edu.uao.compu2.mars.objects.Diamond;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;

/*
 * @author Juan pablo aguirre
 */
public class Main extends SimpleApplication implements ActionListener, PhysicsCollisionListener {

    private Node player;
    private BulletAppState bulletAppState;
    private RigidBodyControl[] controlesHuevos;
    private RigidBodyControl[] controlesAliens;
    private CharacterControl jugador;
    private boolean left = false, right = false, up = false, down = false, shoot = false;
    private Vector3f walkDirection = new Vector3f();
    private float CHARACTER_SPEED = 0.5f;
    private int puntos;
    private boolean DEBUG = false;
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private Quad barrita;
    private Geometry barraVida;
    BitmapText scoreText;

    private AudioNode audioShot;
    private AudioNode coin;
    private AudioNode audioPain;
    private AudioNode enemyDie;
    private AudioNode gameOver;
    private AudioNode playerDie;
    private Spatial[] huevos;
    private Spatial[] aliens;

    private Diamond D1;
    private Diamond D2;
    private Diamond D3;
    private Diamond D4;
    private Diamond D5;
    private boolean muerte;
    private boolean ganar;

    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(30);
        settings.setResolution(800, 600);
        settings.setMinHeight(600);
        settings.setMinWidth(800);
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        inicialGUIPuntos();
        GUIUtilities.iniciarDiamantes(guiNode, assetManager);
        puntos = 0;
        setDisplayStatView(false);
        setDisplayFps(false);
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        if (DEBUG) {
            //bulletAppState.getPhysicsSpace().enableDebug(assetManager);
        }
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));

        flyCam.setEnabled(false);
        stateManager.detach(stateManager.getState(FlyCamAppState.class));

        MyCamera myCam = new MyCamera(cam);
        myCam.registerWithInput(inputManager);

        this.huevos = new Spatial[29];
        this.controlesHuevos = new RigidBodyControl[29];
        this.aliens = new Spatial[29];
        this.controlesAliens = new RigidBodyControl[29];
        this.muerte = false;
        this.ganar = false;

        LightUtilities.luzAmbiental(rootNode);
        LightUtilities.luzDireccional(rootNode, new Vector3f(0, -5f, 0));
        SceneUtilities.agregarEscenario(rootNode, bulletAppState, assetManager);
        SceneUtilities.agregarNave(rootNode, bulletAppState, assetManager, new Vector3f(0, 25f, 0));
        SceneUtilities.agregarNave(rootNode, bulletAppState, assetManager, new Vector3f(20f, 80f, 30f));
        GUIUtilities.marcoVida(guiNode, assetManager);
        GUIUtilities.crearArma(guiNode, assetManager);
        GUIUtilities.miraArma(guiNode, assetManager, guiFont);

        iniciarHuevo();
        iniciarAliens();
        iniciarDiamantes();
        iniciarPersonaje();
        setUpKeys();

        crearBarra();
        iniciarAudio();

    }

    public void iniciarHuevo() {

        for (int i = 0; i < huevos.length; i++) {

            huevos[i] = assetManager.loadModel("Models/Huevo_Alien/Alien_Egg.j3o");
            CollisionShape shapeHuevo = CollisionShapeFactory.createBoxShape(huevos[i]);
            controlesHuevos[i] = new RigidBodyControl(shapeHuevo, 5);

            huevos[i].setLocalTranslation(35, 0, 35);
            huevos[i].setName("AlienEgg" + i);
            huevos[i].addControl(controlesHuevos[i]);
            rootNode.attachChild(huevos[i]);
            bulletAppState.getPhysicsSpace().add(huevos[i]);

        }

    }

    public void iniciarDiamantes() {

        D1 = new Diamond(assetManager, "D1", new Vector3f(2.5433183f, -6.4120007f, -3.8146973E-6f));
        D1.agregarAlJuego(rootNode, bulletAppState);

        D2 = new Diamond(assetManager, "D2", new Vector3f(100.879745f, -6.4120083f, 84.05356f));
        D2.agregarAlJuego(rootNode, bulletAppState);

        D3 = new Diamond(assetManager, "D3", new Vector3f(76.08455f, -4.8942137f, -89.16952f));
        D3.agregarAlJuego(rootNode, bulletAppState);

        D4 = new Diamond(assetManager, "D4", new Vector3f(-98.85161f, -0.4418316f, 91.89937f));
        D4.agregarAlJuego(rootNode, bulletAppState);

        D5 = new Diamond(assetManager, "D5", new Vector3f(-115.80232f, -7.329855f, -113.07232f));
        D5.agregarAlJuego(rootNode, bulletAppState);

    }

    public void morir() {
        rootNode.detachChild(coin);
        rootNode.detachChild(audioShot);
        rootNode.detachChild(enemyDie);
        playerDie.play();
        gameOver.play();
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);

        FogFilter fog = new FogFilter();
        fog.setFogColor(ColorRGBA.Red);
        Vector3f posJugador = jugador.getViewDirection();
        fog.setFogDistance(posJugador.length());
        fog.setFogDensity(1.3f);
        fpp.addFilter(fog);
        viewPort.addProcessor(fpp);

        guiNode.detachAllChildren();

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 6);
        ch.setText("GAME OVER"); // crosshairs
        ch.setColor(ColorRGBA.Black);
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);

    }

    public void iniciarAliens() {
        for (int i = 0; i < aliens.length; i++) {

            aliens[i] = assetManager.loadModel("Models/ALIEN/Chestburster.j3o");
            aliens[i].setLocalScale(2);
            CollisionShape shapeAlien = CollisionShapeFactory.createBoxShape(aliens[i]);
            controlesAliens[i] = new RigidBodyControl(shapeAlien, 5);
            aliens[i].setLocalTranslation(10, 0, 10);
            aliens[i].setLocalScale(3f);
            aliens[i].setUserData("vida", i);
            aliens[i].setName("Alien" + i);
            aliens[i].addControl(controlesAliens[i]);
            rootNode.attachChild(aliens[i]);
            bulletAppState.getPhysicsSpace().add(aliens[i]);

        }

    }

    public void iniciarPersonaje() {

        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1f, 2.5f, 1);
        jugador = new CharacterControl(capsuleShape, 0.05f);
        jugador.setJumpSpeed(10);
        jugador.setFallSpeed(15);
        jugador.setGravity(20);
        jugador.setPhysicsLocation(new Vector3f(10, 20, 5));

        player = new Node("Yo");
        player.setUserData("Vida", 200);
        player.addControl(jugador);

        bulletAppState.getPhysicsSpace().add(jugador);

    }

    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");

        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "Shoot");

    }

    @Override
    public void simpleUpdate(float tpf) {

        actualizarPuntos();

        if (muerte == false && ganar == false) {
            if (shoot) {
                procesarDisparo();
                procesarDisparoAliens();
                audioShot.playInstance();

            }
            int vidaJugador = player.getUserData("Vida");
            barrita.updateGeometry(((float) vidaJugador), 30);
            camine();
            perseguir();

            verificarDiamantes();
        }

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void onAction(String name, boolean isPressed, float tpf) {

        if (name.equals("Shoot")) {
            shoot = isPressed;
        } else if (name.equals("Left")) {
            left = isPressed;
        } else if (name.equals("Right")) {
            right = isPressed;
        } else if (name.equals("Up")) {
            up = isPressed;
        } else if (name.equals("Down")) {
            down = isPressed;

        } else if (name.equals("Jump")) {
            if (isPressed) {
                jugador.jump();
            }
        }

    }

    public void camine() {

        camDir.set(cam.getDirection()).multLocal(CHARACTER_SPEED);

        camDir.setY(0);

        camLeft.set(cam.getLeft()).multLocal(CHARACTER_SPEED);
        walkDirection.set(0, 0, 0);
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {

            walkDirection.addLocal(camDir);
        }

        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        jugador.setWalkDirection(walkDirection);
        cam.setLocation(jugador.getPhysicsLocation());

    }

    public void perseguir() {
        Vector3f posJugador = jugador.getPhysicsLocation();
        for (int i = 0; i < huevos.length; i++) {
            Vector3f posEnemigo = controlesHuevos[i].getPhysicsLocation();
            Vector3f direction = posJugador.subtract(posEnemigo);
            Vector3f go = direction.normalizeLocal();
            Vector3f direccionFuerza = new Vector3f(go.getX(), 0, go.getZ());
            controlesHuevos[i].applyCentralForce(direccionFuerza.mult(40));

        }
        for (int i = 0; i < aliens.length; i++) {
            Vector3f posEnemigo = controlesAliens[i].getPhysicsLocation();
            Vector3f direction = posJugador.subtract(posEnemigo);
            Vector3f go = direction.normalizeLocal();
            Vector3f direccionFuerza = new Vector3f(go.getX(), 0, go.getZ());
            controlesAliens[i].applyCentralForce(direccionFuerza.mult(40));
        }

    }

    @Override
    public void collision(PhysicsCollisionEvent event) {

        if (muerte != true) {
            if (event.getNodeA().getName().contains("Alien") || event.getNodeB().getName().contains("Alien")) {

                if ("Yo".equals(event.getNodeA().getName()) || "Yo".equals(event.getNodeB().getName())) {

                    int vidaJugador = (Integer) player.getUserData("Vida");
                    player.setUserData("Vida", (vidaJugador - 1));
                    if (vidaJugador > 0) {
                        audioPain.playInstance();

                    } else {

                        morir();
                        muerte = true;

                    }
                }

            }

            /* if (event.getNodeA().getName().contains("D") || event.getNodeB().getName().contains("D")) {
                if ("Yo".equals(event.getNodeA().getName()) || "Yo".equals(event.getNodeB().getName())) {

                    if (event.getNodeA().getName().contains("D")) {
                        rootNode.detachChild(event.getNodeA());
                        bulletAppState.getPhysicsSpace().remove(event.getNodeA());
                        coin.playInstance();
                        
                        GUIUtilities.crearDiamante(250, 550, guiNode, assetManager, true);

                    }

                    if (event.getNodeB().getName().contains("D")) {
                        rootNode.detachChild(event.getNodeB());
                        bulletAppState.getPhysicsSpace().remove(event.getNodeB());
                        coin.playInstance();

                    }

                }

            }*/
            if ("Yo".equals(event.getNodeA().getName()) || "Yo".equals(event.getNodeB().getName())) {

                if (event.getNodeA().getName().contains("D1") || event.getNodeB().getName().contains("D1")) {

                    rootNode.detachChild(event.getNodeA());
                    bulletAppState.getPhysicsSpace().remove(event.getNodeA());
                    coin.playInstance();
                    GUIUtilities.crearDiamante(250, 550, guiNode, assetManager, "Di1", true);
                    guiNode.detachChildNamed("notDi1");
                }

                if (event.getNodeA().getName().contains("D2") || event.getNodeB().getName().contains("D2")) {

                    rootNode.detachChild(event.getNodeA());
                    bulletAppState.getPhysicsSpace().remove(event.getNodeA());
                    coin.playInstance();
                    GUIUtilities.crearDiamante(320, 550, guiNode, assetManager, "Di2", true);
                    guiNode.detachChildNamed("notDi2");
                }

                if (event.getNodeA().getName().contains("D3") || event.getNodeB().getName().contains("D3")) {

                    rootNode.detachChild(event.getNodeA());
                    bulletAppState.getPhysicsSpace().remove(event.getNodeA());
                    coin.playInstance();
                    GUIUtilities.crearDiamante(390, 550, guiNode, assetManager, "Di3", true);
                    guiNode.detachChildNamed("notDi3");
                }

                if (event.getNodeA().getName().contains("D4") || event.getNodeB().getName().contains("D4")) {

                    rootNode.detachChild(event.getNodeA());
                    bulletAppState.getPhysicsSpace().remove(event.getNodeA());
                    coin.playInstance();
                    GUIUtilities.crearDiamante(460, 550, guiNode, assetManager, "Di4", true);
                    guiNode.detachChildNamed("notDi4");
                }

                if (event.getNodeA().getName().contains("D5") || event.getNodeB().getName().contains("D5")) {

                    rootNode.detachChild(event.getNodeA());
                    bulletAppState.getPhysicsSpace().remove(event.getNodeA());
                    coin.playInstance();
                    GUIUtilities.crearDiamante(530, 550, guiNode, assetManager, "Di15", true);
                    guiNode.detachChildNamed("notDi5");
                }

            }
        }
    }

    public void crearBarra() {

        barrita = new Quad(200, 30);
        barraVida = new Geometry("Barra", barrita);
        Material normal = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        normal.setColor("Color", ColorRGBA.Green);
        barraVida.setMaterial(normal);
        barraVida.setLocalTranslation(15, 555, 0);
        guiNode.attachChild(barraVida);
    }

    public void procesarDisparo() {
        // 1. Reset results list.
        CollisionResults results = new CollisionResults();
        // 2. Aim the ray from cam loc to cam direction.
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        // 3. Collect intersections between Ray and Shootables in results list.
        rootNode.collideWith(ray, results);
        // 4. Print the results
        //System.out.println("----- Collisions? " + results.size() + "-----");
        for (int i = 0; i < results.size(); i++) {
            // For each hit, we know distance, impact point, name of geometry.
            float dist = results.getCollision(i).getDistance();
            //System.out.println("Distance="+dist);
            Vector3f pt = results.getCollision(i).getContactPoint();
            String hit = results.getCollision(i).getGeometry().getName();
            for (i = 0; i < huevos.length; i++) {

                if (("AlienEgg" + i).equals(hit)) {

                    enemyDie.playInstance();
                    puntos++;
                    rootNode.detachChild(huevos[i]);
                    bulletAppState.getPhysicsSpace().remove(huevos[i]);
                }
            }

        }
    }

    public void procesarDisparoAliens() {

        // 1. Reset results list.
        CollisionResults results = new CollisionResults();
        // 2. Aim the ray from cam loc to cam direction.
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        // 3. Collect intersections between Ray and Shootables in results list.
        rootNode.collideWith(ray, results);
        // 4. Print the results
        //System.out.println("----- Collisions? " + results.size() + "-----");
        for (int i = 0; i < results.size(); i++) {
            String nombreNodo = results.getCollision(i).getGeometry().getParent().getName();

            for (i = 0; i < aliens.length; i++) {

                if (("Alien" + i).equals(nombreNodo)) {
                    enemyDie.playInstance();
                    rootNode.detachChild(aliens[i]);
                    puntos++;
                    bulletAppState.getPhysicsSpace().remove(aliens[i]);
                }

            }

        }
    }

    public void iniciarAudio() {
        audioShot = new AudioNode(assetManager, "Sounds/GunShot.wav", false);
        audioShot.setPositional(false);
        audioShot.setLooping(false);
        audioShot.setVolume(1f);
        rootNode.attachChild(audioShot);

        audioPain = new AudioNode(assetManager, "Sounds/Damage.wav", false);
        audioPain.setPositional(false);
        audioPain.setLooping(false);
        audioPain.setVolume(1.2f);
        rootNode.attachChild(audioPain);

        coin = new AudioNode(assetManager, "Sounds/coin.ogg", false);
        coin.setPositional(false);
        coin.setLooping(false);
        coin.setVolume(1f);
        rootNode.attachChild(coin);

        enemyDie = new AudioNode(assetManager, "Sounds/rat_die.ogg", false);
        enemyDie.setPositional(false);
        enemyDie.setLooping(false);
        enemyDie.setVolume(1f);
        rootNode.attachChild(enemyDie);

        gameOver = new AudioNode(assetManager, "Sounds/gameOver.ogg", false);
        gameOver.setPositional(false);
        gameOver.setLooping(false);
        gameOver.setVolume(2f);
        rootNode.attachChild(gameOver);

        playerDie = new AudioNode(assetManager, "Sounds/Scream.ogg", false);
        playerDie.setPositional(false);
        playerDie.setLooping(false);
        playerDie.setVolume(1.5f);
        rootNode.attachChild(playerDie);

        AudioNode nature = new AudioNode(assetManager, "Sounds/ambient.ogg", false);
        nature.setPositional(false);
        nature.setLooping(true);
        nature.setVolume(7);
        rootNode.attachChild(nature);
        nature.play();

    }

    private void ganar() {
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);

        FogFilter fog = new FogFilter();
        fog.setFogColor(ColorRGBA.White);
        Vector3f posJugador = jugador.getViewDirection();
        fog.setFogDistance(posJugador.length());
        fog.setFogDensity(0.5f);
        fpp.addFilter(fog);
        viewPort.addProcessor(fpp);
        guiNode.detachAllChildren();

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        ch.setText("¡YOU WON!" + "\n" + "Killed Aliens: " + Integer.toString(puntos)); // crosshairs
        ch.setColor(ColorRGBA.Black);
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);

    }

    private void verificarDiamantes() {
        if (!rootNode.hasChild(D1.getDiamante())) {
            if (!rootNode.hasChild(D2.getDiamante())) {
                if (!rootNode.hasChild(D3.getDiamante())) {
                    if (!rootNode.hasChild(D4.getDiamante())) {
                        if (!rootNode.hasChild(D5.getDiamante())) {
                            ganar = true;
                            ganar();
                        }
                    }
                }
            }
        }
    }

    private void actualizarPuntos() {
        scoreText.setText("Killed Aliens: " + Integer.toString(puntos));
    }

    private void inicialGUIPuntos() {
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        scoreText = new BitmapText(guiFont, false);
        scoreText.setSize(guiFont.getCharSet().getRenderedSize() * 1.5f);
        scoreText.setText("Score: " + Integer.toString(puntos));
        scoreText.setColor(ColorRGBA.White);
        scoreText.setLocalTranslation(settings.getWidth() / 8 * 6, 580, 0);
        scoreText.setName("scoreText");
        guiNode.attachChild(scoreText);
    }

}
