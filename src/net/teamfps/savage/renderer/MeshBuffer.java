package net.teamfps.savage.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.teamfps.java.serialization.math.Vec2f;
import net.teamfps.java.serialization.math.Vec3f;
import net.teamfps.savage.StateManager;
import net.teamfps.savage.renderer.model.Face;
import net.teamfps.savage.renderer.model.Index;

/**
 * 
 * @author Zekye
 *
 */
public class MeshBuffer {
	protected float[] vertices;
	protected float[] textures;
	protected float[] normals;
	protected float[] weights;
	protected int[] indices;
	protected int[] joints;

	public MeshBuffer() {

	}

	public MeshBuffer(float[] vertices, float[] textures, float[] normals, int[] indices) {
		this.vertices = vertices;
		this.textures = textures;
		this.normals = normals;
		this.indices = indices;
	}

	public FloatBuffer getVertexBuffer() {
		return vertices != null ? StateManager.createFloatBuffer(vertices) : null;
	}

	public FloatBuffer getTextureBuffer() {
		return textures != null ? StateManager.createFloatBuffer(textures) : null;
	}

	public FloatBuffer getNormalBuffer() {
		return normals != null ? StateManager.createFloatBuffer(normals) : null;
	}

	public FloatBuffer getWeightBuffer() {
		return weights != null ? StateManager.createFloatBuffer(weights) : null;
	}

	public IntBuffer getIndexBuffer() {
		return indices != null ? StateManager.createIntBuffer(indices) : null;
	}

	public IntBuffer getJointIndexBuffer() {
		return joints != null ? StateManager.createIntBuffer(joints) : null;
	}

	public int getCount() {
		return indices != null ? indices.length : 0;
	}

	@Override
	public String toString() {
		return ("FaceBuffer has " + vertices.length + " vertices, " + textures.length + " texture coordinates, " + normals.length + " normals and " + indices.length + " indices.");
	}

	public static MeshBuffer reorder(List<Vec3f> vertices, List<Vec2f> textures, List<Vec3f> normals, List<Face> faces) {
		MeshBuffer result = new MeshBuffer();

		List<Integer> indices = new ArrayList<Integer>();

		result.vertices = new float[vertices.size() * 3];
		result.textures = new float[vertices.size() * 2];
		result.normals = new float[vertices.size() * 3];

		int i = 0;
		for (Vec3f vec : vertices) {
			result.vertices[i * 3] = vec.getX();
			result.vertices[i * 3 + 1] = vec.getY();
			result.vertices[i * 3 + 2] = vec.getZ();
			i++;
		}

		for (Face f : faces) {
			Index[] fi = f.getIndices();
			for (Index v : fi) {
				int index = v.getVertex() - 1;
				indices.add(index);
				if (v.getTexture() >= 0) {
					Vec2f t = textures.get(v.getTexture() - 1);
					result.textures[index * 2] = t.getX();
					result.textures[index * 2 + 1] = 1 - t.getY();
				}
				if (v.getNormal() >= 0) {
					Vec3f n = normals.get(v.getNormal() - 1);
					result.normals[index * 3] = n.getX();
					result.normals[index * 3 + 1] = n.getY();
					result.normals[index * 3 + 2] = n.getZ();
				}
			}
		}
		result.indices = indices.stream().mapToInt((Integer v) -> v).toArray();
		return result;
	}

	public float[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public float[] getNormals() {
		return normals;
	}

	public float[] getTextures() {
		return textures;
	}

}
