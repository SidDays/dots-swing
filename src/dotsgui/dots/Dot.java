package dotsgui.dots;

public class Dot {
	private Dot up, left, right, down;
	private int i, j;

	Dot(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public void setConnection(Dot d, DotsGame game) {
		if (hasConnection(d)) {
			System.err.println(this + " is already connected to " + d);
			game.lines--;
			game.turn--;
			game.player--;
			return;
		}
		if (d.i == i && d.j == j + 1) {
			right = d;
			d.left = this;
			return;
		}
		if (d.i == i + 1 && d.j == j) {
			down = d;
			d.up = this;
			return;
		}
		if (i == d.i + 1 && j == d.j) {
			up = d;
			d.down = this;
			return;
		}
		if (i == d.i && d.j + 1 == j) {
			left = d;
			d.right = this;
			return;
		}
		game.lines--;
		game.turn--;
		game.player--;
		System.err.println("Invalid connection.");
	}

	public boolean hasConnection(Dot d) {
		try {
			if (left == d || right == d || up == d || down == d)
				return true;
		} catch (NullPointerException npe) {
		}
		return false;
	}

	public String toString() {
		return ("(" + i + "," + j + ")");
	}
}