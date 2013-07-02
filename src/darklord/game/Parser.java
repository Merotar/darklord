package darklord.game;

import darklord.enemies.ChasingBlockEnemy;
import darklord.enemies.Enemy;
import darklord.enemies.EnemyRandomMove;
import darklord.enemies.StaticEnemyCrystal;
import darklord.enemies.StaticEnemyOneShot;
import darklord.media.SoundLoader;
import darklord.rules.AddCrystalsReaction;
import darklord.rules.AllEnemiesDestroyedCondition;
import darklord.rules.Condition;
import darklord.rules.PlaySoundReaction;
import darklord.rules.PlayerCollideBlockCondition;
import darklord.rules.PlayerInsideBlockCondition;
import darklord.rules.Reaction;
import darklord.rules.SetBlockTypeReaction;

public class Parser
{
	public static String newRule = "#StartRule";
	public static String endRule = "#EndRule";
	
	public static String Reaction = "Reaction";
	public static String Condition = "Condition";
	public static String CollideBlock = "CollideBlock";
	public static String InsideBlock = "InsideBlock";
	public static String Player = "Player";
	public static String Add = "Add";
	public static String CrystalsRed = "CrystalsRed";
	public static String CrystalsBlue = "CrystalsBlue";
	public static String CrystalsGreen = "CrystalsGreen";
	public static String CrystalsYellow = "CrystalsYellow";
	public static String setBlockType = "SetBlockType";
	public static String allEnemiesDestroyed = "AllEnemiesDestroyed";
	public static String playSound = "PlaySound";
	public static String Pling = "Pling";
	public static String Block = "Block";
	public static String Enemy = "Enemy";
	
	public static Condition parseCondition(String[] words)
	{
		Condition tmpCondition = null;
		
		// collide with block
		if (words[1].equals(CollideBlock))
		{
			float x = (float) Math.floor(Integer.parseInt(words[2]));
			float y = (float) Math.floor(Integer.parseInt(words[3]));
			tmpCondition = new PlayerCollideBlockCondition(x, y);
		}
		
		// inside with block
		if (words[1].equals(InsideBlock))
		{
			float x = (float) Math.floor(Integer.parseInt(words[2]));
			float y = (float) Math.floor(Integer.parseInt(words[3]));
			tmpCondition = new PlayerInsideBlockCondition(x, y);
		}
		
		// all enemies are destroyed
		if (words[1].equals(allEnemiesDestroyed))
		{
			tmpCondition = new AllEnemiesDestroyedCondition();
		}
		
		return tmpCondition;
	}
	
	public static Reaction parseReaction(String[] words)
	{
		Reaction tmpReaction = null;
		
		// add crystals
		if (words[1].equals(Player))
		{
			if (words[2].equals(Add))
			{
				if (words[3].equals(CrystalsRed))
				{
					tmpReaction = new AddCrystalsReaction(0, Integer.parseInt(words[4]));
				}
				if (words[3].equals(CrystalsBlue))
				{
					tmpReaction = new AddCrystalsReaction(1, Integer.parseInt(words[4]));
				}
				if (words[3].equals(CrystalsGreen))
				{
					tmpReaction = new AddCrystalsReaction(2, Integer.parseInt(words[4]));
				}
				if (words[3].equals(CrystalsYellow))
				{
					tmpReaction = new AddCrystalsReaction(3, Integer.parseInt(words[4]));
				}
			}
		}
		
		// set block type
		if (words[1].equals(setBlockType))
		{
			int tmpType = Integer.parseInt(words[4]);
			tmpReaction = new SetBlockTypeReaction(Integer.parseInt(words[2]),Integer.parseInt(words[3]), BlockType.values()[tmpType]);
		}
		
		// play sound
		if (words[1].equals(playSound))
		{
			if (words[2].equals(Pling))
			{
				tmpReaction = new PlaySoundReaction();
			}
		}
		
		return tmpReaction;
	}

	public static BlockType parseBlockType(String string)
	{
		if (string.equals("BLOCK_NONE")) return BlockType.BLOCK_NONE;
		if (string.equals("BLOCK_ROCK")) return BlockType.BLOCK_ROCK;
		if (string.equals("BLOCK_DIRT")) return BlockType.BLOCK_DIRT;
		if (string.equals("BLOCK_RED")) return BlockType.BLOCK_RED;
		if (string.equals("BLOCK_BLUE")) return BlockType.BLOCK_BLUE;
		if (string.equals("BLOCK_GREEN")) return BlockType.BLOCK_GREEN;
		if (string.equals("BLOCK_YELLOW")) return BlockType.BLOCK_YELLOW;
		if (string.equals("BLOCK_WHITE")) return BlockType.BLOCK_WHITE;
		if (string.equals("BLOCK_GLASS")) return BlockType.BLOCK_GLASS;
		
		Print.err("could not find block type: "+string);
		return BlockType.BLOCK_NONE;
	}

	public static Enemy parseEnemy(String[] string)
	{
		float posX = Float.parseFloat(string[1]);
		float posY = Float.parseFloat(string[2]);
		
		if (string[3].equals("EnemyRandomMove")) return new EnemyRandomMove(posX, posY);
		if (string[3].equals("StaticEnemyCrystal")) return new StaticEnemyCrystal(posX, posY);
		if (string[3].equals("StaticEnemyOneShot")) return new StaticEnemyOneShot(posX, posY);
		if (string[3].equals("ChasingBlockEnemy")) return new ChasingBlockEnemy(posX, posY);
		
		return null;
	}
}
