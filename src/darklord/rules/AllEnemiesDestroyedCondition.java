package darklord.rules;

import darklord.game.GameEngine;
import darklord.game.Player;

public class AllEnemiesDestroyedCondition implements Condition
{

	public boolean isTrue(GameEngine engine, Player thePlayer)
	{
		if (engine.map.getEnemies().size() == 0) return true;
		return false;
	}

}
