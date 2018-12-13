package me.zombies;
import java.util.ArrayList;

public class Weapon {
	int maxammo = 5;
	int damage = 5;
	int ammo;
	int rate = 400; // how many ms between shots
	double bulletspeed = 1.0;

	void shoot(ArrayList<Bullet> blist, Player player, int mx, int my) {
		if (ammo <= 0) return;
		ammo--;
		//do whatever shooting does
		blist.add(new Bullet(player.x,player.y,bulletspeed, mx,my));
		
	}

	void reload(int n) { //n is how many bullets you picked up
		if (n < 0 ) return;		
		ammo += n;
		if (ammo > maxammo) ammo = maxammo;
	}
	
	void weapon1(int damage, int ammo, int bulletspeed) { //machine gun
		damage = 10;
		ammo = 100;

	}
}



