package darklord.collectables;

import darklord.game.GameEngine;
import darklord.game.Print;
import darklord.media.Sprite;

public class UncoverMapCollectable extends Collectable
{
	public UncoverMapCollectable()
	{
		this(0.f, 0.f);
	}
	
	public UncoverMapCollectable(float x, float y)
	{
		super(x, y);
		((Sprite)appearance).setTextureRegion(7*128, 4*128, 128, 128);
	}
	
	public void onCollect(GameEngine engine)
	{
		for (int i=0;i<engine.map.levelStructure.getGridSizeX();i++)
		{
			for (int j=0;j<engine.map.levelStructure.getGridSizeY();j++)
			{
				engine.map.levelStructure.getActiveRoom().setFogAt(i, j, 0.f);
			}
		}
	}
	
	public Collectable createNew()
	{
		return new UncoverMapCollectable();
	}
}
