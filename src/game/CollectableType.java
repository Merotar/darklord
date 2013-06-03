package game;

import java.io.Serializable;

/**
 * enum to describe different collectables
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
enum CollectableType implements Serializable
{
	
	NONE(0), BLOCK_ROCK(1), BLOCK_BROWN(2), BLOCK_RED(3), BLOCK_BLUE(4), BLOCK_GREEN(5),
	BLOCK_YELLOW(6), ABILITY_TELEPORT(7), ABILITY_DIGGING(8), DIAMOND(9);

	int id;
	
	CollectableType(int theId)
	{
		id = theId;
	}
};