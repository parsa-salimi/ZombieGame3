package me.zombies;

import java.awt.Graphics;

public class Player {
	static final int v = 7;
	int x;
	int y;
	int hp = 100;
	boolean isdead = false;
	
	boolean U,R,D,L;
	
	Player(int scnx, int scny){
		System.out.println("sx=" +scnx);
		this.x = scnx/2;
		this.y = scny/2;
	}
	
	double getX(){
		return x;
	}
	
	double getY(){
		return y;
	}
	
	void movePlayer() {
		if (U) {
			y-=v;
		}
		if (R) {
			x+=v;
		}
		if (D) {
			y+=v;
		}
		if (L) {
			x-=v;
		}
	}
	
	void playerDraw(Graphics g){
		g.drawOval(this.x, this.y, 2*rad, 2*rad);
	}
}
