package edu.uao.compu2.mars.objects;

import com.jme3.input.InputManager;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/*
 * @author Juan pablo aguirre
 */
public class MyCamera implements AnalogListener {

    private Camera cam;
    private Vector3f initialUpVec;
    private float rotationSpeed = 1f;

    public MyCamera(Camera cam) {
        this.cam = cam;
        initialUpVec = cam.getUp().clone();
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void registerWithInput(InputManager inputManager) {

        String[] mappings = new String[]{
            "MYCAM_Left",
            "MYCAM_Right",
            "MYCAM_Up",
            "MYCAM_Down",};

        // both mouse and button - rotation of cam
        inputManager.addMapping("MYCAM_Left", new MouseAxisTrigger(0, true));

        inputManager.addMapping("MYCAM_Right", new MouseAxisTrigger(0, false));

        inputManager.addMapping("MYCAM_Up", new MouseAxisTrigger(1, false));

        inputManager.addMapping("MYCAM_Down", new MouseAxisTrigger(1, true));

        inputManager.addListener(this, mappings);
        inputManager.setCursorVisible(false);
    }

    private void rotateCamera(float value, Vector3f axis) {

        Matrix3f mat = new Matrix3f();
        mat.fromAngleNormalAxis(rotationSpeed * value, axis);

        Vector3f up = cam.getUp();
        Vector3f left = cam.getLeft();
        Vector3f dir = cam.getDirection();

        mat.mult(up, up);
        mat.mult(left, left);
        mat.mult(dir, dir);

        Quaternion q = new Quaternion();
        q.fromAxes(left, up, dir);
        q.normalizeLocal();

        float[] angles = new float[3];

        q.toAngles(angles);

        if ((angles[0] > -90 * FastMath.DEG_TO_RAD) && (angles[0] < 65 * FastMath.DEG_TO_RAD)) {

            cam.setAxes(q);
        }

    }

    @Override
    public void onAnalog(String name, float value, float tpf) {

        if (name.equals("MYCAM_Left")) {
            rotateCamera(value, initialUpVec);
        } else if (name.equals("MYCAM_Right")) {
            rotateCamera(-value, initialUpVec);
        } else if (name.equals("MYCAM_Up")) {
            rotateCamera(-value, cam.getLeft());
        } else if (name.equals("MYCAM_Down")) {
            rotateCamera(value, cam.getLeft());

        }
    }

}
