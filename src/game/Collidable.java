/*
 * basic class for objects which can collide with each other
 * 
 * @author Sebastian Artz
 * 
 */

package game;
abstract class Collidable
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
	
	public boolean collide(Collidable obj)
	{
		return collide(getPosX()+getSizeX()/2.f, getPosY()+getSizeY()/2.f, getSizeX()/2.f, getSizeY()/2.f, obj.getPosX()+obj.getSizeX()/2.f, obj.getPosY()+obj.getSizeY()/2.f, obj.getSizeX()/2.f, obj.getSizeY()/2.f);
	}
}