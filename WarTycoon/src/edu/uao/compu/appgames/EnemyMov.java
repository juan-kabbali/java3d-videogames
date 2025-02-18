/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uao.compu.appgames;

/**
 *
 * @author Usuario
 */
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 *
 * @author Usuario
 */
public class EnemyMov implements AnimEventListener {

    private Node enemy, node;
    private AnimChannel channel;
    private AnimControl control;
    private CharacterControl enemyControl;
    private Vector3f dir, temp;
    private static final int RECORRER = 0;
    private static final int PERSEGUIR = 1;
    private static final int ATTACK = 2;
     private String attackAnim = "Attack";
    private static final int DEAD = 3;
    public int mode = 0;
    private CheckList<Vector3f> checkPoints = new CheckList<Vector3f>();
    private Vector3f targetPoint;
    private int hp = 5;
    private Random random = new Random();
    //private int x, z;
    // private Vector3f target;

    public void createMob(float x, float y, float z, Spatial enem, String name) {
        this.enemy = (Node) enem.clone();
        enemy.getChild(0).setName(name);
        enemy.setLocalTranslation(x, y, z);
        control = enemy.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        System.out.println(channel.getAnimationName());
        channel.setLoopMode(LoopMode.Loop);
        channel.setSpeed(4f);

        //.. and add physics
        CapsuleCollisionShape monsterShape = new CapsuleCollisionShape(0.1f, 1f);
        //SphereCollisionShape monsterShape = new SphereCollisionShape(1.0f);
        enemyControl = new CharacterControl(monsterShape, 1f);

        node = new Node();
        node.setLocalTranslation(x, y, z);
        node.attachChild(enemy);
        node.addControl(enemyControl);
        enemy.setLocalTranslation(0, 7, 0);
        //monsterC.setJumpSpeed(0);
        // monsterC.setFallSpeed(20);
        // monsterC.setGravity(30);

        for (int i = 0; i < 5; i++) {
            x = random.nextInt(500);
            if (x > 250) {
                x -= 500;
            }
            z = random.nextInt(500);
            if (z > 250) {
                z -= 500;
            }
            //System.out.println("  bla " + monster.getName()  + "  asda " + x + " " + z);
            checkPoints.add(new Vector3f(x, 0, z));
        }
        //checkPoints.add(new Vector3f(250, 0, 0));
        //checkPoints.add(new Vector3f(20, 0, 250));

    }

    public Vector3f calcDir(Vector3f target) {
        temp = new Vector3f(target.x - enemyControl.getPhysicsLocation().x, 0, target.z - enemyControl.getPhysicsLocation().z);
        //this.target = target;
        return temp;
    }

    public boolean isAttacking() {
        return channel.getAnimationName().equals("Attack");
    }

    public void getHitted() {
        if (hp > 0) {
            channel.setAnim("hitted");
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(5);

            --hp;
            if (hp == 0) {

                channel.setAnim("dead");
                channel.setLoopMode(LoopMode.DontLoop);
                channel.setSpeed(5);
            }
        }
    }

    public int getHP() {
        return hp;
    }

    public boolean isDead() {
        return mode == DEAD;
    }

    public void setMoving(Vector3f target) {

        if (mode == PERSEGUIR) {
            //diretion mob to target
            dir = calcDir(target);

            calculateDist(target);
            setDirection(new Vector3f(0, 0, 0));

            enemyControl.setViewDirection(dir);

            if (calculate3DDist(target) >= 15) {
                setDirection(dir.normalize().divide(2));
            }

        } else if (mode == RECORRER) {
            targetPoint = checkPoints.select();
            //diretion mob to target
            dir = calcDir(targetPoint);

            calculateDist(targetPoint);
            setDirection(new Vector3f(0, 0, 0));

            enemyControl.setViewDirection(dir);

            if (calculateDist(targetPoint) >= 10) {
                setDirection(dir.normalize().divide(2));
            } else {
                checkPoints.next();
            }

        } else if (mode == ATTACK) {
        } else if (mode == DEAD) {
            setDirection(new Vector3f(0, 0, 0));
        }
        if (hp == 0) {
            mode = DEAD;
        } else if (calculate3DDist(target) < 80 && calculate3DDist(target) > 15) {
            mode = PERSEGUIR;
        } else if (calculate3DDist(target) < 15) {
            mode = ATTACK;
        } else {
            mode = RECORRER;
        }


    }

    public void attachTo(Node nnode) {
        nnode.attachChild(this.node);

    }

    public void setMode(int newMode) {
        if (newMode <= 2) {
            this.mode = newMode;
        }
    }

    public void setDirection(Vector3f dir) {
        enemyControl.setWalkDirection(dir);
    }

    public CharacterControl getCharacterControl() {
        return enemyControl;
    }

    public double calculateDist(Vector3f p) {
        double a = enemyControl.getPhysicsLocation().x - p.x;
        double b = enemyControl.getPhysicsLocation().z - p.z;
        double dist = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        return dist;


    }

    public double calculate3DDist(Vector3f p) {
        double a = enemyControl.getPhysicsLocation().x - p.x;
        double b = enemyControl.getPhysicsLocation().z - p.z;
        double c = enemyControl.getPhysicsLocation().y - p.y;
        double dist2 = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2));
        return dist2;


    }

    public void setCoords(float x, float y, float z) {
        enemy.setLocalTranslation(x, y, z);
    }

    public void setRot(float x, float y, float z) {
        enemy.rotate(x, y, z);
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (mode != DEAD) {
            if (mode != ATTACK) {
                channel.setAnim("run");
                channel.setLoopMode(LoopMode.Loop);
                channel.setSpeed(4f);
            } else {
                channel.setAnim(attackAnim);
                channel.setLoopMode(LoopMode.Loop);
                channel.setSpeed(2f);
            }
        }
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }
     }