package dotsgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

public class DotsBox extends JComponent {

	private static final long serialVersionUID = 1L;
	static final int PADDING = 12; // padding around dots grid
	static final int DOT_RADIUS = 7; // radius of dots
	private static final int FONT_SIZE = 36; // size of text glyphs
	private static final Color COL_DEF_DOT = Color.BLUE, 
			COL_DEF_LINE = Color.WHITE, 
			COL_DEF_CHAR = Color.GRAY;

	int h, w; // height and width of dots box
	int r, c; // no of rows and columns
	Color[] playercolors;
	DotsGame game;

	DotsBox(DotsGame game) {
		newGame(game);

	}

	void newGame(DotsGame game) {
		this.game = game;
		this.r = game.grid.length - 1;
		this.c = game.grid[0].length - 1;
		playercolors = new Color[game.players];
		int rgb;
		for (int i = 0; i < playercolors.length; i++) {
			rgb = Color.HSBtoRGB(i * 1.0f / playercolors.length, 1.0f, 0.5f);
			playercolors[i] = new Color(rgb);
		}
	}

	public void paintComponent(Graphics g) {
		h = getHeight() - 1 - 2 * PADDING;
		w = getWidth() - 1 - 2 * PADDING;

		float i, j;
		int a, b;
		g.setColor(COL_DEF_LINE);
		for (i = PADDING, a = 0; a <= r; i = i + (float) h / r, a++) // draw horizontal lines
		{
			g.drawLine(PADDING, (int) i, w + PADDING, (int) i);

		}
		for (j = PADDING, b = 0; b <= w; j = j + (float) w / c, b++) // draw vertical lines
		{
			g.drawLine((int) j, PADDING, (int) j, h + PADDING);
		}

		g.setColor(Color.BLACK);
		for (i = PADDING, a = 0; a <= r; i = i + (float) h / r, a++) // draw proper lines
		{
			try {
				for (j = PADDING, b = 0; b <= c; j = j + (float) w / c, b++) {
					if (b < c && game.grid[a][b].hasConnection(game.grid[a][b + 1]))
						g.drawLine((int) j, (int) i, (int) (j + (float) w / c), (int) i);
					if (a < r && game.grid[a][b].hasConnection(game.grid[a + 1][b]))
						g.drawLine((int) j, (int) i, (int) j, (int) (i + (float) h / r));
				}
			} catch (ArrayIndexOutOfBoundsException aear) {
				System.err.println("Exception at a=" + a + ", b=" + b);
			}
		}

		g.setColor(COL_DEF_DOT); // draw dots
		for (i = PADDING, a = 0; a <= r; i = i + (float) h / r, a++) {
			for (j = PADDING, b = 0; b <= w; j = j + (float) w / c, b++) {

				g.fillOval((int) j - DOT_RADIUS, (int) i - DOT_RADIUS, 2 * DOT_RADIUS, 2 * DOT_RADIUS);
			}
		}

		g.setFont(new Font("Arial Black", Font.PLAIN, FONT_SIZE));
		for (i = PADDING, a = 0; a < r; i = i + (float) h / r, a++) {
			try {
				for (j = PADDING, b = 0; b < c; j = j + (float) w / c, b++) {
					if (game.sq[a][b].getData() != DotsGame.MASK)
						g.setColor(playercolors[(int) (game.sq[a][b].getData() - '0') - 1]);
					else
						g.setColor(COL_DEF_CHAR);
					g.drawString(String.valueOf(game.sq[a][b]), (int) j + (int) ((float) w / c / 2 - FONT_SIZE / 4),
							(int) i + (int) ((float) h / r / 2 + FONT_SIZE / 4));
				}
			} catch (ArrayIndexOutOfBoundsException aear) {
				System.err.println("Exception at a=" + a + ", b=" + b);
			}
		}
	}
}
