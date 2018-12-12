
			
		}


	}





}
			
			playerActions();
			enemyActions();			
			bulletActions();
			
			gameStuff();

			player.movePlayer();
			player.canGoDown = player.canGoLeft = player.canGoUp = player.canGoRight = true;
			
			if(player.upDown) {
				pRect = new Rectangle(player.x -7, player.y -25, 33, 64);
			}
			else {
				pRect = new Rectangle(player.x-25, player.y-10, 64, 33);
			}
			for(Rectangle o : obstacles) {
				if(o.intersects(pRect)) {
					if(o.getX() > pRect.getX()) {
						player.R = false;
						player.canGoRight = false;
					}
					if(o.getX() < pRect.getX() ) {
						player.L = false;
						player.canGoLeft = false;
					}
					if(o.getY()  < player.getY() +64) {
						player.D = false;
						player.canGoDown = false;
					}
					if(o.getY() + o.getHeight() < player.getY()) {
						player.U = false;
						player.canGoUp = false;
					}
				}
			}
			for (Enemy i : birds) {
				i.moveToPosition(player.getX()+player.rad, player.getY()+player.rad);
			}
			
		}


	}





}