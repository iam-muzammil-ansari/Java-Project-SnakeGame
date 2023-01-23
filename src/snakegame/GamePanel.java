package snakegame;

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

public class GamePanel extends JPanel implements ActionListener, KeyListener{
    
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];
    private int lengthOfSnake = 3;

    int[] xPos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] yPos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random = new Random();
    private int enemyX,enemyY;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private int moves=0;
    private int score=0;
    private boolean gameOver=false;

    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));

    private Timer timer;
    private int delay=100;

    GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        
        timer = new Timer(delay, this);
        timer.start();

        newEnemy();
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);
        snaketitle.paintIcon(this, g, 25, 11);
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);

        if(moves==0){
            snakeXLength[0]=100;
            snakeXLength[1]=75;
            snakeXLength[2]=50;

            snakeYLength[0]=100;
            snakeYLength[1]=100;
            snakeYLength[2]=100;
        }
        if(left){
            leftmouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
        }
        if(right){
            rightmouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
        }
        if(up){
            upmouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
        }
        if(down){
            downmouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
        }

        for(int i=1; i<lengthOfSnake; i++){
            snakeimage.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
        }
        enemy.paintIcon(this, g, enemyX, enemyY);

        if(gameOver){
            g.setColor(Color.WHITE);
            g.setFont( new Font("Ink Free",Font.BOLD,50));
            g.drawString("Game Over", 300, 300);
            g.setFont( new Font("Arial",Font.PLAIN,20));
            g.drawString("Press SPACE to Restart", 320, 350);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,14));
        g.drawString("Score :"+score, 750, 30);
        g.drawString("Length :"+lengthOfSnake, 750, 50);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        for(int i=lengthOfSnake-1; i>0; i--){
            snakeXLength[i]=snakeXLength[i-1];
            snakeYLength[i]=snakeYLength[i-1];
        }

        if(left){
            snakeXLength[0]=snakeXLength[0]-25;
        }
        if(right){
            snakeXLength[0]=snakeXLength[0]+25;
        }
        if(up){
            snakeYLength[0]=snakeYLength[0]-25;
        }
        if(down){
            snakeYLength[0]=snakeYLength[0]+25;
        }

        if(snakeXLength[0]>850){
            snakeXLength[0]=25;
        }
        if(snakeXLength[0]<25){
            snakeXLength[0]=850;
        }
        if(snakeYLength[0]>625){
            snakeYLength[0]=75;
        }
        if(snakeYLength[0]<75){
            snakeYLength[0]=625;
        }


        collidesWithEnemy();
        collidesWithBody();

        repaint();
    }

    private void collidesWithBody() {
        for(int i=lengthOfSnake-1; i>0; i--){
            if(snakeXLength[i]==snakeXLength[0] && snakeYLength[i]==snakeYLength[0]){
                timer.stop();
                gameOver=true;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }
        
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)){
            left=true;
            right=false;
            up=false;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)){
            left=false;
            right=true;
            up=false;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!down)){
            left=false;
            right=false;
            up=true;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)){
            left=false;
            right=false;
            up=false;
            down=true;
            moves++;
        }
    }

    private void restart() {
        gameOver=false;
        moves=0;
        score=0;
        lengthOfSnake=3;
        left=false;
        right=true;
        up=false;
        down=false;
        timer.start();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void newEnemy() {
        enemyX=xPos[random.nextInt(34)];
        enemyY=yPos[random.nextInt(23)];

        for(int i=lengthOfSnake-1; i>=0; i--){
            if(snakeXLength[i]==enemyX && snakeYLength[i]==enemyY){
                newEnemy();
            }
        }
    }

    private void collidesWithEnemy(){
        if(snakeXLength[0]==enemyX && snakeYLength[0]==enemyY){
            newEnemy();
            lengthOfSnake++;
            score++;
        }
    }

}
