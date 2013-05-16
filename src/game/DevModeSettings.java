package game;

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
	static int activeBlock = 1;
	static int maxActiveEnemy = 1;
	static int activeEnemy = 0;
	static CollectableType activeCollectable = CollectableType.ABILITY_DIGGING;
	
	static void increaseActiveBlock()
	{
		activeBlock++;
		if (activeBlock > maxActiveBlock) activeBlock = 0;
	}
	
	static int getActiveBLock()
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