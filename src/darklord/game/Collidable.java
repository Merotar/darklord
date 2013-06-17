package darklord.game;


import java.io.Serializable;
import darklord.math.Vector2f;

/**
 * basic class for objects which can collide with each other
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
abstract class Collidable implements Serializable
{
	private float posX, posY, sizeX, sizeY;
	
//	public abstract boolean collide(Collidable obj);

	public Vector2f getPos()
	{
		return new Vector2f(posX, posY);
	}
	
	public void setPos(Vector2f pos)
	{
		posX = pos.getX();
		posY = pos.getY();
	}
	
	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public float getSizeX() {
		return sizeX;
	}

	public void setSize(float s)
	{
		setSizeX(s);
		setSizeY(s);
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
	
	public Vector2f getCenter()
	{
		return new Vector2f(getPosX()+getSizeX()/2.f, getPosY()+getSizeY()/2.f);
	}
	
//	public boolean collideWithBlock(int x, int y)
//	{
//		// TODO: implementieren
//		return false;
//	}
	
	public static boolean collide(float x, float y, float halfSizeX, float halfSizeY, float a, float b, float halfSizeA, float halfSizeB)
	{
//		return !(x > a+halfSizeA || x+halfSizeX < a || y > b + halfSizeB || y + halfSizeY < b);
		return ((Math.abs(x-a) < halfSizeX+halfSizeA) && (Math.abs(y-b) < halfSizeY+halfSizeB)); 
	}
	
	/**
	 * 
	 * @param obj collidable to collide with
	 * @return return true if objects collide
	 */
	public boolean collide(Collidable obj)
	{
		return collide(getPosX()+getSizeX()/2.f, getPosY()+getSizeY()/2.f, getSizeX()/2.f, getSizeY()/2.f, obj.getPosX()+obj.getSizeX()/2.f, obj.getPosY()+obj.getSizeY()/2.f, obj.getSizeX()/2.f, obj.getSizeY()/2.f);
	}
	
	public boolean collide(Vector2f collVec)
	{
		if (collVec.getX() < posX) return false;
		if (collVec.getX() >= posX+sizeX) return false;
		if (collVec.getY() < posY) return false;
		if (collVec.getY() >= posY+sizeY) return false;
		return true;
	}
}