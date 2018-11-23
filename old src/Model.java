package net.teamfps.savage;

import net.teamfps.java.serialization.math.Mat4;
import net.teamfps.java.serialization.math.Vec3f;
import net.teamfps.savage.obj.VBO;

/**
 * @author Zekye
 *
 */
public class Model {
	private Shader shader;
	private Texture texture;
	private Mesh mesh;
	private VBO vbo;
	private Mat4 ml_matrix;

	private Vec3f pos = new Vec3f(0, 0, 0);
	private Vec3f rot = new Vec3f(0, 0, 0);

	public Model(Shader shader, Mesh mesh, Texture texture, Mat4 pr_matrix) {
		this.shader = shader;
		this.texture = texture;
		this.mesh = mesh;
		this.ml_matrix = Mat4.identity();
		shader.bind();
		shader.setUniform("pr_matrix", pr_matrix);
		shader.setUniform("ml_matrix", ml_matrix);
		shader.setUniform("tex", 1);
		shader.unbind();
	}

	public Model(Shader shader, VBO vbo, Texture texture, Mat4 pr_matrix) {
		this.shader = shader;
		this.texture = texture;
		this.vbo = vbo;
		this.ml_matrix = Mat4.identity();
		shader.bind();
		shader.setUniform("pr_matrix", pr_matrix);
		shader.setUniform("ml_matrix", ml_matrix);
		shader.setUniform("tex", 1);
		shader.unbind();
	}

	public void render(Mat4 view_matrix) {
		if (shader != null) shader.bind();
		if (texture != null) texture.bind();
		if (shader != null) {
			shader.setUniform("vw_matrix", view_matrix);
			shader.setUniform("ml_matrix", ml_matrix);
		}
		if (mesh != null) mesh.render();
		if (vbo != null) vbo.render();
		if (texture != null) texture.unbind();
		if (shader != null) shader.unbind();
	}

	public void rotate(float angle, float x, float y, float z) {
		ml_matrix.rotate(angle, x, y, z);
	}

	public void translate(float x, float y, float z) {
		ml_matrix.translate(x, y, z);
	}

	public void setPos(Vec3f pos) {
		this.pos = pos;
	}

	public void setRot(Vec3f rot) {
		this.rot = rot;
	}

}
