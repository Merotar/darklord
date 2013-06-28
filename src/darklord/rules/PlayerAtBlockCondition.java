package darklord.rules;

import java.util.Vector;

import darklord.game.CollidableBox;
import darklord.game.GameEngine;
import darklord.game.Player;
import darklord.math.Vector2f;

public class PlayerAtBlockCondition implements Condition
{
	private CollidableBox position;
	
	public PlayerAtBlockCondition(float x, float y)
	{
		position = new CollidableBox(x, y, 1.f, 1.f);
	}
	
	public boolean isTrue(GameEngine engine, Player thePlayer)
	{
		if (position.collide(thePlayer.getLocalPosition(engine.map.levelStructure))) return true;
		return false;
	}
	
}
