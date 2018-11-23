package net.teamfps.savage.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import net.teamfps.java.serialization.math.Vec2f;
import net.teamfps.java.serialization.math.Vec3f;
import net.teamfps.savage.Shader;

/**
 * 
 * @author Zekye
 *
 */
public class Mesh {
	protected int count, vao, vbo, ibo, tbo, nbo;
	protected MeshBuffer buffer;

	public Mesh(MeshBuffer buffer) {
		this.buffer = buffer;
		GenBuffers();
	}


	private void GenBuffers() {
		System.out.println(buffer);
		FloatBuffer v = buffer.getVertexBuffer();
		FloatBuffer t = buffer.getTextureBuffer();
		FloatBuffer n = buffer.getNormalBuffer();
		IntBuffer i = buffer.getIndexBuffer();

		count = buffer.getCount();

//		vao = glGenVertexArrays();
//		glBindVertexArray(vao);
//
//		vbo = glGenBuffers();
//		glBindBuffer(GL_ARRAY_BUFFER, vbo);
//		glBufferData(GL_ARRAY_BUFFER, v, GL_STATIC_DRAW);
//		glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
//
//		tbo = glGenBuffers();
//		glBindBuffer(GL_ARRAY_BUFFER, tbo);
//		glBufferData(GL_ARRAY_BUFFER, t, GL_STATIC_DRAW);
//		glVertexAttribPointer(Shader.TCOORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
//
//		nbo = glGenBuffers();
//		glBindBuffer(GL_ARRAY_BUFFER, nbo);
//		glBufferData(GL_ARRAY_BUFFER, n, GL_STATIC_DRAW);
//		glVertexAttribPointer(Shader.NORMAL_ATTRIB, 3, GL_FLOAT, false, 0, 0);
//
//		ibo = glGenBuffers();
//		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
//		glBufferData(GL_ELEMENT_ARRAY_BUFFER, i, GL_STATIC_DRAW);
//
//		glBindBuffer(GL_ARRAY_BUFFER, 0);
//		glBindVertexArray(0);
		
		
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);

//		unsigned int vertex_size = sizeof(VertexData);
//		print("Vertex Size: ");
//		println(vertex_size);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer.vertex.size() * vertex_size, buffer.vertex.data(), GL_STATIC_DRAW);

		glVertexAttribPointer(VERTEX_ATTRIB, 3, GL_FLOAT, false, vertex_size, VertexData.getPositionPointer());
		glVertexAttribPointer(TCOORD_ATTRIB, 2, GL_FLOAT, false, vertex_size, VertexData.getTexturePointer());
		glVertexAttribPointer(NORMAL_ATTRIB, 3, GL_FLOAT, false, vertex_size, VertexData.getNormalPointer());
		
		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer.indices.size() * sizeof(unsigned int), buffer.indices.data(), GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	protected void bind() {
		glBindVertexArray(vao);
		glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);
		glEnableVertexAttribArray(Shader.TCOORD_ATTRIB);
		glEnableVertexAttribArray(Shader.NORMAL_ATTRIB);
	}

	protected void unbind() {
		glDisableVertexAttribArray(Shader.VERTEX_ATTRIB);
		glDisableVertexAttribArray(Shader.TCOORD_ATTRIB);
		glDisableVertexAttribArray(Shader.NORMAL_ATTRIB);
		glBindVertexArray(0);
	}

	public void render() {
		bind();
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
		unbind();
	}

	public MeshBuffer getBuffer() {
		return buffer;
	}
}
