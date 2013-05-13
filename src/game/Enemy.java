package game;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

/**
 * general description of an enemy
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Enemy extends Collidable
{
	int type;
	float maxHp, hp, dmgOnContact;
	
	public Enemy()
	{
		type = 0;
		hp = maxHp = 2.f;
		dmgOnContact = 1.f;
		setSizeX(1.f);
		setSizeY(1.f);
	}
	
	public Enemy(Enemy e)
	{
		this.setPos(e.getPos());
		this.setSizeX(e.getSizeX());
		this.setSizeY(e.getSizeY());
		this.setType(e.getType());
	}
	
	public Enemy(int t)
	{
		this();
		type = t;
	}
	
	public Enemy(float x, float y, int t)
	{
		this(t);
		setPosX(x);
		setPosY(y);
	}
	
//	public Vector2f getPosition()
//	{
//		return position;
//	}
	
	void draw()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);  
		Color.white.bind();
		
		Darklords.textures.enemies.get(getType()).bind();
		
//		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.f, 1.f);
		GL11.glVertex2f(0.f, getSizeY());
		GL11.glTexCoord2f(1.f, 1.f);
		GL11.glVertex2f(getSizeX(), getSizeY());
		GL11.glTexCoord2f(1.f, 0.f);
		GL11.glVertex2f(getSizeX(), 0.f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(0.f, 0.f);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	/**
	 * 
	 * @param f
	 * @return	returns true if enemy has no hp left
	 */
	public boolean decreaseHp(float f)
	{
		hp -= f;
		if (hp<=0) return true;
		return false;
	}

	public float getDmgOnContact() {
		return dmgOnContact;
	}

	public void setDmgOnContact(float dmgOnContact) {
		this.dmgOnContact = dmgOnContact;
	}
	
	public void update()
	{
		
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}
}