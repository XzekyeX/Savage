package net.teamfps.savage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.teamfps.java.serialization.math.Vec2f;
import net.teamfps.java.serialization.math.Vec3f;
import net.teamfps.java.serialization.math.Vec3i;

public class OBJLoader {

	// public static Mesh loadMesh(String fileName) {
	// return loadMesh(fileName, 1);
	// }

	public static Mesh loadMesh(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String s = "";
			List<Float> vertices = new ArrayList<Float>();
			List<Float> tcoords = new ArrayList<Float>();
			List<Float> normals = new ArrayList<Float>();
			List<Integer> indices = new ArrayList<Integer>();
			while ((s = br.readLine()) != null) {
				String[] tokens = s.split(" ");
				tokens = StateManager.RemoveEmptyStrings(tokens);
				if (tokens.length == 0 || tokens[0].equals("#")) {
					continue;
				} else if (tokens[0].equals("v")) {
					vertices.add(Float.valueOf(tokens[1]));
					vertices.add(Float.valueOf(tokens[2]));
					vertices.add(Float.valueOf(tokens[3]));
				} else if (tokens[0].equals("vt")) {
					tcoords.add(Float.valueOf(tokens[1]));
					tcoords.add(Float.valueOf(tokens[2]));
				} else if (tokens[0].equals("vn")) {
					normals.add(Float.valueOf(tokens[1]));
					normals.add(Float.valueOf(tokens[2]));
					normals.add(Float.valueOf(tokens[3]));
				} else if (tokens[0].equals("f")) {
					indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
					indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
					indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
					if (tokens.length > 4) {
						indices.add((Integer.parseInt(tokens[1].split("/")[0]) - 1));
						indices.add((Integer.parseInt(tokens[3].split("/")[0]) - 1));
						indices.add((Integer.parseInt(tokens[4].split("/")[0]) - 1));
					}
				}
			}
			br.close();
			return new Mesh(StateManager.toFloats(vertices), StateManager.toFloats(tcoords), StateManager.toFloats(normals), StateManager.toInts(indices));
		} catch (IOException e) {
			System.err.println("Object Loading Exception: " + e.getMessage());
		}
		return null;
	}

	private static void processVertex(String[] data, List<Integer> indices, List<Vec2f> textures, List<Vec3f> normals, float[] texArray, float[] normArray) {
		int current = Integer.parseInt(data[0]) - 1;
		indices.add(current);
		Vec2f tex = textures.get(Integer.parseInt(data[1]) - 1);
		texArray[current * 2] = tex.getX();
		texArray[current * 2 + 1] = 1 - tex.getY();
		Vec3f norm = normals.get(Integer.parseInt(data[2]) - 1);
		normArray[current * 3] = norm.getX();
		normArray[current * 3 + 1] = norm.getY();
		normArray[current * 3 + 2] = norm.getZ();
	}

	private static Mesh reOrderList(List<Integer> indices, List<Vec3f> vertices, List<Vec2f> textures, List<Vec3f> normals) {
		// List<Face> faces = new ArrayList<Face>();
		// for (FaceObj obj : faceObjs) {
		// for (Vec3i v : obj.faces) {
		// int index = v.getX();
		// Vec3f vertex = vertices.get(v.getX() - 1);
		// Vec2f texCoord = textures.get(v.getY() - 1);
		// Vec3f normal = normals.get(v.getZ() - 1);
		// System.out.println("getting vertex: " + v.getX() + " = " + vertex);
		// Face face = new Face(vertex, texCoord, normal, index);
		// faces.add(face);
		// }
		// }
		// List<Vec3f> vert = new ArrayList<Vec3f>();
		// List<Vec2f> tex = new ArrayList<Vec2f>();
		// List<Vec3f> norm = new ArrayList<Vec3f>();

		// for (Face f : faces) {
		// vert.add(f.vertex);
		// tex.add(f.texture);
		// norm.add(f.normal);
		// indices.add(f.index);
		// }
		float[] v = Vec3f.toFloatArray(vertices);
		float[] t = Vec2f.toFloatArray(textures);
		float[] n = Vec3f.toFloatArray(normals);
		int[] i = listIntToArray(indices);
		// System.out.println("OLD vertices: " + vertices);
		// System.out.println("NEW vertices: " + toStr(v) + "\n");
		// System.out.println("OLD textures: " + textures);
		// System.out.println("NEW textures: " + toStr(t) + "\n");
		// System.out.println("OLD normals: " + normals);
		// System.out.println("NEW normals: " + toStr(n) + "\n");
		return new Mesh(v, t, n, i);
	}

	public static String toStr(float[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			String val = String.format("% .2f", arr[i]);
			sb.append(i > arr.length - 1 ? "" + val : val + ", ");
		}
		return "[" + sb.toString() + "]";
	}

	static class Face {
		protected Vec3f vertex;
		protected Vec2f texture;
		protected Vec3f normal;
		protected int index;

		public Face(Vec3f vertex, Vec2f texture, Vec3f normal, int index) {
			this.vertex = vertex;
			this.texture = texture;
			this.normal = normal;
			this.index = index;
		}
	}

	static class FaceObj {
		protected List<Vec3i> faces = new ArrayList<Vec3i>();

		public FaceObj(String[] tokens) {
			for (String token : tokens) {
				faces.add(toVec3i(token));
			}
		}

	}

	public static Vec3i toVec3i(String s) {
		String[] sp = s.split("/");
		return new Vec3i(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]), Integer.parseInt(sp[2]));
	}

	// private static Mesh reorderLists(List<Vector3f> posList, List<Vector2f> textCoordList, List<Vector3f> normList, List<Face> facesList, int instances) {
	//
	// List<Integer> indices = new ArrayList<Integer>();
	// // Create position array in the order it has been declared
	// float[] posArr = new float[posList.size() * 3];
	// int i = 0;
	// for (Vector3f pos : posList) {
	// posArr[i * 3] = pos.x;
	// posArr[i * 3 + 1] = pos.y;
	// posArr[i * 3 + 2] = pos.z;
	// i++;
	// }
	// float[] textCoordArr = new float[posList.size() * 2];
	// float[] normArr = new float[posList.size() * 3];
	//
	// for (Face face : facesList) {
	// IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
	// for (IdxGroup indValue : faceVertexIndices) {
	// processFaceVertex(indValue, textCoordList, normList, indices, textCoordArr, normArr);
	// }
	// }
	// int[] indicesArr = listIntToArray(indices);
	// Mesh mesh;
	// if (instances > 1) {
	// mesh = new InstancedMesh(posArr, textCoordArr, normArr, indicesArr, instances);
	// } else {
	// mesh = new Mesh(posArr, textCoordArr, normArr, indicesArr);
	// }
	// return mesh;
	// }

	// private static void processFaceVertex(IdxGroup indices, List<Vector2f> textCoordList, List<Vector3f> normList, List<Integer> indicesList, float[] texCoordArr, float[] normArr) {
	//
	// // Set index for vertex coordinates
	// int posIndex = indices.idxPos;
	// indicesList.add(posIndex);
	//
	// // Reorder texture coordinates
	// if (indices.idxTextCoord >= 0) {
	// Vector2f textCoord = textCoordList.get(indices.idxTextCoord);
	// texCoordArr[posIndex * 2] = textCoord.x;
	// texCoordArr[posIndex * 2 + 1] = 1 - textCoord.y;
	// }
	// if (indices.idxVecNormal >= 0) {
	// // Reorder vectornormals
	// Vector3f vecNorm = normList.get(indices.idxVecNormal);
	// normArr[posIndex * 3] = vecNorm.x;
	// normArr[posIndex * 3 + 1] = vecNorm.y;
	// normArr[posIndex * 3 + 2] = vecNorm.z;
	// }
	// }

	// protected static class Face {
	//
	// /**
	// * List of idxGroup groups for a face triangle (3 vertices per face).
	// */
	// private IdxGroup[] idxGroups = new IdxGroup[3];
	//
	// public Face(String v1, String v2, String v3) {
	// idxGroups = new IdxGroup[3];
	// // Parse the lines
	// idxGroups[0] = parseLine(v1);
	// idxGroups[1] = parseLine(v2);
	// idxGroups[2] = parseLine(v3);
	// }
	//
	// private IdxGroup parseLine(String line) {
	// IdxGroup idxGroup = new IdxGroup();
	//
	// String[] lineTokens = line.split("/");
	// int length = lineTokens.length;
	// idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
	// if (length > 1) {
	// // It can be empty if the obj does not define text coords
	// String textCoord = lineTokens[1];
	// idxGroup.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IdxGroup.NO_VALUE;
	// if (length > 2) {
	// idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
	// }
	// }
	//
	// return idxGroup;
	// }
	//
	// public IdxGroup[] getFaceVertexIndices() {
	// return idxGroups;
	// }
	// }

	protected static class IdxGroup {

		public static final int NO_VALUE = -1;

		public int idxPos;

		public int idxTextCoord;

		public int idxVecNormal;

		public IdxGroup() {
			idxPos = NO_VALUE;
			idxTextCoord = NO_VALUE;
			idxVecNormal = NO_VALUE;
		}
	}

	public static int[] listIntToArray(List<Integer> list) {
		int[] result = list.stream().mapToInt((Integer v) -> v).toArray();
		return result;
	}

}
