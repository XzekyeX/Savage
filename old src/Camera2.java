package net.teamfps.sandbox;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import net.teamfps.savage.Savage;

/**
 * 
 * @author Zekye
 *
 */
public class Camera {

	public Vector3f position = new Vector3f(0, 0, 0);
	public Vector3f rotation = new Vector3f(0, 0, 0);
	public Matrix4f vw_matrix = new Matrix4f();

	public Camera() {

	}

	public Camera(float x, float y, float z) {
		this.position.set(x, y, z);
	}

	private float dix, diy, px, py;

	public void rot() {
		dix = 0;
		diy = 0;
		if (px > 0 && py > 0 && Savage.MOUSE_ENTERED) {
			float dx = Savage.MOUSE_X - px;
			float dy = Savage.MOUSE_Y - py;
			boolean rotX = dx != 0;
			boolean rotY = dy != 0;
			if (rotX) diy = -dx;
			if (rotY) dix = dy;
		}
		px = Savage.MOUSE_X;
		py = Savage.MOUSE_Y;
		float SENSITIVITY = 0.125f;
		if (Savage.isButtonDown(0)) {
			// (dix * SENSITIVITY, diy * SENSITIVITY, 0);
			// rotation.addX(dix * SENSITIVITY);
			rotation.y += (diy * SENSITIVITY);
			rotation.x += dix * SENSITIVITY;
		}
		// float max = 150.0f;
		// float min = -36.0f;
		// if (position.getY() < min) position.setY(min);
		// if (position.getY() > max) position.setY(max);
	}

	public void move(float speed, float rotate) {
		if (Savage.isKeyDown(Savage.KEY_W)) {
			float xa = (float) Math.sin(Math.toRadians(-rotation.x())) * speed;
			float za = (float) Math.cos(Math.toRadians(-rotation.x())) * speed;
			translate(xa, 0.0f, za);
		}
		if (Savage.isKeyDown(Savage.KEY_S)) {
			float xa = (float) (-Math.sin(Math.toRadians(-rotation.x()))) * speed;
			float za = (float) (-Math.cos(Math.toRadians(-rotation.x()))) * speed;
			translate(xa, 0.0f, za);
		}
		if (Savage.isKeyDown(Savage.KEY_A)) {
			float xa = (float) Math.sin(Math.toRadians(-rotation.x() + 90.0f)) * speed;
			float za = (float) Math.cos(Math.toRadians(-rotation.x() + 90.0f)) * speed;
			translate(xa, 0.0f, za);
		}
		if (Savage.isKeyDown(Savage.KEY_D)) {
			float xa = (float) Math.sin(Math.toRadians(-rotation.x() - 90.0f)) * speed;
			float za = (float) Math.cos(Math.toRadians(-rotation.x() - 90.0f)) * speed;
			translate(xa, 0.0f, za);
		}
		if (Savage.isKeyDown(Savage.KEY_SPACE)) {
			translate(0.0f, -speed, 0.0f);
		}
		if (Savage.isKeyDown(Savage.KEY_LEFT_SHIFT)) {
			translate(0.0f, speed, 0.0f);
		}
		if (Savage.isKeyDown(Savage.KEY_E)) rotation.y += (rotate);
		if (Savage.isKeyDown(Savage.KEY_Q)) rotation.y -= (rotate);
		vw_matrix = new Matrix4f().translate(position).rotateX((float) Math.toRadians(rotation.x)).rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z));// .scale(scale);

	}

	private Vector3f negativePos() {
		return new Vector3f(-position.x, -position.y, -position.z);
	}

	private float getRotX() {
		return (float) Math.toRadians(rotation.x);
	}

	private float getRotY() {
		return (float) Math.toRadians(rotation.y);
	}

	private float getRotZ() {
		return (float) Math.toRadians(rotation.z);
	}

	public void translate(float xa, float ya, float za) {
		this.position.add(xa, ya, za);
//		System.out.println("position(" + position.x + ", " + position.y + ", " + position.z + ")");
	}

	public Matrix4f getViewMatrix() {
		return vw_matrix;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

	// public Matrix4f getWorldMatrix(Matrix4f worldMatrix, Vector3f offset, Vector3f rotation, float scale) {
	// worldMatrix.identity().translate(offset).rotateX((float) Math.toRadians(rotation.x)).rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z)).scale(scale);
	// return worldMatrix;
	// }

}
