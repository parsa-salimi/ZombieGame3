package Package;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;




public class LoadScreen {
	public static void main(String[] args) {
		new LoadScreen();
	}
	
	
	
	JFrame window;
	DrawingPanel dpanel;
	Random r = new Random();
	
	final static int T1_SPEED = 10;
	
	//these are initial positions of various strokes, they all have corresponding change operations at
	//Timer1Listener
	
	int strokex1 = 400;
	int strokex2 = 800;
	int strokey2 = 900;
	int strokey3 = 900;
	int strokey1 = 300;
	int strokex3 = 400;
	int strokey4 = 700;
	int strokey5 = 700;
	int strokey6 = 300;
	int strokex4 = 600;
	int strokex5 = 600;
	int strokey7 = 600;
	int strokey8 =  600;
	
	//filling up the hourglass
	int linepxl = 1400; //the bottom portion
	int line2pxl = 100;
	int line3pxl = -200; //empties upper portion, negative because it should wait a while until it gets filled
	
	//the circles
	int radius = 0; //the white initial one
	int r1 = -100;  // the secondary small circle, negative because of timing(wont draw until its zero)
	int r3 = 0;		//background
	
	String col="#FFFFFF";
	String col2="#388E3C";
	String col3="#3E2723";
	
	//colors for first circle
	String colorArray[] = {"#ef9a9a","#CE93D8",
			"#9FA8DA","#81D4FA","#80CBC4",
			"#C5E1A5","#FFF59D","#FFCC80","#BCAAA4"};

	//colors for bottom of hourglass
	String col2Array[] = {"#d32f2f","#C2185B","#7B1FA2",
			"#512DA8","#303F9F","#1976D2","#0288D1","#0097A7",
			"#00796B","#388E3C","#689F38","#AFB42B","#FBC02D",
			"#FFA000", "#F57C00", "#E64A19"};
	
	//colors for background circle
	String col3Array[] = {"#b71c1c","#4A148C","#1A237E","#01579B",
			"#004D40","#33691E","#F57F17",
			"#E65100","#3E2723","#263238"};
	


	
	ArrayList <Brush> brushes = new ArrayList<Brush>();
	
	//bottom horizontal line
	Brush b1 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
			 g2.setStroke(new BasicStroke(30,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
              g2.setColor(Color.decode("#01579B")); //dark blue
              g2.draw(new Line2D.Float(400, 900, strokex1, 900));
              
		}
	};
	
	//top horizontal line
	Brush b2 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
			 g2.setStroke(new BasicStroke(30,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
              g2.setColor(Color.decode("#b71c1c")); //dark red
              g2.draw(new Line2D.Float(800, 300, strokex2, 300)); 
		}
	};
	
	//blue vertical lines
	Brush b3 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
              g2.setStroke(new BasicStroke(30,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
              g2.setColor(Color.decode("#03A9F4")); //blue
              g2.draw(new Line2D.Float(800, 900, 800, strokey3));
              g2.draw(new Line2D.Float(400, 300, 400, strokey1));
		}
	};
	
	//red oblique line
	Brush b4 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
			 g2.setStroke(new BasicStroke(30,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
              g2.setColor(Color.decode("#f44336")); //red
              g2.draw(new Line2D.Float(400, 700, strokex3, strokey4));
              
		}
	};
	
	//red vertical lines
	Brush b5 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
			 g2.setStroke(new BasicStroke(30,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
              g2.setColor(Color.decode("#f44336")); //red
              g2.draw(new Line2D.Float(400, 700, 400, strokey5));
              g2.draw(new Line2D.Float(800, 300, 800, strokey6));
		}
	};
	
	//blue oblique line
	Brush b6 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
			  g2.setStroke(new BasicStroke(30,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
              g2.setColor(Color.decode("#03A9F4")); //blue
              g2.draw(new Line2D.Float(strokex4, strokey7, strokex5, strokey8));
              
		}
	};
	
