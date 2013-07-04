package darklord.collectables;

import darklord.game.GameEngine;
import darklord.media.Sprite;

public class OrbGreenCollectable extends Collectable
{
	public OrbGreenCollectable()
	{
		this(0.f, 0.f);
	}
	
	public OrbGreenCollectable(float x, float y)
	{
		super(x, y);
		((Sprite)appearance).setTextureRegion(2*128, 4*128, 128, 128);
	}

	public void onCollect(GameEngine engine)
	{
		engine.mainPlayer.addEnergyGreen(1.f);
	}

	public OrbGreenCollectable createNew()
	{
		return new OrbGreenCollectable();
	}
}
