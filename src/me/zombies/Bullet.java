package me.zombies;
import java.lang.*;

public class Bullet {
	//velocity
	double vx;
	double vy;
	double x;
	double y;
	

	
	//given current player position, mouse position, and net velocity
	//initial x, initial y, net speed,  mouse position x, and y
	Bullet(int initX, int initY,int v, int mouseX, int mouseY) {
		x = initX;
		y = initY;
		int displaceX = mouseX - initX;
		int displaceY = mouseY - initY;
		double angle = Math.atan(displaceY/displaceX);
		this.vx = v * Math.cos(angle);
		this.vy = v * Math.sin(angle);
		
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
