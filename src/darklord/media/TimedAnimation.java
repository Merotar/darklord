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

public class TimedAnimation implements Drawable
{
	private Vector<TimedTextureRegion> timedTextureRegions;
	private int currentTextureRegionNumber;
	private TextureRegion currentTextureRegion;
	private float sizeX, sizeY;
	private boolean repeat;
	boolean finished;
	
	public TimedAnimation()
	{
		timedTextureRegions = new Vector<TimedTextureRegion>();
		currentTextureRegionNumber = -1;
		currentTextureRegion = null;
		
		repeat = false;
		finished = false;
		setSizeX(1.f);
		setSizeY(1.f);
	}
	
	public void addTimedTextureRegion(TimedTextureRegion region)
	{
		timedTextureRegions.add(region);
		if (timedTextureRegions.size() == 1)
		{
			currentTextureRegionNumber = 0;
			currentTextureRegion = timedTextureRegions.firstElement().getTextureRegion();
		}
	}
	
	public TextureRegion getCurrentTextureRegion()
	{
		return currentTextureRegion;
	}
	
	public void draw()
	{
		Darklord.textures.draw(currentTextureRegion);
	}
	
	public void drawColor(float r, float g, float b, float a)
	{
		Darklord.textures.draw(currentTextureRegion, 0.f, 0.f, 1.f, 1.f, r, g, b, a);
	}
	
	public void draw(float posX, float posY, float sizeX, float sizeY)
	{
		Darklord.textures.draw(currentTextureRegion, posX, posY, sizeX, sizeY);
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
	
	public void reset()
	{
		for (TimedTextureRegion theRegion : timedTextureRegions)
		{
			theRegion.getTimer().setCurrent(0.f);
		}
		currentTextureRegionNumber = 0;
		currentTextureRegion = timedTextureRegions.firstElement().getTextureRegion();
		
		finished = false;
	}
	
	public void update(float dt)
	{
		if(!timedTextureRegions.get(currentTextureRegionNumber).getTimer().increase(dt))
		{
			if (currentTextureRegionNumber+1 < timedTextureRegions.size())
			{
				currentTextureRegionNumber++;
				currentTextureRegion = timedTextureRegions.get(currentTextureRegionNumber).getTextureRegion();
			} else
			{
				finished = true;
				
				if (repeat)
				{
					currentTextureRegionNumber = 0;
					currentTextureRegion = timedTextureRegions.get(currentTextureRegionNumber).getTextureRegion();
				}
			}
		}
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}