

/**
 * tool to check collision
 * 
 * @deprecated use class Collidable
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Collider
{
	Collider(){};
	
	public static boolean collide(float x, float y, float halfSizeX, float halfSizeY, float a, float b, float halfSizeA, float halfSizeB)
	{
//		return !(x > a+halfSizeA || x+halfSizeX < a || y > b + halfSizeB || y + halfSizeY < b);
		return ((Math.abs(x-a) < halfSizeX+halfSizeA) && (Math.abs(y-b) < halfSizeY+halfSizeB)); 
	}
	
	public static boolean collideBorders(float x1, float x2, float y1, float y2, float a1, float a2, float b1, float b2)
	{
		float sizeX = Math.abs(x1-x2);
		float sizeY = Math.abs(y1-y2);
		float sizeA = Math.abs(a1-a2);
		float sizeB = Math.abs(b1-b2);
		return collide(x1+sizeX/2.f,y1+sizeY/2.f, sizeX/2.f, sizeY/2.f, a1+sizeA/2.f,b1+sizeB/2.f, sizeA/2.f, sizeB/2.f);
	}
}