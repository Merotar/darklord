package darklord.media;


import java.io.Serializable;

/**
 * description of a region in a texture
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 14-05-2013
 * 
 */

public class TextureRegion implements Serializable
{
	private int x, y, width, height;
	
	public TextureRegion()
	{
		this(0, 0, 1, 1);
	}
	
	/**
	 * 
	 * @param theX x position in grid
	 * @param theY y position in grid
	 * @param size size of a tile
	 */
	public TextureRegion(int theX, int theY, int size)
	{
		setX(theX*size);
		setY(theY*size);
		setWidth(size);
		setHeight(size);
	}
	
	public TextureRegion(int theX, int theY, int theWidth, int theHeight)
	{
		setX(theX);
		setY(theY);
		setWidth(theWidth);
		setHeight(theHeight);
	}
	
	public TextureRegion(TextureRegion t)
	{
		this(t.getX(), t.getY(), t.getWidth(), t.getHeight());
	}
	
	public void set(int theX, int theY, int theWidth, int theHeight)
	{
		setX(theX);
		setY(theY);
		setWidth(theWidth);
		setHeight(theWidth);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}