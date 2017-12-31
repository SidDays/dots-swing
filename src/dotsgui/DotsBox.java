package dotsgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

import dotsgui.dots.Dots;

public class DotsBox extends JComponent implements DotsConstants {
	private static final long serialVersionUID = 1L;
	private int h, w; // height and width of dots box

	// private int x1,y1; //corners of dots grid
	// private int x2, y2;

	private int r, c; // no of rows and columns
	Dots game;

	DotsBox(Dots game) {
		this.game = game;
		this.r = game.grid.length - 1;
		this.c = game.grid[0].length - 1;
	}

	void newGame(Dots game) {
		this.game = game;
		this.r = game.grid.length - 1;
		this.c = game.grid[0].length - 1;
	}

	DotsBox() {
		r = 3;
		c = 3;
	}

	public void paint(Graphics g) {
		h = getHeight() - 1 - 2 * PADDING;
		w = getWidth() - 1 - 2 * PADDING;

		// System.out.println(h+" "+w);
		// x1=0;
		// y1=0;
		// x2=x1+w;
		// y2=y1+h;

		// g.drawRect(0,0,w-1,h-1);

		float i, j;
		int a, b;
		g.setColor(Color.WHITE);
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

		g.setColor(Color.RED); // draw dots
		for (i = PADDING, a = 0; a <= r; i = i + (float) h / r, a++) {
			for (j = PADDING, b = 0; b <= w; j = j + (float) w / c, b++) {
				g.fillOval((int) j - DOT_RADIUS, (int) i - DOT_RADIUS, 2 * DOT_RADIUS, 2 * DOT_RADIUS);
			}
		}

		g.setColor(Color.BLUE); // player numbers
		g.setFont(new Font("Arial Black", Font.PLAIN, FONT_SIZE));
		for (i = PADDING, a = 0; a < r; i = i + (float) h / r, a++) {
			try {
				for (j = PADDING, b = 0; b < c; j = j + (float) w / c, b++) {
					g.drawString(String.valueOf(game.sq[a][b]), (int) j + (int) ((float) w / c / 2 - FONT_SIZE / 4),
							(int) i + (int) ((float) h / r / 2 + FONT_SIZE / 4));
				}
			} catch (ArrayIndexOutOfBoundsException aear) {
				System.err.println("Exception at a=" + a + ", b=" + b);
			}
		}
	}
}