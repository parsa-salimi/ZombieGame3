package me.zombies;

public class Player {
	static final int v = 10;
	int x;
	int y;
	
	double getX(){
		return x;
	}
	
	
	double getY(){
		return y;
	}
	
	Player(int scnx, int scny, int v){
		this.x = scnx/2;
		this.y = scny/2;
	}
}
