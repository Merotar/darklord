package darklord.collectables;

import darklord.game.GameEngine;

public class BlockBrownCollectable extends Collectable
{

	public void onCollect(GameEngine engine)
	{

	}

	public Collectable createNew()
	{
		return new BlockBrownCollectable();
	}
}
