package game;

import java.io.FileInputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * handles a sprite sheet
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 14-05-2013
 * 
 * TODO: add position and size to draw() and call GL11.glBegin() only once in begin()
 * 
 */

public class SpriteSheet
{
	private Texture texture;
	
	int width, height;
	
	public SpriteSheet()
	{
		
	}
	
	public SpriteSheet(String fileName)
	{
		try
		{
			texture = TextureLoader.getTexture("PNG", new FileInputStream(fileName));
			width = texture.getImageWidth();
			height = texture.getImageHeight();
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void begin()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);  
		Color.white.bind();
		texture.bind();
	}
	
	public void end()
	{

		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public void draw(TextureRegion region)
	{
		float x = 1.f*region.getX()/width;
		float y = 1.f*region.getY()/height;
		float dx = 1.f*region.getWidth()/width;
		float dy = 1.f*region.getHeight()/height;		
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(x, y+dy); // bottom left
		GL11.glVertex2f(0.f, 0.f);
		GL11.glTexCoord2f(x+dx, y+dy); // bottom right
		GL11.glVertex2f(1.f, 0.f);
		GL11.glTexCoord2f(x+dx, y); // top right
		GL11.glVertex2f(1.f, 1.f);
		GL11.glTexCoord2f(x, y); // top left
		GL11.glVertex2f(0.f, 1.f);
		GL11.glEnd();
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