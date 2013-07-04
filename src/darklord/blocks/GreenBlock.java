package darklord.blocks;

import darklord.collectables.CrystalGreenCollectable;
import darklord.collectables.CrystalRedCollectable;
import darklord.media.Sprite;

public class GreenBlock extends Block
{
	public GreenBlock()
	{
		super();
		setSolid(true);
		setDestroyable(true);
		setTransparent(false);
//		((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
		((Sprite)appearance).setTextureRegion(5*128, 0*128, 128, 128);
		setMaxHp(2);
		dropCollectable = new CrystalGreenCollectable();
	}
	
	public GreenBlock createNew()
	{
		return new GreenBlock();
	}
}
