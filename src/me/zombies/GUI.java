
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


//TODO add the initialize method thingys

public class GUI extends JFrame {

	BufferedImage hull,turret,turretF,backG;

	DrawPanel pan;
	ArrayList<Enemy> birds;
	ArrayList<Bullet> bullets;
	ArrayList<Obstacle> obstacles;
	Player player;

	int mouseX = 0;
	int mouseY = 0;
	boolean rightClick = false;
	double angle;
	
	int panSize=600; //initial value;
	int playerAngle;

	boolean init = false;
	int birdSpawn = 0;
	int hpMax;
	//for key binding
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	static final int T1_SPEED = 20;
	int damage = 5;
	
	int score = 0;


	public static void main(String[] args) {
		new GUI();
	}
	GUI(){

		birds = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		obstacles = new ArrayList<Obstacle>();
		//create 5 enemies
		Random r = new Random();
		for(int i = 0; i < 15; i++) {
			birds.add(new Enemy(i*50+1, i*40+1,r.nextInt(6) + 1));
		}
		


		pan = new DrawPanel();
		pan.addKeyListener(new KL());
		pan.addMouseListener(new ML());
		pan.addMouseMotionListener(new ML2());
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
		Random r = new Random();
		panSize = pan.getWidth();
		try {
			hull = ImageIO.read(new File("./res/imgs/hull.png"));
			turret = ImageIO.read(new File("./res/imgs/turret.png"));
			turretF = ImageIO.read(new File("./res/imgs/turretF.png"));
			backG = ImageIO.read(new File("./res/imgs/backG.png"));
			
		} catch (IOException e) {
			System.out.println("An image could not be loaded or is missing.");
			System.exit(0);
		}
		//player
		player = new Player(pan.getWidth(),pan.getHeight());
		hpMax = player.hp;
		//enemies
		birds = new ArrayList<Enemy>();

		for(int i = 0; i < 15; i++) {
			addEnemy();
		}
		
		for(int i = 0; i < 10; i++) {
			int upleftx = r.nextInt(pan.getWidth()-40);
			int uplefty = r.nextInt(pan.getHeight()-40);
			obstacles.add(new Obstacle(upleftx, uplefty,r.nextInt(40)+10,r.nextInt(40)+10)); 
		}
	}

	void addEnemy() {
		Random r = new Random();
		birds.add(new Enemy(pan.getWidth(), pan.getHeight(),r.nextInt(6) + 1));
	}

	void drawHealth(Graphics2D g2, int hp) {
		double barw = pan.getWidth()-(pan.getWidth()/5);
		int hpBar =(int) ((barw/hpMax)*hp); 

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
			player.canGoDown = player.canGoLeft = player.canGoUp = player.canGoRight = true;
			for(Obstacle o : obstacles) {
				if(o.ULY - player.y < 20 && o.ULY - player.y > 0 
						&& player.x > o.ULX && player.x < o.ULX + o.W) {
					player.canGoDown = false;
				}
			}
			for (Enemy i : birds) {
				i.moveToPosition(player.getX()+player.rad, player.getY()+player.rad);
			}
			resetPlayerPosition();
			pan.repaint();
			for(Bullet b : bullets) {
				b.updatePosition();
			}
			if (birdSpawn %100 == 0) {
				for (int i = 0; i < birdSpawn/100; i++) {
					addEnemy();
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
			g.drawImage(backG, 0, 0, pan.getWidth(), pan.getHeight(), null);
			
			for(int j = 0; j < birds.size() ; j++) {
				g.setColor(Color.PINK);
				Enemy i = birds.get(j);
				g2.drawRect((int)i.getX(),(int)i.getY(), 13,13);
				double positionXY = Math.sqrt(Math.pow((player.getX() - i.getX()), 2)+ (Math.pow(player.getY() - i.getY(), 2)));
				if (positionXY <= 20) {
					player.hp -= damage;
					birds.remove(i);
				}
				for(Bullet b : bullets) {
					g2.drawRect((int)b.x,(int) b.y, 3, 3);
				}
				if(player.hp <= 0) {
					player.isdead = true;

				}
				for(Bullet b : bullets) {
					g.setColor(Color.RED);
					g2.drawRect((int)b.x,(int) b.y, 3, 3);
				}
				
				for (Bullet b : bullets ) {
					double BulletXY = Math.sqrt(Math.pow((b.getX() - i.getX()), 2)+ (Math.pow(b.getY() - i.getY(), 2)));
					if (BulletXY <= 20) {
						birds.remove(i);
						score += 100;
						
					}
				}
				player.checkAngle();
			}

			drawHealth(g2, player.hp);
			for(Obstacle o: obstacles) {
				g2.fillRect(o.ULX,o.ULY,o.W,o.H);
			}
			if(player.isdead) {
				System.out.println("GAME OVER");
				g.setColor(Color.BLACK);
				g.fillRect(1000, 1000, 1000, 1000);
				birds.clear();
			}
			else {
			player.playerDraw(g2, hull,turret, angle);
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
			if (e.getKeyCode()==KeyEvent.VK_DOWN && player.canGoDown) {
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
			

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			bullets.add(new Bullet(player.x,player.y,50, e.getX(),e.getY()));
			if(e.getButton() == MouseEvent.BUTTON3) {
				rightClick = true;
			}
			else {
				rightClick = false;
			}
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
	class ML2 implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
			public void mouseMoved(MouseEvent e) {
			int dispX = e.getX() - player.x;
			int dispY = -(e.getY() - player.y);
		   angle = Math.atan((double)Math.abs(dispY)/(double)Math.abs((dispX)));
			if(dispY < 0) {
				if(dispX < 0) {
					angle += Math.PI;
				}
				else {
					angle = Math.PI*2 - angle;
				}
			}
			else {
				if(dispX < 0) {
					angle = Math.PI - angle;
				}
			  }
			}
			
		
	}
	




}