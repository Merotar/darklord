

/**
 * Settings for the developer mode
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class DevModeSettings
{
	
	static int maxActiveBlock = 8;
//		static int maxActiveCollectable = 2;
	static BlockType activeBlock = BlockType.BLOCK_ROCK;
	static int maxActiveEnemy = 1;
	static int activeEnemy = 0;
	static CollectableType activeCollectable = CollectableType.ABILITY_DIGGING;
	
	static void increaseActiveBlock()
	{
		if (activeBlock == BlockType.BLOCK_YELLOW) {activeBlock =  BlockType.BLOCK_NONE; return;}
		if (activeBlock == BlockType.BLOCK_GREEN) {activeBlock =  BlockType.BLOCK_YELLOW; return;}
		if (activeBlock == BlockType.BLOCK_BLUE) {activeBlock =  BlockType.BLOCK_GREEN; return;}
		if (activeBlock == BlockType.BLOCK_RED) {activeBlock =  BlockType.BLOCK_BLUE; return;}
		if (activeBlock == BlockType.BLOCK_DIRT) {activeBlock =  BlockType.BLOCK_RED; return;}
		if (activeBlock == BlockType.BLOCK_ROCK) {activeBlock =  BlockType.BLOCK_DIRT; return;}
		if (activeBlock == BlockType.BLOCK_NONE) {activeBlock =  BlockType.BLOCK_ROCK; return;}
	}
	
	static BlockType getActiveBLock()
	{
		return activeBlock;
	}
	
	static void increaseActiveCollectable()
	{
		if (activeCollectable == CollectableType.ABILITY_TELEPORT)
		{
			activeCollectable = CollectableType.BLOCK_BROWN;
			return;
		}
		if (activeCollectable == CollectableType.BLOCK_BROWN)
		{
			activeCollectable = CollectableType.BLOCK_RED;
			return;
		}			
		if (activeCollectable == CollectableType.BLOCK_RED)
		{
			activeCollectable = CollectableType.ABILITY_DIGGING;
			return;
		}
		if (activeCollectable == CollectableType.ABILITY_DIGGING)
		{
			activeCollectable = CollectableType.ABILITY_TELEPORT;
			return;
		}
	}
	
	static CollectableType getActiveCollectable()
	{
		return activeCollectable;
	}

	public static int getActiveEnemy() {
		return activeEnemy;
	}

	public static void increaseActiveEnemy()
	{
		activeEnemy++;
		if (activeEnemy > maxActiveEnemy) activeEnemy = 0;
	}
	
	public static void setActiveEnemy(int activeEnemy) {
		DevModeSettings.activeEnemy = activeEnemy;
	}
}