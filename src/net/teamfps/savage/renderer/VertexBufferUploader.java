package net.teamfps.savage.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * 
 * @author Zekye
 *
 */
public class VertexBufferUploader {
	private int defaultTexUnit = 33984;

	@SuppressWarnings("incomplete-switch")
	public void draw(VertexBuffer vertexBufferIn) {
		if (vertexBufferIn.getVertexCount() > 0) {
			VertexFormat vertexformat = vertexBufferIn.getVertexFormat();
			int i = vertexformat.getNextOffset();
			ByteBuffer bytebuffer = vertexBufferIn.getByteBuffer();
			List<VertexFormatElement> list = vertexformat.getElements();

			for (int j = 0; j < list.size(); ++j) {
				VertexFormatElement vertexformatelement = (VertexFormatElement) list.get(j);
				VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
				int k = vertexformatelement.getType().getGlConstant();
				int l = vertexformatelement.getIndex();
				bytebuffer.position(vertexformat.getOffset(j));

				switch (vertexformatelement$enumusage) {
				case POSITION:
					glVertexPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
					glEnableClientState(32884);
					break;

				case UV:
					setClientActiveTexture(defaultTexUnit + l);
					glTexCoordPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
					glEnableClientState(GL_TEXTURE_COORD_ARRAY);
					setClientActiveTexture(defaultTexUnit);
					break;

				case COLOR:
					glColorPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
					glEnableClientState(32886);
					break;

				case NORMAL:
					glNormalPointer(k, i, bytebuffer);
					glEnableClientState(32885);
				}
			}

			glDrawArrays(vertexBufferIn.getDrawMode(), 0, vertexBufferIn.getVertexCount());
			int i1 = 0;

			for (int j1 = list.size(); i1 < j1; ++i1) {
				VertexFormatElement vertexformatelement1 = (VertexFormatElement) list.get(i1);
				VertexFormatElement.EnumUsage vertexformatelement$enumusage1 = vertexformatelement1.getUsage();
				int k1 = vertexformatelement1.getIndex();

				switch (vertexformatelement$enumusage1) {
				case POSITION:
					glDisableClientState(32884);
					break;

				case UV:
					setClientActiveTexture(defaultTexUnit + k1);
					glDisableClientState(32888);
					setClientActiveTexture(defaultTexUnit);
					break;

				case COLOR:
					glDisableClientState(32886);
					// GlStateManager.resetColor();
					break;

				case NORMAL:
					glDisableClientState(32885);
				}
			}
		}

		vertexBufferIn.reset();
	}

	public static void setClientActiveTexture(int texture) {
		glClientActiveTexture(texture);
	}
}
