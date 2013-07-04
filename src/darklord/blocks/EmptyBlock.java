package darklord.blocks;

import darklord.media.Sprite;

public class EmptyBlock extends Block
{
	public EmptyBlock()
	{
		super();
		setSolid(false);
		setDestroyable(false);
		setTransparent(false);
		background = new Sprite();
		((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
		((Sprite)appearance).setTextureRegion(7*128, 7*128, 128, 128);
		setMaxHp(2);
	}
	
	public EmptyBlock createNew()
	{
		return new EmptyBlock();
	}
}
