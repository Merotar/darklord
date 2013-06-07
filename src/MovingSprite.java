
/**
 * describes an sprite moving along a path
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 24-05-2013
 * 
 * 
 */

public class MovingSprite extends Sprite
{
	private Vector2f direction, position, size;
	private TimeStore time;
	private float fullIntensityTime;
	private float intensity, speed;
	boolean alive;
	
	public MovingSprite(Vector2f theStart, Vector2f theStop)
	{
		super();
		direction = theStop.sub(theStart);
		direction.normalize();
		speed = 1.f;
		time = new TimeStore(1.f);
		setFullIntensityTime(200.f);
		setIntensity(1.f);
		size = new Vector2f(2.f, 1.f).mul(1.5f);
		setAlive(true);
		setPosition(theStart);
		position.print();
		direction.print();
	}
	
	public MovingSprite()
	{
		this(new Vector2f(), new Vector2f());
	}
	
	public void draw()
	{
		Darklords.sprites01.begin();
		Darklords.sprites01.draw(getTextureRegion(), getPosition().getX()-getSize().getX()/2.f, 
				getPosition().getY()-getSize().getY()/2.f, getSize().getX(), getSize().getY(), getIntensity());
		Darklords.sprites01.end();
	}
	
//	public void draw(float posX, float posY, float sizeX, float sizeY)
//	{
//		float relativeTime = motionTimer.getTimeDelta()/getLifetime();
//		
//		super.draw(posX, posY, sizeX, sizeY);
//	}
	
	public void update(float dt)
	{
//		if (motionTimer.getTimeDelta() < getFullIntensityTime())
//		{
//			setIntensity(1.f);
//		}
		
		// TODO: maybe better use direction and add position
		position = position.add(direction.mul(dt*speed));
		setSize(getSize().mul(0.97f));
		
		boolean delete = time.add(dt);
		
		if (time.getCurrent() > getFullIntensityTime())
		{
			setIntensity(1.f-(time.getCurrent()-getFullIntensityTime())/(time.getMax()-getFullIntensityTime()));
		}

		
		if (delete)
		{
			// delete
			setAlive(false);
		}
	}

	public boolean isAlive()
	{
		return alive;
	}

	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getSize() {
		return size;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public float getFullIntensityTime() {
		return fullIntensityTime;
	}

	public void setFullIntensityTime(float fullIntensityTime) {
		this.fullIntensityTime = fullIntensityTime;
	}
}