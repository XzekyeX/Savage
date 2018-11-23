package net.teamfps.savage;

import net.teamfps.java.serialization.math.Mat4;
import net.teamfps.java.serialization.math.Vec3f;

/**
 * 
 * @author Zekye
 *
 */
public class Camera {

	private Vec3f position = new Vec3f(0, 0, 0);
	private Vec3f rotation = new Vec3f(0, 0, 0);

	// public Mat4 view_matrix = new Mat4();

	private float dix = 0; // display X
	private float diy = 0; // display Y
	private float px = 0; // previous X
	private float py = 0; // previous Y

	public Camera() {
		// this.view_matrix = view_matrix;
	}

	public void rot() {
		dix = 0;
		diy = 0;
		if (px > 0 && py > 0 && Savage.MOUSE_ENTERED) {
			float dx = Savage.MOUSE_X - px;
			float dy = Savage.MOUSE_Y - py;
			boolean rotX = dx != 0;
			boolean rotY = dy != 0;
			if (rotX) diy = dx;
			if (rotY) dix = dy;
		}
		px = Savage.MOUSE_X;
		py = Savage.MOUSE_Y;
		float SENSITIVITY = 1f;
		if (Savage.isButtonDown(0)) {
			rot(dix * SENSITIVITY, diy * SENSITIVITY, 0);
		}
	}

	public void move() {
		float xa = 0;
		float ya = 0;
		float za = 0;
		float speed = 0.2f;
		// float sprint = 0.12f;
		// float rotate = 0.5f;
		if (Savage.isKeyDown(Savage.KEY_W)) {
			// float xa = (float) Math.sin(Math.toRadians(rotZ)) * sprint;
			// float za = (float) Math.cos(Math.toRadians(rotZ)) * sprint;
			// translate(xa, 0.0f, za);
			za = -speed;
		}
		if (Savage.isKeyDown(Savage.KEY_S)) {
			// float xa = (float) (-Math.sin(Math.toRadians(rotZ))) * sprint;
			// float za = (float) (-Math.cos(Math.toRadians(rotZ))) * sprint;
			// translate(xa, 0.0f, za);
			za = speed;
		}
		if (Savage.isKeyDown(Savage.KEY_A)) {
			// float xa = (float) Math.sin(Math.toRadians(rotZ + 90.0f)) * sprint;
			// float za = (float) Math.cos(Math.toRadians(rotZ + 90.0f)) * sprint;
			// translate(xa, 0.0f, za);
			xa = -speed;
		}
		if (Savage.isKeyDown(Savage.KEY_D)) {
			// float xa = (float) Math.sin(Math.toRadians(rotZ - 90.0f)) * sprint;
			// float za = (float) Math.cos(Math.toRadians(rotZ - 90.0f)) * sprint;
			// translate(xa, 0.0f, za);
			xa = speed;
		}
		if (Savage.isKeyDown(Savage.KEY_SPACE)) {
			// translate(0.0f, sprint, 0.0f);
			ya = speed;
		}
		if (Savage.isKeyDown(Savage.KEY_LEFT_SHIFT)) {
			// translate(0.0f, -sprint, 0.0f);
			ya = -speed;
		}
		// if (Savage.isKeyDown(Savage.KEY_E)) rotZ += rotate;
		// if (Savage.isKeyDown(Savage.KEY_Q)) rotZ -= rotate;
		// if (rotZ >= 360) rotZ = 0;
		// if (rotZ < 0) rotZ = 360;
		move(xa, ya, za);
	}

	public void move(float offsetX, float offsetY, float offsetZ) {
		if (offsetZ != 0) {
			position.addX((float) Math.sin(Math.toRadians(rotation.getY())) * -1.0f * offsetZ);
			position.addZ((float) Math.cos(Math.toRadians(rotation.getY())) * offsetZ);
		}
		if (offsetX != 0) {
			position.addX((float) Math.sin(Math.toRadians(rotation.getY() - 90)) * -1.0f * offsetX);
			position.addZ((float) Math.cos(Math.toRadians(rotation.getY() - 90)) * offsetX);
		}
		position.addY(offsetY);
	}

	public void rot(float offsetX, float offsetY, float offsetZ) {
		rotation.addX(offsetX);
		rotation.addY(offsetY);
		rotation.addZ(offsetZ);
	}

	public Mat4 getViewMatrix() {
		Mat4 mat = Mat4.identity();
		mat.rotate((float) Math.toRadians(rotation.getX()), 1, 0, 0).rotate((float) Math.toRadians(rotation.getY()), 0, 1, 0);
		mat.translate(-position.getX(), -position.getY(), -position.getZ());
		return mat;
	}

	public Vec3f getPosition() {
		return position;
	}

	public Vec3f getRotation() {
		return rotation;
	}

}
