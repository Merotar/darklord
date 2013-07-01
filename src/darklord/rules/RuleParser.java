package darklord.rules;

import darklord.game.BlockType;
import darklord.game.Darklord;
import darklord.media.SoundLoader;

public class RuleParser
{
	public static String newRule = "#StartRule";
	public static String endRule = "#EndRule";
	
	public static String Reaction = "Reaction";
	public static String Condition = "Condition";
	public static String CollideBlock = "CollideBlock";
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
	
	public static Condition parseCondition(String[] words)
	{
		Condition tmpCondition = null;
		
		// collide with block
		if (words[1].equals(CollideBlock))
		{
			float x = (float) Math.floor(Integer.parseInt(words[2]));
			float y = (float) Math.floor(Integer.parseInt(words[3]));
			tmpCondition = new PlayerAtBlockCondition(x, y);
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
}
