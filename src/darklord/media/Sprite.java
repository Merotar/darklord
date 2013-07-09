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
	
	public void setTextureRegion(int theX, int theY, int theWidth, int theHeight)
	{
		// TODO: workaround
		textureRegion.setX(theX+1);
		textureRegion.setY(theY+1);
		textureRegion.setWidth(theWidth-2);
		textureRegion.setHeight(theHeight-2);
	}

	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	
	public void draw()
	{
		draw(0.f, 0.f, 1.f, 1.f);
	}
	
	public void draw(float alpha)
	{
		Darklord.textures.draw(getTextureRegion(), 0.f, 0.f, 1.f, 1.f, alpha);
	}
	
	public void drawColor(float r, float g, float b, float a)
	{
		Darklord.textures.draw(getTextureRegion(), 0.f, 0.f, 1.f, 1.f, r, g, b, a);
	}
	
	public void draw(float posX, float posY, float sizeX, float sizeY)
	{
		Darklord.textures.draw(getTextureRegion(), posX, posY, sizeX, sizeY);
	}
}