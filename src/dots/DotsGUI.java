package dots;

import dots.Dots;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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
        final JTextField field1=new JTextField();
        final JTextField field2=new JTextField();
        final JTextField field3=new JTextField();
        final JTextField field4=new JTextField();
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
                DotsGUI gui=new DotsGUI();
            }
        });
    }

}
class DotsBox extends JComponent implements DotsConstants
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int h,w;  //height and width of dots box
    private int x1,y1; //corners of dots grid
    private int x2, y2;
    private int r,c; //no of rows and columns
    Dots game;
    DotsBox(Dots game)
    {
        this.game=game;
        this.r=game.grid.length-1;
        this.c=game.grid[0].length-1;
    }
    void newGame(Dots game)
    {
        this.game=game;
        this.r=game.grid.length-1;
        this.c=game.grid[0].length-1;
    }
    DotsBox()
    {
        r=3;
        c=3;
    }
    
    public void paint(Graphics g)
    {
        h=getHeight()-1-2*PADDING;
        w=getWidth()-1-2*PADDING;
        //System.out.println(h+" "+w);
        x1=0;
        y1=0;
        x2=x1+w;
        y2=y1+h;
        //g.drawRect(0,0,w-1,h-1);
        
        float i,j;
        int a,b;
        g.setColor(Color.WHITE);
        for(i=PADDING,a=0;a<=r;i=i+(float)h/r,a++) //draw horizontal lines
        {
            g.drawLine(PADDING,(int)i,w+PADDING,(int)i);
            
        }
        for(j=PADDING,b=0;b<=w;j=j+(float)w/c,b++) //draw vertical lines
        {
            g.drawLine((int)j,PADDING,(int)j,h+PADDING);
        }
        
        g.setColor(Color.BLACK);  
        for(i=PADDING,a=0; a<=r; i=i+(float)h/r,a++) //draw proper lines
        {
            try{
                for(j=PADDING,b=0; b<=c; j=j+(float)w/c,b++)
                {
                    if(b<c && game.grid[a][b].hasConnection(game.grid[a][b+1]))
                        g.drawLine((int)j,(int)i,
                                (int)(j+(float)w/c),(int)i);
                    if(a<r && game.grid[a][b].hasConnection(game.grid[a+1][b]))
                        g.drawLine((int)j,(int)i,
                                (int)j,(int)(i+(float)h/r));
                }
            }
            catch(ArrayIndexOutOfBoundsException aear){ System.err.println("Exception at a="+a+", b="+b); }     
        }
        
        g.setColor(Color.RED); //draw dots
        for(i=PADDING,a=0;a<=r;i=i+(float)h/r,a++) 
        {
            for(j=PADDING,b=0;b<=w;j=j+(float)w/c,b++)
            {
                g.fillOval((int)j-DOT_RADIUS,(int)i-DOT_RADIUS,2*DOT_RADIUS,2*DOT_RADIUS);
            }
        }
        
        g.setColor(Color.BLUE); //player numbers
        g.setFont(new Font("Arial Black", Font.PLAIN, FONT_SIZE));
        for(i=PADDING,a=0; a<r; i=i+(float)h/r,a++) 
        {
            try{
                for(j=PADDING,b=0; b<c; j=j+(float)w/c,b++)
                {
                    g.drawString(String.valueOf(game.sq[a][b]),(int)j+(int)((float)w/c/2-FONT_SIZE/4),(int)i+(int)((float)h/r/2+FONT_SIZE/4));
                }
            }
            catch(ArrayIndexOutOfBoundsException aear){ System.err.println("Exception at a="+a+", b="+b); }
        }
    }
}
