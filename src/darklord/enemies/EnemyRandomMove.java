package darklord.enemies;


import java.io.Serializable;

import javax.print.attribute.standard.MediaSize.Engineering;

import org.lwjgl.opengl.GL11;

import darklord.game.Block;
import darklord.game.GameEngine;
import darklord.game.RandomGenerator;
import darklord.game.Timer;
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
	public void update(float dt, GameEngine engine)
	{
		super.update(dt, engine);
		
		if(!this.isMoving())
		{
			moveRandom(engine);
		}
		
		if (motionTimer.getTimeDelta() > motionTime)
		{
			this.stopMotion();
		}
		
		if (this.isMoving())
		{
			float path = 1.f*motionTimer.getTimeDelta()/motionTime;
			setPosition(oldPos.add(direction.mul(path)));
		}
	}
	
	/**
	 * moves an EnemyRandomMove in a random direction
	 * @param e enemy to move
	 */
	void moveRandom(GameEngine engine)
	{
		int posX = (int)Math.round(this.getPosX());
		int posY = (int)Math.round(this.getPosY());
//		e.getPos().print();
		
//		System.out.print("move to dir: ");dir.print();
		
		float localX = getLocalPosition(engine.map.levelStructure).getX();
		float localY = getLocalPosition(engine.map.levelStructure).getY();
		
		
		Vector2f dir = RandomGenerator.getRandomDirection();
		
		// make sure that enemy doesn't leave the room
		float newX = localX + dir.getX();
		float newY = localY + dir.getY();
		if (newX < 0) dir.setX(1);
		if (newY < 0) dir.setY(1);
		if (newX > engine.map.levelStructure.getGridSizeX()-1) dir.setX(-1);
		if (newY > engine.map.levelStructure.getGridSizeY()-1) dir.setY(-1);
		
		Block tmpBlock = engine.map.getBlockAt(posX+(int)dir.getX(), posY+(int)dir.getY());
		if (tmpBlock != null && !tmpBlock.isSolid())
		{
//			System.out.println("test");
			this.startMotion(dir);
		}
	}
	
	public void draw()
	{
		super.draw();
	}

	public EnemyRandomMove createNew()
	{
		return new EnemyRandomMove();
	}

//	public float getSpeed() {
//		return speed;
//	}
//
//	public void setSpeed(float speed) {
//		this.speed = speed;
//	}
}