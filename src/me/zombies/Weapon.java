package me.zombies;
import java.util.ArrayList;


public class Weapon {
	static final int INFINITE = -99;
	int maxammo;
	int damage;
	int ammo;
	int magazine;
	int rate; // how many ms between shots
	double bulletspeed;
	long lastshot;

	void shoot(ArrayList<Bullet> blist, Player player, int mx, int my) {
		//checks: can you shoot?
		long now = System.currentTimeMillis();
		if (now - lastshot < rate) {
			return;
		}
		if (ammo <= 0) return;

		lastshot = now;
		ammo--;
		if (maxammo == INFINITE) ammo++; 
		//do whatever shooting does
		blist.add(new Bullet(player.x,player.y,bulletspeed, mx,my));
	}

	void reload() { //n is how many bullets you picked up
		if (ammo > magazine) return;
		if (ammo < magazine ) {
			ammo = magazine;
		}
	}

}



