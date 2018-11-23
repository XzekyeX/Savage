
	class FaceBuffer {
		protected List<Face> faces = new ArrayList<Face>();

		public void add(Face face) {
			faces.add(face);
		}

		public void calcTangents() {
			for (int i = 0; i < faces.size() / 3; i += 3) {
				Vec3f t = tangent(faces.get(i), faces.get(i + 1), faces.get(i + 2));
				faces.get(i).tangent = t;
				faces.get(i + 1).tangent = t;
				faces.get(i + 2).tangent = t;
			}
		}

		public FloatBuffer getTangentBuffer() {
			calcTangents();
			FloatBuffer result = StateManager.createDirectFloatBuffer(faces.size() * 3);
			for (Face f : faces) {
				result.put(f.tangent.getX());
				result.put(f.tangent.getY());
				result.put(f.tangent.getZ());
			}
			result.flip();
			return result;
		}

		public FloatBuffer getVertexBuffer() {
			FloatBuffer result = StateManager.createDirectFloatBuffer(faces.size() * 3);
			for (Face f : faces) {
				result.put(f.vertex.getX());
				result.put(f.vertex.getY());
				result.put(f.vertex.getZ());
			}
			result.flip();
			return result;
		}

		public FloatBuffer getTextureBuffer() {
			FloatBuffer result = StateManager.createDirectFloatBuffer(faces.size() * 2);
			for (Face f : faces) {
				result.put(f.texture.getX());
				result.put(f.texture.getY());
			}
			result.flip();
			return result;
		}

		public FloatBuffer getNormalBuffer() {
			FloatBuffer result = StateManager.createDirectFloatBuffer(faces.size() * 3);
			for (Face f : faces) {
				result.put(f.normal.getX());
				result.put(f.normal.getY());
				result.put(f.normal.getZ());
			}
			result.flip();
			return result;
		}

		public IntBuffer getIndexBuffer() {
			IntBuffer result = StateManager.createDirectIntBuffer(faces.size());
			for (Face f : faces) {
				result.put(f.index);
			}
			result.flip();
			return result;
		}

		@Override
		public String toString() {
			return "FaceBuffer(" + faces.size() + ")";
		}
	}

	class Buffer {
		protected List<Vec3f> vertices = new ArrayList<Vec3f>();
		protected List<Vec2f> textures = new ArrayList<Vec2f>();
		protected List<Vec3f> normals = new ArrayList<Vec3f>();
		protected List<Integer> indices = new ArrayList<Integer>();

		public FloatBuffer getVertexBuffer() {
			FloatBuffer result = StateManager.createDirectFloatBuffer(vertices.size() * 3);
			for (Vec3f f : vertices) {
				result.put(f.getX());
				result.put(f.getY());
				result.put(f.getZ());
			}
			result.flip();
			return result;
		}

		public FloatBuffer getTextureBuffer() {
			FloatBuffer result = StateManager.createDirectFloatBuffer(textures.size() * 2);
			for (Vec2f f : textures) {
				result.put(f.getX());
				result.put(f.getY());
			}
			result.flip();
			return result;
		}

		public FloatBuffer getNormalBuffer() {
			FloatBuffer result = StateManager.createDirectFloatBuffer(normals.size() * 3);
			for (Vec3f f : normals) {
				result.put(f.getX());
				result.put(f.getY());
				result.put(f.getZ());
			}
			result.flip();
			return result;
		}

		public IntBuffer getIndexBuffer() {
			IntBuffer result = StateManager.createDirectIntBuffer(indices.size());
			for (Integer i : indices) {
				result.put(i);
			}
			result.flip();
			return result;
		}

		public FaceBuffer getFaceBuffer() {
			FaceBuffer result = new FaceBuffer();
			for (int index : indices) {
				Vec3f vertex = vertices.get(index - 1);
				Vec2f texture = textures.get(index - 1);
				Vec3f normal = normals.get(index - 1);
				result.add(new Face(vertex, texture, normal, index - 1));
			}
			return result;
		}

		@Override
		public String toString() {
			return ("Buffer has " + vertices.size() + " vertices, " + textures.size() + " texture coordinates, " + normals.size() + " normals and " + indices.size() + " indices.");
		}
	}

	class Face {
		protected Vec3f vertex;
		protected Vec2f texture;
		protected Vec3f normal;
		protected Vec3f tangent;
		protected int index = -1;

		public Face(Vec3f vertex, Vec2f texture, Vec3f normal, int index) {
			this.vertex = vertex;
			this.texture = texture;
			this.normal = normal;
			this.index = index;
		}
	}

	private Vec3f tangent(Face f1, Face f2, Face f3) {
		Vec3f edge1 = f2.vertex.sub(f1.vertex);
		Vec3f edge2 = f3.vertex.sub(f1.vertex);
		float u1 = f2.texture.getX() - f1.texture.getX();
		float v1 = f2.texture.getY() - f1.texture.getY();
		float u2 = f3.texture.getX() - f1.texture.getX();
		float v2 = f3.texture.getY() - f1.texture.getY();
		float dividend = (u1 * v2 - u2 * v2);
		float f = dividend == 0 ? 0.0f : 1.0f / dividend;
		float x = f * (v2 * edge1.getX() - v1 * edge2.getX());
		float y = f * (v2 * edge1.getY() - v1 * edge2.getY());
		float z = f * (v2 * edge1.getZ() - v1 * edge2.getZ());
		return new Vec3f(x, y, z);
	}

	private Vec3f tangent(Vec3f vertex1, Vec3f vertex2, Vec3f vertex3, Vec2f texture1, Vec2f texture2, Vec2f texture3) {
		Vec3f edge1 = vertex2.sub(vertex1);
		Vec3f edge2 = vertex3.sub(vertex1);
		float u1 = texture2.getX() - texture1.getX();
		float v1 = texture2.getY() - texture1.getY();
		float u2 = texture3.getX() - texture1.getX();
		float v2 = texture3.getY() - texture1.getY();
		float dividend = (u1 * v2 - u2 * v2);
		float f = dividend == 0 ? 0.0f : 1.0f / dividend;
		float x = f * (v2 * edge1.getX() - v1 * edge2.getX());
		float y = f * (v2 * edge1.getY() - v1 * edge2.getY());
		float z = f * (v2 * edge1.getZ() - v1 * edge2.getZ());
		return new Vec3f(x, y, z);
	}

	private Vec3i face(String str) {
		String[] tokens = str.split("/");
		return new Vec3i(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
	}
