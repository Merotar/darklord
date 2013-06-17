package darklord.ui;

import darklord.media.TextureRegion;

public class MapButton extends Button
{
	private int type;
	public static final int TYPE_DEFAULT = 0;
	public static final int TYPE_ACTIVE = 1;
	public static final int TYPE_POTENTIAL = 2;
	public static final int TYPE_ACTIVE_POTENTIAL = 3;
	public static final int TYPE_CENTER = 4;

	public MapButton(TextureRegion region, String theName)
	{
		super(region, theName);
		type = TYPE_DEFAULT;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
}
