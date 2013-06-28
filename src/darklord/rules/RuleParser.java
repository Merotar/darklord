package darklord.rules;

import darklord.game.BlockType;

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
	public static String setBlockType = "SetBlockType";
	
	public static Condition parseCondition(String[] words)
	{
		Condition tmpCondition = null;
		
		if (words[1].equals(CollideBlock))
		{
			float x = (float) Math.floor(Integer.parseInt(words[2]));
			float y = (float) Math.floor(Integer.parseInt(words[3]));
			tmpCondition = new PlayerAtBlockCondition(x, y);
		}
		
		return tmpCondition;
	}
	
	public static Reaction parseReaction(String[] words)
	{
		Reaction tmpReaction = null;
		
		if (words[1].equals(Player))
		{
			if (words[2].equals(Add))
			{
				if (words[3].equals(CrystalsRed))
				{
					tmpReaction = new AddCrystalsRedReaction(Integer.parseInt(words[4]));
				}
			}
		}
		
		if (words[1].equals(setBlockType))
		{
			int tmpType = Integer.parseInt(words[4]);
			tmpReaction = new SetBlockTypeReaction(Integer.parseInt(words[2]),Integer.parseInt(words[3]), BlockType.values()[tmpType]);
		}
		
		return tmpReaction;
	}
}
