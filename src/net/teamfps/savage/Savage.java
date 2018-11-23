package net.teamfps.savage;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import net.teamfps.savage.renderer.FontRenderer;

/**
 * 
 * @author Zekye
 *
 */
public abstract class Savage implements Runnable {
	private static final int MAX_KEYS = 65536;
	private static final int MAX_BUTTONS = 20;
	private static boolean[] keys = new boolean[MAX_KEYS];
	private static boolean[] keyState = new boolean[MAX_KEYS];
	private static boolean[] keyTyped = new boolean[MAX_KEYS];
	private static boolean[] buttons = new boolean[MAX_BUTTONS];
	private static boolean[] buttonState = new boolean[MAX_BUTTONS];
	private static boolean[] buttonClicked = new boolean[MAX_BUTTONS];
	public static final int BUTTON_LEFT = 0;
	public static final int BUTTON_RIGHT = 1;
	public static final int BUTTON_MIDDLE = 2;
	public static final int KEY_SPACE = 32;
	public static final int KEY_APOSTROPHE = 39;
	public static final int KEY_COMMA = 44;
	public static final int KEY_MINUS = 45;
	public static final int KEY_PERIOD = 46;
	public static final int KEY_SLASH = 47;
	public static final int KEY_0 = 48;
	public static final int KEY_1 = 49;
	public static final int KEY_2 = 50;
	public static final int KEY_3 = 51;
	public static final int KEY_4 = 52;
	public static final int KEY_5 = 53;
	public static final int KEY_6 = 54;
	public static final int KEY_7 = 55;
	public static final int KEY_8 = 56;
	public static final int KEY_9 = 57;
	public static final int KEY_SEMICOLON = 59;
	public static final int KEY_EQUAL = 61;
	public static final int KEY_A = 65;
	public static final int KEY_B = 66;
	public static final int KEY_C = 67;
	public static final int KEY_D = 68;
	public static final int KEY_E = 69;
	public static final int KEY_F = 70;
	public static final int KEY_G = 71;
	public static final int KEY_H = 72;
	public static final int KEY_I = 73;
	public static final int KEY_J = 74;
	public static final int KEY_K = 75;
	public static final int KEY_L = 76;
	public static final int KEY_M = 77;
	public static final int KEY_N = 78;
	public static final int KEY_O = 79;
	public static final int KEY_P = 80;
	public static final int KEY_Q = 81;
	public static final int KEY_R = 82;
	public static final int KEY_S = 83;
	public static final int KEY_T = 84;
	public static final int KEY_U = 85;
	public static final int KEY_V = 86;
	public static final int KEY_W = 87;
	public static final int KEY_X = 88;
	public static final int KEY_Y = 89;
	public static final int KEY_Z = 90;
	public static final int KEY_LEFT_BRACKET = 91;
	public static final int KEY_BACKSLASH = 92;
	public static final int KEY_RIGHT_BRACKET = 93;
	public static final int KEY_GRAVE_ACCENT = 96;
	public static final int KEY_WORLD_1 = 161;
	public static final int KEY_WORLD_2 = 162;
	public static final int KEY_ESCAPE = 256;
	public static final int KEY_ENTER = 10;
	public static final int KEY_TAB = 9;
	public static final int KEY_BACKSPACE = 8;
	public static final int KEY_INSERT = 260;
	public static final int KEY_DELETE = 261;
	public static final int KEY_RIGHT = 262;
	public static final int KEY_LEFT = 263;
	public static final int KEY_DOWN = 264;
	public static final int KEY_UP = 265;
	public static final int KEY_PAGE_UP = 266;
	public static final int KEY_PAGE_DOWN = 267;
	public static final int KEY_HOME = 268;
	public static final int KEY_END = 269;
	public static final int KEY_CAPS_LOCK = 280;
	public static final int KEY_SCROLL_LOCK = 281;
	public static final int KEY_NUM_LOCK = 282;
	public static final int KEY_PRINT_SCREEN = 283;
	public static final int KEY_PAUSE = 284;
	public static final int KEY_F1 = 290;
	public static final int KEY_F2 = 291;
	public static final int KEY_F3 = 292;
	public static final int KEY_F4 = 293;
	public static final int KEY_F5 = 294;
	public static final int KEY_F6 = 295;
	public static final int KEY_F7 = 296;
	public static final int KEY_F8 = 297;
	public static final int KEY_F9 = 298;
	public static final int KEY_F10 = 299;
	public static final int KEY_F11 = 300;
	public static final int KEY_F12 = 301;
	public static final int KEY_F13 = 302;
	public static final int KEY_F14 = 303;
	public static final int KEY_F15 = 304;
	public static final int KEY_F16 = 305;
	public static final int KEY_F17 = 306;
	public static final int KEY_F18 = 307;
	public static final int KEY_F19 = 308;
	public static final int KEY_F20 = 309;
	public static final int KEY_F21 = 310;
	public static final int KEY_F22 = 311;
	public static final int KEY_F23 = 312;
	public static final int KEY_F24 = 313;
	public static final int KEY_F25 = 314;
	public static final int KEY_KP_0 = 320;
	public static final int KEY_KP_1 = 321;
	public static final int KEY_KP_2 = 322;
	public static final int KEY_KP_3 = 323;
	public static final int KEY_KP_4 = 324;
	public static final int KEY_KP_5 = 325;
	public static final int KEY_KP_6 = 326;
	public static final int KEY_KP_7 = 327;
	public static final int KEY_KP_8 = 328;
	public static final int KEY_KP_9 = 329;
	public static final int KEY_KP_DECIMAL = 330;
	public static final int KEY_KP_DIVIDE = 331;
	public static final int KEY_KP_MULTIPLY = 332;
	public static final int KEY_KP_SUBTRACT = 333;
	public static final int KEY_KP_ADD = 334;
	public static final int KEY_KP_ENTER = 335;
	public static final int KEY_KP_EQUAL = 336;
	public static final int KEY_LEFT_SHIFT = 340;
	public static final int KEY_LEFT_CONTROL = 341;
	public static final int KEY_LEFT_ALT = 342;
	public static final int KEY_LEFT_SUPER = 343;
	public static final int KEY_RIGHT_SHIFT = 344;
	public static final int KEY_RIGHT_CONTROL = 345;
	public static final int KEY_RIGHT_ALT = 346;
	public static final int KEY_RIGHT_SUPER = 347;
	public static final int KEY_MENU = 348;
	public static final int KEY_LAST = 348;
	public static int MOUSE_X, MOUSE_Y, MOUSE_DX, MOUSE_DY, MOUSE_WHEEL;
	private static float mouseX, mouseY;
	public static boolean MOUSE_ENTERED = false;
	protected Thread thread;
	protected boolean running;
	protected boolean resizable;
	protected int width, height;
	protected long window;
	protected final String VERSION = "V.1.0";
	protected final String ENGINE = "Savage";
	protected String title = "";
	private FontRenderer fontRenderer;

