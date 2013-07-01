package darklord.rules;

import darklord.game.GameEngine;
import darklord.game.Player;
import darklord.game.Room;

public class AddCrystalsReaction implements Reaction
{
	private int amount;
	private int color;
	
	public AddCrystalsReaction(int theColor, int theAmount)
	{
		amount = theAmount;
		color = theColor;
	}
	
	public void apply(GameEngine engine, Player thePlayer)
	{
		switch (color)
		{
		case 0:
			thePlayer.addCrystalsRed(amount);
			break;
		case 1:
			thePlayer.addCrystalsBlue(amount);
			break;
		case 2:
			thePlayer.addCrystalsGreen(amount);
			break;
		case 3:
			thePlayer.addCrystalsYellow(amount);
			break;
		default:
		}
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
