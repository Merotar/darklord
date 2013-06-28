package darklord.rules;

import java.io.Serializable;

import darklord.game.GameEngine;
import darklord.game.Player;
import darklord.game.Room;

public interface Reaction extends Serializable
{
	public void apply(GameEngine engine, Player thePlayer);
}