	public Savage(String title, int width, int height, boolean resizable) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.resizable = resizable;
	}

	private boolean initialize() {
		if (createWindow()) {
			// try {
			// JarLoader loader = new JarLoader("C:/eclipse/workspace/Savage/natives/jars", "lwjgl-natives-windows.jar");
			// if (loader.extract()) {
			// loader.loadNativeFiles();
			// }
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			fontRenderer = new FontRenderer(new Texture("/font/ascii.png"), false, false);
			init();
			return true;
		}
		return false;
	}

	private boolean createWindow() {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW!");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		window = glfwCreateWindow(width, height, title + " [" + ENGINE + " " + VERSION + "]", NULL, NULL);
		if (window == NULL) throw new RuntimeException("Failed to create the GLFW window!");
		GLFWVidMode vm = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vm.width() - width) / 2, (vm.height() - height) / 2);
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);
		GL.createCapabilities();
		glClearColor(0.0f, 0.5f, 0.8f, 1.0f);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> Keyboard(window, key, scancode, action, mods));
		glfwSetMouseButtonCallback(window, (window, button, action, mods) -> Mouse(window, button, action, mods));
		glfwSetScrollCallback(window, (window, xoffset, yoffset) -> Scroll(window, xoffset, yoffset));
		glfwSetCursorPosCallback(window, (window, xpos, ypos) -> Cursor(window, xpos, ypos));
		glfwSetWindowSizeCallback(window, (window, width, height) -> Resize(window, width, height));
		glfwSetCursorEnterCallback(window, (window, entered) -> Focus(window, entered));
		StateManager.ortho(width, height);
		return true;
	}

	private void Focus(long window, boolean entered) {
		MOUSE_ENTERED = entered;
	}

	private void Scroll(long window, double xoffset, double yoffset) {
		// System.out.println("Scroll Offset(" + xoffset + ", " + yoffset + ")");
		MOUSE_WHEEL = (int) yoffset;
	}

	private void Resize(long window, int width, int height) {
		this.width = width;
		this.height = height;
		StateManager.ortho(width, height);
		System.out.println("Window Resized(" + width + "x" + height + ")");
		onResize();
	}

	private void Keyboard(long window, int key, int scancode, int action, int mods) {
		// System.out.println("action: " + action + ", key: " + key);
		keys[key] = action != GLFW_RELEASE;
	}

	private void Mouse(long window, int button, int action, int mods) {
		buttons[button] = action != GLFW_RELEASE;
	}

	private void Cursor(long window, double xpos, double ypos) {
		MOUSE_DX = (int) (xpos - MOUSE_X);
		MOUSE_DX = (int) (ypos - MOUSE_Y);
		MOUSE_X = (int) xpos;
		MOUSE_Y = (int) ypos;
		float normX = (float) ((xpos - width / 2.0) / width * 2.0);
		float normY = (float) ((ypos - height / 2.0) / height * 2.0);
		mouseX = Math.max(-width / 2.0f, Math.min(width / 2.0f, normX));
		mouseY = Math.max(-height / 2.0f, Math.min(height / 2.0f, normY));
	}

	public void start() {
		if (running) return;
		running = true;
		thread = new Thread(this, "Savage Thread!");
		thread.start();
	}

	public void stop() {
		if (!running) return;
		running = false;
		thread.interrupt();
	}

	@Override
	public void run() {
		long timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double delta = 0;
		double ns = 1000000000 / 60.0;
		int fps = 0;
		int ups = 0;
		if (!initialize()) return;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			setDelta(delta);
			while (delta >= 1) {
				delta--;
				ups++;
				tick();
			}
			StateManager.clear();
			draw();
			fps++;
			glfwSwapBuffers(window);
			glfwPollEvents();
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				setFpsAndUps(fps, ups);
				fps = 0;
				ups = 0;
			}
			if (glfwWindowShouldClose(window)) {
				glfwTerminate();
				stop();
				System.exit(1);
			}
		}
	}

	private static double delta;

	private void setDelta(double delta) {
		Savage.delta = delta;
	}

	private int fps, ups;

	private void setFpsAndUps(int fps, int ups) {
		this.fps = fps;
		this.ups = ups;
	}

	public String getFpsAndUps() {
		return "fps[" + fps + "], ups[" + ups + "]";
	}

	private void tick() {
		updateKeyboard();
		updateMouse();
		update();
	}

	private void updateKeyboard() {
		for (int i = 0; i < MAX_KEYS; i++)
			keyTyped[i] = keys[i] && !keyState[i];
		for (int i = 0; i < MAX_KEYS; i++)
			keyState[i] = keys[i];
	}

	private void updateMouse() {
		for (int i = 0; i < MAX_BUTTONS; i++)
			buttonClicked[i] = buttons[i] && !buttonState[i];
		for (int i = 0; i < MAX_BUTTONS; i++)
			buttonState[i] = buttons[i];
	}

	private float fov = 60;
	private float near = 1;
	private float far = -1;

	private void draw() {
		StateManager.perspective(fov, (float) width / (float) height, near, far);
		renderPerspective();
		StateManager.ortho(width, height);
		renderOrtho();
	}

	public abstract void init();

	public abstract void update();

	public abstract void renderOrtho();

	public abstract void renderPerspective();

	public abstract void onResize();

	public void setPerspective(float fov, float near, float far) {
		this.fov = fov;
		this.near = near;
		this.far = far;
	}

	public static boolean isKeyDown(int key) {
		if (key >= 0 && key <= keys.length - 1) return keys[key];
		return false;
	}

	public static boolean isKeyTyped(int key) {
		if (key >= 0 && key <= keyTyped.length - 1) return keyTyped[key];
		return false;
	}

	public static boolean isButtonDown(int key) {
		if (key >= 0 && key <= buttons.length - 1) return buttons[key];
		return false;
	}

	public static boolean isButtonClicked(int key) {
		if (key >= 0 && key <= buttonClicked.length - 1) return buttonClicked[key];
		return false;
	}

	public static float getMouseX() {
		return mouseX;
	}

	public static float getMouseY() {
		return mouseY;
	}

	public static double getDelta() {
		return delta;
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
}