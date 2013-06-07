

import org.lwjgl.opengl.GL11;

/**
 * description of an static text
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
public class StaticText<T extends Number>
{
	private T value;
	private Vector2f position, size;
	private float intensity;
	String text;
	
	public StaticText(String s, T theValue)
	{
		this(s, new Vector2f(), theValue);
	}
	
	public StaticText(String s, Vector2f thePos, T theValue)
	{
		if (s.equals("")) s = (String) "$";
		text = s;
		value = theValue;
		setIntensity(1.f);
		size = new Vector2f(0.5f, 1.f);
		setPosition(thePos);
	}
	
	
	public void draw(TextDrawer theTextDrawer)
	{
		String drawString = text;
		drawString = drawString.replaceAll("\\$", value.toString());

		GL11.glPushMatrix();
		GL11.glTranslatef(position.getX(), position.getY(), 0.f);
		GL11.glScalef(size.getX(), size.getY(), 1.f);
		theTextDrawer.draw(drawString, getPosition());
		GL11.glPopMatrix();
	}
	
//	public void draw(float posX, float posY, float sizeX, float sizeY)
//	{
//		float relativeTime = motionTimer.getTimeDelta()/getLifetime();
//		
//		super.draw(posX, posY, sizeX, sizeY);
//	}

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
