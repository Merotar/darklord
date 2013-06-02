package game;

import org.lwjgl.opengl.GL11;

public class Beam
{
	Vector2f start, direction, end;
	float maxLength, length, width, stepSize;
	Drawable appearance;
	boolean active;
	private TimeStore lifetime;
	
	public Beam(Vector2f theStart, Vector2f theDirection)
	{
		start = theStart;
		theDirection.normalize();
		direction = theDirection;
		end = new Vector2f();
		width = 0.3f;
		maxLength = 8.f;
		stepSize = 0.1f;
		appearance = new Sprite();
		lifetime = new TimeStore(1.f);
		((Sprite)appearance).setTextureRegion(new TextureRegion(0*128, 3*128, 2*128, 32));
		setActive(false);
	}
	
	public Beam()
	{
		this(new Vector2f(0.f, 0.f), new Vector2f(1.f, 0.f));
		end = new Vector2f(1.f, 0.f);
	}
	
	public void calcEnd(Map map)
	{
		int maxSteps = (int)(getMaxLength()/stepSize);
		Vector2f currentPos = new Vector2f(start);
		int x, y;
		
		for (int step=0;step<maxSteps; step++)
		{
			currentPos = currentPos.add(direction.mul(stepSize));
			x = (int)Math.floor(currentPos.getX());
			y = (int)Math.floor(currentPos.getY());
//			Print.outln("x: "+x+", "+"y: "+y);
			
			if (map.getBlockAt(x, y).isSolid())
			{
				end = currentPos;
				length = end.sub(start).length();
				return;
			}
		}
		end = currentPos;
		length = maxLength;
		return;
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

	public void setDirection(Vector2f theDirection) {
		theDirection.normalize();
		this.direction = theDirection;
	}

	public Vector2f getEnd() {
		return end;
	}

	public void setEnd(Vector2f end) {
		this.end = end;
	}

	public float getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(float maxLength) {
		this.maxLength = maxLength;
	}
	
	public void resetLifetime()
	{
		lifetime.setCurrent(0.f);
	}
	
	public void draw()
	{
		GL11.glPushMatrix();

		GL11.glTranslatef(start.getX(), start.getY(), 0.f);
		float angle = (float)(-Math.atan2(-direction.getY(), direction.getX())*180/Math.PI);
		GL11.glRotatef(angle, 0.f, 0.f, 1.f);
		GL11.glScaled(length, width, 1.f);
		GL11.glTranslatef(0.f, -0.5f, 0.f);
		Darklords.sprites01.begin();
		appearance.draw();
		Darklords.sprites01.end();
		GL11.glPopMatrix();
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getStepSize() {
		return stepSize;
	}

	public void setStepSize(float stepSize) {
		this.stepSize = stepSize;
	}
	
	public void update()
	{
		if (lifetime.add(Darklords.dt))
		{
			setActive(false);
		}
	}
}
