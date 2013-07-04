package darklord.collectables;

import darklord.game.GameEngine;
import darklord.media.Sprite;

public class OrbBlueCollectable extends Collectable
{
	public OrbBlueCollectable()
	{
		this(0.f, 0.f);
	}
	
	public OrbBlueCollectable(float x, float y)
	{
		super(x, y);
		((Sprite)appearance).setTextureRegion(1*128, 4*128, 128, 128);
	}
	
	public void onCollect(GameEngine engine)
	{
		engine.mainPlayer.addEnergyBlue(1.f);
	}

	public OrbBlueCollectable createNew()
	{
		return new OrbBlueCollectable();
	}
}
