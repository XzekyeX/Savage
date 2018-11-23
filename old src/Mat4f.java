package net.teamfps.java.serialization.math;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import net.teamfps.java.serialization.array.FloatArray;
import net.teamfps.java.serialization.field.FloatField;

/**
 * 
 * @author Zekye
 *
 */
public class Mat4f {
	protected FloatArray elements = new FloatArray("Elements", new float[4 * 4]);

	public static Mat4f identity() {
		Mat4f result = new Mat4f();
		for (int i = 0; i < result.elements.length(); i++) {
			result.elements.setData(i, 0.0f);
		}
		result.setData(0, 0, 1.0f);
		result.setData(1, 1, 1.0f);
		result.setData(2, 2, 1.0f);
		result.setData(3, 3, 1.0f);
		return result;
	}

	public Mat4f identityThis() {
		for (int i = 0; i < elements.length(); i++) {
			elements.setData(i, 0.0f);
		}
		setData(0, 0, 1.0f);
		setData(1, 1, 1.0f);
		setData(2, 2, 1.0f);
		setData(3, 3, 1.0f);
		return this;
	}

	public Mat4f translate(Vec3f vec) {
		setData(0, 3, vec.getX());
		setData(1, 3, vec.getY());
		setData(2, 3, vec.getZ());
		return this;
	}

	public Mat4f translate(float x, float y, float z) {
		setData(0, 3, x);
		setData(1, 3, y);
		setData(2, 3, z);
		return this;
	}

	public Vec3f transformPosition(Vec3f v) {
		v.set(getData(0, 0) * v.getX() + getData(1, 0) * v.getY() + getData(2, 0) * v.getZ() + getData(3, 0), getData(0, 1) * v.getX() + getData(1, 1) * v.getY() + getData(2, 1) * v.getZ() + getData(3, 1), getData(0, 2) * v.getX() + getData(1, 2) * v.getY() + getData(2, 2) * v.getZ() + getData(3, 2));
		return v;
	}

	public Mat4f rotate(float angle, Vec3f axis) {
		float r = (float) Math.toRadians(angle);
		float c = (float) Math.cos(r);
		float s = (float) Math.sin(r);
		float omc = 1.0f - c;
		float x = axis.getX();
		float y = axis.getY();
		float z = axis.getZ();
		setData(0, 0, x * omc + c);
		setData(1, 0, y * x * omc + z * s);
		setData(2, 0, x * z * omc - y * s);
		setData(0, 1, x * y * omc - z * s);
		setData(1, 1, y * omc + c);
		setData(2, 1, y * z * omc + x * s);
		setData(0, 2, x * z * omc + y * s);
		setData(1, 2, y * z * omc - x * s);
		setData(2, 2, z * omc + c);
		return this;
	}

	public Mat4f rotateX(float angle) {
		float rad = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(rad);
		float sin = (float) Math.sin(rad);
		// elements.setData(0, 1);
		// elements.setData(1, 0);
		// elements.setData(2, 0);
		// elements.setData(3, 0);
		//
		// elements.setData(4, 0);
		// elements.setData(5, cos);
		// elements.setData(6, -sin);
		// elements.setData(7, 0);
		//
		// elements.setData(8, 0);
		// elements.setData(9, sin);
		// elements.setData(10, cos);
		// elements.setData(11, 0);
		//
		// elements.setData(12, 0);
		// elements.setData(13, 0);
		// elements.setData(14, 0);
		// elements.setData(15, 1);
		// identityThis();
		setData(1, 1, cos);
		setData(1, 2, sin);
		setData(2, 1, -sin);
		setData(2, 2, cos);
		return this;
	}

	public Mat4f rotateY(float angle) {
		float rad = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(rad);
		float sin = (float) Math.sin(rad);
		// elements.setData(0, cos);
		// elements.setData(1, 0);
		// elements.setData(2, sin);
		// elements.setData(3, 0);
		//
		// elements.setData(4, 0);
		// elements.setData(5, 1);
		// elements.setData(6, 0);
		// elements.setData(7, 0);
		//
		// elements.setData(8, -sin);
		// elements.setData(9, 0);
		// elements.setData(10, cos);
		// elements.setData(11, 0);
		//
		// elements.setData(12, 0);
		// elements.setData(13, 0);
		// elements.setData(14, 0);
		// elements.setData(15, 1);
		setData(0, 0, cos);
		setData(0, 2, -sin);
		setData(2, 0, sin);
		setData(2, 2, cos);
		return this;
	}

