package darklord.media;


import java.io.FileInputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import darklord.game.Print;

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
			texture = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream(fileName));
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
		draw(region, 0.f, 0.f, 1.f, 1.f);
	}
	
	public void draw(TextureRegion region, double posX, double posY, double sizeX, double sizeY, double a)
	{
		draw(region, posX, posY, sizeX, sizeY, 1.f, 1.f, 1.f, a);
	}
	
	public void draw(TextureRegion region, double posX, double posY, double sizeX, double sizeY)
	{
		draw(region, posX, posY, sizeX, sizeY, 1.f, 1.f, 1.f, 1.f);
	}

	public void draw(TextureRegion region, double posX, double posY, double sizeX, double sizeY, double r, double g, double b, double intensity)
	{	
		if (region != null)
		{
			double x = 1.*region.getX()/width;
			double y = 1.*region.getY()/height;
			double dx = 1.*region.getWidth()/width;
			double dy = 1.*region.getHeight()/height;
			
			// FIXME: workaround
//			GL11.glColor4f(red, green, blue, intensity);
//			GL11.glBegin(GL11.GL_QUADS);
//			GL11.glTexCoord2f(x, y+dy);
//			GL11.glVertex2f(posX, posY+sizeY);
//			GL11.glTexCoord2f(x+dx, y+dy);
//			GL11.glVertex2f(posX+sizeX, posY+sizeY);
//			GL11.glTexCoord2f(x+dx, y);
//			GL11.glVertex2f(posX+sizeX, posY);
//			GL11.glTexCoord2f(x, y);
//			GL11.glVertex2f(posX, posY);
//			GL11.glEnd();
			
			GL11.glColor4d(r, g, b, intensity);
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glTexCoord2d(x, y+dy);
			GL11.glVertex2d(posX, posY);
			
			GL11.glTexCoord2d(x+dx, y+dy);
			GL11.glVertex2d(posX+sizeX, posY);
			
			GL11.glTexCoord2d(x+dx, y);
			GL11.glVertex2d(posX+sizeX, posY+sizeY);

			GL11.glTexCoord2d(x, y);
			GL11.glVertex2d(posX, posY+sizeY);

			GL11.glEnd();
			
			// TODO: uncomment
//			GL11.glBegin(GL11.GL_QUADS);
//			GL11.glTexCoord2f(x, y+dy); // bottom left
//			GL11.glVertex2f(0.f, 0.f);
//			GL11.glTexCoord2f(x+dx, y+dy); // bottom right
//			GL11.glVertex2f(1.f, 0.f);
//			GL11.glTexCoord2f(x+dx, y); // top right
//			GL11.glVertex2f(1.f, 1.f);
//			GL11.glTexCoord2f(x, y); // top left
//			GL11.glVertex2f(0.f, 1.f);
//			GL11.glEnd();
		}
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