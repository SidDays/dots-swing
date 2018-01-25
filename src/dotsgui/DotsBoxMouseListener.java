package dotsgui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DotsBoxMouseListener implements MouseListener {
	private static final int DOT_MASK = DotsBox.DOT_RADIUS + 4; // radius of the clickable area of dot

	DotsGame game;
	DotsBox box;
	DotsGUI frame;
	short counter;
	int x, y;
	int r, c, h, w;

	DotsBoxMouseListener(DotsGame game, DotsBox box, DotsGUI frame) {
		this.game = game;
		this.box = box;
		this.frame = frame;
		this.h = box.h;
		this.w = box.w;
		this.r = box.r;
		this.c = box.c;
		counter = 0;
	}

	public void mouseClicked(MouseEvent me) {
		if (game.isGameOver())
			return;
		x = me.getX();
		y = me.getY();
		this.h = box.h;
		this.w = box.w;
		this.r = box.r;
		this.c = box.c;

		int a = -1, b = -1;
		float i, j;

		// System.out.println("Point clicked on: "+x+","+y);
		if (counter == 0) {
			for (i = DotsBox.PADDING, a = 0; a <= r; i = i + (float) h / r, a++) {
				for (j = DotsBox.PADDING, b = 0; b <= w; j = j + (float) w / c, b++) {
					if ((x >= (int) j - DOT_MASK && y >= (int) i - DOT_MASK)
							&& (x <= (int) j + DOT_MASK && y <= (int) i + DOT_MASK)) {
						System.out.println("First dot selected: " + a + "," + b);
						frame.field1.setText(String.valueOf(a));
						frame.field2.setText(String.valueOf(b));
						if (frame.field1.getText() != null && frame.field2.getText() != null)
							frame.connect.requestFocus();
						counter++;
						break;
					}
				}
			}
		} else {
			for (i = DotsBox.PADDING, a = 0; a <= r; i = i + (float) h / r, a++) {
				for (j = DotsBox.PADDING, b = 0; b <= w; j = j + (float) w / c, b++) {
					if ((x >= (int) j - DOT_MASK && y >= (int) i - DOT_MASK)
							&& (x <= (int) j + DOT_MASK && y <= (int) i + DOT_MASK)) {
						System.out.println("Second dot selected: " + a + "," + b);
						frame.field3.setText(String.valueOf(a));
						frame.field4.setText(String.valueOf(b));
						frame.connect.requestFocus();
						counter--;
						break;
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent me) {
	}

	public void mouseExited(MouseEvent me) {
	}

	public void mousePressed(MouseEvent me) {
	}

	public void mouseReleased(MouseEvent me) {
	}
}