	// bottom filler
	Brush b7 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
              g2.setStroke(new BasicStroke(1));
              int x = 1;
              g2.setColor(Color.decode(col2)); 
              for(int i = 890; i > linepxl; i-=1) {
            	  if(i < 706) {
            		  g.drawLine(415+2*x,i,785-2*x,i);
            		  x++;
            	  }
            	  else {
            	  g.drawLine(415,i,785,i);
            	  }
            	  if(i == 606) {
            		  linepxl=900;
            		  i = 900;
            		  dpanel.repaint();
            		  col2=col2Array[r.nextInt(15)];
            	  }
              }
              
		}
	};
	
	//orange upper filler
	Brush b8 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
              g2.setStroke(new BasicStroke(1));
              int x = 1;
              g2.setColor(Color.decode("#F57C00")); //orange
              for(int i = 310; i < line2pxl; i+=1) {
            	  if(i > 494) {
            		  g.drawLine(415+2*x,i,785-2*x,i);
            		  x++;
            	  }
            	  else {
            	  g.drawLine(415,i,785,i);
            	  }
            	  //sleep
              }
              
		}
	};
	
	//upper portion emptier
	Brush b9 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
              g2.setStroke(new BasicStroke(1));
              int x = 1;
              g2.setColor(Color.decode("#37474F")); //grey
              for(int i = 310; i < line3pxl; i+=1) {
            	  if(i > 500) {
            		  g.drawLine(415+2*x,i,785-2*x,i);
            		  x++;
            	  }
            	  else {
            		  g.drawLine(415,i,785,i);
            	  }
            	  
            	  if(i == 594) {
            		  line3pxl = 300;
            		  i = 300;
            	  }
              }
              
		}
	};
	
	//small circle changing color
	Brush b10 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
              g2.setStroke(new BasicStroke(1));
              g2.setColor(Color.decode(col));
              g2.fillOval(600-radius/2, 600-radius/2, radius, radius);
              if(radius == 500) {
            	  radius = 0;
              }
              
		}
	};
	
	//white circle
	Brush b11 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
              g2.setStroke(new BasicStroke(1));
              g2.setColor(Color.WHITE);
              g2.fillOval(600-r1/2, 600-r1/2, r1, r1);
                
		}
	};
	
	//background color circle
	Brush b12 = new Brush() {
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
              g2.setStroke(new BasicStroke(1));
              g2.setColor(Color.decode(col3));
              g2.fillOval(600-r3/2, 600-r3/2, r3, r3);
              if(r3 == 1600) {
            	  r3 = 500;
              }
              
		}
	};
	
	
	
	LoadScreen() {
		window = new JFrame("load screen");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//window.setUndecorated(true);
		window.setLocationRelativeTo(null);
	    dpanel = new DrawingPanel();
	    Timer firstTimer = new Timer(T1_SPEED,new Timer1Listener());
	    firstTimer.start();
		window.add(dpanel);
		window.setVisible(true);
		brushes.add(b12);
		brushes.add(b11);
		brushes.add(b10);
		brushes.add(b1);
		brushes.add(b2);
		brushes.add(b3);
		brushes.add(b4);
		brushes.add(b5);
		brushes.add(b6);
		brushes.add(b7);
		brushes.add(b8);
		brushes.add(b9);
		
		
	}
	
	private class DrawingPanel extends JPanel {
		DrawingPanel() {
			this.setBackground(Color.decode("#37474F"));	
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for( Brush b : brushes) {
				b.draw(g);
			}
		}
	}
	
	
	
	private class Timer1Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(strokex1 < 800) strokex1 += 2;
			if(strokex2 > 400) strokex2 -= 2;
			if(strokey2 > 300)  strokey2 -= 3;
			if(strokey3 > 705 ) strokey3 -= 2;
			if(strokey1 < 495)	strokey1 +=2;
			if(strokex3 < 800)  strokex3 +=2;
			if(strokey4 > 500)	strokey4 -=1;
			if(strokey5 < 900)	strokey5 +=2;
			if(strokey6 < 490)	strokey6 +=1;
			if(strokex4 > 400)	strokex4 -=2;
			if(strokey7 > 500)	strokey7 -=1;
			if(strokex5 < 800)	strokex5 +=2;
			if(strokey8 < 700) 	strokey8 +=1;
			if(linepxl > 605) 	linepxl--;
			if(line2pxl < 590)	line2pxl++;
			if(line3pxl < 595)	line3pxl++;
			if(r1 < 500)		r1 += 2;
			if(radius < 500)	radius+= 2;
			if(radius == 500) {
				col = colorArray[r.nextInt(9)];
			}
			if(r3<2000) r3+= 4;
			if(r3 == 1600) {
				col3 = col3Array[r.nextInt(9)];
			}
			
			dpanel.repaint();
		}
	}

}









