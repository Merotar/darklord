package game;

public class UIBar extends UIObject
{
	private float scale;
	
	public UIBar(TextureRegion region, String theName)
	{
		super(region, theName);
		setScale(1.f);
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float s) {
		this.scale = s;
	}
}
