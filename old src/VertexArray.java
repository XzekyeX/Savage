package net.teamfps.savage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zekye
 *
 */
public class VertexArray {

	private int count; // size of indices
	private int vao; // vertex array object
	private int vbo; // vertex buffer object
	private int ibo; // index buffer object
	private int tbo; // texture coordinate object
	private int nbo; // normal buffer object

	public static VertexArray readObj(String path) {
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
			return new VertexArray(StateManager.toFloats(vertices), StateManager.toInts(indices), StateManager.toFloats(tcoords), StateManager.toFloats(normals));
		} catch (IOException e) {
			System.err.println("Object Loading Exception: " + e.getMessage());
		}
		return null;
	}

	public VertexArray(float[] vertices, int[] indices, float[] tcoords, float[] normals) {
		count = indices.length;
		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vbo = glGenBuffers();
		// glBindBuffer(GL_ARRAY_BUFFER, vbo);
		// glBufferData(GL_ARRAY_BUFFER,
		// StateManager.createFloatBuffer(vertices), GL_STATIC_DRAW);
		Buffer.ARRAY_BUFFER.bind(vbo);
		Buffer.ARRAY_BUFFER.data(vertices, GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);

		tbo = glGenBuffers();
		// glBindBuffer(GL_ARRAY_BUFFER, tbo);
		// glBufferData(GL_ARRAY_BUFFER,
		// StateManager.createFloatBuffer(tcoords), GL_STATIC_DRAW);
		Buffer.ARRAY_BUFFER.bind(tbo);
		Buffer.ARRAY_BUFFER.data(tcoords, GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.TCOORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.TCOORD_ATTRIB);

		if (normals != null) {
			nbo = glGenBuffers();
			// glBindBuffer(GL_ARRAY_BUFFER, nbo);
			// glBufferData(GL_ARRAY_BUFFER,
			// StateManager.createFloatBuffer(normals), GL_STATIC_DRAW);
			Buffer.ARRAY_BUFFER.bind(nbo);
			Buffer.ARRAY_BUFFER.data(normals, GL_STATIC_DRAW);
			glVertexAttribPointer(Shader.NORMAL_ATTRIB, 3, GL_FLOAT, false, 0, 0);
			glEnableVertexAttribArray(Shader.NORMAL_ATTRIB);
		}

		ibo = glGenBuffers();
		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		// glBufferData(GL_ELEMENT_ARRAY_BUFFER,
		// StateManager.createIntBuffer(indices), GL_STATIC_DRAW);
		Buffer.ELEMENT_ARRAY_BUFFER.bind(ibo);
		Buffer.ELEMENT_ARRAY_BUFFER.data(indices, GL_STATIC_DRAW);

		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		// glBindBuffer(GL_ARRAY_BUFFER, 0);

		Buffer.ELEMENT_ARRAY_BUFFER.unbind();
		Buffer.ARRAY_BUFFER.unbind();

		glBindVertexArray(0);

	}

	public void bind() {
		glBindVertexArray(vao);
		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		Buffer.ELEMENT_ARRAY_BUFFER.bind(ibo);
	}

	public void unbind() {
		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		Buffer.ELEMENT_ARRAY_BUFFER.unbind();
		glBindVertexArray(0);
	}

	public void draw() {
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
	}

	public void render() {
		bind();
		draw();
		unbind();
	}

}
