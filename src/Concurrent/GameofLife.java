package Concurrent;


import java.awt.*;         
import java.applet.*;

public class GameofLife extends Applet {
	
	    private LifeEnv env;    
	    private Worker worker;      
	    
	// Get the applet started
	   public void init() {
	        build(this);
	        new Worker(this).start();
	    }
	   
	    public void work() {
	        while (true) {
	        	// Just sit in a loop running forever
	        	env.run1Generation();
	        }
	    }
	    
	   // Make a user interface
	   private void build(Container f) {
	        setLayout(new BorderLayout());
	        env = new LifeEnv();
	        env.setBackground(Color.white);  
	        f.add("Center", env);        
	    }
}


class LifeEnv extends Canvas {
// This holds the data structures for the game and computes the currents
	// The update environment
    private int update[][];  
    // The current env
    private int current[][];  
    // Need to swap the envs over
    private int swap[][];

    // private static final variables are constants
    private static final int POINT_SIZE = 7;
    private static final Color POINT_COLOR = Color.blue;
    // Width and height of environment
    private static final int N = 100;
    private static final int CANVAS_SIZE = 800;     


    public LifeEnv() {   
        update = new int[N][N];  
        current = new int[N][N];  
        
        // Glider
        current[21][20] = 1;
        current[22][21] = 1;
        current[22][22] = 1;
        current[21][22] = 1;
        current[20][22] = 1;
        
        // Spaceship
        current[50][50] = 1;
        current[53][50] = 1;
        current[54][51] = 1;
        current[50][52] = 1;
        current[54][52] = 1;
        current[51][53] = 1;
        current[52][53] = 1;
        current[53][53] = 1;
        current[54][53] = 1;
        
        setSize(CANVAS_SIZE, CANVAS_SIZE);
    }
    
    public void run1Generation() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int im = (i+N-1) % N; int ip = (i+1) % N;
                int jm = (j+N-1) % N; int jp = (j+1) % N;
                switch (current[im][jm] + current[im][j] + current[im][jp] + current[i][jm] + current[i][jp] + current[ip][jm] + current[ip][j] + current[ip][jp]) {
                    case 0 :
                    case 1 : update[i][j] = 0; break;
                    case 2 : update[i][j] = current[i][j]; break;
                    case 3 : update[i][j] = 1; break;
                    case 4 :
                    case 5 :
                    case 6 :
                    case 7 :
                    case 8 : update[i][j] = 0; break;
                }
                
                // Slow things down so that you can see them
                long iters = 10000;
                do {
                } while (--iters > 0);            }
        }
        
        swap = current; current = update; update = swap;
        repaint();     
    }

    // Draw the points that have value 1
    public void paint(Graphics g) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (current[i][j] == 1) {
                    drawPoint(i, j, 1, g);
                }
            }
        }

        g.setColor(Color.black);
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
    }

    private void drawPoint(int x, int y, int v, Graphics g) {
        Dimension d = (getSize());
        int mx = d.width * x / N;       
        int my = d.height * y / N;  
        if (v == 1) {
            g.setColor(POINT_COLOR);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(mx, my, POINT_SIZE, POINT_SIZE);
    }
}

class Worker extends Thread {

    private GameofLife game;

    public Worker(GameofLife g) {
        game = g;
    }

    public void run() {
        game.work();
    }
    
}
