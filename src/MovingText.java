

import org.lwjgl.opengl.GL11;

/**
 * describes a text moving along a path
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
public class MovingText {
	private Vector2f start, direction, position, size;
	private TimeStore time;
	private float pathLength;
	private float intensity, fullIntensityTime;
	boolean alive;
	String text;
	
	public MovingText(String s, Vector2f theStart, Vector2f dir, float length)
	{
		text = s;
		time = new TimeStore(1.5f);
		setFullIntensityTime(200.f);
		setIntensity(1.f);
		size = new Vector2f(0.5f, 1.f);
		setAlive(true);
//		theStart.addX(-size.getX()*s.length()/2.f);
		setStart(theStart);
		dir.normalize();
		setDirection(dir);
		setPosition(getStart());
		setPathLength(length);
	}
	
	public MovingText()
	{
		this("0", new Vector2f());
	}
	
	public MovingText(String s, Vector2f start)
	{
		
		this(s, start, new Vector2f(0.f, -1.f), 1.f);
	}
	
	public void draw(TextDrawer theTextDrawer)
	{
		GL11.glPushMatrix();
		theTextDrawer.draw(text, getPosition());
		GL11.glPopMatrix();
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
		if (!time.add(dt))
		{
			position = position.add(direction.mul(dt*pathLength/time.getMax()));
		} else
		{
			setAlive(false);
		}
		setSize(getSize().mul(0.97f));

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

	public Vector2f getDirection() {
		return direction;
	}

	public void setDirection(Vector2f dir) {
		this.direction = dir;
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

	public float getPathLength() {
		return pathLength;
	}

	public void setPathLength(float pathLength) {
		this.pathLength = pathLength;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
