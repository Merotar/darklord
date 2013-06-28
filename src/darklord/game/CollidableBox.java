package darklord.game;

import java.util.Collection;

public class CollidableBox extends Collidable
{
	public CollidableBox(float theX, float theY, float theSizeX, float theSizeY)
	{
		posX = theX;
		posY = theY;
		sizeX = theSizeX;
		sizeY = theSizeY;
		angle = 0.f;
	}
}
