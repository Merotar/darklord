package darklord.game;


import java.io.Serializable;

/**
 * enum to describe different blocks
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * @deprecated no longer needed
 * 
 */
public enum BlockType implements Serializable
{
	
	BLOCK_NONE(0), BLOCK_DIRT(1), BLOCK_ROCK(2), BLOCK_RED(3), BLOCK_BLUE(4),
	BLOCK_GREEN(5), BLOCK_YELLOW(6), BLOCK_CRYSTAL(7), BLOCK_PLANTS(8),
	BLOCK_GLASS(9), BLOCK_WHITE(10);

	int id;
	
	BlockType(int theId)
	{
		id = theId;
	}
};