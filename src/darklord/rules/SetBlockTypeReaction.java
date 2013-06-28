package darklord.rules;

import darklord.game.BlockType;
import darklord.game.GameEngine;
import darklord.game.Player;

public class SetBlockTypeReaction implements Reaction
{
	int x, y;
	BlockType type;
	
	public SetBlockTypeReaction(int theX, int theY, BlockType theType)
	{
		x = theX;
		y = theY;
		type = theType;
	}

	public void apply(GameEngine engine, Player thePlayer)
	{
		engine.map.setLocalBlockAt(x, y, type);
	}

}