	public Mat4f combine(Mat4f m) {
		Mat4f result = new Mat4f();
		for (int i = 0; i < elements.length(); i++) {
			float v = elements.getData(i) + m.elements.getData(i);
			result.setData(i, v);
		}
		return result;
	}

	public Mat4f rotateZ(float angle) {
		float rad = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(rad);
		float sin = (float) Math.sin(rad);
		// elements.setData(0, cos);
		// elements.setData(1, -sin);
		// elements.setData(2, 0);
		// elements.setData(3, 0);
		//
		// elements.setData(4, sin);
		// elements.setData(5, cos);
		// elements.setData(6, 0);
		// elements.setData(7, 0);
		//
		// elements.setData(8, 0);
		// elements.setData(9, 0);
		// elements.setData(10, 1);
		// elements.setData(11, 0);
		//
		// elements.setData(12, 0);
		// elements.setData(13, 0);
		// elements.setData(14, 0);
		// elements.setData(15, 1);
		setData(0, 0, cos);
		setData(0, 1, sin);
		setData(1, 0, -sin);
		setData(1, 1, cos);
		return this;
	}

	public float cos(float angle) {
		return (float) Math.cos(Math.toRadians(angle));
	}

	public float sin(float angle) {
		return (float) Math.sin(Math.toRadians(angle));
	}

	public Mat4f rotateXYZ(float angleX, float angleY, float angleZ) {
		float cX = cos(angleX);
		float sX = sin(angleX);
		float cY = cos(angleY);
		float sY = sin(angleY);
		float cZ = cos(angleZ);
		float sZ = sin(angleZ);

		float nsX = -sX;
		float nsY = -sY;
		float nsZ = -sZ;

		float nm00 = cZ;
		float nm01 = sZ;
		float nm10 = nsZ;
		float nm11 = cZ;
		// rotateY
		float nm20 = nm00 * sY;
		float nm21 = nm01 * sY;
		float nm22 = cY;

		this.setData(0, 0, (nm00 * cY));
		this.setData(0, 1, (nm01 * cY));
		this.setData(0, 2, nsY);
		this.setData(0, 3, 0.0f);
		// rotateX
		this.setData(1, 0, (nm10 * cX + nm20 * sX));
		this.setData(1, 1, (nm11 * cX + nm21 * sX));
		this.setData(1, 2, (nm22 * sX));
		this.setData(1, 3, 0.0f);
		this.setData(2, 0, (nm10 * nsX + nm20 * cX));
		this.setData(2, 1, (nm11 * nsX + nm21 * cX));
		this.setData(2, 2, (nm22 * cX));
		this.setData(2, 3, 0.0f);
		// set last column to identity
		this.setData(3, 0, 0.0f);
		this.setData(3, 1, 0.0f);
		this.setData(3, 2, 0.0f);
		this.setData(3, 3, 1.0f);

		return this;
	}

	public Mat4f rotate(float angle, float x, float y, float z) {
		float r = (float) Math.toRadians(angle);
		float c = (float) Math.cos(r);
		float s = (float) Math.sin(r);
		float omc = 1.0f - c;
		setData(0, 0, x * omc + c);
		setData(1, 0, y * x * omc + z * s);
		setData(2, 0, x * z * omc - y * s);
		setData(0, 1, x * y * omc - z * s);
		setData(1, 1, y * omc + c);
		setData(2, 1, y * z * omc + x * s);
		setData(0, 2, x * z * omc + y * s);
		setData(1, 2, y * z * omc - x * s);
		setData(2, 2, z * omc + c);
		return this;
	}

	public static Mat4f orientation(Vec3f xaxis, Vec3f yaxis, Vec3f zaxis) {
		Mat4f orientation = new Mat4f();
		orientation.elements.setData(0, xaxis.getX());
		orientation.elements.setData(1, yaxis.getX());
		orientation.elements.setData(2, zaxis.getX());
		orientation.elements.setData(3, 0);

		orientation.elements.setData(4, xaxis.getY());
		orientation.elements.setData(5, yaxis.getY());
		orientation.elements.setData(6, zaxis.getY());
		orientation.elements.setData(7, 0);

		orientation.elements.setData(8, xaxis.getZ());
		orientation.elements.setData(9, yaxis.getZ());
		orientation.elements.setData(10, zaxis.getZ());
		orientation.elements.setData(11, 0);

		orientation.elements.setData(12, 0);
		orientation.elements.setData(13, 0);
		orientation.elements.setData(14, 0);
		orientation.elements.setData(15, 1);
		return orientation;
	}

