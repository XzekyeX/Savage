package net.teamfps.savage.renderer.terrain;

/**
 * 
 * @author Zekye
 *
 */
public class NoiseMap extends HeightMap {
	protected int width, height, octaves;
	protected float roughness, scale;

	public NoiseMap(int width, int height, int octaves, float roughness, float scale, float minY, float maxY, int tc, int maxCol) {
		this.width = width;
		this.height = height;
		this.octaves = octaves;
		this.roughness = roughness;
		this.scale = scale;
		this.minY = minY;
		this.maxY = maxY;
		this.maxCol = maxCol;
		this.tc = tc;
		this.texture = Noise.generateNoiseTexture(width, height, octaves, roughness, scale);
		this.mesh = load();
	}

}
