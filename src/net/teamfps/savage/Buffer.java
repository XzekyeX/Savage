package net.teamfps.savage;

import static org.lwjgl.opengl.GL15.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.PointerBuffer;

/**
 * 
 * @author Zekye
 *
 */
public enum Buffer {
	ELEMENT_ARRAY_BUFFER(GL_ELEMENT_ARRAY_BUFFER), ARRAY_BUFFER(GL_ARRAY_BUFFER);
	private int target;

	private Buffer(int target) {
		this.target = target;
	}

	public void bind(int buffer) {
		glBindBuffer(target, buffer);
	}

	public void unbind() {
		glBindBuffer(target, 0);
	}

	public void data(FloatBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	public void data(IntBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	public void data(ByteBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	public void data(float[] data, int usage) {
		data(createBuffer(data), usage);
	}

	public void data(int[] data, int usage) {
		data(createBuffer(data), usage);
	}

	public void data(byte[] data, int usage) {
		data(createBuffer(data), usage);
	}

	public void subdata(FloatBuffer data, long offset) {
		glBufferSubData(target, offset, data);
	}

	public FloatBuffer getSubData(FloatBuffer data, long offset) {
		glGetBufferSubData(target, offset, data);
		return data;
	}

	public int getParameteri(int pname) {
		return glGetBufferParameteri(target, pname);
	}

	public IntBuffer getParameteriv(IntBuffer data, int pname) {
		glGetBufferParameteriv(target, pname, data);
		return data;
	}

	public PointerBuffer getPointerv(PointerBuffer params, int pname) {
		glGetBufferPointerv(target, pname, params);
		return params;
	}

	public long getPointer(int pname) {
		return glGetBufferPointer(target, pname);
	}

	public void map(int access) {
		glMapBuffer(target, access);
	}

	public void unmap() {
		glUnmapBuffer(target);
	}

	public ByteBuffer createBuffer(byte[] array) {
		ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		result.put(array).flip();
		return result;
	}

	public IntBuffer createBuffer(int[] array) {
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}

	public FloatBuffer createBuffer(float[] array) {
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(array).flip();
		return result;
	}

}