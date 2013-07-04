package darklord.blocks;

import darklord.media.Sprite;

public class DirtBlock extends Block
{
	public DirtBlock()
	{
		super();
		setSolid(true);
		setDestroyable(true);
		setTransparent(false);
//		((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
		((Sprite)appearance).setTextureRegion(2*128, 0*128, 128, 128);
		setMaxHp(2);
	}
	
	public DirtBlock createNew()
	{
		return new DirtBlock();
	}
}
