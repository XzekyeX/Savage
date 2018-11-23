package net.teamfps.savage.renderer.model.animation;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class JointTransform {
	private final Vector3f position;
	private final Quaternionf rotation;

	public JointTransform(Vector3f position, Quaternionf rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	protected Matrix4f getLocalTransform() {
		Matrix4f result = new Matrix4f();
		result.translate(position);
		result.rotate(rotation);
		return result;
	}

	protected static JointTransform interpolate(JointTransform frameA, JointTransform frameB, float progression) {
		Vector3f pos = interpolate(frameA.position, frameB.position, progression);
		Quaternionf rot = slerp(frameA.rotation, frameB.rotation, progression);
		return new JointTransform(pos, rot);
	}

	private static Vector3f interpolate(Vector3f position2, Vector3f position3, float progression) {

		return null;
	}

	public static Quaternionf slerp(Quaternionf q1, Quaternionf q2, float t) {
		Quaternionf qInterpolated = new Quaternionf();
		if (isEqual(q1, q2)) {
			return q1;
		}
		// Temporary array to hold second quaternion.
		float cosTheta = q1.x * q2.x + q1.y * q2.y + q1.z * q2.z + q1.w * q2.w;
		if (cosTheta < 0.0f) {
			// Flip sign if so.
			q2 = conjugate(q2);
			cosTheta = -cosTheta;
		}
		float beta = 1.0f - t;
		// Set the first and second scale for the interpolation
		float scale0 = 1.0f - t;
		float scale1 = t;
		if (1.0f - cosTheta > 0.1f) {
			// We are using spherical interpolation.
			float theta = (float) Math.acos(cosTheta);
			float sinTheta = (float) Math.sin(theta);
			scale0 = (float) Math.sin(theta * beta) / sinTheta;
			scale1 = (float) Math.sin(theta * t) / sinTheta;
		}
		// Interpolation.
		qInterpolated.x = scale0 * q1.x + scale1 * q2.x;
		qInterpolated.y = scale0 * q1.y + scale1 * q2.y;
		qInterpolated.z = scale0 * q1.z + scale1 * q2.z;
		qInterpolated.w = scale0 * q1.w + scale1 * q2.w;
		return qInterpolated;
	}

	public static Quaternionf conjugate(Quaternionf q1) {
		return new Quaternionf(-q1.x, -q1.y, -q1.z, q1.w);
	}

	public static boolean isEqual(Quaternionf q1, Quaternionf q2) {
		return (q1.x == q2.x && q1.y == q2.y && q1.z == q2.z && q1.w == q2.w);
	}

}
