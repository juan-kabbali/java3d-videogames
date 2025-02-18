package edu.uao.compu.appgames;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.audio.LowPassFilter;
import com.jme3.bullet.BulletAppState;
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
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;

/**
 * Camilo Sandoval 2126521 Harrisson Zapata 2127214
 */
public class Game extends SimpleApplication implements ActionListener {

    //Atributos del filtro de agua y de iluminación
    private WaterFilter water;
    private Vector3f lightDir = new Vector3f(-4.9f, -1.3f, 5.9f); // same as light source
    private float initialWaterHeight = 120f;
    private float waterHeight = 0.0f;
    AudioNode waves;
    LowPassFilter underWaterAudioFilter = new LowPassFilter(0.5f, 0.1f);
    LowPassFilter underWaterReverbFilter = new LowPassFilter(0.5f, 0.1f);
    LowPassFilter aboveWaterAudioFilter = new LowPassFilter(1, 1);
    private boolean uw = false;
    private float time = 0.0f;
    private float time_game = 250;
    private Node escenaPrincipal;
    private Node enemigoUno;
    private Node enemigoDos;
    private Geometry mark;
    private BulletAppState bulletAppState;
    private RigidBodyControl escenario;
    private RigidBodyControl cuerpoRidigoEnemigoUno;
    private RigidBodyControl cuerpoRigidoEnemigoDos;
    //Atributos del jugador (velocidad, gravedad, Vector dirección)
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, up = false, down = false, run = false, shoot = false;
    private float stepSize = .5f;
    private float walkSpeed = 2.0f;//.5f;
    private int hp = 100;
    private BitmapText hudText;
    //Atributos del arma (Gun - Audio, Modelo)
    private AudioNode audio_gun;
    private Geometry Gun;
    private int framesToApplyForce = 100;
    private int framesForce = 0;
    private boolean FuerzaUno = false;
    private boolean FuerzaDos = false;
    private boolean DEBUG_PHYSICS = false;

    //Objetos tipo enemigo
    private EnemyMov enemies[];
    private Spatial enem;
    private Node shootable;
    private EnemyMov enemy1;
    //Temporary vectors used on each frame.
    //They here to avoid instanciating new vectors on each frame
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();

    public static void main(String[] args) {
        Game app = new Game();
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(30);
        settings.setSettingsDialogImage("Interface/inicio.png");
        app.setSettings(settings);
        app.start();
    }

