package net.teamfps.savage.renderer;

/**
 * 
 * @author Zekye
 *
 */
public class DefaultVertexFormats {
	public static final VertexFormat POSITION = new VertexFormat();
	public static final VertexFormat POSITION_TEX = new VertexFormat();
	public static final VertexFormatElement POSITION_3F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3);
	public static final VertexFormatElement TEX_2F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2);
	static {
		POSITION.addElement(POSITION_3F);
		POSITION_TEX.addElement(POSITION_3F);
		POSITION_TEX.addElement(TEX_2F);
	}
}
