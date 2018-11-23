package net.teamfps.savage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.teamfps.java.serialization.math.Vec2f;
import net.teamfps.java.serialization.math.Vec3f;
import net.teamfps.java.serialization.math.Vec3i;

/**
 * 
 * @author Zekye
 *
 */
public class StateManager {

	public static void perspective(float fov, float aspect, float zNear, float zFar) {
		float fH = (float) Math.tan(fov / 360 * Math.PI) * zNear;
		float fW = fH * aspect;
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glFrustum(-fW, fW, -fH, fH, zNear, zFar);
		glMatrixMode(GL_MODELVIEW);
	}

	public static void ortho(int width, int height) {
		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public static void color(int color) {
		float alpha = (float) (color >> 24 & 255) / 255.0F;
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		color(red, green, blue, alpha);
	}

	public static void color(float red, float green, float blue, float alpha) {
		glColor4f(red, green, blue, alpha);
	}

	public static void scale(float x, float y, float z) {
		glScalef(x, y, z);
	}

	public static void translate(float x, float y, float z) {
		glTranslatef(x, y, z);
	}

	/**
	 * Creates and returns a direct byte buffer with the specified capacity. Applies native ordering to speed up access.
	 */
	public static synchronized ByteBuffer createDirectByteBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
	}

	/**
	 * Creates and returns a direct int buffer with the specified capacity. Applies native ordering to speed up access.
	 */
	public static IntBuffer createDirectIntBuffer(int capacity) {
		return createDirectByteBuffer(capacity << 2).asIntBuffer();
	}

	/**
	 * Creates and returns a direct float buffer with the specified capacity. Applies native ordering to speed up access.
	 */
	public static FloatBuffer createDirectFloatBuffer(int capacity) {
		return createDirectByteBuffer(capacity << 2).asFloatBuffer();
	}

	public static ByteBuffer createByteBuffer(byte[] array) {
		ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		result.put(array).flip();
		return result;
	}

	public static IntBuffer createIntBuffer(int[] array) {
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}

	public static FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(array).flip();
		return result;
	}

	public static FloatBuffer createVec3fFloatBuffer(List<Vec3f> list) {
		float[] arr = new float[list.size() * 3];
		int i = 0;
		for (Vec3f vec : list) {
			arr[i] = vec.getX();
			arr[i + 1] = vec.getY();
			arr[i + 2] = vec.getZ();
			i++;
		}
		return createFloatBuffer(arr);
	}

	public static FloatBuffer createVec2fFloatBuffer(List<Vec2f> list) {
		float[] arr = new float[list.size() * 2];
		int i = 0;
		for (Vec2f vec : list) {
			arr[i] = vec.getX();
			arr[i + 1] = vec.getY();
			i++;
		}
		return createFloatBuffer(arr);
	}

	public static IntBuffer createVec3iIntBuffer(List<Vec3i> list) {
		int[] arr = new int[list.size() * 3];
		int i = 0;
		for (Vec3i vec : list) {
			arr[i] = vec.getX();
			arr[i + 1] = vec.getY();
			arr[i + 2] = vec.getZ();
			i++;
		}
		return createIntBuffer(arr);
	}

	public static String toString(String[] tokens) {
		String result = "";
		for (int i = 0; i < tokens.length; i++) {
			result += i < tokens.length - 1 ? tokens[i] + ", " : tokens[i];
		}
		return "[" + result + "]";
	}

	public static int roundUp(int number, int interval) {
		if (interval == 0) {
			return 0;
		} else if (number == 0) {
			return interval;
		} else {
			if (number < 0) {
				interval *= -1;
			}

			int i = number % interval;
			return i == 0 ? number : number + interval - i;
		}
	}

	public static int clamp_int(int num, int min, int max) {
		return num < min ? min : (num > max ? max : num);
	}

	public static float clamp_float(float num, float min, float max) {
		return num < min ? min : (num > max ? max : num);
	}

	public static float[] toFloats(List<Float> vertices) {
		float[] result = new float[vertices.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = vertices.get(i);
		}
		return result;
	}

	public static byte[] toBytes(List<Byte> indices) {
		byte[] result = new byte[indices.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = indices.get(i);
		}
		return result;
	}

	public static int[] toInts(List<Integer> indices) {
		int[] result = new int[indices.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = indices.get(i);
		}
		return result;
	}

	public static String[] RemoveEmptyStrings(String[] tokens) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < tokens.length; i++) {
			if (!tokens[i].equals("")) {
				result.add(tokens[i]);
			}
		}
		return toArrayStr(result);
	}

	public static String[] toArrayStr(List<String> tokens) {
		String[] result = new String[tokens.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = tokens.get(i);
		}
		return result;
	}

	public static void ActiveTexture(int i) {
		glActiveTexture(GL_TEXTURE0 + i);
	}

	public static void CullFace(int face) {
		glCullFace(face);
	}

	public static void push() {
		glPushMatrix();
	}

	public static void pop() {
		glPopMatrix();
	}

	public static void rotate(float angle, float x, float y, float z) {
		glRotatef(angle, x, y, z);
	}
}
