package darklord.blocks;

import darklord.media.Sprite;

public class StoneBlock extends Block
{
	public StoneBlock()
	{
		super();
		setSolid(true);
		setDestroyable(false);
		setTransparent(false);
//		((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
		((Sprite)appearance).setTextureRegion(1*128, 0*128, 128, 128);
		setMaxHp(2);
	}
	
	public StoneBlock createNew()
	{
		return new StoneBlock();
	}
}
