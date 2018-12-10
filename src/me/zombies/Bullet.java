package me.zombies;
import java.lang.*;

public class Bullet {
	//velocity
	double v = 10;
	double vx;
	double vy;
	double x;
	double y;
	double angle;
	

	
	//given current player position, mouse position, and net velocity
	//initial x, initial y, net speed,  mouse position x, and y
	Bullet(int initX, int initY,int v, int mouseX, int mouseY) {
		x = initX;
		y = initY;
		int displaceX = mouseX - initX;
		int displaceY = mouseY - initY;
		angle = Math.atan((double)Math.abs(displaceY)/(double)Math.abs(displaceX));
		this.vx = v * Math.cos(angle);
		this.vy = v * Math.sin(angle);
		boolean q1 = true;
		boolean q2,q3,q4 = false;
		if(displaceY < 0) {
			this.vy *= -1;
			q3 = q4 = true;
			q1 = q2 = false;
		}
		else {
			q3 = q4 = false;
			q1 = q2 = true;
		}
		if(displaceX < 0) {
			this.vx *= -1;
			q2 = q3 = true;
			q1 = q4 = false;
		}
		else {
			q2 = q3 = false;
			q1 = q4 = true;
		}
		if(q2) {
			angle = Math.PI - angle;
		}
		if(q3) {
			angle = Math.PI + angle;
		}
		if(q4) {
			angle = Math.PI*2 - angle;
		}
		
	}
	
	Bullet(int initX, int initY) {
		x = initX;
		y = initY;
		this.vx = 0;
		this.vy = -v;
	}
	
	void updatePosition() {
		this.x += vx;
		this.y += vy;
	}
	

	
	double getX() {
		return this.x;
	}
	
	double getY() {
		return this.y;
	}
	
}
