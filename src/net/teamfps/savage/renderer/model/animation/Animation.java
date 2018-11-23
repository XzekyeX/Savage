package net.teamfps.savage.renderer.model.animation;

public class Animation {
	private final float length; // Seconds
	private final KeyFrame[] keyFrames;

	public Animation(float length, KeyFrame[] keyFrames) {
		this.length = length;
		this.keyFrames = keyFrames;
	}
}
