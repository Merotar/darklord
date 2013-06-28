package darklord.rules;

import darklord.game.GameEngine;
import darklord.game.Player;
import darklord.game.Room;

public class AddCrystalsRedReaction implements Reaction
{
	private int amount;
	
	public AddCrystalsRedReaction(int theAmount)
	{
		amount = theAmount;
	}
	
	public void apply(GameEngine engine, Player thePlayer)
	{
		thePlayer.addCrystalsRed(amount);
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
