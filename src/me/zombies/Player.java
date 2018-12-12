package me.zombies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player {
	static final int v = 6;
	int x;
	int y;
	int hp = 500;
	int rad = 8;
	boolean isdead = false;
	boolean canGoUp,canGoDown,canGoLeft,canGoRight = true;
	boolean upDown = false;
	double angle;

	
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
	
	void checkAngle() {
		//primary angles
		if (U&&R) {
			angle = Math.PI/4 + Math.PI/2;
			return;
		}
		if (U&&L) {
			angle = Math.PI/4;
			return;
		}
		if (D&&L) {
			angle = -Math.PI/4;
			return;
		}
		if (D&&R) {
			angle = Math.PI/4 - Math.PI;
			return;
		}
		
		if (U) {
			angle = Math.PI/2; 
		}
		if (R) {
			angle = Math.PI; 
		}
		if (D) {
			angle = -Math.PI/2; 
		}
		if (L) {
			angle = 0; 
		}
		

	}
	
	void playerDraw(Graphics2D g2d, BufferedImage hull, BufferedImage turret, double turretAngle){
		AffineTransform old = g2d.getTransform();
		
		g2d.rotate(angle, x+7, y+7);
		
		g2d.drawImage(hull, x-25, y-24, 64, 64, null);
		g2d.drawOval(this.x, this.y, 2*rad, 2*rad);
		g2d.setTransform(old);
		g2d.rotate(turretAngle, x+7, y+7);
		g2d.drawImage(turret, x-55, y-56, 128, 128, null);
	}
}
