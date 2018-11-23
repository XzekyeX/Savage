package net.teamfps.savage.renderer.terrain;

import java.util.ArrayList;
import java.util.List;

import net.teamfps.savage.StateManager;
import net.teamfps.savage.Texture;
import net.teamfps.savage.renderer.Mesh;
import net.teamfps.savage.renderer.MeshBuffer;

/**
 * 
 * @author Zekye
 *
 */
public class HeightMap extends Map {
	protected Texture texture;
	protected float minY, maxY;
	protected int tc, maxCol;
	public HeightMap(Texture texture, float minY, float maxY, int tc, int maxCol) {
		this.texture = texture;
		this.minY = minY;
		this.maxY = maxY;
		this.tc = tc;
		this.maxCol = maxCol;
		this.mesh = load();
	}

	public HeightMap() {

	}

	protected Mesh load() {
		float sx = -0.5f;
		float sz = -0.5f;
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textures = new ArrayList<Float>();
		List<Integer> indices = new ArrayList<Integer>();
		float ix = Math.abs(-sx * 2);
		float iz = Math.abs(-sz * 2);
		int width = texture.getWidth();
		int height = texture.getHeight();
		float[] heights = new float[width * height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				float ch = getHeight(x, y);
				heights[x + y * width] = ch;
				vertices.add(sx + x * ix);
				vertices.add(ch);
				vertices.add(sz + y * iz);

				textures.add((float) tc * (float) x / (float) width);
				textures.add((float) tc * (float) y / (float) height);

				if (x < width - 1 && y < height - 1) {
					int lt = x + y * width;
					int lb = x + (y + 1) * width;
					int rb = (x + 1) + (y + 1) * width;
					int rt = (x + 1) + y * width;
					indices.add(lt);
					indices.add(lb);
					indices.add(rt);

					indices.add(rt);
					indices.add(lb);
					indices.add(rb);
				}
			}
		}
		float[] v = StateManager.toFloats(vertices);
		float[] t = StateManager.toFloats(textures);
		float[] n = calcNormals(v, width, height);
		int[] i = StateManager.toInts(indices);
		MeshBuffer buffer = new MeshBuffer(v, t, n, i);
		return new Mesh(buffer);
	}


	public float getHeight(int x, int y) {
		return minY + Math.abs(maxY - minY) * ((float) (texture.getPixels()[x + y * texture.getWidth()] / (float) maxCol));
	}

}