    protected void initCrossHairs() {
        setDisplayStatView(true);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    protected void initMark() {
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
        rootNode.attachChild(mark);
    }

    private void initAudio() {
        /* gun shot sound is to be triggered by a mouse click. */
        audio_gun = new AudioNode(assetManager, "Sound/Effects/Gun.wav", false);
        audio_gun.setPositional(false);
        audio_gun.setLooping(false);
        audio_gun.setVolume(2);
        rootNode.attachChild(audio_gun);
    }

    private void initKeys() {
        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "Shoot");
    }
    /**
     * Defining the "Shoot" action: Play a gun sound.
     */
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Shoot") && !keyPressed) {
                audio_gun.playInstance(); // play each instance once!
            }
        }
    };

    public void drawText() {

        hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.Blue);                             // font color
        hudText.setText("HEALTH " + hp+ "TIEMPO" +time_game);             // the text
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 1.0f); // position
        //hudText.rotate(0, 50, 0);
        guiNode.attachChild(hudText);

    }

    public void simpleInitApp() {
        cam.setFrustumFar(10000);
        rootNode.attachChild(SkyFactory.createSky(
                assetManager, "Scenes/Beach/FullskiesSunset0068.dds", false));
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        flyCam.setMoveSpeed(100);
        setUpKeys();
        setUpLight();
        /*
         * Añadiendo e inicializando filtro del agua para que aparezca en el juego
         */
        water = new WaterFilter(rootNode, lightDir);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(water);
        water.setWaterHeight(initialWaterHeight);
        water.setWaveScale(
                0.003f);
        water.setMaxAmplitude(
                2f);
        water.setFoamExistence(
                new Vector3f(1f, 4, 0.5f));
        water.setFoamTexture(
                (Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));
        water.setRefractionStrength(
                0.2f);
        uw = cam.getLocation().y < waterHeight;
        waves = new AudioNode(assetManager, "Sound/Environment/Ocean Waves.ogg", false);

        waves.setLooping(
                true);
        waves.setReverbEnabled(
                true);
        if (uw) {
            waves.setDryFilter(new LowPassFilter(0.5f, 0.1f));
        } else {
            waves.setDryFilter(aboveWaterAudioFilter);
        }
        audioRenderer.playSource(waves);
        viewPort.addProcessor(fpp);

        /*
         * Carga del escenario principal del juego "loadModel"
         */
        escenaPrincipal = (Node) assetManager.loadModel("Models/Escenario.j3o");
        escenaPrincipal.setLocalScale(
                50.0f);
        rootNode.attachChild(escenaPrincipal);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) escenaPrincipal);
        escenario = new RigidBodyControl(sceneShape, 0);
        escenaPrincipal.addControl(escenario);

        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(7f, 20f, 1);
        player = new CharacterControl(capsuleShape, stepSize);
        player.setJumpSpeed(30);
        player.setFallSpeed(100000);
        player.setGravity(30);
        player.setPhysicsLocation(new Vector3f(-45, 227, 421));

        Gun = (Geometry) assetManager.loadModel("Models/MP5K.j3o");
        Material textGun = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Models/guntex2.jpg");
        textGun.setTexture("ColorMap", tex);
        Gun.setMaterial(textGun);
        Gun.scale(.06f, .06f, .06f);
        rootNode.attachChild(Gun);

        enemigoDos = (Node) assetManager.loadModel("Models/Enemigo.j3o");
        enemigoDos.setName("EnemigoDos");
        enemigoDos.setLocalScale(5.0f);
        rootNode.attachChild(enemigoDos);
        CollisionShape shapeObjeto =
                CollisionShapeFactory.createDynamicMeshShape((Node) enemigoDos);
        cuerpoRigidoEnemigoDos = new RigidBodyControl(shapeObjeto, 0.02f);
        enemigoDos.addControl(cuerpoRigidoEnemigoDos);
        cuerpoRigidoEnemigoDos.setPhysicsLocation(
                new Vector3f(-45, 227, 100));
       Quaternion pitch90 = new Quaternion();
        pitch90.fromAngleAxis(FastMath.PI/2, new Vector3f(0,227,0));
        cuerpoRigidoEnemigoDos.setPhysicsRotation(pitch90);
        bulletAppState.getPhysicsSpace()
                .add(cuerpoRigidoEnemigoDos);


        enemigoUno = (Node) assetManager.loadModel("Models/Enemigo.j3o");
        enemigoUno.setName("EnemigoUno");
        enemigoUno.setLocalScale(5.0f);
        rootNode.attachChild(enemigoUno);
        CollisionShape shapeObjeto1 =
                CollisionShapeFactory.createDynamicMeshShape((Node) enemigoUno);
        cuerpoRidigoEnemigoUno = new RigidBodyControl(shapeObjeto1, 0.05f);
        enemigoUno.addControl(cuerpoRidigoEnemigoUno);
        cuerpoRidigoEnemigoUno.setPhysicsLocation(
                new Vector3f(-45, 227, 400));
        bulletAppState.getPhysicsSpace()
                .add(player);
        bulletAppState.getPhysicsSpace()
                .add(escenario);
        bulletAppState.getPhysicsSpace()
                .add(cuerpoRidigoEnemigoUno);
        bulletAppState.setDebugEnabled(DEBUG_PHYSICS);
        initCrossHairs();
        initMark();
        createEnemy();
        drawText();
        initAudio();
        initKeys();

    }

    @Override
    public void simpleUpdate(float tpf) {
        time += tpf;
        time_game -=tpf;
        waterHeight = (float) Math.cos(((time * 0.6f) % FastMath.TWO_PI)) * 1.5f;
        water.setWaterHeight(initialWaterHeight + waterHeight);
        if (water.isUnderWater() && !uw) {

            waves.setDryFilter(new LowPassFilter(0.5f, 0.1f));
            uw = true;
        }
        if (!water.isUnderWater() && uw) {
            uw = false;
            waves.setDryFilter(new LowPassFilter(1, 1f));
        }

        camDir.set(cam.getDirection()).multLocal(walkSpeed);

        camDir.setY(0);

        camLeft.set(cam.getLeft()).multLocal(walkSpeed);
        walkDirection.set(0, 0, 0);
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            if (run) {
                walkDirection.addLocal(camDir.mult(4));
            } else {
                walkDirection.addLocal(camDir);
            }
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        player.setWalkDirection(walkDirection);
        cam.setLocation(player.getPhysicsLocation());

        Vector3f vectorDifference = new Vector3f(cam.getLocation().subtract(Gun.getWorldTranslation()));
        Gun.setLocalTranslation(vectorDifference.addLocal(Gun.getLocalTranslation()));

        Quaternion worldDiff = new Quaternion(cam.getRotation().mult(Gun.getWorldRotation().inverse()));
        Gun.setLocalRotation(worldDiff.multLocal(Gun.getLocalRotation()));

        // Move it to the bottom right of the screen
        Gun.move(cam.getDirection().mult(3));
        Gun.move(cam.getUp().mult(-2.0f));
        Gun.move(cam.getLeft().mult(-0.6f));
        Gun.rotate(0.05f, FastMath.PI, 0);
        if (shoot) {
            procesarDisparo();
        }
        System.out.println("Location=" + player.getPhysicsLocation());
        if (FuerzaUno) {
            framesForce++;
            if (framesForce >= framesToApplyForce) {
                cuerpoRidigoEnemigoUno.clearForces();
                framesForce = 0;
                FuerzaUno = false;

            } else {
                if (FuerzaDos) {
                    framesForce++;
                    if (framesForce >= framesToApplyForce) {
                        cuerpoRigidoEnemigoDos.clearForces();
                        framesForce = 0;
                        FuerzaDos = false;
                    }
                }
            }
        }
        
        //Me persigue el enemigo

//        enemy1.setMoving(player.getPhysicsLocation());
//         if (enemy1.isAttacking()) {
//            guiNode.attachChild(geom);
//        }
//        if (guiNode.hasChild(geom) && hp > 0) {
//            --hp;
//        }
//        hudText.setText("HEALTH " + hp + "      Mean Zergs left: " + mobNum);             // the text
//        if (hp == 0) {
//            dead = true;
//        }
//        if (dead) {
//            walkDirection = new Vector3f(0, 0, 0);
//        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void setUpLight() {
        // We add light so we see the scene
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(al);

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        rootNode.addLight(dl);
    }

    /**
     * We over-write some navigational key mappings here, so we can add
     * physics-controlled walking and jumping:
     */
    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Run", new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
        inputManager.addListener(this, "Run");

        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "Shoot");
    }

    public void procesarDisparo() {
        // 1. Reset results list.
        CollisionResults results = new CollisionResults();
        // 2. Aim the ray from cam loc to cam direction.
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        // 3. Collect intersections between Ray and Shootables in results list.
        rootNode.collideWith(ray, results);
        // 4. Print the results
        System.out.println("----- Collisions? " + results.size() + "-----");
        for (int i = 0; i < results.size(); i++) {
            // For each hit, we know distance, impact point, name of geometry.
            float dist = results.getCollision(i).getDistance();
            System.out.println("Distance=" + dist);
            Vector3f pt = results.getCollision(i).getContactPoint();
            String hit = results.getCollision(i).getGeometry().getName();
            Node nodoPadre = results.getCollision(i).getGeometry().getParent();
            if (hit.contains("Enemigo-geom")) {
                if ("EnemigoUno".equals(nodoPadre.getName())) {
                    cuerpoRidigoEnemigoUno.applyForce(camDir.mult(1), pt);
                    FuerzaUno = true;
                }
                if ("EnemigoDos".equals(nodoPadre.getName())) {
                    cuerpoRigidoEnemigoDos.applyForce(camDir.mult(1), pt);
                    FuerzaDos = true;
                }
                mark.setLocalTranslation(pt);

            }
        }
    }

    /**
     * These are our custom actions triggered by key presses. We do not walk
     * yet, we just keep track of the direction the user pressed.
     */
    public void onAction(String binding, boolean isPressed, float tpf) {

        if (binding.equals("Shoot")) {
            shoot = isPressed;
        } else if (binding.equals("Left")) {
            left = isPressed;
        } else if (binding.equals("Right")) {
            right = isPressed;
        } else if (binding.equals("Up")) {
            up = isPressed;
        } else if (binding.equals("Down")) {
            down = isPressed;
        } else if (binding.equals("Run")) {
            run = isPressed;
        } else if (binding.equals("Jump")) {
            if (isPressed) {
                player.jump();
            }
        }
    }

    private void createEnemy() {
        enem = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        enem.scale(0.08f, 0.08f, 0.08f);
        shootable = new Node();


        enemy1 = new EnemyMov();
        enemy1.createMob(-40, 280, 521, enem, "enemy1");

        enemy1.attachTo(shootable);
        bulletAppState.getPhysicsSpace().add(enemy1.getCharacterControl());


        enemies = new EnemyMov[20];
        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new EnemyMov();
            enemies[i].createMob(-40, 280, 421, enem, "enemy" + i);
            enemies[i].attachTo(shootable);
            bulletAppState.getPhysicsSpace().add(enemies[i].getCharacterControl());
        }


        rootNode.attachChild(shootable);
    }
}