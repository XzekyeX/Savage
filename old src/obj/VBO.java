package net.teamfps.savage.obj;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class VBO {
	// sizeof float/sizeof int
	public final static int FL_SIZE = 4;
	public final static int INDICE_SIZE_BYTES = 4;
	// Vertex Attribute Data - i.e. x,y,z then normalx, normaly, normalz, then texture u,v - so 8 floats.
	public final static int ATTR_V_FLOATS_PER = 3;
	public final static int ATTR_N_FLOATS_PER = 3;
	public final static int ATTR_T_FLOATS_PER = 2;
	public final static int ATTR_SZ_FLOATS = ATTR_V_FLOATS_PER + ATTR_N_FLOATS_PER + ATTR_T_FLOATS_PER;
	public final static int ATTR_SZ_BYTES = ATTR_SZ_FLOATS * FL_SIZE;
	public final static int ATTR_V_OFFSET_BYTES = 0;
	public final static int ATTR_V_OFFSET_FLOATS = 0;
	public final static int ATTR_N_OFFSET_FLOATS = ATTR_V_FLOATS_PER;
	public final static int ATTR_N_OFFSET_BYTES = ATTR_N_OFFSET_FLOATS * FL_SIZE;

	public final static int ATTR_T_OFFSET_FLOATS = ATTR_V_FLOATS_PER + ATTR_N_FLOATS_PER;
	public final static int ATTR_T_OFFSET_BYTES = ATTR_T_OFFSET_FLOATS * FL_SIZE;
	public final static int ATTR_V_STRIDE2_BYTES = ATTR_SZ_FLOATS * FL_SIZE;
	public final static int ATTR_N_STRIDE2_BYTES = ATTR_SZ_FLOATS * FL_SIZE;
	public final static int ATTR_T_STRIDE2_BYTES = ATTR_SZ_FLOATS * FL_SIZE;

	public List<FloatBuffer> vBuffers = new ArrayList<FloatBuffer>();
	public List<IntBuffer> iBuffers = new ArrayList<IntBuffer>();
	private Build build = new Build();
	private String file;
	private int vID, iID, iCount;

	public VBO(String file) {
		this.file = file;
		init();
	}

	private void init() {
		try {
			new Parse(build, file);
			ArrayList<ArrayList<Face>> facesByTextureList = createFaceListsByMaterial(build);
			for (ArrayList<Face> faceList : facesByTextureList) {
				if (faceList.isEmpty()) {
					continue;
				}
				ArrayList<Face> triangles = splitQuads(faceList);
				calcMissingVertexNormals(triangles);
				if (triangles.size() <= 0) {
					continue;
				}

				HashMap<FaceVertex, Integer> indexMap = new HashMap<FaceVertex, Integer>();
				int nextVertexIndex = 0;
				ArrayList<FaceVertex> faceVertexList = new ArrayList<FaceVertex>();
				for (Face face : triangles) {
					for (FaceVertex vertex : face.vertices) {
						if (!indexMap.containsKey(vertex)) {
							indexMap.put(vertex, nextVertexIndex++);
							faceVertexList.add(vertex);
						}
					}
				}

				FloatBuffer fb = buildVertices(triangles, faceVertexList, nextVertexIndex);
				vBuffers.add(fb);
				IntBuffer ib = buildIndices(triangles, indexMap);
				iBuffers.add(ib);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		FloatBuffer fb = vBuffers.get(0);
		IntBuffer ib = iBuffers.get(0);
		vID = ARBVertexBufferObject.glGenBuffersARB();
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vID);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, fb, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);

		iID = ARBVertexBufferObject.glGenBuffersARB();
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, iID);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, ib, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);

		iCount = ib.capacity();
	}

	public void render() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vID);

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, ATTR_V_STRIDE2_BYTES, ATTR_V_OFFSET_BYTES);

		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glNormalPointer(GL11.GL_FLOAT, ATTR_N_STRIDE2_BYTES, ATTR_N_OFFSET_BYTES);

		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, ATTR_T_STRIDE2_BYTES, ATTR_T_OFFSET_BYTES);

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iID);
		GL11.glDrawElements(GL11.GL_TRIANGLES, iCount, GL11.GL_UNSIGNED_INT, 0);

		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}

	public IntBuffer buildIndices(ArrayList<Face> triangles, HashMap<FaceVertex, Integer> indexMap) {
		int indicesCount = triangles.size() * 3;
		IntBuffer result = BufferUtils.createIntBuffer(indicesCount);
		for (Face face : triangles) {
			for (FaceVertex vertex : face.vertices) {
				int index = indexMap.get(vertex);
				result.put(index);
			}
		}
		result.flip();
		return result;
	}

	public FloatBuffer buildVertices(ArrayList<Face> triangles, ArrayList<FaceVertex> faceVertexList, int verticeAttributesCount) {
		FloatBuffer result = BufferUtils.createFloatBuffer(verticeAttributesCount * ATTR_SZ_FLOATS);
		for (FaceVertex vertex : faceVertexList) {
			result.put(vertex.v.getX());
			result.put(vertex.v.getY());
			result.put(vertex.v.getZ());
			if (vertex.n == null) {
				result.put(1.0f);
				result.put(1.0f);
				result.put(1.0f);
			} else {
				result.put(vertex.n.getX());
				result.put(vertex.n.getY());
				result.put(vertex.n.getZ());
			}
			if (vertex.t == null) {
				result.put((float) Math.random());
				result.put((float) Math.random());
			} else {
				result.put(vertex.t.getX());
				result.put(vertex.t.getY());
			}
		}
		result.flip();
		return result;
	}

	private ArrayList<ArrayList<Face>> createFaceListsByMaterial(Build builder) {
		ArrayList<ArrayList<Face>> facesByTextureList = new ArrayList<ArrayList<Face>>();
		Material currentMaterial = null;
		ArrayList<Face> currentFaceList = new ArrayList<Face>();
		for (Face face : builder.faces) {
			if (face.material != currentMaterial) {
				if (!currentFaceList.isEmpty()) {
					facesByTextureList.add(currentFaceList);
				}
				currentMaterial = face.material;
				currentFaceList = new ArrayList<Face>();
			}
			currentFaceList.add(face);
		}
		if (!currentFaceList.isEmpty()) {
			facesByTextureList.add(currentFaceList);
		}
		return facesByTextureList;
	}

	private ArrayList<Face> splitQuads(ArrayList<Face> faceList) {
		ArrayList<Face> triangleList = new ArrayList<Face>();
		// int countTriangles = 0;
		// int countQuads = 0;
		// int countNGons = 0;
		for (Face face : faceList) {
			if (face.vertices.size() == 3) {
				// countTriangles++;
				triangleList.add(face);
			} else if (face.vertices.size() == 4) {
				// countQuads++;
				FaceVertex v1 = face.vertices.get(0);
				FaceVertex v2 = face.vertices.get(1);
				FaceVertex v3 = face.vertices.get(2);
				FaceVertex v4 = face.vertices.get(3);
				Face f1 = new Face();
				f1.map = face.map;
				f1.material = face.material;
				f1.add(v1);
				f1.add(v2);
				f1.add(v3);
				triangleList.add(f1);
				Face f2 = new Face();
				f2.map = face.map;
				f2.material = face.material;
				f2.add(v1);
				f2.add(v3);
				f2.add(v4);
				triangleList.add(f2);
			} else {
				// countNGons++;
			}
		}
		// int texturedCount = 0;
		// int normalCount = 0;
		// for (Face face : triangleList) {
		// if ((face.vertices.get(0).n != null) && (face.vertices.get(1).n != null) && (face.vertices.get(2).n != null)) {
		// normalCount++;
		// }
		// if ((face.vertices.get(0).t != null) && (face.vertices.get(1).t != null) && (face.vertices.get(2).t != null)) {
		// texturedCount++;
		// }
		// }
		return triangleList;
	}

	private void calcMissingVertexNormals(ArrayList<Face> triangleList) {
		for (Face face : triangleList) {
			face.calcTriangleNormal();
			for (int loopv = 0; loopv < face.vertices.size(); loopv++) {
				FaceVertex fv = face.vertices.get(loopv);
				if (face.vertices.get(0).n == null) {
					FaceVertex newFv = new FaceVertex();
					newFv.v = fv.v;
					newFv.t = fv.t;
					newFv.n = face.faceNormal;
					face.vertices.set(loopv, newFv);
				}
			}
		}
	}

}
