package darklord.media;

import darklord.game.Darklord;


/**
 * Describes sprites
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 14-05-2013
 * 
 */

public class Sprite implements Drawable
{
	TextureRegion textureRegion;
	
	public Sprite()
	{
		super();
		textureRegion = new TextureRegion();
	}

	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	
	public void setTextureRegion(float theX, float theY, float theWidth, float theHeight)
	{
		textureRegion.setX(theX);
		textureRegion.setY(theY);
		textureRegion.setWidth(theWidth);
		textureRegion.setHeight(theWidth);
	}

	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	
	public void draw()
	{
		draw(0.f, 0.f, 1.f, 1.f);
	}
	
	public void draw(float posX, float posY, float sizeX, float sizeY)
	{
		Darklord.sprites01.draw(getTextureRegion(), posX, posY, sizeX, sizeY);
	}
}