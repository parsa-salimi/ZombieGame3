
package me.zombies;

public class Enemy {
	static final int v = 10;
	int x;
	int y;
	int vx;
	int vy;
//hehe brennan was here 
//method
	//test
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
	
	void moveToPosition(int toX, int toY) {
		int dispX =  toX - x;
		int dispY = toY - y;
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

