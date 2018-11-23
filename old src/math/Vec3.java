package net.teamfps.savage.math;

public class Vec3 {
	public final float kEpsilon = 1E-05f;
	public final static float Epsilon = 1E-6f;
	public float x;

	public float y;

	public float z;

	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3(float x, float y) {
		this.x = x;
		this.y = y;
		this.z = 0f;
	}

	public Vec3 normalized() {
		return Vec3.Normalize(this);
	}

	public Vec3 multiply(float v) {
		return Vec3.mul(this, v);
	}

	public void Normalize() {
		float num = Vec3.Magnitude(this);
		if (num > 1E-05f) {
			this.x /= num;
			this.y /= num;
			this.z /= num;
		} else {
			this.x = 0;
			this.y = 0;
			this.z = 0;
		}
	}

	public float magnitude() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	public float sqrMagnitude() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	public static float Dot(Vec3 lhs, Vec3 rhs) {
		return lhs.x * rhs.x + lhs.y * rhs.y + lhs.z * rhs.z;
	}

	public static Vec3 Project(Vec3 vector, Vec3 onNormal) {
		float num = Vec3.Dot(onNormal, onNormal);
		Vec3 result;
		if (num < Epsilon) {
			result = Vec3.zero();
		} else {
			result = Vec3.mul(onNormal, Vec3.Dot(vector, onNormal) / num);
		}
		return result;
	}

	public static Vec3 ProjectOnPlane(Vec3 vector, Vec3 planeNormal) {
		return Vec3.sub(vector, Vec3.Project(vector, planeNormal));
	}

	public static float Angle(Vec3 from, Vec3 to) {
		return (float) (Math.acos(Clamp(Vec3.Dot(from.normalized(), to.normalized()), -1f, 1f)) * 57.29578f);
	}

	public static float Clamp(float value, float min, float max) {
		if (value < min) {
			value = min;
		} else if (value > max) {
			value = max;
		}
		return value;
	}

	public static float Distance(Vec3 a, Vec3 b) {
		Vec3 vector = new Vec3(a.x - b.x, a.y - b.y, a.z - b.z);
		return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
	}

	public static Vec3 ClampMagnitude(Vec3 vector, float maxLength) {
		Vec3 result;
		if (vector.sqrMagnitude() > maxLength * maxLength) {
			result = Vec3.mul(vector.normalized(), maxLength);
		} else {
			result = vector;
		}
		return result;
	}

	public static Vec3 zero() {
		return new Vec3(0f, 0f, 0f);
	}

	public static Vec3 one() {
		return new Vec3(1f, 1f, 1f);
	}

	public static Vec3 forward() {
		return new Vec3(0f, 0f, 1f);
	}

	public static Vec3 back() {
		return new Vec3(0f, 0f, -1f);
	}

	public static Vec3 up() {
		return new Vec3(0f, 1f, 0f);
	}

	public static Vec3 down() {
		return new Vec3(0f, -1f, 0f);
	}

	public static Vec3 left() {
		return new Vec3(-1f, 0f, 0f);
	}

	public static Vec3 right() {
		return new Vec3(1f, 0f, 0f);
	}

	public static Vec3 fwd() {
		return new Vec3(0f, 0f, 1f);
	}

	public static Vec3 Normalize(Vec3 value) {
		float num = Vec3.Magnitude(value);
		Vec3 result;
		if (num > 1E-05f) {
			result = Vec3.div(value, num);
		} else {
			result = Vec3.zero();
		}
		return result;
	}

	public static float Magnitude(Vec3 a) {
		return (float) Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
	}

	public static float SqrMagnitude(Vec3 a) {
		return a.x * a.x + a.y * a.y + a.z * a.z;
	}

	public static Vec3 add(Vec3 a, Vec3 b) {
		return new Vec3(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static Vec3 sub(Vec3 a, Vec3 b) {
		return new Vec3(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	public static Vec3 sub(Vec3 a) {
		return new Vec3(-a.x, -a.y, -a.z);
	}

	public static Vec3 mul(Vec3 a, float d) {
		return new Vec3(a.x * d, a.y * d, a.z * d);
	}

	public static Vec3 mul(float d, Vec3 a) {
		return new Vec3(a.x * d, a.y * d, a.z * d);
	}

	public static Vec3 div(Vec3 a, float d) {
		return new Vec3(a.x / d, a.y / d, a.z / d);
	}
}