	public static Mat4f translation(Vec3f eye) {
		Mat4f translation = new Mat4f();
		translation.elements.setData(0, 1);
		translation.elements.setData(1, 0);
		translation.elements.setData(2, 0);
		translation.elements.setData(3, 0);

		translation.elements.setData(4, 0);
		translation.elements.setData(5, 1);
		translation.elements.setData(6, 0);
		translation.elements.setData(7, 0);

		translation.elements.setData(8, 0);
		translation.elements.setData(9, 0);
		translation.elements.setData(10, 1);
		translation.elements.setData(11, 0);

		translation.elements.setData(12, -eye.getX());
		translation.elements.setData(13, -eye.getY());
		translation.elements.setData(14, -eye.getZ());
		translation.elements.setData(15, 1);

		return translation;
	}

	public static Mat4f lookat(Vec3f eye, Vec3f target, Vec3f up) {
		Vec3f zaxis = eye.sub(target).normalized();
		Vec3f xaxis = up.cross(zaxis).normalized();
		Vec3f yaxis = zaxis.cross(xaxis);

		Mat4f orientation = orientation(xaxis, yaxis, zaxis);
		Mat4f translation = translation(eye);

		return orientation.multiply(translation);
	}

	public Mat4f scale(float x, float y, float z) {
		// setData(0, 0, x);
		// setData(1, 1, y);
		// setData(2, 2, z);
		// setData(3, 3, 1);
		Mat4f result = Mat4f.identity();
		result.setData(0, 0, getData(0, 0) * x);
		result.setData(0, 1, getData(0, 1) * x);
		result.setData(0, 2, getData(0, 2) * x);
		result.setData(0, 3, getData(0, 3) * x);

		result.setData(1, 0, getData(1, 0) * y);
		result.setData(1, 1, getData(1, 1) * y);
		result.setData(1, 2, getData(1, 2) * y);
		result.setData(1, 3, getData(1, 3) * y);

		result.setData(2, 0, getData(2, 0) * z);
		result.setData(2, 1, getData(2, 1) * z);
		result.setData(2, 2, getData(2, 2) * z);
		result.setData(2, 3, getData(2, 3) * z);

		result.setData(3, 0, getData(3, 0));
		result.setData(3, 1, getData(3, 1));
		result.setData(3, 2, getData(3, 2));
		result.setData(3, 3, getData(3, 3));

		return result;
	}

	public Mat4f scale(Vec3f scale) {
		return scale(scale.getX(), scale.getY(), scale.getZ());
	}

	public Mat4f rotate(Vec3f vec) {
		rotate(vec.getX(), 1, 0, 0);
		rotate(vec.getY(), 0, 1, 0);
		rotate(vec.getZ(), 0, 0, 1);
		return this;
	}

	public Mat4f rotate(float angle, FloatField x, FloatField y, FloatField z) {
		return rotate(angle, x.getData(), y.getData(), z.getData());
	}

