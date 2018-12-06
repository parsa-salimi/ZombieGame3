
package me.zombies;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.sun.prism.BasicStroke;

//TODO add the initialize method thingys

public class GUI extends JFrame {
	DrawPanel pan;
	ArrayList<Enemy> birds;
	Player player;
	int panSize; //initial value;
	boolean init = false;
	int birdSpawn = 0;
	
	//for key binding
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
   static final int T1_SPEED = 20;
   int damage = 5;
	

	public static void main(String[] args) {
				new GUI();
			}
	GUI(){
		
		pan = new DrawPanel();
		pan.addKeyListener(new KL());
		Timer firstTimer = new Timer(T1_SPEED,new Timer1Listener());
		
		this.setTitle("Main graphics ..."); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(pan);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); //fill full screen no matter what monitor size
		//this.pack();
		this.setVisible(true);
		

		firstTimer.start();
	}
	
	void initializeGameObjects() {
		panSize = pan.getWidth();
		System.out.println(pan.getWidth() + "THIS IS THE ACTUAL WIDTH");
		//player
		player = new Player(pan.getWidth(),pan.getHeight());
		System.out.println(pan.getWidth()+" "+pan.getHeight());
		
		//enemies
		birds = new ArrayList<Enemy>();
		
		for(int i = 0; i < 15; i++) {
			addEnemy();
		}
	}
	
	void addEnemy() {
		Random r = new Random();
		birds.add(new Enemy(pan.getWidth(), pan.getHeight(),r.nextInt(6) + 1));
	}
	
	void drawHealth(Graphics2D g2, int hp) {
		System.out.println(hp);
		double barw = pan.getWidth()-(pan.getWidth()/5);
		int hpBar =(int) ((barw/100)*hp); 
		
		g2.setColor(new Color (200,200,200));
		g2.fillRect((pan.getWidth()/10),pan.getHeight()/40,hpBar,pan.getHeight()/20);
		g2.setColor(Color.BLACK);
		g2.drawRect((pan.getWidth()/10),pan.getHeight()/40,(int)(barw),pan.getHeight()/20);
		
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
			if (!init) {
				return;
			}
			player.movePlayer();
			for (Enemy i : birds) {
				i.moveToPosition(player.getX()+player.rad, player.getY()+player.rad);
			}
			resetPlayerPosition();
			pan.repaint();
			
			if (birdSpawn %100 == 0) {
				for (int i = 0; i < birdSpawn/100; i++) {
					addEnemy();
					System.out.println("friends");
				}

			}
			birdSpawn++;
			
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
				initializeGameObjects();
				doInit = false;
				init = true;
				
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
					System.out.println(player.hp);
					birds.remove(i);
				}

				if(player.hp <= 0) {
					player.isdead = true;
					
				}
			}
			
			drawHealth(g2, player.hp);
			
			if(player.isdead) {
				System.out.println("GAME OVER");
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
	



}