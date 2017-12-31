package dotsgui.dots;

import java.util.Scanner;

import dotsgui.DotsConstants;

public class Dots implements DotsConstants {
	public int turn;
	public int lines;
	public int lines_max;
	public short player;
	private short players;
	private int score[];
	public Dot grid[][];
	public Square sq[][];

	public Dots(int m, int n, int players) {
		turn = 1;
		player = 1;
		lines = 0;
		this.players = (short) players;
		score = new int[players];
		lines_max = m - 1 + n - 1 + 2 * (m - 1) * (n - 1);

		grid = new Dot[m][n];
		int i, j;
		for (i = 0; i < m; i++)
			for (j = 0; j < n; j++)
				grid[i][j] = new Dot(i, j);
		sq = new Square[m - 1][n - 1];
		for (i = 0; i < m - 1; i++)
			for (j = 0; j < n - 1; j++)
				sq[i][j] = new Square(i, j);
		for (i = 0; i < score.length; i++)
			score[i] = 0;
	}

	boolean isGameOver() {
		if (lines >= lines_max)
			return true;
		return false;
	}

	boolean canBeFilled(Square s) {

		if (s.getData() != MASK)// KEEP TRACK OF THIS
			return false;
		int i, j;
		i = s.getX();
		j = s.getY();
		try {
			if (grid[i][j].hasConnection(grid[i][j + 1]) && grid[i][j].hasConnection(grid[i + 1][j])
					&& grid[i + 1][j].hasConnection(grid[i + 1][j + 1])
					&& grid[i][j + 1].hasConnection(grid[i + 1][j + 1])) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException aioob) {
		}
		return false;
	}

	public void updateSquares() {
		boolean justwon = false;
		try {
			for (int i = 0; i < sq.length; i++) {
				for (int j = 0; j < sq[0].length; j++) {
					if (canBeFilled(sq[i][j])) {
						justwon = true;
						score[player - 1]++;
						sq[i][j].setData((char) String.valueOf(player).charAt(0));

					}
				}

			}
		} catch (ArrayIndexOutOfBoundsException aioob) {
			System.err.println("array index out of bounds during update (!)");
		} finally {
			if (justwon) {
				System.out.println("Congrats player " + player + ", you go dude!");
				player--;
			}
		}
	}

	public void whosPlayin() {
		lines++;
		player = (short) (player % players + 1);
		turn++;
	}

	void sayHi() {
		System.out.println(
				"Turn " + turn + ", (" + lines + "/" + lines_max + " done) - Your turn, player " + player + "!");
	}

	public void disp() {
		int m = grid.length;
		int n = grid[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(".");
				try {
					if (j < n - 1 && grid[i][j].hasConnection(grid[i][j + 1]))
						System.out.print("___");
					else
						System.out.print("   ");
				} catch (NullPointerException npe) {
					System.out.print("   ");
				}

			}
			System.out.println();
			if (i < m - 1) {
				for (int j = 0; j < n; j++) {
					try {
						if (grid[i][j].hasConnection(grid[i + 1][j]))
							System.out.print("|");
						else
							System.out.print(" ");
					} catch (NullPointerException npe) {
						System.out.print(" ");
					}
					if (j < n - 1)
						System.out.print(" " + sq[i][j] + " ");
				}
				System.out.println();
			}
		}
	}

	void getScore() {
		System.out.println("\nPLAYA\tSCORE\t");
		for (int i = 0; i < score.length; i++)
			System.out.println((i + 1) + "\t" + score[i]);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int x, y;
		int z, w;
		int p;
		char ch;

		System.out.print("How many of y'all are playing? ");
		p = sc.nextInt();
		System.out.print("Enter the no. of rows and columns: ");
		x = sc.nextInt();
		y = sc.nextInt();
		Dots game = new Dots(x + 1, y + 1, p);
		sc.nextLine();
		do {
			System.out.println("\nThe grid is currently...");
			game.disp();
			game.sayHi();
			System.out.println("a. Make a connection");
			System.out.println("b. Read the player score");
			System.out.println("z. Leave");
			System.out.print("Pick one: ");

			try {
				ch = sc.nextLine().charAt(0);
			} catch (StringIndexOutOfBoundsException e) {
				ch = '!';
			}
			switch (ch) {
			case 'a':
				System.out.print("Enter the co-ordinates of two points: ");
				x = sc.nextInt();
				y = sc.nextInt();
				z = sc.nextInt();
				w = sc.nextInt();
				try {
					game.grid[x][y].setConnection(game.grid[z][w], game);
					game.updateSquares();
					game.whosPlayin();
				} catch (ArrayIndexOutOfBoundsException aioob) {
					System.err.println("Array index went out of bounds.");
				}
				sc.nextLine();
				break;

			case 'b':
				game.getScore();
				break;

			case 'z':
				System.out.print("Goodbye!");
				break;
			default:
				System.err.println("invalid instruction.");

			}
		} while (!(game.isGameOver()) && ch != 'z');
		if (game.isGameOver()) {
			game.getScore();
			System.out.println("Thank you for playing! Buh-bye!");
		}
		sc.close();

	}

}
