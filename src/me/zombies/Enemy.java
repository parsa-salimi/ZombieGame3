
package me.zombies;

public class Enemy {
    int v = 10;
	double x;
	double y;
	int vx;
	int vy;
	int hp = 20;
	int damage = 5;
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
	
	void mapSpawn(int scrnx, int scrny) {
		int r = (int) (Math.random()*2);
		int r2 = (int) (Math.random()*2);
	
		if (r == 0) {
			x = Math.random()*scrnx;
			y = r2*scrny;
		}else if (r == 1) {
			x = r2*scrnx;
			y = Math.random()*scrny;
		}
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
	
	Enemy(int x, int y,int assignV) {
		
		mapSpawn(x,y);
		this.v = assignV;
	}
	
	Enemy(int x, int y,int vx, int vy){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		
	}
}


	