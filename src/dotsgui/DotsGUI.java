package dotsgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class DotsGUI {
	// the parts of DotsGUI that need to be accessed externally
	DotsGame game;
	final JTextField field1 = new JTextField();
	final JTextField field2 = new JTextField();
	final JTextField field3 = new JTextField();
	final JTextField field4 = new JTextField();
	final JButton connect = new JButton("Go");

	DotsGUI() {
		final JFrame frame = new JFrame();
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("DotsGame");
		frame.setLayout(new BorderLayout());

		// Box area
		game = new DotsGame(3 + 1, 3 + 1, 2); // since dots constructor takes no. of vertices
		final DotsBox box = new DotsBox(game);
		final DotsBoxMouseListener mouse = new DotsBoxMouseListener(game, box, this);
		game.disp(); // console output (debugging)
		box.repaint();
		box.addMouseListener(mouse);
		frame.add(box);

		// menu bar and its components
		JMenuBar menubar = new JMenuBar();
		menubar.setLayout(new FlowLayout(FlowLayout.LEFT));

		final JLabel status = new JLabel("Application started. Default 3x3 game with 2 players.");
		status.setForeground(Color.GRAY);

		JMenu file = new JMenu("File");
		JMenuItem newgame = new JMenuItem("New Game");
		newgame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int r = 2, c = 2, p = 2;
				try {
					r = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter number of rows."));
					c = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter number of columns."));
					p = Integer.parseInt(JOptionPane.showInputDialog(frame, "How many people are playing?"));
				} catch (NumberFormatException nfe) {
					System.err.println("exception");
					JOptionPane.showMessageDialog(frame, "Invalid input.\nCreating default 3x3 game\nwith two players.",
							"Invalid input", JOptionPane.ERROR_MESSAGE);
					r = 3;
					c = 3;
					p = 2;
				}
				if (!(r > 0 && c > 0 && p > 0)) {
					System.err.println("Nonzero");
					JOptionPane.showMessageDialog(frame,
							"Positive non-zero values required.\nCreating default 3x3 game\nwith two players.",
							"Impossible values", JOptionPane.ERROR_MESSAGE);
					r = 3;
					c = 3;
					p = 2;
				}
				game = new DotsGame(r + 1, c + 1, p);
				game.disp();
				box.newGame(game);

				status.setText("New " + r + "x" + c + " game created with " + p + " players!");
				box.repaint();
			}
		});
		file.add(newgame);
		file.setMnemonic(KeyEvent.VK_F);

		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(frame,
						"DotsGame v1.0\nA mini project in java\n<html><b>SE-A2-1311020 Aditya G.<br>SE-A2-1311028 Chinmay K.<br>SE-A2-1311029 Siddhesh K.<br>SE-A2-1311033 Ashutosh M.</b></html>",
						"About DotsGame", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		help.add(about);
		help.setMnemonic(KeyEvent.VK_H);

		menubar.add(file);
		menubar.add(help);
		menubar.add(status);
		frame.add(menubar, BorderLayout.NORTH);

		// Bottom panel with text fields and Go button
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1, 0, 5, 0));
		JLabel label1 = new JLabel("x1", SwingConstants.CENTER);
		JLabel label2 = new JLabel("y1", SwingConstants.CENTER);
		JLabel label3 = new JLabel("x2", SwingConstants.CENTER);
		JLabel label4 = new JLabel("y2", SwingConstants.CENTER);
		JLabel label5 = new JLabel("Ready?", SwingConstants.CENTER);

		connect.setMnemonic(KeyEvent.VK_G);
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!game.isGameOver()) {
					int x1 = 0, y1 = 0, x2 = 0, y2 = 1;
					try {
						x1 = Integer.parseInt(field1.getText());
						x2 = Integer.parseInt(field3.getText());
						y1 = Integer.parseInt(field2.getText());
						y2 = Integer.parseInt(field4.getText());
						game.grid[x1][y1].setConnection(game.grid[x2][y2], game);
						game.updateSquares();
						game.disp();
						game.whosPlayin();
						box.repaint();
					} catch (Exception e) {
						System.err.println("incorrect input in fields. ");
					} finally {
						field1.setText(null);
						field2.setText(null);
						field3.setText(null);
						field4.setText(null);
						if (game.isGameOver()) {
							String scoreboard = "<html><b>Scoreboard</b><br>";
							for (int i = 0; i < game.score.length; i++)
								scoreboard += ("Player " + (i + 1) + " - " + game.score[i] + "<br>");
							scoreboard += "<br>Thank you for playing!</html>";
							JOptionPane.showMessageDialog(frame, scoreboard, "Final scores",
									JOptionPane.INFORMATION_MESSAGE);
							status.setText("Turn " + game.turn + ", (" + game.lines + "/" + game.lines_max
									+ " done) - Game over!");
							field1.setEnabled(false);
							field2.setEnabled(false);
							field3.setEnabled(false);
							field4.setEnabled(false);
						} else {
							status.setText("Turn " + game.turn + ", (" + game.lines + "/" + game.lines_max
									+ " done) - Your turn, player " + game.player + "!");
							field1.requestFocus();
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame,
							"The current game has been completed.\nStart a new one to continue!", "Game Over",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		bottom.add(label1);
		bottom.add(field1);
		bottom.add(label2);
		bottom.add(field2);
		bottom.add(label3);
		bottom.add(field3);
		bottom.add(label4);
		bottom.add(field4);
		bottom.add(label5);
		bottom.add(connect);
		frame.add(bottom, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// run the program on Swing's event dispatch thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
				}
				;
				new DotsGUI();
			}
		});
	}

}
