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

import dotsgui.dots.Dots;

public class DotsGUI
{
    Dots game;
    DotsGUI()
    {
        final JFrame frame=new JFrame();
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Dots");
        frame.setLayout(new BorderLayout());
        
        game=new Dots(3+1,3+1,2); //since dots constructor takes no. of vertices
        final DotsBox box=new DotsBox(game);
        game.disp();
        box.newGame(game);
        box.repaint();
        frame.add(box);
        
        //menu bar and its components
        JMenuBar menubar=new JMenuBar();
        menubar.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        final JLabel status=new JLabel("Application started.");
        status.setForeground(Color.GRAY);
        
        JMenu file=new JMenu("File");
        JMenuItem newgame=new JMenuItem("New Game");
        newgame.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae)
                    {
                        int r=2,c=2,p=2;
                        try{
                            r = Integer.parseInt(JOptionPane.showInputDialog(frame,"Enter number of rows."));
                            c = Integer.parseInt(JOptionPane.showInputDialog(frame,"Enter number of columns."));
                            p = Integer.parseInt(JOptionPane.showInputDialog(frame,"How many people are playing?"));
                        }
                        catch(NumberFormatException nfe)
                        {
                            System.err.println("exception");
                            JOptionPane.showMessageDialog(frame, "There was an error.\nCreating default 2x2 game.","Invalid input...",JOptionPane.ERROR_MESSAGE);
                            r=2; c=2; p=2;
                        }
                        game=new Dots(r+1,c+1,p);
                        game.disp();
                        box.newGame(game);
                        
                        status.setText("New "+r+"x"+c+" game created with "+p+" players!");
                        box.repaint();
                    }
                });
        file.add(newgame);
        file.setMnemonic(KeyEvent.VK_F);
        
        JMenu help=new JMenu("Help");
        JMenuItem about=new JMenuItem("About");
        about.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae)
                    {
                        JOptionPane.showMessageDialog(frame, "Dots v0.01\nso program much java\nmany code such swing\n\nMade by:\nThe bruthaz. #swag", "About Dots",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
        );
        help.add(about);
        help.setMnemonic(KeyEvent.VK_H);
        
        menubar.add(file);
        menubar.add(help);
        menubar.add(status);
        frame.add(menubar,BorderLayout.NORTH);
        
        JPanel bottom=new JPanel();
        bottom.setLayout(new GridLayout(1,8));
        JLabel label1=new JLabel ("x1",SwingConstants.CENTER);
        JLabel label2=new JLabel ("y1",SwingConstants.CENTER);
        JLabel label3=new JLabel ("x2",SwingConstants.CENTER);
        JLabel label4=new JLabel ("y2",SwingConstants.CENTER);
        JLabel label5=new JLabel ("Ready?",SwingConstants.CENTER);
        
        // Text fields
        final JTextField field1=new JTextField();
        final JTextField field2=new JTextField();
        final JTextField field3=new JTextField();
        final JTextField field4=new JTextField();
        
        // Connect Button
        JButton connect=new JButton("Go");
        connect.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae)
                    {
                        int x1=0,y1=0,x2=0,y2=1;
                        try{
                            x1=Integer.parseInt(field1.getText());
                            x2=Integer.parseInt(field3.getText());
                            y1=Integer.parseInt(field2.getText());
                            y2=Integer.parseInt(field4.getText());
                            game.grid[x1][y1].setConnection(game.grid[x2][y2],game);
                            game.updateSquares();
                            game.disp();
                            box.repaint();
                            game.whosPlayin();
                        }
                        catch(Exception e){ System.err.println("incorrect input in fields. "); }
                        finally
                        {
                            field1.setText(null);
                            field2.setText(null);
                            field3.setText(null);
                            field4.setText(null);
                            status.setText("Turn "+game.turn+", ("+game.lines+"/"+game.lines_max+" done) - Your turn, player "+game.player+"!");
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
        frame.add(bottom,BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch(Exception e) {};
                
                new DotsGUI();
            }
        });
    }

}
