package net.teamfps.savage;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

/**
 * 
 * @author Zekye
 *
 */
public class Texture {
	protected String path;
	protected int width;
	protected int height;
	protected int[] pixels;
	protected BufferedImage image;
	protected int texture;

	public Texture(String path) {
		this.path = path;
		load();
	}

	public Texture(int[] pixels, int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		this.texture = genTexture(pixels, width, height);
	}

	private void load() {
		try {
			this.image = ImageIO.read(getClass().getResourceAsStream(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			this.pixels = new int[width * height];
			this.image.getRGB(0, 0, width, height, pixels, 0, width);
			this.texture = genTexture(pixels, width, height);
			System.out.println("new texture has been loaded! " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int genTexture(int[] pixels, int width, int height) {
		int texture = glGenTextures();
		int[] data = new int[width * height];
		for (int i = 0; i < data.length; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			data[i] = a << 25 | b << 16 | g << 8 | r;
		}
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, createIntBuffer(data));
		glBindTexture(GL_TEXTURE_2D, 0);
		return texture;
	}

	private IntBuffer createIntBuffer(int[] array) {
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, texture);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public int getTexture() {
		return texture;
	}

	public void cleanup() {
		glDeleteTextures(texture);
	}

	public int[] getPixels() {
		return pixels;
	}

	public static Texture Split(Texture s, int x, int y, int width, int height) {
		int[] pixels = new int[width * height];
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				pixels[w + h * width] = s.pixels[((w + x) + (h + y) * s.width)];
			}
		}
		return new Texture(pixels, width, height);
	}

	public static Texture[] Split(Texture s, int w, int h) {
		int sw = s.width / w;
		int sh = s.height / h;
		Texture[] textures = new Texture[sw * sh];
		for (int y = 0; y < sh; y++) {
			for (int x = 0; x < sw; x++) {
				textures[x + y * sw] = Split(s, x * w, y * h, w, h);
			}
		}
		return textures;
	}

}