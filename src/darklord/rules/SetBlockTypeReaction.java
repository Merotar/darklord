package darklord.rules;

import darklord.blocks.Block;
import darklord.game.BlockType;
import darklord.game.GameEngine;
import darklord.game.Player;

public class SetBlockTypeReaction implements Reaction
{
	int x, y;
	Block block;
	
	public SetBlockTypeReaction(int theX, int theY, Block theBlock)
	{
		x = theX;
		y = theY;
		block = theBlock;
	}

	public void apply(GameEngine engine, Player thePlayer)
	{
		engine.map.setLocalBlockAt(x, y, block);
	}

}
