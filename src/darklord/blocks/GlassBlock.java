package darklord.blocks;

import darklord.media.Sprite;

public class GlassBlock extends Block
{
	public GlassBlock()
	{
		super();
		setSolid(true);
		setDestroyable(true);
		setTransparent(true);
//		((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
		((Sprite)appearance).setTextureRegion(6*128, 1*128, 128, 128);
		setMaxHp(2);
	}
	
	public GlassBlock createNew()
	{
		return new GlassBlock();
	}
}
