package net.teamfps.savage.math;

public class Quaternion {
	private static final float radToDeg = (float) (180.0 / Math.PI);
	private static final float degToRad = (float) (Math.PI / 180.0);
	private static final float Rad2Deg = (float) (360.0 / (Math.PI * 2));
	private static final float kEpsilon = 1E-06f;
	public float x, y, z, w;

	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public float get(int index) {
		switch (index) {
			case 0:
				return this.x;
			case 1:
				return this.y;
			case 2:
				return this.z;
			case 3:
				return this.w;
			default:
				throw new IndexOutOfBoundsException("Invalid Quaternion Index!");
		}
	}

	public void set(int index, int value) {
		switch (index) {
			case 0:
				this.x = value;
				break;
			case 1:
				this.y = value;
				break;
			case 2:
				this.z = value;
				break;
			case 3:
				this.w = value;
				break;
			default:
				throw new IndexOutOfBoundsException("Invalid Quaternion Index!");
		}
	}

	public static Quaternion identity() {
		return new Quaternion(0f, 0f, 0f, 1f);
	}

	public Vec3 eulerAngles() {
		return InternalToEulerRad(this).multiply(radToDeg);
	}

	public float Length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public float LengthSquared() {
		return x * x + y * y + z * z + w * w;
	}

	public void Normalize() {
		float scale = 1.0f / Length();
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		this.w *= scale;
	}

	public static Quaternion Normalize(Quaternion q) {
		Quaternion result = new Quaternion(q.x, q.y, q.z, q.w);
		float scale = 1.0f / q.Length();
		result.x *= scale;
		result.y *= scale;
		result.w *= scale;
		result.z *= scale;
		return result;
	}

	private static Vec3 InternalToEulerRad(Quaternion rot) {
		float sqw = rot.w * rot.w;
		float sqx = rot.x * rot.x;
		float sqy = rot.y * rot.y;
		float sqz = rot.z * rot.z;
		float unit = sqx + sqy + sqz + sqw;
		float test = rot.x * rot.w - rot.y * rot.z;
		Vec3 v = Vec3.zero();
		if (test > 0.4995f * unit) {
			v.y = (float) (2f * Math.atan2(rot.y, rot.x));
			v.x = (float) Math.PI / 2;
			v.z = 0;
			return NormalizeAngles(v.multiply(Rad2Deg));
		}
		if (test < -0.4995f * unit) {
			v.y = (float) (-2f * Math.atan2(rot.y, rot.x));
			v.x = (float) -Math.PI / 2;
			v.z = 0;
			return NormalizeAngles(v.multiply(Rad2Deg));
		}
		Quaternion q = new Quaternion(rot.w, rot.z, rot.x, rot.y);
		v.y = (float) Math.atan2(2f * q.x * q.w + 2f * q.y * q.z, 1 - 2f * (q.z * q.z + q.w * q.w)); // Yaw
		v.x = (float) Math.asin(2f * (q.x * q.z - q.w * q.y)); // Pitch
		v.z = (float) Math.atan2(2f * q.x * q.y + 2f * q.z * q.w, 1 - 2f * (q.y * q.y + q.z * q.z)); // Roll
		return NormalizeAngles(v.multiply(Rad2Deg));
	}

	private static Vec3 NormalizeAngles(Vec3 angles) {
		angles.x = NormalizeAngle(angles.x);
		angles.y = NormalizeAngle(angles.y);
		angles.z = NormalizeAngle(angles.z);
		return angles;
	}

	private static float NormalizeAngle(float angle) {
		while (angle > 360)
			angle -= 360;
		while (angle < 0)
			angle += 360;
		return angle;
	}
}
