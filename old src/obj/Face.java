package net.teamfps.savage.obj;

import java.util.ArrayList;
import java.util.List;

import net.teamfps.java.serialization.math.Vec3f;

public class Face {
	protected List<FaceVertex> vertices = new ArrayList<FaceVertex>();
	protected Vec3f faceNormal = new Vec3f(0, 0, 0);
	protected Material material = null;
	protected Material map = null;

	public void add(FaceVertex vertex) {
		this.vertices.add(vertex);
	}

	public void calcTriangleNormal() {
		float[] edge1 = new float[3];
		float[] edge2 = new float[3];
		float[] normal = new float[3];
		Vec3f v1 = vertices.get(0).v;
		Vec3f v2 = vertices.get(1).v;
		Vec3f v3 = vertices.get(2).v;
		float[] p1 = { v1.getX(), v1.getY(), v1.getZ() };
		float[] p2 = { v2.getX(), v2.getY(), v2.getZ() };
		float[] p3 = { v3.getX(), v3.getY(), v3.getZ() };

		edge1[0] = p2[0] - p1[0];
		edge1[1] = p2[1] - p1[1];
		edge1[2] = p2[2] - p1[2];

		edge2[0] = p3[0] - p2[0];
		edge2[1] = p3[1] - p2[1];
		edge2[2] = p3[2] - p2[2];

		normal[0] = edge1[1] * edge2[2] - edge1[2] * edge2[1];
		normal[1] = edge1[2] * edge2[0] - edge1[0] * edge2[2];
		normal[2] = edge1[0] * edge2[1] - edge1[1] * edge2[0];

		faceNormal.set(normal[0], normal[1], normal[2]);

	}
}
