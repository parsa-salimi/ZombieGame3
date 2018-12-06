
package me.zombies;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import javafx.scene.input.MouseButton;

//TODO add the initialize method thingys

public class GUI extends JFrame {
	static final int BULLETSPEED = 50;
	DrawPanel pan;
	ML mouse;
	ArrayList<Enemy> birds;
	Player player;
	int mouseX = 0;
	int mouseY = 0;
	boolean rightClick = false;
	ArrayList<Bullet> bullets;
	int panSize=600; //initial value;

	
	//for key binding
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
   static final int T1_SPEED = 20;
   int damage = 10;
	

	public static void main(String[] args) {
				new GUI();
			}
	GUI(){
		birds = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		//create 5 enemies
		Random r = new Random();
		for(int i = 0; i < 15; i++) {
			birds.add(new Enemy(i*50+1, i*40+1,r.nextInt(6) + 1));
		}
		pan = new DrawPanel();
		pan.addKeyListener(new KL());
		mouse = new ML();
		pan.addMouseListener(mouse);
		Timer firstTimer = new Timer(T1_SPEED,new Timer1Listener());
		
		this.setTitle("Main graphics ..."); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(pan);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); //fill full screen no matter what monitor size
		//this.pack();
		this.setVisible(true);
		
		panSize = this.getWidth();
		System.out.println(panSize);
		
		player = new Player(pan.getWidth(),pan.getHeight());
		//bullet = new Bullet(pan.getWidth(), pan.getHeight());
		System.out.println("gui run");
		firstTimer.start();
	}
	
	void resetPlayerPosition() {
		if (player.getX() > pan.getWidth()-(player.rad*2)) {
			player.x = pan.getWidth()-(player.rad*2);
		}
		if (player.getX() < 0) {
			player.x = 0;
		}
		if (player.getY() > pan.getHeight()-(player.rad*2)) {
			player.y = pan.getHeight()-(player.rad*2);
		}
		if (player.getY() < 0) {
			player.y = 0;
		}
	}
	
	
	private class Timer1Listener  implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			player.movePlayer();
			for (Enemy i : birds) {
				i.moveToPosition(player.getX()+player.rad, player.getY()+player.rad);
			}
			for(Bullet b : bullets) {
				b.updatePosition();
			}
			resetPlayerPosition();
			pan.repaint();
		}
	}
	
	
	class DrawPanel extends JPanel {
		
		DrawPanel() {	
			this.setBackground(Color.WHITE);			
			//this.setPreferredSize(new Dimension(panSize, panSize));		
		}
		
		boolean doInit = true;
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g); //clear screen and repaint using background colour
			
			/* The following code is designed to initialize data once, but only after the screen is displayed */
			if (pan.getWidth() < 50) { //screen width is ridiculously small: .: not actually displayed yet
				return;
			}
			
			if (doInit) {
				//initializeAllObjects();
				doInit = false;
			}
			/* ****************************** */
			
			this.requestFocus();
			panSize = this.getWidth();
			this.requestFocus();
			
			Graphics2D g2 = (Graphics2D) g;		
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			for(int j = 0; j < birds.size() ; j++) {
				Enemy i = birds.get(j);
				g2.drawRect((int)i.getX(),(int)i.getY(), 2,2);
				
				double positionXY = Math.sqrt(Math.pow((player.getX() - i.getX()), 2)+ (Math.pow(player.getY() - i.getY(), 2)));
				if (positionXY <= 20) {
					player.hp -= damage;
					birds.remove(i);
					System.out.println(mouseX);
				}
				for(Bullet bullet : bullets) {
					g2.drawRect((int)bullet.getX(),(int) bullet.getY(), 3, 3);
					double bulletXY = Math.sqrt(Math.pow((bullet.getX() - i.getX()), 2)+ (Math.pow(bullet.getY() - i.getY(), 2)));
					if (bulletXY <= 20) {
						i.hp -= damage;
					}
			
					if (i.hp == 0) {
						birds.remove(i);
					}
				}
				

				if(player.hp <= 0) {
					player.isdead = true;
				}
				
			}
			if(player.isdead) {
				//System.out.println("GAME OVER");
				g.setColor(Color.BLACK);
				g.fillRect(1000, 1000, 1000, 1000);
				birds.clear();
				}
			else {
			player.playerDraw(g);
			}
		}
			
		
	}
	
	class KL implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			//up on
			if (e.getKeyCode()==KeyEvent.VK_UP) {
				player.U=true;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
				player.R=true;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_LEFT) {
				player.L=true;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_DOWN) {
				player.D=true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//up on
			if (e.getKeyCode()==KeyEvent.VK_UP) {
				player.U=false;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
				player.R=false;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_LEFT) {
				player.L=false;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_DOWN) {
				player.D=false;
			}
		}
		
	}
	
	class ML implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			bullets.add(new Bullet(player.x,player.y,BULLETSPEED, mouseX,mouseY));
			if(e.getButton() == MouseEvent.BUTTON3) {
				rightClick = true;
			}
			else {
				rightClick = false;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	



}