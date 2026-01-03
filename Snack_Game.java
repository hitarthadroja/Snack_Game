import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class SnakeAWT extends Frame implements Runnable,KeyListener{
    int MAX_SIZE = 2500;
    int fw=500,fh=500;

    int dotsize=10;
    int dots;

    int score = 0;
    boolean Game = true;

    int foodx,foody;

    boolean left=false,right=true,up=false,down=false;
    
    Random rand;
    int[] x = new int[MAX_SIZE];
    int[] y = new int[MAX_SIZE];

    Thread snake;

    //---------------------------------------------------------------------
    SnakeAWT(){
        setTitle("Snake Game");
        setSize(fw,fh);
        rand = new Random();
        addKeyListener(this);
        setVisible(true);
        inigame();
    }

    //---------------------------------------------------------------------
    public void paint(Graphics g){
        if(Game){
            g.setColor(Color.red);
            g.fillOval(foodx, foody, dotsize, dotsize);
            for(int i=0;i<dots;i++){
                if(i==0){
                    g.setColor(Color.GREEN);
                }
                else{
                    g.setColor(Color.black);
                }
                g.fillOval(x[i],y[i],dotsize,dotsize);
            }
        }
        else{
            g.drawString("Game Over", 230, 230);
            g.drawString("Your Game Score is :"+score, 200, 250);
        }
    }

    //---------------------------------------------------------------------
    public void inigame(){
        dots = 3;
        for(int i=0;i<3;i++){
            x[i] = 50 - i*dotsize;
            y[i] = 50;
        }
        locateFood();
        snake = new Thread(this);
        snake.start();
    }

    //---------------------------------------------------------------------
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        int obj = e.getKeyCode();
        if(obj == KeyEvent.VK_LEFT){
            left = true;
            right = false;
            up = false;
            down = false;
        }
        else if(obj == KeyEvent.VK_RIGHT){
            right = true;
            left = false;
            up = false;
            down = false;
        }
        else if(obj == KeyEvent.VK_UP){
            up = true;
            down = false;
            left = false;
            right = false;
        }
        else if(obj == KeyEvent.VK_DOWN){
            down = true;
            up = false;
            left = false;
            right = false;
        }
    }
    public void keyReleased(KeyEvent e){}

    //---------------------------------------------------------------------
    public void move(){
        for(int i=dots;i>0;i--){
            //Shifting Dots
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left) x[0] -= dotsize;
        else if(right) x[0] += dotsize;
        else if(up) y[0] -= dotsize;
        else if(down) y[0] += dotsize;
    }
    
    //---------------------------------------------------------------------
    public void locateFood(){
        foodx = rand.nextInt(fw/dotsize)*dotsize;
        foody = rand.nextInt(fh/dotsize)*dotsize;
    }

    //---------------------------------------------------------------------
    public void checkFood(){
        if(x[0]==foodx && y[0]==foody){
            dots++;
            score += 10;
            locateFood();
        }
    }

    //---------------------------------------------------------------------
    public void checkBounds(){
        for(int i=dots;i>0;i--){
            if(i>4 && x[i]==x[0] && y[i]==y[0]){
                Game = false;
                repaint();
            }
        }
        if(x[0] > fw || x[0]<0 || y[0]>fh || y[0]<0){
            Game = false;
            repaint();
        }
    }

    //---------------------------------------------------------------------
    public void run(){
        while(Game){
            if(Game){
                move();
                checkFood();
                checkBounds();
            }
            repaint();
            try{
                Thread.sleep(100);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    //---------------------------------------------------------------------
    public static void main(String args[]){
        new SnakeAWT();
    }
}