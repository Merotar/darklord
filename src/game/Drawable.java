package game;

import java.util.Vector;

/**
 * Describes animations
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 14-05-2013
 * 
 */

public interface Drawable
{
	
	public void draw();
	public void draw(float posX, float posY, float sizeX, float sizeY);
}