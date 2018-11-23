package net.teamfps.savage.renderer;

import net.teamfps.java.serialization.math.Vec2f;
import net.teamfps.java.serialization.math.Vec3f;

public class VertexData {
	protected Vec3f pos;
	protected Vec2f texture;
	protected Vec3f normal;

	public VertexData(Vec3f pos, Vec2f texture, Vec3f normal) {
		this.pos = pos;
		this.texture = texture;
		this.normal = normal;
	}
	
	

	public static long getPositionPointer() {
		return Float.BYTES + Float.BYTES + Float.BYTES;
	}

	public static long getTexturePointer() {
		return getPositionPointer() + Float.BYTES + Float.BYTES;
	}

	public static long getNormalPointer() {
		return getTexturePointer() + Float.BYTES + Float.BYTES + Float.BYTES;
	}
	
	
	public static long getSize() {
		return Vec3f.getByteSize() + Vec2f.getByteSize() + Vec3f.getByteSize();
	}
	
}