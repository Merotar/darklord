/*
 * enum to describe different collectables
 * 
 * @deprecated
 * 
 * @author Sebastian Artz
 * 
 */

package game;

enum CollectableType
{
	
	NONE(0), BLOCK_ROCK(1), BLOCK_BROWN(2), BLOCK_RED(3), BLOCK_BLUE(4), BLOCK_GREEN(5), ABILITY_TELEPORT(6), ABILITY_DIGGING(7);

	int id;
	
	CollectableType(int theId)
	{
		id = theId;
	}
};