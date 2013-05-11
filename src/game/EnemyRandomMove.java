package game;

public class EnemyRandomMove extends Enemy
{
	Vector2f direction, oldPos;
//	float speed;
	Timer motionTimer;
	float motionTime;	// time to move to the next tile
	
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
		super(x, y, 1);
		direction = new Vector2f();
//		speed = 1.f;
		motionTimer = new Timer();
		motionTime = 1000;
		oldPos = getPos();
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
		oldPos = getPos();
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
	
	public void update()
	{
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

//	public float getSpeed() {
//		return speed;
//	}
//
//	public void setSpeed(float speed) {
//		this.speed = speed;
//	}
}