package darklord.blocks;

import darklord.collectables.CrystalRedCollectable;
import darklord.media.Sprite;

public class RedBlock extends Block
{
	public RedBlock()
	{
		super();
		setSolid(true);
		setDestroyable(true);
		setTransparent(false);
//		((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
		((Sprite)appearance).setTextureRegion(3*128, 0*128, 128, 128);
		setMaxHp(2);
		dropCollectable = new CrystalRedCollectable();
	}
	
	public RedBlock createNew()
	{
		return new RedBlock();
	}
}
