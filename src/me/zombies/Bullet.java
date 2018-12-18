package me.zombies;
import java.lang.*;

public class Bullet {
	//velocity
	double v = 1;
	double vx;
	double vy;
	double x;
	double y;
	double angle;
	
	

	
	//given current player position, mouse position, and net velocity
	//initial x, initial y, net speed,  mouse position x, and y
	Bullet(int initX, int initY,double v, int mouseX, int mouseY) {
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

		}
		

		

		if(displaceX < 0) {
			this.vx *= -1;
		}
		angle=Math.atan(vy/vx);
		
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
