package darklord.blocks;

import darklord.collectables.CrystalBlueCollectable;
import darklord.collectables.CrystalRedCollectable;
import darklord.media.Sprite;

public class BlueBlock extends Block
{
	public BlueBlock()
	{
		super();
		setSolid(true);
		setDestroyable(true);
		setTransparent(false);
//		((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
		((Sprite)appearance).setTextureRegion(4*128, 0*128, 128, 128);
		setMaxHp(2);
		dropCollectable = new CrystalBlueCollectable();
	}

	public BlueBlock createNew()
	{
		return new BlueBlock();
	}
}
