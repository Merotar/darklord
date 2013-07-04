package darklord.ui;

import darklord.collectables.Collectable;
import darklord.media.TextureRegion;

public class ButtonCollectable extends Button
{
	Collectable collectable;
	
	public ButtonCollectable(TextureRegion region, String theName, Collectable theCollectable)
	{
		super(region, region, theName);
		setName(theName);
		collectable = theCollectable;
	}

	public Collectable getCollectable() {
		return collectable;
	}

	public void setCollectable(Collectable theCollectable) {
		this.collectable = theCollectable;
	}
}
