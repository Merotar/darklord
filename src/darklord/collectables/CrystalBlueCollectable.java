package darklord.collectables;

import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.media.Sprite;

public class CrystalBlueCollectable extends Collectable
{
	public CrystalBlueCollectable()
	{
		this(0.f, 0.f);
	}
	
	public CrystalBlueCollectable(float x, float y)
	{
		super(x, y);
		((Sprite)appearance).setTextureRegion(5*128, 3*128, 128, 128);
	}
	
	public void onCollect(GameEngine engine)
	{
		Darklord.sounds.pling.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
		engine.mainPlayer.addCrystalsBlue(1);
	}

	public CrystalBlueCollectable createNew()
	{
		return new CrystalBlueCollectable();
	}

}
