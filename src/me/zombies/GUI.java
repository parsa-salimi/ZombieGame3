
package me.zombies;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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

	BufferedImage hull,turret,turretF,backG,barrier,enemy,enemy2,enemy3;

	DrawPanel pan;
	ArrayList<Enemy> birds;
	ArrayList<Bullet> bullets;
	ArrayList<Rectangle> obstacles;
	Player player;

	int mouseX = 0;
	int mouseY = 0;
	boolean rightClick = false;
	double angle;
	
	int panSize=600; //initial value;
	int playerAngle;
	boolean turretDrawer = false;

	boolean init = false;
	int timerTick = 0;
	int hpMax;
	//for key binding
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	static final int T1_SPEED = 20;
	

	int score = 0;
	Rectangle pRect;


	public static void main(String[] args) {
		new GUI();
	}
	GUI(){
		
		player = new Player(0,0);
		birds = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		obstacles = new ArrayList<Rectangle>();
		
		//create 5 enemies
		Random r = new Random();
		for(int i = 0; i < 2; i++) {
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
			barrier = ImageIO.read(new File("./res/imgs/barrier.png"));
			enemy = ImageIO.read(new File("./res/imgs/enemy.png"));
			enemy2 = ImageIO.read(new File("./res/imgs/enemy2.png"));
			enemy3 = ImageIO.read(new File("./res/imgs/enemy3.png"));
		} catch (IOException e) {
			System.out.println("An image could not be loaded or is missing.");
			System.exit(0);
		}
		//player
		player = new Player(pan.getWidth(),pan.getHeight());
		for(int i = 0; i < 10; i++) {
			int width = pan.getWidth();
			int height = pan.getHeight();
			obstacles.add(new Rectangle(r.nextInt(width), r.nextInt(height), r.nextInt(width/10)
					,r.nextInt(height/10)));
		}
		hpMax = player.hp;
		//enemies
		birds = new ArrayList<Enemy>();

		for(int i = 0; i < 1; i++) {
			addEnemy();
		}

		for(int i = 0; i < 10; i++) {
			int upleftx = r.nextInt(pan.getWidth()-40);
			int uplefty = r.nextInt(pan.getHeight()-40);
		}
	}

	void addEnemy() {
		birds.add(new Enemy(pan.getWidth(), pan.getHeight(),1));
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
	
	void playerActions() {
		player.movePlayer();
		player.canGoDown = player.canGoLeft = player.canGoUp = player.canGoRight = true;
		if(player.upDown) {
			pRect = new Rectangle(player.x -7, player.y -25, 33, 64);
		}
		else {
			pRect = new Rectangle(player.x-25, player.y-10, 64, 33);
		}
		for(Rectangle o : obstacles) {
			if(o.intersects(pRect)) {
				if(o.getX() > pRect.getX()) {
					player.R = false;
					player.canGoRight = false;
				}
				if(o.getX() < pRect.getX() ) {
					player.L = false;
					player.canGoLeft = false;
				}
				if(o.getY()  < player.getY() +64) {
					player.D = false;
					player.canGoDown = false;
				}
				if(o.getY() + o.getHeight() < player.getY()) {
					player.U = false;
					player.canGoUp = false;
				}
			}
		}
	}
	
	void enemyActions() {
		for (Enemy b : birds) {
			b.moveToPosition(player.getX()+player.rad, player.getY()+player.rad);
		}
		if (timerTick %100 == 0) {
			for (int i = 0; i < timerTick/100; i++) {
				if (birds.size() >= 100){
					System.out.println("nope");
				} else {
					addEnemy();
				}	
			}
		}
		timerTick++;
	}
	
	void gameStuff() {
		for(int j = 0; j < birds.size() ; j++) {
			Enemy i = birds.get(j);
			double positionXY = Math.sqrt(Math.pow((player.getX() - i.getX()), 2)+ (Math.pow(player.getY() - i.getY(), 2)));
			if (positionXY <= 20 && i.birdType == i.FLAMINGO) {
				player.hp -= i.damage;
				birds.remove(i);
			}
			if (positionXY <= 20 && i.birdType == i.PIGEON) {
				player.hp -= i.damage;
				birds.remove(i);
			}
			if (positionXY <= 60 && i.birdType == i.GOOSE) {
				player.hp -= i.damage;
				birds.remove(i);
			}
			if(player.hp <= 0) {
				player.isdead = true;

			}	
			for (int b = 0; b < bullets.size(); b++) {
				
				Bullet c = bullets.get(b);
				double BulletXY = Math.sqrt(Math.pow((c.getX() - i.getX()), 2)+ (Math.pow(c.getY() - i.getY(), 2)));
				if (BulletXY <= 23 && i.birdType == i.FLAMINGO) {
					i.hp -= player.weapons.get(player.currentWeapon).damage;
					bullets.remove(c);	
				} else if (BulletXY <= 15 && i.birdType == i.PIGEON) {
					i.hp -= player.weapons.get(player.currentWeapon).damage;
					bullets.remove(c);	
				} else if (BulletXY <= 45 && i.birdType == i.GOOSE) {
					i.hp -= player.weapons.get(player.currentWeapon).damage;
					bullets.remove(c);	
				}
				if (i.hp <= 0) {
					birds.remove(i);
					score += 100;
				}
			}
			
			
			
			player.checkAngle();
			updateTurretAngle();
		}
	}
	
	void updateTurretAngle() {
		int dispX = mouseX - player.x;
		int dispY = -(mouseY - player.y);
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
		angle *= -1;
		
	}
	
	void bulletActions() {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			if(b.life > 10) {
				bullets.remove(b);
				i--;
			}
		}
		
		for(Bullet b : bullets) {
			b.updatePosition();
			

		}
		if (rightClick) {
			if (timerTick %7 == 0) {
				turretDrawer = true;
				bullets.add(new Bullet(player.x,player.y,50, mouseX,mouseY));
			} else turretDrawer = false;
			
		} 
	}


	private class Timer1Listener  implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (!init) {
				return;
			}
			

			for (Enemy i : birds) {
				i.moveToPosition(player.getX()+player.rad, player.getY()+player.rad);
			}
			resetPlayerPosition();
			playerActions();
			enemyActions();			
			bulletActions();
			gameStuff();
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
			for(Rectangle o : obstacles) {
				g2.drawImage(barrier, (int)o.getX(),(int)o.getY(),(int) o.getWidth(),(int)o.getHeight(),null);
			}

			for(int j = 0; j < birds.size() ; j++) {
				g.setColor(Color.PINK);
				Enemy i = birds.get(j);
				g2.rotate(i.accurateAngle, i.x,i.y);
				if (i.birdType == i.FLAMINGO){
					g2.drawImage(enemy,(int)i.getX(),(int)i.getY(), 26,26,null);
				} else if (i.birdType == i.PIGEON){
					g2.drawImage(enemy2,(int)i.getX(),(int)i.getY(), 26,26,null);
				} else if (i.birdType == i.GOOSE){
					g2.drawImage(enemy3,(int)i.getX(),(int)i.getY(), 52,52,null);
				}
				
				g2.rotate(0-i.accurateAngle, i.x, i.y);
				double positionXY = Math.sqrt(Math.pow((player.getX() - i.getX()), 2)+ (Math.pow(player.getY() - i.getY(), 2)));
				if (positionXY <= 20) {
					player.hp -= i.damage;
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
				player.checkAngle();
			}
			
			for(Bullet b : bullets) {
				g.setColor(Color.GREEN);
				g2.fillOval((int)b.x,(int) b.y, 3, 3);
				g.setColor(Color.WHITE);
				g2.drawOval((int)b.x,(int) b.y, 3, 3);
			}
			
			
			
			drawHealth(g2, player.hp);
			if(player.isdead) {
				System.out.println("GAME OVER");
				g.setColor(Color.BLACK);
				g.fillRect(1000, 1000, 1000, 1000);
				birds.clear();
			}
			else if (turretDrawer) {
				player.playerDraw(g2, hull,turretF, angle);
			} else player.playerDraw(g2, hull,turret, angle);
		}


	}

	class KL implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			//up on
			if (e.getKeyCode()==KeyEvent.VK_W && player.canGoUp) {
				player.U=true;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_D &&player.canGoRight) {
				player.R=true;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_A && player.canGoLeft) {
				player.L=true;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_S && player.canGoDown) {
				player.D=true;
			}
			if (e.getKeyCode()== KeyEvent.VK_R) {
				player.weapons.get(player.currentWeapon).reload();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//up on
			if (e.getKeyCode()==KeyEvent.VK_W) {
				player.U=false;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_D) {
				player.R=false;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_A) {
				player.L=false;
			}
			//up on
			if (e.getKeyCode()==KeyEvent.VK_S) {
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
			
			//harwood testing
		//	while(rightClick) {
				//bullets.add(new Bullet(player.x,player.y,50, e.getX(),e.getY()));
				player.weapons.get(player.currentWeapon).shoot(bullets, player, e.getX(), e.getY());
		//	}
		}


		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3) {
				rightClick = false;
			}

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
			mouseX = e.getX();
			mouseY = e.getY();
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			
		}


	}





}