package game;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Projectile extends Collidable
{
//	private Vector2f position, direction;
	private Vector2f direction;
	private float lifeTime;
	private Timer timer;
	private boolean active;
	private float speed;
	private float timePassed;
	private float size;
	
	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public Projectile()
	{
//		position = new Vector2f();
		direction = new Vector2f();
		lifeTime = 1000;
		timer = new Timer();
		timer.start();
		active = true;
		speed = 2.f;
		timePassed = 0.f;
		size = 0.3f;
	}
	
//	public Projectile(float posX, float posY)
//	{
//		this();
//		position.setX(posX);
//		position.setY(posY);
//	}
	
	public Projectile(Vector2f pos, Vector2f dir)
	{
		this();
		setPosX(pos.getX());
		setPosY(pos.getY());
		direction = dir;
	}
	
//	public void setPositionX(float posX)
//	{
//		position.setX(posX);
//	}
//	
//	public void setPositionY(float posY)
//	{
//		position.setY(posY);
//	}
	
	public void setDirectionX(float dirX)
	{
		direction.setX(dirX);
	}
	
	public void setDirectionY(float dirY)
	{
		direction.setY(dirY);
	}
	
//	public float getPositionX()
//	{
//		return position.getX();
//	}
//	
//	public float getPositionY()
//	{
//		return position.getY();
//	}
	
	public void draw()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);  
		Color.white.bind();
		
		Darklords.textures.projectile01.bind();
		
//		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.f, 1.f);
		GL11.glVertex2f(0.f, size);
		GL11.glTexCoord2f(1.f, 1.f);
		GL11.glVertex2f(size, size);
		GL11.glTexCoord2f(1.f, 0.f);
		GL11.glVertex2f(size, 0.f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(0.f, 0.f);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public boolean collideWithBlock(float x, float y)
	{
		boolean collision;
		
		collision = Collider.collideBorders(getPosX(), getPosX()+getSize(), getPosY(), getPosY()+getSize(), x, x+1.f, y, y+1.f);
		
		return collision;
	}
	
	public void update()
	{
		float time = timer.getTimeDelta();
		
		if (time > lifeTime)
		{
			active = false;
		}
		
		// move
		while ((time-timePassed) > 1.f/Darklords.maxFPS)
		{
			timePassed = time;
			setPos(getPos().add(direction.mul(speed*0.1f)));
//			position = position.add(direction.mul(speed*0.1f));
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}