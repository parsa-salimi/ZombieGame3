
package me.zombies;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;



public class GUI extends JFrame {
	DrawPanel pan;
	ArrayList<Enemy> birds;
	ArrayList<Bullet> bullets;
	int playerX = 100;
	int playerY = 100;
	static final int playerV = 10;
	//for key binding
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private static final String MOVE_UP = "move up";
    private static final String MOVE_DOWN = "move down";
    private static final String MOVE_LEFT = "move left";
    private static final String MOVE_RIGHT = "move right";
    private static final String SHOOT_BULLET = "shoot";
    static final int T1_SPEED = 100;
	

	public static void main(String[] args) {
		
				new GUI();
			}
	GUI(){
		birds = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		Random r = new Random();
		//create 5 enemies
		for(int i = 0; i < 5; i++) {
			birds.add(new Enemy(i*50, i*40,(r.nextInt(5)+1)*3));
		}
		pan = new DrawPanel();
		
		Timer firstTimer = new Timer(T1_SPEED,new Timer1Listener());
		firstTimer.start();
		this.setTitle("Main graphics ..."); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(pan);	
		this.pack();
		this.setVisible(true);
		
		//key bindings
		pan.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
		pan.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
		pan.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
		pan.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);
		pan.getInputMap(IFW).put(KeyStroke.getKeyStroke('j'), SHOOT_BULLET);
		//binding keys to actions
		pan.getActionMap().put(MOVE_UP, new MoveAction(1));
		pan.getActionMap().put(MOVE_DOWN, new MoveAction(2));
		pan.getActionMap().put(MOVE_LEFT, new MoveAction(3));
		pan.getActionMap().put(MOVE_RIGHT, new MoveAction(4));
		pan.getActionMap().put(SHOOT_BULLET, new MoveAction(10));
		

	}
	
	private class Timer1Listener  implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (Enemy i : birds) {
				i.moveToPosition(playerX, playerY);
			}
		}
	}
	
	class DrawPanel extends JPanel {
					
		int panSize=600;
		
		DrawPanel() {	
			this.setBackground(Color.WHITE);			
			this.setPreferredSize(new Dimension(panSize, panSize));		
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g); //clear screen and repaint using background colour
			panSize = this.getWidth();
			
			Graphics2D g2 = (Graphics2D) g;		
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			for( Enemy i : birds) {
				g2.drawRect((int)i.getX(),(int)i.getY(), 2,2);
				
			}
			for( Bullet i : bullets) {
				g2.drawRect((int)i.getX(),(int)i.getY(), 2,2);
				i.updatePosition();
			}
			g2.drawRect(playerX, playerY, 10, 10);
			pan.repaint();
		}
			
		
	}
	
	
	
	private class MoveAction extends AbstractAction {
		int direction;
        MoveAction(int direction) {

            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        	if (direction ==1) {
        		playerY -= playerV;
        	}
        	else if (direction ==2) {
        		playerY += playerV;
        	}
        	else if (direction == 3) {
        		playerX -= playerV;
        	}
        	else if (direction == 4) {
        		playerX += playerV;
        	}
        	else if (direction == 10) {
        		bullets.add(new Bullet(playerX,playerY));
        	}
          pan.repaint();
        }
    }
}