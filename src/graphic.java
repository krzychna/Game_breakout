import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import static java.awt.Font.BOLD;

public class graphic extends JPanel implements KeyListener, ActionListener {


    private int score = 0;
    private Timer timer;
    boolean play = false;
    boolean game_over = false;
    // dimensions of game
    private int height = 640;
    private int width = 800;

    //position of slide
    private int posslid_X = 400;
    final private int posslid_Y = 590;


    //position of ball
    private int posball_X = 100;
    private int posball_Y = 250;

    //direction of ball
    private int dirX = -3;
    private int dirY = -3;

    //brick dimensions
    private int brickwidth = 25;
    private int brickheight= 15;
    private int bricks [][];
    private int columns = 20;
    private int rows = 8;
    private int nofbricks = columns *rows;

    public graphic(){
        bricks = new int[columns][rows];
        for (int i =0;i<columns;i++){
            Arrays.fill(bricks[i],1);
        }

        addKeyListener(this);
        setFocusable(true);
        timer = new Timer(1, this);
    }

    public void paint(Graphics g){
        moveball();

        g.setColor(new Color(200, 102, 96));
        g.fillRect(5,5, width-10,height-10);

        drawrectangle(g);

        g.setColor(Color.yellow);
        g.fillOval(posball_X,posball_Y,20,20);
        g.setColor(Color.GREEN);
        g.fillOval(posball_X,posball_Y,5,5);

        g.setColor(new Color(93, 57, 200));
        g.fillRect(posball_X,posslid_Y,50,10);
        score(g);
        lost(game_over,g);
        if (nofbricks ==0) {
            win(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private void score(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font(Font.SERIF, BOLD,20));
        g.drawString("Score: " + score, width-200,height-20);
    }

    private void win(Graphics g) {
        timer.stop();
        play = false;
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED,Font.BOLD,18));
        g.drawString("You Won", width/2,height/2);
        for (int i =0;i<columns;i++){
            Arrays.fill(bricks[i],1);
        }
        nofbricks = rows * columns;
        posball_X = 100;
        posball_Y = 200;
        dirX = -3;
        dirY = -3;
    }

    private void drawrectangle(Graphics g) {

        for (int i =0; i< columns;i++){
            for (int j =0; j< rows;j++){
                if (bricks[i][j]>0) {
                    g.setColor(Color.black);
                    g.fillRect(100 + i * brickwidth + i * 5, 40 + j * brickheight + j * 2, brickwidth, brickheight);
                    g.setColor(Color.white);
                    g.drawRect(100 + i * brickwidth + i * 5, 40 + j * brickheight + j * 2, brickwidth, brickheight);
                }
            }
        }
    }

    private void lost(boolean game_over, Graphics g) {
        if (game_over)
        {
            g.setColor(Color.BLACK);
            g.setFont(new Font(Font.MONOSPACED,BOLD,18));
            g.drawString("GAME OVER", width/2,height/2);
        }
        else this.game_over = false;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!play) timer.start();

        game_over = false;
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if (posslid_X>=this.width-50) {
                posslid_X = this.width-50;
            }
            else {
            posslid_X += 20;
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if (posslid_X < 5) {
                posslid_X = 5;
            }
            else {
                posslid_X -= 20;
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_P){
            if (play) {
                play = false;
                timer.stop();
            }
            else if (!play){
                play=true;
                timer.start();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        play = true;
        if (play) {
            repaint();
        }
    }

    private void moveball() {

        Rectangle ballrect = new Rectangle(posball_X,posball_Y,20,20);
        Rectangle slider = new Rectangle(posball_X,posslid_Y,50,10);

        if (posball_X <6) dirX = -dirX;


        if (posball_X > width-22) dirX = -dirX;

        if (posball_Y > height-22) {
            play = false;
            game_over = true;
            posball_X = 100;
            posball_Y = 250;
            dirX = -1;
            dirY = -3;
            for (int i =0;i<columns;i++){
                Arrays.fill(bricks[i],1);
            }
            timer.stop();
        }


        if (posball_Y < 6) dirY = -dirY;

        if  (ballrect.intersects(slider)) dirY = -dirY;


        A:for (int i =0; i< columns;i++){
            for (int j =0; j< rows;j++){
                if (bricks[i][j]>0) {
                    int brickXpos = 100 + i * brickwidth + i * 5;
                    int brickYpos = 40 + j * brickheight + j * 2;
                    Rectangle brickrect = new Rectangle(brickXpos, brickYpos, brickwidth, brickheight);
                    if (ballrect.intersects(brickrect)) {
                        bricks[i][j] = 0;
                        if (posball_X+19 <= brickXpos || posball_X+1>=brickXpos + brickwidth ){

                            dirX = -dirX;
                        }
                        else {
                            dirY = -dirY;
                        }
                        score +=2;
                        nofbricks--;
                        break A;
                    }

                }
            }
        }


        this.posball_X += dirX;
        this.posball_Y += dirY;
    }
}
