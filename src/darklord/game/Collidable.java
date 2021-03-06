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
public abstract class Collidable implements Serializable
{
	protected float posX;
	protected float posY;
	protected float sizeX;
	protected float sizeY;
	protected float angle = 0.f;
	private boolean solid = true;
	
//	public abstract boolean collide(Collidable obj);
	
	public Vector2f getPosition()
	{
		return new Vector2f(posX, posY);
	}
	
	public void setPosition(Vector2f pos)
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
	
	public Vector2f getSizeVec()
	{
		return new Vector2f(sizeX, sizeY);
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
	
	public void setCenter(Vector2f center)
	{
		posX = center.getX() - sizeX;
		posY = center.getY() - sizeY;
	}
	
	public Vector2f getCenter()
	{
		return new Vector2f(getPosX()+getSizeX()/2.f, getPosY()+getSizeY()/2.f);
	}
	
	/**
	 * angle in degree
	 * @param rotAngle
	 */
	public void rotate(float rotAngle)
	{
		angle += rotAngle;
		while (angle > 360) angle -=360;
	}
	
	public Vector2f getLocalPosition(LevelStructure level)
	{
		float x = (posX) % level.getGridSizeX();
		float y = (posY) % level.getGridSizeY();
		
		if (x < 0) x += level.getGridSizeX();
		if (y < 0) y += level.getGridSizeY();
		
		return new Vector2f(x, y);
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
	
	/**
	 * not yet tested!
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean collideWithBlock(int x, int y)
	{
		boolean collision = false;

		collision = collide(getPosX()+getSizeX()/2.f, getPosY()+getSizeY()/2.f, getSizeX()/2.f, getSizeY()/2.f, x+0.5f, y+0.5f, 0.5f, 0.5f);

		return collision;
	}
	
	public Vector2f collideOverlapWithBlock(int x, int y)
	{
		return collideOverlap(getPosX(), getPosY(), getSizeX(), getSizeY(), x, y, 1.f, 1.f);
	}
	
	public static Vector2f collideOverlap(float x1, float y1, float sX1, float sY1, float x2, float y2, float sX2, float sY2)
	{
		Vector2f overlap = new Vector2f(0.f, 0.f);
		
		float overlapX = Math.min(x1+sX1, x2+sX2) - Math.max(x1, x2);
		float overlapY = Math.min(y1+sY1, y2+sY2) - Math.max(y1, y2);
		
		// if the two objects overlap
		if (overlapX > 0 && overlapY > 0)
		{
			if (x1+sX1/2.f > x2+sX2/2.f)
			{
				if (overlapX > 0) overlap.setX(-overlapX);
			} else
			{
				if (overlapX > 0) overlap.setX(overlapX);
			}
			
			if (y1+sY1/2.f > y2+sY2/2.f)
			{
				if (overlapY > 0) overlap.setY(-overlapY);
			} else
			{
				if (overlapY > 0) overlap.setY(overlapY);
			}
		}
		
		return overlap;		
	}
	
	/**
	 * 
	 * @param obj
	 * @return vector which describes overlap in the directions
	 */
	public Vector2f collideOverlap(Collidable obj)
	{
		return collideOverlap(getPosX(), getPosY(), getSizeX(), getSizeY(), obj.getPosX(), obj.getPosY(), obj.getSizeX(), obj.getSizeY());
	}
	
	public boolean collideWithRotation(Collidable obj)
	{
		return (collideWithRotationOneDirection(this, obj) && collideWithRotationOneDirection(obj, this));
	}
	
	public boolean collideWithRotationOneDirection(Collidable obj1, Collidable obj2)
	{
		Vector2f rotSizeHalfX = new Vector2f(obj2.getSizeX()/2.f, 0.f);
		rotSizeHalfX.rotate(obj2.getAngle());
		Vector2f rotSizeHalfY = new Vector2f(0.f, obj2.getSizeY()/2.f);
		rotSizeHalfY.rotate(obj2.getAngle());
		
		Vector2f dir = new Vector2f(obj1.getSizeX(), 0.f);
		dir.rotate(obj1.getAngle());
		dir = dir.mul(1.f/dir.squaredLength());
		Vector2f dirOrtho = new Vector2f(0.f, obj1.getSizeY());
		dirOrtho.rotate(obj1.getAngle());
		dirOrtho = dirOrtho.mul(1.f/dirOrtho.squaredLength());
		
		Vector2f rotatedOrigin = new Vector2f(obj1.getSizeVec().mul(0.5f));
		rotatedOrigin.rotate(obj1.getAngle());
		rotatedOrigin = obj1.getCenter().sub(rotatedOrigin);
		
		float originX = rotatedOrigin.scalar(dir);
		float originY = rotatedOrigin.scalar(dirOrtho);
		
		Vector2f point1 = obj2.getCenter().sub(rotSizeHalfX);
		point1 = point1.sub(rotSizeHalfY);
		
		Vector2f point2 = obj2.getCenter().add(rotSizeHalfX);
		point2 = point2.sub(rotSizeHalfY);
		
		Vector2f point3 = obj2.getCenter().add(rotSizeHalfX);
		point3 = point3.add(rotSizeHalfY);
		
		Vector2f point4 = obj2.getCenter().sub(rotSizeHalfX);
		point4 = point4.add(rotSizeHalfY);
		
		float minX = point1.scalar(dir);
		float maxX = point1.scalar(dir);
		
		float tmp;
		
		tmp = point2.scalar(dir);
		minX = Math.min(minX, tmp);
		maxX = Math.max(maxX, tmp);
		
		tmp = point3.scalar(dir);
		minX = Math.min(minX, tmp);
		maxX = Math.max(maxX, tmp);
		
		tmp = point4.scalar(dir);
		minX = Math.min(minX, tmp);
		maxX = Math.max(maxX, tmp);
		
		if (minX > 1 + originX || maxX < originX) return false;
		
		float minY = point1.scalar(dirOrtho);
		float maxY = point1.scalar(dirOrtho);
		
		tmp = point2.scalar(dirOrtho);
		minY = Math.min(minY, tmp);
		maxY = Math.max(maxY, tmp);
		
		tmp = point3.scalar(dirOrtho);
		minY = Math.min(minY, tmp);
		maxY = Math.max(maxY, tmp);
		
		tmp = point4.scalar(dirOrtho);
		minY = Math.min(minY, tmp);
		maxY = Math.max(maxY, tmp);
		
		if (minY > 1 + originY || maxY < originY) return false;
		
		return true;
	}
	
	public boolean collide(Vector2f collVec)
	{
		if (collVec.getX() < posX) return false;
		if (collVec.getX() >= posX+sizeX) return false;
		if (collVec.getY() < posY) return false;
		if (collVec.getY() >= posY+sizeY) return false;
		return true;
	}
	
	public boolean contains(Vector2f thePosition, Vector2f theSize)
	{
		if (thePosition.getX() < posX) return false;
		if (thePosition.getX()+theSize.getX() > posX+sizeX) return false;
		if (thePosition.getY() < posY) return false;
		if (thePosition.getY()+theSize.getY() > posY+sizeY) return false;
		
		return true;
	}
	
	public boolean collide(Vector2f thePosition, Vector2f theSize)
	{
		Vector2f tmpSizeHalf = theSize.mul(0.5f);
		Vector2f tmpCenter = (tmpSizeHalf).add(thePosition);
		
		return collide(getPosX()+getSizeX()/2.f, getPosY()+getSizeY()/2.f, getSizeX()/2.f, getSizeY()/2.f, tmpCenter.getX(), tmpCenter.getY(), tmpSizeHalf.getX(), tmpSizeHalf.getY());

	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
}