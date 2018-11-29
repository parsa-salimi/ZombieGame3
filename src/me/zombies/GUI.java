
package me.zombies;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;


public class GUI extends JFrame {
	int playerX = 100;
	int playerY = 100;
	static final int playerV = 10;
	//for key binding
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private static final String MOVE_UP = "move up";
    private static final String MOVE_DOWN = "move down";
    private static final String MOVE_LEFT = "move left";
    private static final String MOVE_RIGHT = "move right";
	

	public static void main(String[] args) {
		
				new GUI();
			}
	GUI(){
		DrawPanel pan = new DrawPanel();
		
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
		//binding keys to actions
		pan.getActionMap().put(MOVE_UP, new MoveAction(1));
		pan.getActionMap().put(MOVE_DOWN, new MoveAction(2));
		pan.getActionMap().put(MOVE_LEFT, new MoveAction(3));
		pan.getActionMap().put(MOVE_RIGHT, new MoveAction(4));
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
			
			int r = 20; //circle radius
			
			g.setColor(Color.GREEN.darker());
			g.drawLine(0, panSize/2, panSize/2, panSize/2);
			g.drawLine(panSize/2, panSize/2, panSize/2, panSize);
			g.setColor(Color.BLUE);
			g.drawOval(panSize/2-r/2, panSize/2-r/2, r, r);			
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
          
        }
    }
}