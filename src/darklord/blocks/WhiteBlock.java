package darklord.blocks;

import darklord.media.Sprite;

public class WhiteBlock extends Block
{
	public WhiteBlock()
	{
		super();
		setSolid(true);
		setDestroyable(true);
		setTransparent(false);
//		((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
		((Sprite)appearance).setTextureRegion(6*128, 0*128, 128, 128);
		setMaxHp(2);
	}
	
	public WhiteBlock createNew()
	{
		return new WhiteBlock();
	}
}
