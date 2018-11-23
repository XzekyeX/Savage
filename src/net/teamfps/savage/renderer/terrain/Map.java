package net.teamfps.savage.renderer.terrain;

import java.util.ArrayList;
import java.util.List;

import net.teamfps.java.serialization.math.Vec3f;
import net.teamfps.savage.StateManager;
import net.teamfps.savage.renderer.Mesh;

/**
 * 
 * @author Zekye
 *
 */
public class Map {
	protected Mesh mesh;

	public void render() {
		if (mesh != null) mesh.render();
	}

	public float getHeight(int x, int y) {
		return 0;
	}

	protected float[] calcNormals(float[] v, int w, int h) {
		List<Float> normals = new ArrayList<Float>();
		Vec3f v0 = new Vec3f(0, 0, 0);
		Vec3f v1 = new Vec3f(0, 0, 0);
		Vec3f v2 = new Vec3f(0, 0, 0);
		Vec3f v3 = new Vec3f(0, 0, 0);
		Vec3f v4 = new Vec3f(0, 0, 0);
		Vec3f v12 = new Vec3f(0, 0, 0);
		Vec3f v23 = new Vec3f(0, 0, 0);
		Vec3f v34 = new Vec3f(0, 0, 0);
		Vec3f v41 = new Vec3f(0, 0, 0);
		Vec3f normal = new Vec3f(0, 0, 0);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (x > 0 && x < w - 1 && y > 0 && y < h - 1) {
					int i0 = (x * 3) + y * w * 3;
					v0.set(v[i0], v[i0 + 1], v[i0 + 2]);

					int i1 = ((x - 1) * 3) + y * w * 3;
					v1.set(v[i1], v[i1 + 1], v[i1 + 2]);
					v1 = v1.sub(v0);

					int i2 = (x * 3) + (y + 1) * w * 3;
					v2.set(v[i2], v[i2 + 1], v[i2 + 2]);
					v2 = v2.sub(v0);

					int i3 = ((x + 1) * 3) + (y) * w * 3;
					v3.set(v[i3], v[i3 + 1], v[i3 + 2]);
					v3 = v3.sub(v0);

					int i4 = (x * 3) + (y - 1) * w * 3;
					v4.set(v[i4], v[i4 + 1], v[i4 + 2]);
					v4 = v4.sub(v0);

					v1.cross(v2, v12);
					v12.normalize();

					v2.cross(v3, v23);
					v23.normalize();

					v4.cross(v1, v41);
					v41.normalize();

					normal = v12.add(v23).add(v34).add(v41);
					normal.normalize();
				} else {
					normal.set(0, 1, 0);
				}
				normal.normalize();
				normals.add(normal.getX());
				normals.add(normal.getY());
				normals.add(normal.getZ());
			}
		}
		return StateManager.toFloats(normals);
	}

	
	public Mesh getMesh() {
		return mesh;
	}
}
