package net.teamfps.savage.renderer.model;

/**
 * 
 * @author Zekye
 *
 */
public class Index {
	int vertex = -1;
	int texture = -1;
	int normal = -1;

	public static Index parse(String str) {
		Index result = new Index();
		String[] split = str.split("/");
		if (split.length > 0) {
			result.vertex = parseInt(split[0]);
			if (split.length > 1) {
				result.texture = parseInt(split[1]);
				if (split.length > 2) {
					result.normal = parseInt(split[2]);
				}
			}
		}
		return result;
	}

	public static int parseInt(String s) {
		if (s.isEmpty()) return -1;
		try {
			int i = Integer.parseInt(s);
			return i;
		} catch (NumberFormatException e) {
			System.err.println("Invalid string integer! String=" + s);
		}
		return -1;
	}

	@Override
	public String toString() {
		return "Index[Vertex = " + vertex + ", Texture = " + texture + ", Normal = " + normal + "]";
	}

	public int getVertex() {
		return vertex;
	}

	public int getTexture() {
		return texture;
	}

	public int getNormal() {
		return normal;
	}
}