	public Mat4f multiply(Mat4f m) {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.elements.getData()[x + e * 4] * m.elements.getData()[e + y * 4];
				}
				this.elements.setData(x + y * 4, sum);
			}
		}
		return this;
	}

	public Mat4f mul(Mat4f m) {
		// Mat4f result = Mat4f.identity();
		float nm00 = getData(0, 0) * m.getData(0, 0) + getData(1, 0) * m.getData(0, 1) + getData(2, 0) * m.getData(0, 2) + getData(3, 0) * m.getData(0, 3);
		float nm01 = getData(0, 1) * m.getData(0, 0) + getData(1, 1) * m.getData(0, 1) + getData(2, 1) * m.getData(0, 2) + getData(3, 1) * m.getData(0, 3);
		float nm02 = getData(0, 2) * m.getData(0, 0) + getData(1, 2) * m.getData(0, 1) + getData(2, 2) * m.getData(0, 2) + getData(3, 2) * m.getData(0, 3);
		float nm03 = getData(0, 3) * m.getData(0, 0) + getData(1, 3) * m.getData(0, 1) + getData(2, 3) * m.getData(0, 2) + getData(3, 3) * m.getData(0, 3);
		float nm10 = getData(0, 0) * m.getData(1, 0) + getData(1, 0) * m.getData(1, 1) + getData(2, 0) * m.getData(1, 2) + getData(3, 0) * m.getData(1, 3);
		float nm11 = getData(0, 1) * m.getData(1, 0) + getData(1, 1) * m.getData(1, 1) + getData(2, 1) * m.getData(1, 2) + getData(3, 1) * m.getData(1, 3);
		float nm12 = getData(0, 2) * m.getData(1, 0) + getData(1, 2) * m.getData(1, 1) + getData(2, 2) * m.getData(1, 2) + getData(3, 2) * m.getData(1, 3);
		float nm13 = getData(0, 3) * m.getData(1, 0) + getData(1, 3) * m.getData(1, 1) + getData(2, 3) * m.getData(1, 2) + getData(3, 3) * m.getData(1, 3);
		float nm20 = getData(0, 0) * m.getData(2, 0) + getData(1, 0) * m.getData(2, 1) + getData(2, 0) * m.getData(2, 2) + getData(3, 0) * m.getData(2, 3);
		float nm21 = getData(0, 1) * m.getData(2, 0) + getData(1, 1) * m.getData(2, 1) + getData(2, 1) * m.getData(2, 2) + getData(3, 1) * m.getData(2, 3);
		float nm22 = getData(0, 2) * m.getData(2, 0) + getData(1, 2) * m.getData(2, 1) + getData(2, 2) * m.getData(2, 2) + getData(3, 2) * m.getData(2, 3);
		float nm23 = getData(0, 3) * m.getData(2, 0) + getData(1, 3) * m.getData(2, 1) + getData(2, 3) * m.getData(2, 2) + getData(3, 3) * m.getData(2, 3);
		float nm30 = getData(0, 0) * m.getData(3, 0) + getData(1, 0) * m.getData(3, 1) + getData(2, 0) * m.getData(3, 2) + getData(3, 0) * m.getData(3, 3);
		float nm31 = getData(0, 1) * m.getData(3, 0) + getData(1, 1) * m.getData(3, 1) + getData(2, 1) * m.getData(3, 2) + getData(3, 1) * m.getData(3, 3);
		float nm32 = getData(0, 2) * m.getData(3, 0) + getData(1, 2) * m.getData(3, 1) + getData(2, 2) * m.getData(3, 2) + getData(3, 2) * m.getData(3, 3);
		float nm33 = getData(0, 3) * m.getData(3, 0) + getData(1, 3) * m.getData(3, 1) + getData(2, 3) * m.getData(3, 2) + getData(3, 3) * m.getData(3, 3);
		setData(0, 0, nm00);
		setData(0, 1, nm01);
		setData(0, 2, nm02);
		setData(0, 3, nm03);
		setData(1, 0, nm10);
		setData(1, 1, nm11);
		setData(1, 2, nm12);
		setData(1, 3, nm13);
		setData(2, 0, nm20);
		setData(2, 1, nm21);
		setData(2, 2, nm22);
		setData(2, 2, nm23);
		setData(3, 0, nm30);
		setData(3, 1, nm31);
		setData(3, 2, nm32);
		setData(3, 3, nm33);
		return this;
	}

	public static Mat4f perspective(float fov, float aspectRatio, float near, float far) {
		Mat4f result = identity();
		float q = 1.0f / (float) Math.tan(Math.toRadians(0.5f * fov));
		float a = q / aspectRatio;
		float b = (near + far) / (near - far);
		float c = (2.0f * near * far) / (near - far);
		result.setData(0, 0, a);
		result.setData(1, 1, q);
		result.setData(2, 2, b);
		result.setData(3, 2, -1.0f);
		result.setData(2, 3, c);

		return result;
	}

	public void setData(int x, int y, float value) {
		int index = x + y * 4;
		// if (index > 0 && index < elements.length() - 1)
		elements.setData(index, value);
	}

	public void setData(int index, float value) {
		elements.setData(index, value);
	}

	public float getData(int x, int y) {
		int index = x + y * 4;
		return elements.getData(index);
	}

	public FloatBuffer toFloatBuffer() {
		return createFloatBuffer(elements.getData());
	}

	private FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(array).flip();
		return result;
	}

	public void translate(float rotZ, Vec3f pos) {
		translate(pos);
		rotate(rotZ, 0, 1, 0);
	}
}
