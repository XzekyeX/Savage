package net.teamfps.savage.obj;

import net.teamfps.java.serialization.math.Vec2f;
import net.teamfps.java.serialization.math.Vec3f;

public class FaceVertex {
	protected int index = -1;
	protected Vec3f v = null; // vertex
	protected Vec2f t = null; // texture
	protected Vec3f n = null; // normal

	public FaceVertex() {

	}

	/**
	 * 
	 * @param vertex
	 *            x
	 * @param vertex
	 *            y
	 * @param vertex
	 *            z
	 * @param texture
	 *            u
	 * @param texture
	 *            v
	 * @param normal
	 *            x
	 * @param normal
	 *            y
	 * @param normal
	 *            z
	 */
	public FaceVertex(float vx, float vy, float vz, float tx, float ty, float nx, float ny, float nz, int index) {
		this.v = new Vec3f(vx, vy, vz);
		this.t = new Vec2f(tx, ty);
		this.n = new Vec3f(nx, ny, nz);
		this.index = index;
	}
}
