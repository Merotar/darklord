
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
	private Vector2f start, stop, position, size;
	private Timer motionTimer;
	private float lifetime, fullIntensityTime;
	private float intensity;
	boolean alive;
	
	public MovingSprite(Vector2f theStart, Vector2f theStop)
	{
		super();
		motionTimer = new Timer();
		motionTimer.start();
		setLifetime(1000.f);
		setFullIntensityTime(200.f);
		setIntensity(1.f);
		size = new Vector2f(2.f, 1.f).mul(1.5f);
		setAlive(true);
		setStart(theStart);
		setStop(theStop);
		setPosition(getStart());
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

	public float getLifetime() {
		return lifetime;
	}

	public void setLifetime(float lifetime) {
		this.lifetime = lifetime;
	}
	
	public void update(float dt)
	{
//		if (motionTimer.getTimeDelta() < getFullIntensityTime())
//		{
//			setIntensity(1.f);
//		}
		
		float relativeTime = motionTimer.getTimeDelta()/getLifetime();
		// TODO: maybe better use direction and add position
		setPosition(getStart().add(getStop().sub(getStart()).mul(relativeTime)));
		setSize(getSize().mul(0.97f));
		
		if (motionTimer.getTimeDelta() > getFullIntensityTime())
		{
			setIntensity(1.f-(motionTimer.getTimeDelta()-getFullIntensityTime())/(getLifetime()-getFullIntensityTime()));
		}

		
		if (motionTimer.getTimeDelta() > getLifetime())
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

	public Vector2f getStart() {
		return start;
	}

	public void setStart(Vector2f start) {
		this.start = start;
	}

	public Vector2f getStop() {
		return stop;
	}

	public void setStop(Vector2f stop) {
		this.stop = stop;
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