package darklord.media;


import java.util.Vector;

import org.newdawn.slick.tests.TestUtils;

import darklord.game.Darklord;
import darklord.game.Timer;

/**
 * Describes animations
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 14-05-2013
 * 
 */

public class Animation implements Drawable
{
	private Vector<TextureRegion> textureRegion;
	private Timer animationTimer;
	private float animationInterval;
	private float sizeX, sizeY;
	
	public Animation()
	{
		textureRegion = new Vector<TextureRegion>();
		animationTimer = new Timer();
		animationTimer.start();
		animationInterval = 100.f;
		setSizeX(1.f);
		setSizeY(1.f);
	}
	
	public void addTextureRegion(TextureRegion region)
	{
		textureRegion.add(region);
	}
	
	public void draw()
	{

		int textureNum = (int)Math.floor(animationTimer.getTimeDelta() / animationInterval);
//		System.out.println("dt: "+(animationTimer.getTimeDelta()));
		if (textureNum >= textureRegion.size())
		{
//			System.out.println("Reset timer!");
			textureNum = 0;
			animationTimer.reset();
		}
		
		// TODO: implement size and position in draw
		Darklord.sprites01.draw(textureRegion.get(textureNum));
	}
	
	public void drawColor(float r, float g, float b, float a)
	{
		int textureNum = (int)Math.floor(animationTimer.getTimeDelta() / animationInterval);
//		System.out.println("dt: "+(animationTimer.getTimeDelta()));
		if (textureNum >= textureRegion.size())
		{
//			System.out.println("Reset timer!");
			textureNum = 0;
			animationTimer.reset();
		}
		
		// TODO: implement size and position in draw
		Darklord.sprites01.draw(textureRegion.get(textureNum), 0.f, 0.f, 1.f, 1.f, r, g, b, a);
	}
	
	public void draw(float posX, float posY, float sizeX, float sizeY)
	{
		int textureNum = (int)Math.floor(animationTimer.getTimeDelta() / animationInterval);
//		System.out.println("dt: "+(animationTimer.getTimeDelta()));
		if (textureNum >= textureRegion.size())
		{
//			System.out.println("Reset timer!");
			textureNum = 0;
			animationTimer.reset();
		}
		
		// TODO: implement size and position in draw
		Darklord.sprites01.draw(textureRegion.get(textureNum), posX, posY, sizeX, sizeY);
	}
	

	public float getSizeX() {
		return sizeX;
	}

	public void setSize(float sizeX, float sizeY) {
		setSizeX(sizeX);
		setSizeY(sizeY);
	}
	
	public void setSizeX(float sizeX) {
		this.sizeX = sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public void setSizeY(float sizeY) {
		this.sizeY = sizeY;
	}

	public float getAnimationInterval() {
		return animationInterval;
	}

	public void setAnimationInterval(float animationInterval) {
		this.animationInterval = animationInterval;
	}
}