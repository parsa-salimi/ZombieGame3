
package me.zombies;

public class Enemy {
	static final int v = 2;
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
		double dispY = (toY - y);
		double angle = Math.atan(Math.abs(dispY) /Math.abs(dispX));
		if(dispY < 0) {
			y -= v * Math.sin(angle);
		}
		else if (dispY >= 0) {
			y += v* Math.sin(angle);
		}
		if(dispX >= 0) {
			x += v * Math.cos(angle);
		}
		else if (dispX < 0) {
			x -= v * Math.cos(angle);
		}
		
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

