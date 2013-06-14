package darklord.ui;

import darklord.game.Print;
import darklord.media.TextureRegion;
import darklord.math.Vector2f;

/**
 * description of a button
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 16-05-2013
 * 
 */

public class Button
{
	private Vector2f position, size;
	private TextureRegion textureButtonUp, textureButtonDown;
	String name;
	boolean down;
	
	public Button(TextureRegion regionUp, TextureRegion regionDown, String theName)
	{
		this();
		setTextureRegion(regionUp, regionDown);
		setName(theName);
	}
	
	public Button(TextureRegion region, String theName)
	{
		this(region, region, theName);
		setName(theName);
	}
	
	public Button()
	{
		down = false;
	}

	/**
	 * 
	 * @param thePosition position in the menu coordinate system
	 * @return true if button is hit
	 */
	public boolean isInside(Vector2f thePosition)
	{
		boolean inside = true;
		
		if (thePosition.getX()>getPosition().getX()+getSize().getX()) inside = false;
		if (thePosition.getX()<getPosition().getX()) inside = false;
		if (thePosition.getY()>getPosition().getY()+getSize().getY()) inside = false;
		if (thePosition.getY()<getPosition().getY()) inside = false;
//		if (inside) Print.outln("inside of button "+getName());
		
		return inside;
	}
	
	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getSize() {
		return size;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}

	public TextureRegion getTextureRegion() {
		if (down) return textureButtonDown;
		return textureButtonUp;
	}

	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureButtonUp = textureRegion;
		this.textureButtonDown = textureRegion;
	}
	
	public void setTextureRegion(TextureRegion up, TextureRegion down) {
		this.textureButtonUp = up;
		this.textureButtonDown = down;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void print()
	{
		Print.outln(getName());
	}
}