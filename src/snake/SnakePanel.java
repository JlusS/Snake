package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakePanel extends JPanel implements KeyListener,ActionListener{

    ImageIcon up = new ImageIcon("headUp.png");
    ImageIcon down = new ImageIcon("headDown.png");
    ImageIcon left = new ImageIcon("headLeft.png");
    ImageIcon right = new ImageIcon("headRight.png");
    ImageIcon food = new ImageIcon("candy.png");
    ImageIcon body = new ImageIcon("body.png");
    ImageIcon title = new ImageIcon("logo.jpg");

    int[] snakex = new int [750];
    int[] snakey = new int [750];

    Random rand = new Random();
    int foodx = rand.nextInt(34)*25+25;
    int foody = rand.nextInt(24)*25+75;

    int len = 3;
    int score = 0;
    String direction = "R";

    boolean isStarted = false;
    boolean isFailed = false;

    Timer timer = new Timer(100,this);


    public SnakePanel() {
        this.setFocusable(true);
        this.addKeyListener(this);
        setup();
        timer.start();
    }

    public void paint(Graphics g) {

        this.setBackground(Color.BLACK);
        title.paintIcon(this, g, 25, 11);
        g.fillRect(25, 70, 850, 650);

        if (direction.equals("R"))
            right.paintIcon(this, g, snakex[0], snakey[0]);
        else if (direction.equals("L"))
            left.paintIcon(this, g, snakex[0], snakey[0]);
        else if (direction.equals("U"))
            up.paintIcon(this, g, snakex[0], snakey[0]);
        else if (direction.equals("D"))
            down.paintIcon(this, g, snakex[0], snakey[0]);

        for(int i = 1; i < len; i ++)
            body.paintIcon(this, g, snakex[i], snakey[i]);

        if (!isStarted){
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial",Font.BOLD, 30));
            g.drawString("Press Space to start / pause", 200, 300);
        }

        if (isFailed){
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial",Font.BOLD, 30));
            g.drawString("Game Over ! Press space to restart", 200, 300);
        }

        food.paintIcon(this, g, foodx, foody);

        g.setColor(Color.WHITE);
        g.setFont(new Font("arial",Font.PLAIN,15));
        g.drawString("Score : "+score, 650, 37);
        g.drawString("Len :"+len, 650, 57);
    }

    public void setup() {
        isStarted = false;
        isFailed = false;
        len = 3;
        score = 0;
        snakex[0] = 100; snakex[1] = 75; snakex[2] = 50;
        snakey[0] = 100; snakey[1] = 100; snakey[2] = 100;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int KeyCode = e.getKeyCode();
        if (KeyCode == KeyEvent.VK_SPACE){
            if (isFailed){
                setup();
                direction = "R";
            }else
                isStarted = !isStarted;
        } else if (KeyCode ==  KeyEvent.VK_UP && direction != "D")
            direction = "U";
        else if (KeyCode ==  KeyEvent.VK_DOWN && direction != "U" )
            direction = "D";
        else if (KeyCode ==  KeyEvent.VK_RIGHT && direction != "L")
            direction = "R";
        else if (KeyCode ==  KeyEvent.VK_LEFT && direction != "R")
            direction = "L";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (isStarted && !isFailed){
            for (int i = len; i>0; i--){
                snakex[i] = snakex[i-1];
                snakey[i] = snakey[i-1];
            }
            if (direction.equals("R")){
                snakex[0] = snakex[0] + 25;
                if(snakex[0] > 850) snakex[0] = 25;
            }else if (direction.equals("L")){
                snakex[0] = snakex[0] - 25;
            }else if (direction.equals("U")){
                snakey[0] = snakey[0] - 25;
            }else if (direction.equals("D")){
                snakey[0] = snakey[0] + 25;
                if (snakey[0] > 650) snakey[0] = 75;
            }

            if (snakex[0] == foodx && snakey[0] == foody){
                len ++;
                score ++;
                foodx = rand.nextInt(34)*25+25;
                foody = rand.nextInt(24)*25+75;
            }

            for (int i = 1; i < len; i ++){
                if (snakex[0] == snakex[i] && snakey[0] == snakey[i]){
                    isFailed = true;
                } else if (snakex[0]==850||snakey[0]==650) {
                    isFailed = true;
                }
            }

        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}