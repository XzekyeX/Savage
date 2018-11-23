package net.teamfps.savage.math;

public class Vec4 {
	public final float kEpsilon = 1E-05f;

	public float x;

	public float y;

	public float z;

	public float w;

	public Vec4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public void Normalize() {
		float num = Vec4.Magnitude(this);
		if (num > 1E-05f) {
			this.x /= num;
			this.y /= num;
			this.z /= num;
			this.w /= num;
		} else {
			this.x = 0;
			this.y = 0;
			this.z = 0;
			this.w = 0;
		}
	}

	public float magnitude() {
		return (float) Math.sqrt(Vec4.Dot(this, this));
	}

	public static Vec4 Normalize(Vec4 a) {
		float num = Vec4.Magnitude(a);
		Vec4 result;
		if (num > 1E-05f) {
			result = Vec4.div(a, num);
		} else {
			result = Vec4.zero();
		}
		return result;
	}

	public static float Magnitude(Vec4 a) {
		return (float) Math.sqrt(Vec4.Dot(a, a));
	}

	public static float SqrMagnitude(Vec4 a) {
		return Vec4.Dot(a, a);
	}

	public static float Dot(Vec4 a, Vec4 b) {
		return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
	}

	public static Vec4 Project(Vec4 a, Vec4 b) {
		return Vec4.div(Vec4.mul(b, Vec4.Dot(a, b)), Vec4.Dot(b, b));
	}

	public static float Distance(Vec4 a, Vec4 b) {
		return Vec4.Magnitude(Vec4.sub(a, b));
	}

	public static Vec4 Lerp(Vec4 a, Vec4 b, float t) {
		t = Clamp01(t);
		return new Vec4(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t, a.z + (b.z - a.z) * t, a.w + (b.w - a.w) * t);
	}

	public static float Clamp01(float value) {
		float result;
		if (value < 0f) {
			result = 0f;
		} else if (value > 1f) {
			result = 1f;
		} else {
			result = value;
		}
		return result;
	}

	public static Vec4 LerpUnclamped(Vec4 a, Vec4 b, float t) {
		return new Vec4(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t, a.z + (b.z - a.z) * t, a.w + (b.w - a.w) * t);
	}

	public static Vec4 MoveTowards(Vec4 current, Vec4 target, float maxDistanceDelta) {
		Vec4 a = Vec4.sub(target, current);
		float magnitude = a.magnitude();
		Vec4 result;
		if (magnitude <= maxDistanceDelta || magnitude == 0f) {
			result = target;
		} else {
			result = Vec4.add(current, Vec4.div(a, magnitude * maxDistanceDelta));
		}
		return result;
	}

	public static Vec4 Scale(Vec4 a, Vec4 b) {
		return new Vec4(a.x * b.x, a.y * b.y, a.z * b.z, a.w * b.w);
	}

	public static Vec4 zero() {
		return new Vec4(0f, 0f, 0f, 0f);
	}

	public static Vec4 add(Vec4 a, Vec4 b) {
		return new Vec4(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w);
	}

	public static Vec4 sub(Vec4 a, Vec4 b) {
		return new Vec4(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w);
	}

	public static Vec4 sub(Vec4 a) {
		return new Vec4(-a.x, -a.y, -a.z, -a.w);
	}

	public static Vec4 mul(Vec4 a, float d) {
		return new Vec4(a.x * d, a.y * d, a.z * d, a.w * d);
	}

	public static Vec4 mul(float d, Vec4 a) {
		return new Vec4(a.x * d, a.y * d, a.z * d, a.w * d);
	}

	public static Vec4 div(Vec4 a, float d) {
		return new Vec4(a.x / d, a.y / d, a.z / d, a.w / d);
	}
}
