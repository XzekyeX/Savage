package net.teamfps.savage.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Random;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;

import net.teamfps.savage.State;
import net.teamfps.savage.StateManager;
import net.teamfps.savage.Texture;

/**
 * 
 * @author Zekye
 *
 */
public class FontRenderer {
	/** Array of width of all the characters in default.png */

	private final int[] charWidth = new int[256];
	private final int[] colorCode = new int[32];
	private final byte[] glyphWidth = new byte[65536];
	private final Texture texture;
	private Random fontRandom = new Random();
	private float posX;
	private float posY;
	private Texture[] UNICODE_PAGE_LOCATIONS = new Texture[256];
	private boolean unicodeFlag;
	private boolean bidiFlag;
	private boolean randomStyle;
	private boolean boldStyle;
	private boolean italicStyle;
	private boolean underlineStyle;
	private boolean strikethroughStyle;
	private float red;
	private float blue;
	private float green;
	private float alpha;
	// private int textColor;
	public int FONT_HEIGHT = 9;

	public FontRenderer(Texture texture, boolean unicode, boolean anaglyph) {
		this.texture = texture;
		this.unicodeFlag = unicode;
		this.texture.bind();
		for (int i = 0; i < 32; ++i) {
			int j = (i >> 3 & 1) * 85;
			int k = (i >> 2 & 1) * 170 + j;
			int l = (i >> 1 & 1) * 170 + j;
			int i1 = (i >> 0 & 1) * 170 + j;

			if (i == 6) {
				k += 85;
			}
			if (anaglyph) {
				int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
				int k1 = (k * 30 + l * 70) / 100;
				int l1 = (k * 30 + i1 * 70) / 100;
				k = j1;
				l = k1;
				i1 = l1;
			}
			if (i >= 16) {
				k /= 4;
				l /= 4;
				i1 /= 4;
			}

			this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
		}
		this.readGlyphSizes("/font/glyph_sizes.bin");
		this.readFontTexture();
	}

	private void readFontTexture() {
		int w = texture.getWidth();
		int h = texture.getHeight();
		int hh = h / 16;
		int ww = w / 16;
		float f = 8.0f / (float) ww;
		for (int i = 0; i < 256; ++i) {
			int j1 = i % 16;
			int k1 = i / 16;
			if (i == 32) this.charWidth[i] = 4;
			int l1;
			for (l1 = ww - 1; l1 >= 0; --l1) {
				int i2 = j1 * ww + l1;
				boolean flag1 = true;
				for (int j = 0; j < hh && flag1; ++j) {
					int k2 = (k1 * ww + j) * w;
					if ((texture.getPixels()[i2 + k2] >> 24 & 255) != 0) {
						flag1 = false;
					}
				}
				if (!flag1) {
					break;
				}
			}
			++l1;
			this.charWidth[i] = (int) (0.5D + (double) ((float) l1 * f)) + 1;
		}
	}

