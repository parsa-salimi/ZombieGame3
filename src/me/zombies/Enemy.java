package me.zombies;

public class Enemy {
    double v = 10.0;
	double x;
	double y;
	double vx;
	double vy;
	int hp;
	int birdType;
	double angle;
	double accurateAngle;
	int damage;
	
	final int FLAMINGO = 0;
	final int PIGEON = 1;
	final int GOOSE = 3;
//hehe brennan was here 
//method
	//test
	double getX(){
		return x;
	}
	
	
	double getY(){
		return y;
	}
	
	void updatePosition() {
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
		//angle = Math.atan(Math.abs(dispY) /Math.abs(dispX));
		angle = Math.atan2((dispY),(dispX));
		accurateAngle = angle;			
		vx = v * Math.cos(angle);
		vy = v * Math.sin(angle);
		updatePosition();
		
	}
	
	Enemy(int x, int y,int assignV) {
		int type = (int) (Math.random()*100+1);
		
		System.out.println("The bird type is: " + type + " meaning it is number");
		if (type <= 60) {
			this.damage = 10;
			birdType = FLAMINGO;
			this.v = assignV+1;
			this.hp = 20;
			System.out.println("FLAMINGO");
		}
		if (type > 60 && type <= 90) {
			this.damage = 5;
			birdType = PIGEON;
			this.v = assignV+2;
			this.hp = 10;
			System.out.println("PIGEON");
		}
		if (type > 90) {
			this.damage = 75;
			birdType = GOOSE;
			this.hp = 100;
			this.v = assignV;
			System.out.println("GOOSE");
		}
		
		
		mapSpawn(x,y);
	}
	
	Enemy(int x, int y,int vx, int vy){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		
	}
}


	