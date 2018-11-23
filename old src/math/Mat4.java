package net.teamfps.savage.math;

public class Mat4 {
	public float[] elements = new float[4 * 4];

	public void Identity() {
		set(0, 0, 1.0f);
		set(1, 1, 1.0f);
		set(2, 2, 1.0f);
		set(3, 3, 1.0f);
	}

	public void translate(float x, float y, float z) {
		set(0, 0, 1.0f);
		set(1, 1, 1.0f);
		set(2, 2, 1.0f);
		set(3, 0, x);
		set(3, 1, y);
		set(3, 2, z);
		set(3, 3, 1.0f);
	}

	public void rotate(float x, float y, float z, float angle) {
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		set(0, 0, cos + (x * x) * (1 - cos));
		set(1, 0, x * y * (1 - cos) - z * sin);
		set(2, 0, x * z * (1 - cos) + y * sin);
		set(3, 0, 0);

		set(0, 1, y * x * (1 - cos) + z * sin);
		set(1, 1, cos + (y * y) * (1 - cos));
		set(2, 1, y * z * (1 - cos) - x * sin);
		set(3, 1, 0);

		set(0, 2, z * x * (1 - cos) - y * sin);
		set(1, 2, z * y * (1 - cos) + x * sin);
		set(2, 2, cos + (z * z) * (1 - cos));
		set(3, 2, 0);

		set(0, 3, 0);
		set(1, 3, 0);
		set(2, 3, 0);
		set(3, 3, 0);
	}

	public void set(int x, int y, float value) {
		int index = x + y * 4;
		if (index >= 0 && index <= elements.length - 1) {
			elements[index] = value;
		} else {
			System.err.println("Index is OutOfBounds!");
		}
	}

	public void print() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elements.length; i++) {
			sb.append(i < elements.length - 1 ? elements[i] + ", " : elements[i] + "");
		}
		System.out.println("Elements[" + sb.toString() + "]");
	}
}
