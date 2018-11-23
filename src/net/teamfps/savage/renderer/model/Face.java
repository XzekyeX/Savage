package net.teamfps.savage.renderer.model;

/**
 * 
 * @author Zekye
 *
 */
public class Face {
	private Index[] indices;

	public Face(String... tokens) {
		indices = new Index[tokens.length];
		for (int i = 0; i < indices.length; i++) {
			indices[i] = Index.parse(tokens[i]);
			// System.out.println("Index[" + i + "]: " + indices[i]);
		}
	}

	public Index[] getIndices() {
		return indices;
	}
}