package darklord.collectables;

import darklord.game.GameEngine;
import darklord.media.Sprite;

public class OrbYellowCollectable extends Collectable
{
	public OrbYellowCollectable()
	{
		this(0.f, 0.f);
	}
	
	public OrbYellowCollectable(float x, float y)
	{
		super(x, y);
		((Sprite)appearance).setTextureRegion(3*128, 4*128, 128, 128);
	}

	public void onCollect(GameEngine engine)
	{
		engine.mainPlayer.addEnergyYellow(1.f);
	}

	public OrbYellowCollectable createNew()
	{
		return new OrbYellowCollectable();
	}
}
