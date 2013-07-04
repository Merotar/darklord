package darklord.game;

import darklord.blocks.BlueBlock;
import darklord.blocks.DirtBlock;
import darklord.blocks.EmptyBlock;
import darklord.blocks.GlassBlock;
import darklord.blocks.GreenBlock;
import darklord.blocks.RedBlock;
import darklord.blocks.StoneBlock;
import darklord.blocks.WhiteBlock;
import darklord.blocks.YellowBlock;
import darklord.enemies.BounceEnemy;
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
import darklord.blocks.Block;

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
			Block tmpBlock = parseBlockType(words[4]);
			tmpReaction = new SetBlockTypeReaction(Integer.parseInt(words[2]),Integer.parseInt(words[3]), tmpBlock);
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

	public static Block parseBlockType(String string)
	{
		if (string.equals("EmptyBlock")) return new EmptyBlock();
		if (string.equals("StoneBlock")) return new StoneBlock();
		if (string.equals("DirtBlock")) return new DirtBlock();
		if (string.equals("RedBlock")) return new RedBlock();
		if (string.equals("BlueBlock")) return new BlueBlock();
		if (string.equals("GreenBlock")) return new GreenBlock();
		if (string.equals("YellowBlock")) return new YellowBlock();
		if (string.equals("WhiteBlock")) return new WhiteBlock();
		if (string.equals("GlassBlock")) return new GlassBlock();
		
		Print.err("could not find block type: "+string);
		return new EmptyBlock();
	}

	public static Enemy parseEnemy(String[] string, int offsetX ,int offsetY)
	{
		float posX = Float.parseFloat(string[1]);
		float posY = Float.parseFloat(string[2]);
		
		if (string[3].equals("EnemyRandomMove")) return new EnemyRandomMove(offsetX+posX, offsetY+posY);
		if (string[3].equals("StaticEnemyCrystal")) return new StaticEnemyCrystal(offsetX+posX, offsetY+posY);
		if (string[3].equals("StaticEnemyOneShot")) return new StaticEnemyOneShot(offsetX+posX, offsetY+posY, Float.parseFloat(string[4]));
		if (string[3].equals("ChasingBlockEnemy")) return new ChasingBlockEnemy(offsetX+posX, offsetY+posY);
		if (string[3].equals("BounceEnemy")) return new BounceEnemy(offsetX+posX, offsetY+posY);
		
		return null;
	}
}
