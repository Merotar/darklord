package darklord.blocks;

import darklord.collectables.CrystalRedCollectable;
import darklord.collectables.CrystalYellowCollectable;
import darklord.media.Sprite;

public class YellowBlock extends Block
{
	public YellowBlock()
	{
		super();
		setSolid(true);
		setDestroyable(true);
		setTransparent(false);
//		((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
		((Sprite)appearance).setTextureRegion(0*128, 1*128, 128, 128);
		setMaxHp(2);
		dropCollectable = new CrystalYellowCollectable();
	}
	
	public YellowBlock createNew()
	{
		return new YellowBlock();
	}
}
