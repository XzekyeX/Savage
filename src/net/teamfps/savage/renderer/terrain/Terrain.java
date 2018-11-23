package net.teamfps.savage.renderer.terrain;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import net.teamfps.savage.Shader;
import net.teamfps.savage.Texture;
import net.teamfps.savage.renderer.Mesh;

/**
 * 
 * @author Zekye
 *
 */
public class Terrain {
	protected Map map;
	protected Shader shader;
	protected Matrix4f ml_matrix;
	protected Texture texture;
	protected Vector3f rotation = new Vector3f(0, 0, 0);
	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
	private Matrix4f pr_matrix;

	public Terrain(Shader shader, Texture texture, Texture hm, float minY, float maxY, int tc, int maxCol, Matrix4f pr_matrix) {
		this.map = new HeightMap(hm, minY, maxY, tc, maxCol);
		this.pr_matrix = pr_matrix;
		this.shader = shader;
		this.texture = texture;
		init();
	}

	public Terrain(Shader shader, Texture texture, int width, int height, Matrix4f pr_matrix) {
		this.map = new NoiseMap(width, height, 3, 0.4f, 0.005f, -1.1f, 2f, 100, 255 * 255 * 15);
		this.pr_matrix = pr_matrix;
		this.shader = shader;
		this.texture = texture;
		init();
	}

	private void init() {
		this.ml_matrix = new Matrix4f().identity();
		shader.bind();
		shader.setUniform("pr_matrix", pr_matrix);
		shader.setUniform("ml_matrix", ml_matrix);
		shader.setUniform("tex", 1);
		shader.unbind();
	}

	public void render(Matrix4f view_matrix) {
		if (shader != null) shader.bind();
		if (shader != null) {
			// ml_matrix = getWorldMatrix(ml_matrix, position, rotation, scale);
			// ml_matrix.translate(position).rotateX((float) Math.toRadians(rotation.x)).rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z));
			shader.setUniform("vw_matrix", view_matrix);
			shader.setUniform("ml_matrix", ml_matrix);
		}
		if (texture != null) texture.bind();
		if (map != null) map.render();
		if (texture != null) texture.unbind();
		if (shader != null) shader.unbind();
	}

	public Vector3f getScale() {
		return scale;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public float getHeight(int x, int y) {
		return map != null ? map.getHeight(x, y) : 0;
	}

	public Mesh getMesh() {
		return map != null ? map.getMesh() : null;
	}

	// public Matrix4f getWorldMatrix(Matrix4f worldMatrix, Vector3f offset, Vector3f rotation, Vector3f scale) {
	// worldMatrix.identity().translate(offset).rotateX((float) Math.toRadians(rotation.x)).rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z)).scale(scale);
	// return worldMatrix;
	// }
}
