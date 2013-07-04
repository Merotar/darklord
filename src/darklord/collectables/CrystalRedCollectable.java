package darklord.collectables;

import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.media.Sprite;

public class CrystalRedCollectable extends Collectable
{
	public CrystalRedCollectable()
	{
		this(0.f, 0.f);
	}
	
	public CrystalRedCollectable(float x, float y)
	{
		super(x, y);
		((Sprite)appearance).setTextureRegion(4*128, 3*128, 128, 128);
	}

	public void onCollect(GameEngine engine)
	{
		Darklord.sounds.pling.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
		engine.mainPlayer.addCrystalsRed(1);
	}

	public CrystalRedCollectable createNew()
	{
		return new CrystalRedCollectable();
	}
}
