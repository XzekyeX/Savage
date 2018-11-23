package net.teamfps.savage.renderer.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import net.teamfps.savage.Shader;
import net.teamfps.savage.Texture;
import net.teamfps.savage.renderer.Mesh;
import net.teamfps.savage.renderer.MeshBuffer;

/**
 * 
 * @author Zekye
 *
 */
public class Model extends ModelBase {
	protected Shader shader;
	protected Texture texture;
	protected Matrix4f ml_matrix;
	private Matrix4f pr_matrix;
	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f rotation = new Vector3f(0, 0, 0);
	protected Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);

	public Model(String file, Shader shader, Texture texture, Matrix4f pr_matrix) {
		super(file);
		this.shader = shader;
		this.texture = texture;
		this.pr_matrix = pr_matrix;
		init();
	}

	private void init() {
		this.ml_matrix = new Matrix4f();
		shader.bind();
		shader.setUniform("pr_matrix", pr_matrix);
		shader.setUniform("ml_matrix", ml_matrix);
		shader.setUniform("tex", 1);
		shader.unbind();
	}

	public Model(Mesh mesh, Shader shader, Texture texture, Matrix4f pr_matrix) {
		super(mesh);
		this.shader = shader;
		this.texture = texture;
		this.pr_matrix = pr_matrix;
		init();
	}

	public Model(MeshBuffer buffer, Shader shader, Texture texture, Matrix4f pr_matrix) {
		this(new Mesh(buffer), shader, texture, pr_matrix);
	}

	public void render(Matrix4f view_matrix) {
		if (shader != null) shader.bind();
		if (shader != null) {
			ml_matrix = new Matrix4f().translate(position).rotateX((float) Math.toRadians(rotation.x)).rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z)).scale((float) scale.x, (float) scale.y, (float) scale.z);// .scale(scale);
			shader.setUniform("vw_matrix", view_matrix);
			shader.setUniform("ml_matrix", ml_matrix);
		}
		if (texture != null) texture.bind();
		render();
		if (texture != null) texture.unbind();
		if (shader != null) shader.unbind();
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public boolean Intersects(Model m) {

		return false;
	}

	// public Matrix4f getWorldMatrix(Matrix4f worldMatrix, Vector3f offset, Vector3f rotation, Vector3f scale) {
	// worldMatrix.identity().translate(offset).rotateX((float) Math.toRadians(rotation.x)).rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z)).scale(scale);
	// return worldMatrix;
	// }

	// public Matrix4f getWorldMatrix(Matrix4f worldMatrix, Vector3f offset, Vector3f rotation, float scale) {
	// worldMatrix.identity().translate(offset).rotateX((float) Math.toRadians(rotation.x)).rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z)).scale(scale);
	// return worldMatrix;
	// }
}
