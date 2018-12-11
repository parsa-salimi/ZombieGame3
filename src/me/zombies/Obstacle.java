package me.zombies;

public class Obstacle {
	int ULX;
	int ULY;
	int W;
	int H;
	
	Obstacle(int upleftx, int uplefty, int  width, int height) {
		this.ULX = upleftx;
		this.ULY = uplefty;
		this.W = width;
		this.H= height;
	}
	
}
