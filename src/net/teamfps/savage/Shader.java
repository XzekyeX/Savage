package net.teamfps.savage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import net.teamfps.java.serialization.math.Vec2f;
import net.teamfps.java.serialization.math.Vec3f;
import net.teamfps.java.serialization.math.Vec4f;

/**
 * @author Zekye
 * 
 */
public class Shader {
	private HashMap<String, Integer> uniforms = new HashMap<String, Integer>();
	private int program;

	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	public static final int NORMAL_ATTRIB = 2;

	public Shader(String vert, String frag) {
		String v = loadShader(vert);
		String f = loadShader(frag);
		program = create(v, f);
	}

	private int create(String vert, String frag) {
		int program = glCreateProgram();
		int vertID = glCreateShader(GL_VERTEX_SHADER);
		int fragID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertID, vert);
		glShaderSource(fragID, frag);
		glCompileShader(vertID);
		if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertID));
			return -1;
		}
		glCompileShader(fragID);
		if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(fragID));
			return -1;
		}
		glAttachShader(program, vertID);
		glAttachShader(program, fragID);
		glLinkProgram(program);
		glValidateProgram(program);
		glDeleteShader(vertID);
		glDeleteShader(fragID);
		return program;
	}

	private String loadShader(String file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(file)));
			String line = "";
			while ((line = br.readLine()) != null) {
				result.append(line + "\n");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public void bind() {
		glUseProgram(program);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public boolean isBinded() {
		return glIsProgram(program);
	}

	public void setUniform(String uniformName, int value) {
		// bind();
		glUniform1i(getUniform(uniformName), value);
		// unbind();
	}

	public void setUniform(String uniformName, float value) {
		// bind();
		glUniform1f(getUniform(uniformName), value);
		// unbind();
	}

	public void setUniform(String uniformName, Vec2f value) {
		// bind();
		glUniform2f(getUniform(uniformName), value.getX(), value.getY());
		// unbind();
	}

	public void setUniform(String uniformName, Vec3f value) {
		// bind();
		glUniform3f(getUniform(uniformName), value.getX(), value.getY(), value.getZ());
		// unbind();
	}

	public void setUniform(String uniformName, Vec4f value) {
		// bind();
		glUniform4f(getUniform(uniformName), value.getX(), value.getY(), value.getZ(), value.getD());
		// unbind();
	}

	// public void setUniform(String uniformName, Mat4f value) {
	// // bind();
	// glUniformMatrix4fv(getUniform(uniformName), false, value.toFloatBuffer());
	// // unbind();
	// }
	//
	// public void setUniform(String uniformName, Matrix4f value) {
	// // bind();
	// glUniformMatrix4fv(getUniform(uniformName), false, value.toFloatBuffer());
	// // unbind();
	// }

	public void setUniform(String uniformName, Matrix4f value) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			value.get(fb);
			glUniformMatrix4fv(getUniform(uniformName), false, fb);
		}
	}

	public void setUniform(String uniformName, FloatBuffer buffer) {
		// bind();
		glUniformMatrix4fv(getUniform(uniformName), false, buffer);
		// unbind();
	}

	// public void setUniform(String uniformName, Matrix4f value) {
	// // FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
	// // mat.get(buffer);
	// // setUniform(uniformName, buffer);
	// glUniformMatrix4fv(getUniform(uniformName), false, value.toFloatBuffer());
	// }

	public int getUniform(String name) {
		if (uniforms.containsKey(name)) return uniforms.get(name);
		int result = glGetUniformLocation(program, name);
		if (result < 0) System.err.println("Could not find uniform variable " + name + "! result: " + result);
		else uniforms.put(name, result);
		return result;
	}
}
