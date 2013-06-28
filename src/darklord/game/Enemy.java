package darklord.game;


import java.io.Serializable;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import darklord.media.Drawable;

/**
 * general description of an enemy
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Enemy extends Collidable implements Serializable
{
	int type, xp;
	float maxHp, hp, dmgOnContact;
	boolean damaged;
	boolean dead;
	protected Drawable appearance;
	RefillingStore bubbleTimer;
	private Venom venom;
	
	public Enemy()
	{
		type = 0;
		hp = maxHp = 3.f;
		xp = 1;
		dmgOnContact = 1.f;
		setSizeX(1.f);
		setSizeY(1.f);
		damaged = false;
		dead = false;
		bubbleTimer = new RefillingStore(0.5f, 1.f);
	}
	
	public Enemy(Enemy e)
	{
		this.setPos(e.getPosition());
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
	
	public boolean addVenom(Venom theVenom)
	{
		if (venom != null) return false;
		
		venom = theVenom;
		return true;
	}
	
//	public Vector2f getPosition()
//	{
//		return position;
//	}
	
	void draw()
	{
		Darklord.chars.begin();
		GL11.glPushMatrix();
		GL11.glTranslated(getSizeX()/2.f, getSizeY()/2.f, 0.f);
		GL11.glRotated(getAngle(), 0., 0., 1.);
		GL11.glTranslated(-getSizeX()/2.f, -getSizeY()/2.f, 0.f);
		GL11.glScaled(getSizeX(), getSizeY(), 1.f);
		if (venom == null)
		{
			appearance.draw();
		} else
		{
			float intensityGreen = 0.3f;
			appearance.drawColor(intensityGreen, 1.0f, intensityGreen, 1.0f);
		}
		Darklord.chars.end();
		GL11.glPopMatrix();
		
//		GL11.glEnable(GL11.GL_TEXTURE_2D);  
//		Color.white.bind();
//		
//		Darklords.textures.enemies.get(getType()).bind();
//		
////		texture.bind();
//		GL11.glBegin(GL11.GL_QUADS);
//		GL11.glTexCoord2f(0.f, 1.f);
//		GL11.glVertex2f(0.f, getSizeY());
//		GL11.glTexCoord2f(1.f, 1.f);
//		GL11.glVertex2f(getSizeX(), getSizeY());
//		GL11.glTexCoord2f(1.f, 0.f);
//		GL11.glVertex2f(getSizeX(), 0.f);
//		GL11.glTexCoord2f(0f, 0f);
//		GL11.glVertex2f(0.f, 0.f);
//		GL11.glEnd();
//		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	/**
	 * 
	 * @param f
	 * @return	returns true if enemy has no hp left
	 */
	public boolean decreaseHp(float f)
	{
		hp -= f;
		damaged = true;
		if (hp<=0)
		{
			dead = true;
			return true;
		}
		return false;
	}

	public boolean decreaseHp(Projectile p)
	{
		return decreaseHp(p.getDamage());
	}

	public float getDmgOnContact() {
		return dmgOnContact;
	}

	public void setDmgOnContact(float dmgOnContact) {
		this.dmgOnContact = dmgOnContact;
	}
	
	public void update(float dt)
	{
		damaged = false;
		bubbleTimer.increase(dt);
		if (venom != null)
		{
			venom.update(dt, this);
			if (!venom.isActive()) venom = null;
		}
	}

	public boolean canGenerateBubble()
	{
		if (bubbleTimer.getCurrent() == bubbleTimer.getMax())
		{
			return true;
		}
		return false;
	}
	
	public void resetBubbleTimer()
	{
		bubbleTimer.setCurrent(0.f);
	}
	
	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public float getHp() {
		return hp;
	}

	public void setHp(float hp) {
		this.hp = hp;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public boolean isDamaged() {
		return damaged;
	}

	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}