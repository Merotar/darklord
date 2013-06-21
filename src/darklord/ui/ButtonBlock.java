package darklord.ui;

import darklord.game.Block;
import darklord.media.TextureRegion;

public class ButtonBlock extends Button
{
	Block block;
	
	public ButtonBlock(TextureRegion region, String theName, Block theBlock)
	{
		super(region, region, theName);
		setName(theName);
		block = theBlock;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
}
