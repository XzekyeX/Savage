package net.teamfps.savage;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * 
 * @author Zekye
 *
 */
public class Camera {
	private FreeCamera cam = new FreeCamera();
	private Vector3f pos = new Vector3f();
	private Matrix4f matrix = new Matrix4f();

	public Camera(float x, float y, float z) {
		cam.position.set(x, y, z);
	}

	public void update() {
		float accFactor = 0.2f;
		float rotateZ = 0.0f;
		cam.linearAcc.zero();
		if (Savage.isKeyDown(Savage.KEY_W)) cam.linearAcc.fma(accFactor, cam.forward(pos));
		if (Savage.isKeyDown(Savage.KEY_S)) cam.linearAcc.fma(-accFactor, cam.forward(pos));
		if (Savage.isKeyDown(Savage.KEY_D)) cam.linearAcc.fma(accFactor, cam.right(pos));
		if (Savage.isKeyDown(Savage.KEY_A)) cam.linearAcc.fma(-accFactor, cam.right(pos));
		if (Savage.isKeyDown(Savage.KEY_E)) rotateZ += 0.1f;
		if (Savage.isKeyDown(Savage.KEY_Q)) rotateZ -= 0.1f;
		if (Savage.isKeyDown(Savage.KEY_SPACE)) cam.linearAcc.fma(accFactor, cam.up(pos));
		if (Savage.isKeyDown(Savage.KEY_LEFT_SHIFT)) cam.linearAcc.fma(-accFactor, cam.up(pos));
		if (Savage.isKeyDown(Savage.KEY_LEFT_CONTROL)) cam.linearVel.zero();
		float rotateY = Savage.getMouseY();
//		System.out.println("cam.rotation: " + cam.rotation);
		cam.angularVel.y = rotateZ;
		cam.update(0.1f);
		cam.apply(matrix.identity());
	}

	public Matrix4f getMatrix() {
		return matrix;
	}
}