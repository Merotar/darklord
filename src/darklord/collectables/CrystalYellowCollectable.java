package darklord.collectables;

import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.media.SoundLoader;
import darklord.media.Sprite;

public class CrystalYellowCollectable extends Collectable
{
	public CrystalYellowCollectable()
	{
		this(0.f, 0.f);
	}
	
	public CrystalYellowCollectable(float x, float y)
	{
		super(x, y);
		((Sprite)appearance).setTextureRegion(2*128, 3*128, 128, 128);
	}

	public void onCollect(GameEngine engine)
	{
		Darklord.sounds.pling.playAsSoundEffect(1.f, SoundLoader.volumeEffects, false);
		engine.mainPlayer.addCrystalsYellow(1);
	}

	public CrystalYellowCollectable createNew()
	{
		return new CrystalYellowCollectable();
	}
}
