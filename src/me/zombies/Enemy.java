
package me.zombies;

public class Enemy {
	static final int v = 10;
	double x;
	double y;
	int vx;
	int vy;
//hehe brennan was here 
//method
	//test
	double getX(){
		return x;
	}
	
	
	double getY(){
		return y;
	}
	
	void UpdatePosition() {
		x = x+vx;
		y = y+vy;
	}
	
	void moveToPosition(double toX, double toY) {
		double dispX =  toX - x;
		double dispY = toY - y;
		double angle = Math.atan(dispY - dispX);
		x += v * Math.cos(angle);
		y += v * Math.sin(angle);
	}
	
	Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.vx = v;
		this.vy = v;
	}
	
	Enemy(int x, int y,int vx, int vy){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		
	}
}

