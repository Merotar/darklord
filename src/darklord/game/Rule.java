package darklord.game;

import java.io.Serializable;
import java.util.Vector;

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
	
	public boolean checkConditions(Grid theGrid, Player thePlayer)
	{
		for (Condition tmpCondition : conditions)
		{
			if (tmpCondition.isTrue(theGrid, thePlayer)) return true;
		}
		return false;
	}
	
	public void apply(Grid theGrid, Player thePlayer)
	{
		if (active)
		{
			if (checkConditions(theGrid, thePlayer))
			{
				for (Reaction tmpReaction : reactions)
				{
					tmpReaction.apply(theGrid, thePlayer);
				}
			}
		}
		if (oneTimeRule) active = false;
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
