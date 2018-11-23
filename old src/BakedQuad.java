package net.teamfps.savage.renderer.model;

import net.teamfps.savage.Texture;
import net.teamfps.savage.util.EnumFacing;

/**
 * 
 * @author Zekye
 *
 */
public class BakedQuad {
	protected final int[] vertexData;
	protected final int tintIndex;

	protected final EnumFacing face;

	protected final Texture sprite;

	public BakedQuad(int[] vertexData, int tintIndex, EnumFacing face, Texture sprite) {
		this.vertexData = vertexData;
		this.tintIndex = tintIndex;
		this.face = face;
		this.sprite = sprite;
	}

	public Texture getSprite() {
		return this.sprite;
	}

	public int[] getVertexData() {
		return this.vertexData;
	}

	public int getTintIndex() {
		return this.tintIndex;
	}

	public boolean hasTintIndex() {
		return this.tintIndex != -1;
	}

	public EnumFacing getFace() {
		return this.face;
	}
}
