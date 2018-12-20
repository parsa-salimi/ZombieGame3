
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

	BufferedImage hull,turret,turretF,turretC,turretFC,backG,barrier,enemy,enemy2,enemy3;

	DrawPanel pan;
	ArrayList<Enemy> birds;
	ArrayList<Bullet> bullets;
	ArrayList<Bullet> bullets2;
	ArrayList<Rectangle> obstacles;
	Player player;

	int mouseX = 0;
	int mouseY = 0;
	int cannon = 0; 
	boolean rightClick = false;
	boolean leftClick	= false;
	double angle;
	int width, height;
	Random r;
	Timer firstTimer;
	
	int panSize=600; //initial value;
	int playerAngle;
	boolean rightShoot = false;
	boolean leftShoot = false;

	boolean init = false;
	long  timerTick = 0;
	int hpMax;
	//for key binding
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	static final int T1_SPEED = 20;

	Rectangle pRect;


	public static void main(String[] args) {
		new GUI();
	}
	GUI(){
		//initialize variables
		r = new Random();
		player = new Player(0,0);
		birds = new ArrayList<Enemy>();
		//main bullets
		bullets = new ArrayList<Bullet>();
		//machine gun bullets
		bullets2 = new ArrayList<Bullet>();
		obstacles = new ArrayList<Rectangle>();

		//create 2 initial enemies
		for(int i = 0; i < 2; i++) {
			birds.add(new Enemy(i*50+1, i*40+1,r.nextInt(6) + 1));
		}


		pan = new DrawPanel();
		pan.addKeyListener(new KL());
		pan.addMouseListener(new ML());
		pan.addMouseMotionListener(new ML2());
		firstTimer = new Timer(T1_SPEED,new Timer1Listener());

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
		//load sprites
		try {
			hull = ImageIO.read(new File("./res/imgs/hull.png"));
			turret = ImageIO.read(new File("./res/imgs/turret.png"));
			turretF = ImageIO.read(new File("./res/imgs/turretF.png"));
			turretC = ImageIO.read(new File("./res/imgs/turretC.png"));
			turretFC = ImageIO.read(new File("./res/imgs/turretFC.png"));
			backG = ImageIO.read(new File("./res/imgs/backG.png"));
			barrier = ImageIO.read(new File("./res/imgs/barrier.png"));
			enemy = ImageIO.read(new File("./res/imgs/enemy.png"));
			enemy2 = ImageIO.read(new File("./res/imgs/enemy2.png"));
			enemy3 = ImageIO.read(new File("./res/imgs/enemy3.png"));
		} catch (IOException e) {
			System.out.println("An image could not be loaded or is missing.");
			System.exit(0);
		}
		player = new Player(pan.getWidth(),pan.getHeight());
		
		//add 10 obstacles
		for(int i = 0; i < 10; i++) {
			width = pan.getWidth();
			height = pan.getHeight();
			obstacles.add(new Rectangle(r.nextInt(width), r.nextInt(height-50)+50, r.nextInt(width/12) + 30
					,r.nextInt(height/12) + 30));
		}
		hpMax = player.hp;
		addEnemy();
		

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

	//so that the player won't go over the edge of the screen
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
		//collision
		player.canGoDown = player.canGoLeft = player.canGoUp = player.canGoRight = true;
		//if the player is either going up or down(so that it's a vertical rectangle facing up)
		if(player.upDown) {
			pRect = new Rectangle(player.x -7, player.y -25, 33, 64);
		}
		else {
			pRect = new Rectangle(player.x-25, player.y-10, 64, 33);
		}
		//for all obstacles, check collision
		for(Rectangle o : obstacles) {
			if(o.intersects(pRect)) {
				//if it collides with left side
				if(o.getX() > pRect.getX()) {
					player.R = false;
					player.canGoRight = false;
				}
				//if it collides with right side
				if(o.getX() < pRect.getX() ) {
					player.L = false;
					player.canGoLeft = false;
				}
				//if it collides with up side
				if(o.getY()  < player.getY() +64 && o.getX() < player.getX() + 15 && o.getX() + o.getWidth() > player.getX()
						&& o.getY() + o.getHeight() > player.getY()) {
					player.D = false;
					player.canGoDown = false;
				}
				//if it collides with down side
				if(o.getY() + o.getHeight() < player.getY() && o.getX() < player.getX() + 15 && o.getX() + o.getWidth()> player.getX()
						) {
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
		//add birds each 300 milliseconds
		if (timerTick %300 == 0) {
			for (int i = 0; i < timerTick/100; i++) {
				//if there are more than 30 birds don't add anything new
				if (birds.size() >= 30){
				} else {
					addEnemy();
				}	
			}
		}
		timerTick++;
	}
	
	BufferedImage turretImage() {
		BufferedImage img = null;
		//return different sprite for turrent depending on if we are shooting or not
		if (rightShoot && leftShoot) {
			img = turretFC;
		} else if (rightShoot) {
			img = turretF;
		} else if (leftShoot) {
			img = turretC;
		} else img = turret;
		return img;
		
	}

	//logic for when bullets, or players hit the birds and they die
	void bulletsAndBirds() {
		for(int j = 0; j < birds.size() ; j++) {
			Enemy i = birds.get(j);
			//distance between player and bird
			//this part handles when birds hit player
			double positionXY = Math.sqrt(Math.pow((player.getX() - i.getX()), 2)+ (Math.pow(player.getY() - i.getY(), 2)));
			if (positionXY <= 24 && i.birdType == i.FLAMINGO) {
				player.hp -= i.damage;
				birds.remove(i);
			}
			if (positionXY <= 24 && i.birdType == i.PIGEON) {
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
			
			//machine gun
			for (int b = 0; b < bullets2.size(); b++) {
				Bullet c = bullets2.get(b);
				double BulletXY = Math.sqrt(Math.pow((c.getX() - i.getX()), 2)+ (Math.pow(c.getY() - i.getY(), 2)));
				if (BulletXY <= 23 && i.birdType == i.FLAMINGO) {
					i.hp -= 10;
					bullets2.remove(c);
				} else if (BulletXY <= 15 && i.birdType == i.PIGEON) {
					i.hp -= 10;
					bullets2.remove(c);	
				} else if (BulletXY <= 45 && i.birdType == i.GOOSE) {
					i.hp -= 10;
					bullets2.remove(c);	
				}
		
			}
			//main bullets
			for (int b = 0; b < bullets.size(); b++) {
				Bullet c = bullets.get(b);
				double bulletXY = Math.sqrt(Math.pow((c.getX() - i.getX()), 2)+ (Math.pow(c.getY() - i.getY(), 2)));
				
					//this one has very high radius of damage but low damage
					if(player.currentWeapon == 0 && bulletXY < 200) {
					i.hp -= player.weapons.get(player.currentWeapon).damage;
					}
					//only remove bullet if it actually touches enemy
					if(player.currentWeapon == 0 && bulletXY < 23) {
						bullets.remove(c);
						}
					//this one has very high damage but low radius, goes through enemies
					else if(player.currentWeapon == 1 && bulletXY < 23) {
						i.hp -= player.weapons.get(player.currentWeapon).damage;
					}
			}

			if (i.hp <= 0) {
				birds.remove(i);
			}
		}
		/*this is a timer for the main bullet
		it goes up to 100 in bulletsActions then slowly decreases
		we are only allowed to shoot when we hit 0, then we shoot and it 
		goes back to 100 */
		if (cannon > 0 ) {
			cannon--;
		}
		player.checkAngle();
		updateTurretAngle();
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
		for(Bullet b : bullets2) {
			b.updatePosition();
		}
		for(Bullet b : bullets) {
			b.updatePosition();
			
		}
		//main gun every 7 milliseconds
		if (rightClick && timerTick %7 == 0) {
			bullets2.add(new Bullet(player.x,player.y,50, mouseX,mouseY));
			rightShoot = true;
		} else rightShoot = false;
		
		/*machine gun
		 we can't implement the timeout the same as the machine gun
		because the machine gun works by holding down the mouse but the 
		main gun works by clicking*/
		if(leftClick && cannon == 0) {
			player.weapons.get(player.currentWeapon).shoot(bullets, player, mouseX, mouseY);
			leftShoot = true;
			cannon = 100;
		} else leftShoot = false;
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
			bulletsAndBirds();
			pan.repaint();

		}
	}
	
	class DrawPanel extends JPanel {

		DrawPanel() {	
			this.setBackground(Color.WHITE);				
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
			//background texture
			g.drawImage(backG, 0, 0, pan.getWidth(), pan.getHeight(), null);
			//draw the obstacles
			for(Rectangle o : obstacles) {
				g2.drawImage(barrier, (int)o.getX(),(int)o.getY(),(int) o.getWidth(),(int)o.getHeight(),null);
			}
			
			//draw the birds
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
				player.checkAngle();
			}
			
			//draw machine gun bullets
			for(Bullet b : bullets2) {
				g.setColor(Color.GREEN);
				g2.fillOval((int)b.x,(int) b.y, 3, 3);
				g.setColor(Color.WHITE);
				g2.drawOval((int)b.x,(int) b.y, 3, 3);
			}
			
			//main bullets
			for(Bullet b : bullets) {
				g.setColor(Color.RED);
				if(player.currentWeapon == 0) {
				g2.fillOval((int)b.x,(int) b.y, 3, 3);
				}
				if(player.currentWeapon == 1) {
					g2.fillOval((int)b.x,(int) b.y, 3, 3);
					}
			}
			
			//our current score
			g2.drawString(String.valueOf(timerTick/10), 50, 40);
			drawHealth(g2, player.hp);

			player.playerDraw(g2, hull,turretImage(), angle);
			
			
			if(player.isdead) {
				timerTick = 0;
				firstTimer.stop();
				birds.clear();
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
			if (e.getKeyCode()== KeyEvent.VK_1) {
				player.currentWeapon = 0;
			}
			if (e.getKeyCode()== KeyEvent.VK_2) {
				player.currentWeapon = 1;
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
			//machine gun
			if(e.getButton() == MouseEvent.BUTTON3) {
				rightClick = true;
			}
			else {
				rightClick = false;
			}
			//main gun
			if(e.getButton()== MouseEvent.BUTTON1) {
				leftClick = true;
			} else {
				leftClick = false;
			}
		}


		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3) {
				rightClick = false;
			}
			if(e.getButton() == MouseEvent.BUTTON1) {
				leftClick = false;
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




