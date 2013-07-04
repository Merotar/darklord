package darklord.collectables;

import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.media.Sprite;

public class CrystalGreenCollectable extends Collectable
{
	public CrystalGreenCollectable()
	{
		this(0.f, 0.f);
	}
	
	public CrystalGreenCollectable(float x, float y)
	{
		super(x, y);
		((Sprite)appearance).setTextureRegion(3*128, 3*128, 128, 128);
	}

	public void onCollect(GameEngine engine)
	{
		Darklord.sounds.pling.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
		engine.mainPlayer.addCrystalsGreen(1);
	}

	public CrystalGreenCollectable createNew()
	{
		return new CrystalGreenCollectable();
	}
}
