package me.zombies;

public class Obstacle {
	int R;
	int L;
	int U;
	int D;
	
	Obstacle(int up, int right, int down, int left) {
		this.U = up;
		this.R = right;
		this.D = down;
		this.L= left;
	}
	
}
