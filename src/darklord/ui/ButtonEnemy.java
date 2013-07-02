package darklord.ui;

import darklord.enemies.Enemy;
import darklord.game.Block;
import darklord.media.TextureRegion;

public class ButtonEnemy extends Button
{
	Enemy enemy;
	
	public ButtonEnemy(TextureRegion region, String theName, Enemy theEnemy)
	{
		super(region, region, theName);
		setName(theName);
		enemy = theEnemy;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy theEnemy) {
		this.enemy = theEnemy;
	}
}