	private void readGlyphSizes(String path) {
		InputStream reader = getClass().getResourceAsStream(path);
		try {
			reader.read(this.glyphWidth);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public int drawCenteredString(String text, float x, float y, int color, boolean dropShadow) {
		return drawString(text, (float) (x - getStringWidth(text) / 2), y, color, dropShadow);
	}

	public int drawString(String text, float x, float y, int color, boolean dropShadow) {
		int i;
		if (dropShadow) {
			i = this.renderString(text, x + 1.0F, y + 1.0F, color, true);
			i = Math.max(i, this.renderString(text, x, y, color, false));
		} else {
			i = this.renderString(text, x, y, color, false);
		}
		return i;
	}

	public int drawString(String text, float fsize, float x, float y, int color, boolean dropShadow) {
		int i;
		StateManager.push();
		StateManager.translate(-fsize * FONT_HEIGHT, -fsize * FONT_HEIGHT, 0);
		StateManager.scale(fsize, fsize, fsize);
		i = drawString(text, x, y, color, dropShadow);
		StateManager.pop();
		return i;
	}

	private String bidiReorder(String text) {
		try {
			Bidi bidi = new Bidi((new ArabicShaping(8)).shape(text), 127);
			bidi.setReorderingMode(0);
			return bidi.writeReordered(2);
		} catch (ArabicShapingException var3) {
			return text;
		}
	}

	private int renderString(String text, float x, float y, int color, boolean dropShadow) {
		if (text == null) {
			return 0;
		} else {
			if (this.bidiFlag) {
				text = this.bidiReorder(text);
			}

			if ((color & -67108864) == 0) {
				color |= -16777216;
			}

			if (dropShadow) {
				color = (color & 16579836) >> 2 | color & -16777216;
			}
			this.red = (float) (color >> 16 & 255) / 255.0F;
			this.blue = (float) (color >> 8 & 255) / 255.0F;
			this.green = (float) (color & 255) / 255.0F;
			this.alpha = (float) (color >> 24 & 255) / 255.0F;
			glColor4f(this.red, this.blue, this.green, this.alpha);
			this.posX = x;
			this.posY = y;
			this.renderStringAtPos(text, dropShadow);
			return (int) this.posX;
		}
	}

	private void renderStringAtPos(String text, boolean shadow) {
		State.BLEND.enable();
		for (int i = 0; i < text.length(); ++i) {
			char c0 = text.charAt(i);

			if (c0 == 167 && i + 1 < text.length()) {
				int i1 = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

				if (i1 < 16) {
					this.randomStyle = false;
					this.boldStyle = false;
					this.strikethroughStyle = false;
					this.underlineStyle = false;
					this.italicStyle = false;

					if (i1 < 0 || i1 > 15) {
						i1 = 15;
					}

					if (shadow) {
						i1 += 16;
					}

					int j1 = this.colorCode[i1];
					// this.textColor = j1;
					glColor4f((float) (j1 >> 16) / 255.0F, (float) (j1 >> 8 & 255) / 255.0F, (float) (j1 & 255) / 255.0F, this.alpha);
				} else if (i1 == 16) {
					this.randomStyle = true;
				} else if (i1 == 17) {
					this.boldStyle = true;
				} else if (i1 == 18) {
					this.strikethroughStyle = true;
				} else if (i1 == 19) {
					this.underlineStyle = true;
				} else if (i1 == 20) {
					this.italicStyle = true;
				} else if (i1 == 21) {
					this.randomStyle = false;
					this.boldStyle = false;
					this.strikethroughStyle = false;
					this.underlineStyle = false;
					this.italicStyle = false;
					glColor4f(this.red, this.blue, this.green, this.alpha);
				}
				++i;
			} else {
				int j = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(c0);

				if (this.randomStyle && j != -1) {
					int k = this.getCharWidth(c0);
					char c1;

					while (true) {
						j = this.fontRandom.nextInt("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".length());
						c1 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".charAt(j);

						if (k == this.getCharWidth(c1)) {
							break;
						}
					}

					c0 = c1;
				}

				float f1 = this.unicodeFlag ? 0.5F : 1.0F;
				boolean flag = (c0 == 0 || j == -1 || this.unicodeFlag) && shadow;

				if (flag) {
					this.posX -= f1;
					this.posY -= f1;
				}

				float f = this.renderChar(c0, this.italicStyle);

				if (flag) {
					this.posX += f1;
					this.posY += f1;
				}

				if (this.boldStyle) {
					this.posX += f1;

					if (flag) {
						this.posX -= f1;
						this.posY -= f1;
					}

					this.renderChar(c0, this.italicStyle);
					this.posX -= f1;

					if (flag) {
						this.posX += f1;
						this.posY += f1;
					}

					++f;
				}

				if (this.strikethroughStyle) {
					Tessellator tessellator = Tessellator.getInstance();
					VertexBuffer vertexbuffer = tessellator.getBuffer();
					State.TEXTURE_2D.disable();
					vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
					vertexbuffer.pos((double) this.posX, (double) (this.posY + (float) (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
					vertexbuffer.pos((double) (this.posX + f), (double) (this.posY + (float) (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
					vertexbuffer.pos((double) (this.posX + f), (double) (this.posY + (float) (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
					vertexbuffer.pos((double) this.posX, (double) (this.posY + (float) (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
					tessellator.draw();
					State.TEXTURE_2D.enable();
				}

				if (this.underlineStyle) {
					Tessellator tessellator1 = Tessellator.getInstance();
					VertexBuffer vertexbuffer1 = tessellator1.getBuffer();
					State.TEXTURE_2D.disable();
					vertexbuffer1.begin(7, DefaultVertexFormats.POSITION);
					int l = this.underlineStyle ? -1 : 0;
					vertexbuffer1.pos((double) (this.posX + (float) l), (double) (this.posY + (float) this.FONT_HEIGHT), 0.0D).endVertex();
					vertexbuffer1.pos((double) (this.posX + f), (double) (this.posY + (float) this.FONT_HEIGHT), 0.0D).endVertex();
					vertexbuffer1.pos((double) (this.posX + f), (double) (this.posY + (float) this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
					vertexbuffer1.pos((double) (this.posX + (float) l), (double) (this.posY + (float) this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
					tessellator1.draw();
					State.TEXTURE_2D.enable();
				}

				this.posX += (float) ((int) f);
			}
		}
	}

	public int getStringWidth(String text) {
		if (text == null) {
			return 0;
		} else {
			int i = 0;
			boolean flag = false;

			for (int j = 0; j < text.length(); ++j) {
				char c0 = text.charAt(j);
				int k = this.getCharWidth(c0);

				if (k < 0 && j < text.length() - 1) {
					++j;
					c0 = text.charAt(j);

					if (c0 != 108 && c0 != 76) {
						if (c0 == 114 || c0 == 82) {
							flag = false;
						}
					} else {
						flag = true;
					}

					k = 0;
				}

				i += k;

				if (flag && k > 0) {
					++i;
				}
			}

			return i;
		}
	}

	public int getCharWidth(char character) {
		if (character == 167) {
			return -1;
		} else if (character == 32) {
			return 4;
		} else {
			int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(character);

			if (character > 0 && i != -1 && !this.unicodeFlag) {
				return this.charWidth[i];
			} else if (this.glyphWidth[character] != 0) {
				int j = this.glyphWidth[character] & 255;
				int k = j >>> 4;
				int l = j & 15;
				++l;
				return (l - k) / 2 + 1;
			} else {
				return 0;
			}
		}
	}

	private float renderChar(char ch, boolean italic) {
		if (ch == 32) {
			return 4.0F;
		} else {
			int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(ch);
			return i != -1 && !this.unicodeFlag ? this.renderDefaultChar(i, italic) : this.renderUnicodeChar(ch, italic);
		}
	}

	private float renderDefaultChar(int ch, boolean italic) {
		int i = ch % 16 * 8;
		int j = ch / 16 * 8;
		int k = italic ? 1 : 0;
		texture.bind();
		int l = this.charWidth[ch];
		float f = (float) l - 0.01F;
		glBegin(GL_TRIANGLE_STRIP);
		glTexCoord2f((float) i / 128.0F, (float) j / 128.0F);
		glVertex3f(this.posX + (float) k, this.posY, 0.0F);
		glTexCoord2f((float) i / 128.0F, ((float) j + 7.99F) / 128.0F);
		glVertex3f(this.posX - (float) k, this.posY + 7.99F, 0.0F);
		glTexCoord2f(((float) i + f - 1.0F) / 128.0F, (float) j / 128.0F);
		glVertex3f(this.posX + f - 1.0F + (float) k, this.posY, 0.0F);
		glTexCoord2f(((float) i + f - 1.0F) / 128.0F, ((float) j + 7.99F) / 128.0F);
		glVertex3f(this.posX + f - 1.0F - (float) k, this.posY + 7.99F, 0.0F);
		glEnd();
		return (float) l;
	}

	private Texture getUnicodePageLocation(int page) {
		if (UNICODE_PAGE_LOCATIONS[page] == null) {
			String path = String.format("/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(page) });
			UNICODE_PAGE_LOCATIONS[page] = new Texture(path);
		}
		return UNICODE_PAGE_LOCATIONS[page];
	}

	private void loadGlyphTexture(int page) {
		Texture t = getUnicodePageLocation(page);
		if (t != null) t.bind();
	}

	private float renderUnicodeChar(char ch, boolean italic) {
		int i = this.glyphWidth[ch] & 255;
		if (i == 0) {
			return 0.0F;
		} else {
			int j = ch / 256;
			this.loadGlyphTexture(j);
			int k = i >>> 4;
			int l = i & 15;
			float f = (float) k;
			float f1 = (float) (l + 1);
			float f2 = (float) (ch % 16 * 16) + f;
			float f3 = (float) ((ch & 255) / 16 * 16);
			float f4 = f1 - f - 0.02F;
			float f5 = italic ? 1.0F : 0.0F;
			glBegin(GL_TRIANGLE_STRIP);
			glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
			glVertex3f(this.posX + f5, this.posY, 0.0F);
			glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
			glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
			glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
			glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
			glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
			glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
			glEnd();
			return (f1 - f) / 2.0F + 1.0F;
		}
	}
}
