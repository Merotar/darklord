
/**
 * describes a (partially filled) bar
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
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
