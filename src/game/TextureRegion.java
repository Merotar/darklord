package game;
/**
 * description of a region in a texture
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 14-05-2013
 * 
 */

public class TextureRegion
{
	private float x, y, width, height;
	
	public TextureRegion()
	{
		this(0.f, 0.f, 1.f, 1.f);
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
	
	public TextureRegion(float theX, float theY, float theWidth, float theHeight)
	{
		setX(theX);
		setY(theY);
		setWidth(theWidth);
		setHeight(theHeight);
	}
	
	public void set(float theX, float theY, float theWidth, float theHeight)
	{
		setX(theX);
		setY(theY);
		setWidth(theWidth);
		setHeight(theWidth);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}