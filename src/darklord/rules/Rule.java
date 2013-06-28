package darklord.rules;

import java.io.Serializable;
import java.util.Vector;

import darklord.game.GameEngine;
import darklord.game.Player;
import darklord.game.Print;
import darklord.game.Room;


public class Rule implements Serializable
{
	boolean active, oneTimeRule;
	Vector<Condition> conditions;
	Vector<Reaction> reactions;
	
	public Rule()
	{
		active = true;
		oneTimeRule = true;
		conditions = new Vector<Condition>();
		reactions = new Vector<Reaction>();
	}
	
	public void addCondition(Condition theCondition)
	{
		conditions.add(theCondition);
	}
	
	public void addReaction(Reaction theReaction)
	{
		reactions.add(theReaction);
	}
	
	private boolean checkConditions(GameEngine engine, Player thePlayer)
	{
		for (Condition tmpCondition : conditions)
		{
			if (tmpCondition.isTrue(engine, thePlayer)) return true;
		}
		return false;
	}
	
	public void apply(GameEngine engine, Player thePlayer)
	{
		if (active)
		{
			if (checkConditions(engine, thePlayer))
			{
				for (Reaction tmpReaction : reactions)
				{
					tmpReaction.apply(engine, thePlayer);
				}
				if (oneTimeRule) active = false;
			}
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isOneTimeRule() {
		return oneTimeRule;
	}

	public void setOneTimeRule(boolean oneTimeRule) {
		this.oneTimeRule = oneTimeRule;
	}
}
