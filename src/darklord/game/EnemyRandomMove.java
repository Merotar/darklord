package darklord.game;


import java.io.Serializable;

import org.lwjgl.opengl.GL11;

import darklord.media.Animation;
import darklord.media.Drawable;
import darklord.media.TextureRegion;
import darklord.math.Vector2f;

/**
 * description of an enemy which performs random movement
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class EnemyRandomMove extends Enemy implements Serializable
{
	Vector2f direction, oldPos;
//	float speed;
	Timer motionTimer;
	float motionTime;	// time to move to the next tile
//	private Drawable appearance;
	
	enum Direction
	{
		NONE, UP, DOWN, LEFT, RIGHT
	}
	
	public EnemyRandomMove()
	{
		this(1.f, 1.f);
	}
	
	public EnemyRandomMove(EnemyRandomMove e)
	{
		super(e);
	}
	
	public EnemyRandomMove(float x, float y)
	{
		this(x, y, 1);
	}
	
	public EnemyRandomMove(float x, float y, int type)
	{
		super(x, y, type);
		setXp(3);
		direction = new Vector2f();
//		speed = 1.f;
		motionTimer = new Timer();
		motionTime = 500;
		oldPos = getPosition();
		
		appearance = new Animation();
		((Animation)appearance).addTextureRegion(new TextureRegion(0, 1, 128));
		((Animation)appearance).addTextureRegion(new TextureRegion(1, 1, 128));
		((Animation)appearance).addTextureRegion(new TextureRegion(2, 1, 128));
	}
	
	public void setDirection(Vector2f d)
	{
		direction = d;
	}
	
	public Vector2f getDirection()
	{
		return direction;
	}
	
	public void startMotion(Vector2f direction)
	{
		setDirection(direction);
		motionTimer.start();
		oldPos = getPosition();
	}
	
	public void stopMotion()
	{
		motionTimer.stop();
		motionTimer.reset();
		
		setPosX(Math.round(getPosX()));
		setPosY(Math.round(getPosY()));
	}
	
	public boolean isMoving()
	{
		return motionTimer.running();
	}
	
	/**
	 * updates position
	 */
	public void update(float dt)
	{
		super.update(dt);
		if (motionTimer.getTimeDelta() > motionTime)
		{
			this.stopMotion();
		}
		
		if (this.isMoving())
		{
			float path = 1.f*motionTimer.getTimeDelta()/motionTime;
			setPos(oldPos.add(direction.mul(path)));
		}
	}
	
	public void draw()
	{
		super.draw();
	}

//	public float getSpeed() {
//		return speed;
//	}
//
//	public void setSpeed(float speed) {
//		this.speed = speed;
//	}
}