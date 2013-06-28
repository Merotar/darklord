package darklord.rules;

import java.io.Serializable;

import darklord.game.GameEngine;
import darklord.game.Player;
import darklord.game.Room;

public interface Condition extends Serializable
{
	
	public boolean isTrue(GameEngine engine, Player thePlayer);
}
