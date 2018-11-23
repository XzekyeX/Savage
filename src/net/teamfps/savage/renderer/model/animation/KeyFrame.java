package net.teamfps.savage.renderer.model.animation;

import java.util.Map;

public class KeyFrame {
	private final float timeStamp;
	private final Map<String, JointTransform> pose;

	public KeyFrame(float timeStamp, Map<String, JointTransform> pose) {
		this.pose = pose;
		this.timeStamp = timeStamp;
	}

	protected float getTimeStamp() {
		return timeStamp;
	}

	protected Map<String, JointTransform> getJointKeyFrames() {
		return pose;
	}
}
