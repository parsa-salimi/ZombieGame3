
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
import java.util.ArrayList;

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
	Player player;

	static final int playerV = 10;
	//for key binding
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    static final int T1_SPEED = 20;
	

	public static void main(String[] args) {
				new GUI();
			}
	GUI(){
		birds = new ArrayList<Enemy>();
		//create 5 enemies
		for(int i = 0; i < 5; i++) {
			birds.add(new Enemy(i*50+1, i*40+1));
		}
		pan = new DrawPanel();
		pan.addKeyListener(new KL());
		
		Timer firstTimer = new Timer(T1_SPEED,new Timer1Listener());
		
		this.setTitle("Main graphics ..."); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(pan);	
		this.pack();
		this.setVisible(true);
		
		player = new Player(pan.getWidth(),pan.getHeight());
		System.out.println("gui run");
		firstTimer.start();
	}
	
	private class Timer1Listener  implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			player.movePlayer();
			for (Enemy i : birds) {
				i.moveToPosition(player.getX(), player.getY());
			}
			pan.repaint();

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
			this.requestFocus();
			panSize = this.getWidth();
			
			Graphics2D g2 = (Graphics2D) g;		
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			for( Enemy i : birds) {
				g2.drawRect((int)i.getX(),(int)i.getY(), 2,2);
			}
			player.playerDraw(g);
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