package net.teamfps.savage.renderer;

/**
 * 
 * @author Zekye
 *
 */
public class Tessellator {
	private final VertexBuffer buffer;
	private final VertexBufferUploader uploader = new VertexBufferUploader();
	private static Tessellator INSTANCE = new Tessellator(2097152);

	public static Tessellator getInstance() {
		return INSTANCE;
	}

	public Tessellator(int bufferSize) {
		this.buffer = new VertexBuffer(bufferSize);
	}

	public void draw() {
		this.buffer.finishDrawing();
		this.uploader.draw(this.buffer);
	}

	public VertexBuffer getBuffer() {
		return this.buffer;
	}
}
