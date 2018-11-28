package me.zombies;

public class Enemy {
	int x;
	int y;
	int vx;
	int vy;
//hehe brennan was here 
//method
	int getX(){
		return x;
	}
	
	
	int getY(){
		return y;
	}
	
	void UpdatePosition() {
		x = x+vx;
		y = y+vy;
	}
	
}
