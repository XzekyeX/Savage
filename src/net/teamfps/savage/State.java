package net.teamfps.savage;

import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * @author Zekye
 *
 */
public enum State {
	CULL_FACE(GL_CULL_FACE), TEXTURE_2D(GL_TEXTURE_2D), BLEND(GL_BLEND), DEPTH_TEST(GL_DEPTH_TEST);
	private int state;

	private State(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void enable() {
		glEnable(state);
	}

	public void disable() {
		glDisable(state);
	}
}