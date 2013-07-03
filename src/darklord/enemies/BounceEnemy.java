package darklord.enemies;


import java.io.Serializable;
import darklord.game.GameEngine;
import darklord.media.Animation;
import darklord.media.TextureRegion;
import darklord.math.Vector2f;

/**
 * description of an enemy which moves along straight line and bounces at walls
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 03-07-2013
 * 
 */
public class BounceEnemy extends Enemy implements Serializable
{
	Vector2f direction;
	float speed;
//	private Drawable appearance;
	
	public BounceEnemy()
	{
		this(1.f, 1.f);
	}
	
	public BounceEnemy(float x, float y)
	{
		this(x, y, new Vector2f(1.f, 1.f));
	}
	
	public BounceEnemy(float x, float y, Vector2f theDirection)
	{
		super(x, y, 0);
		setXp(3);
		direction = theDirection;
		direction.normalize();
		speed = 4.f;
		setDmgOnContact(5);
		setMaxHp(5);
		
		appearance = new Animation();
		((Animation)appearance).addTextureRegion(new TextureRegion(0, 5, 128));
	}
	
	public void setDirection(Vector2f d)
	{
		direction = d;
	}
	
	public Vector2f getDirection()
	{
		return direction;
	}
	
	public float checkCollisionTop(GameEngine engine)
	{
		Vector2f tmpOverlap = new Vector2f(0.f, 0.f);
		
		// check first block
		int tmpX = (int)Math.floor(getPosX());
		int tmpY = (int)Math.floor(getPosY()+1);
		
		float overlapY = 0.f;
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getY()*tmpOverlap.getY() > overlapY*overlapY) overlapY = tmpOverlap.getY();
		}
		
		// check second block
		tmpX = (int)Math.floor(getPosX()+1);
		tmpY = (int)Math.floor(getPosY()+1);
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getY()*tmpOverlap.getY() > overlapY*overlapY) overlapY = tmpOverlap.getY();
		}
		
		// check third block
		tmpX = (int)Math.floor(getPosX()+1);
		tmpY = (int)Math.floor(getPosY());
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getY()*tmpOverlap.getY() > overlapY*overlapY) overlapY = tmpOverlap.getY();
		}
		
		return overlapY;
	}
	
	public float checkCollisionDown(GameEngine engine)
	{
		Vector2f tmpOverlap = new Vector2f(0.f, 0.f);
		
		// check first block
		int tmpX = (int)Math.floor(getPosX());
		int tmpY = (int)Math.floor(getPosY());
		
		float overlapY = 0.f;
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getY()*tmpOverlap.getY() > overlapY*overlapY) overlapY = tmpOverlap.getY();
		}
		
		// check second block
		tmpX = (int)Math.floor(getPosX()+1);
		tmpY = (int)Math.floor(getPosY());
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getY()*tmpOverlap.getY() > overlapY*overlapY) overlapY = tmpOverlap.getY();
		}
		
		// check third block
		tmpX = (int)Math.floor(getPosX());
		tmpY = (int)Math.floor(getPosY()+1);
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getY()*tmpOverlap.getY() > overlapY*overlapY) overlapY = tmpOverlap.getY();
		}
		
		return overlapY;
	}
	
	public float checkCollisionRight(GameEngine engine)
	{
		Vector2f tmpOverlap = new Vector2f(0.f, 0.f);
		
		// check first block
		int tmpX = (int)Math.floor(getPosX()+1);
		int tmpY = (int)Math.floor(getPosY());
		
		float overlapX = 0.f;
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getX()*tmpOverlap.getX() > overlapX*overlapX) overlapX = tmpOverlap.getX();
		}
		
		// check second block
		tmpX = (int)Math.floor(getPosX()+1);
		tmpY = (int)Math.floor(getPosY()+1);
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getX()*tmpOverlap.getX() > overlapX*overlapX) overlapX = tmpOverlap.getX();
		}
		
		// check third block
		tmpX = (int)Math.floor(getPosX());
		tmpY = (int)Math.floor(getPosY());
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getX()*tmpOverlap.getX() > overlapX*overlapX) overlapX = tmpOverlap.getX();
		}
		
		return overlapX;
	}
	
	public float checkCollisionLeft(GameEngine engine)
	{
		Vector2f tmpOverlap = new Vector2f(0.f, 0.f);
		
		// check first block
		int tmpX = (int)Math.floor(getPosX());
		int tmpY = (int)Math.floor(getPosY());
		
		float overlapX = 0.f;
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getX()*tmpOverlap.getX() > overlapX*overlapX) overlapX = tmpOverlap.getX();
		}
		
		// check second block
		tmpX = (int)Math.floor(getPosX());
		tmpY = (int)Math.floor(getPosY()+1);
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getX()*tmpOverlap.getX() > overlapX*overlapX) overlapX = tmpOverlap.getX();
		}
		
		// check third block
		tmpX = (int)Math.floor(getPosX()+1);
		tmpY = (int)Math.floor(getPosY()+1);
		
		if (engine.map.getBlockAt(tmpX, tmpY).isSolid())
		{
			tmpOverlap = this.collideOverlapWithBlock(tmpX, tmpY);
		}
		
		// switch direction
		if (tmpOverlap.getX() != 0 || tmpOverlap.getY() != 0)
		{
			if (tmpOverlap.getX()*tmpOverlap.getX() > overlapX*overlapX) overlapX = tmpOverlap.getX();
		}
		
		return overlapX;
	}
	
	private void checkCollisions(GameEngine engine)
	{
		Vector2f tmpOverlap = new Vector2f(0.f, 0.f);
		
		int tmpX, tmpY;
		float overlapX = 0;
		float overlapY = 0;
		
		if (direction.getY() > 0)
		{
			if (direction.getX() > 0)
			{
				//// up right
				overlapX = checkCollisionRight(engine);
				overlapY = checkCollisionTop(engine);
				
			} else
			{
				//// up left
				overlapX = checkCollisionLeft(engine);
				overlapY = checkCollisionTop(engine);
			}
		} else
		{
			if (direction.getX() > 0)
			{
				/// down right
				overlapX = checkCollisionRight(engine);
				overlapY = checkCollisionDown(engine);
			} else
			{
				// down left
				overlapX = checkCollisionLeft(engine);
				overlapY = checkCollisionDown(engine);
			}
		}

		if (overlapX != 0 || overlapY != 0)
		{
			if (overlapX*overlapX < overlapY*overlapY)
			{
				if (overlapX != 0)
				{
					direction.setX(-direction.getX());
					setPosX(getPosX()-overlapX);
				} else
				{
					direction.setY(-direction.getY());
					setPosY(getPosY()-overlapY);
				}

			} else
			{
				if (overlapY != 0)
				{
					direction.setY(-direction.getY());
					setPosY(getPosY()-overlapY);
				} else
				{
					direction.setX(-direction.getX());
					setPosX(getPosX()-overlapX);
				}
			}
		}		
	}
	
	/**
	 * updates position
	 */
	public void update(float dt, GameEngine engine)
	{
		super.update(dt, engine);
		
		Vector2f oldPos = new Vector2f(getPosition());
		
		setPosition(getPosition().add(direction.mul(dt*speed)));
		
		checkCollisions(engine);		
	}
	
	public void draw()
	{
		super.draw();
	}

	public BounceEnemy createNew()
	{
		return new BounceEnemy();
	}

}