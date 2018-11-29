
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

