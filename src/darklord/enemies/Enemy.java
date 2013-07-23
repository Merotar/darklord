package darklord.enemies;


import java.io.Serializable;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import darklord.collectables.Collectable;
import darklord.collectables.CollectableType;
import darklord.game.Collidable;
import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.game.Print;
import darklord.game.Projectile;
import darklord.game.RefillingStore;
import darklord.game.Venom;
import darklord.math.Vector2f;
import darklord.media.Drawable;

/**
 * general description of an enemy
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public abstract class Enemy extends Collidable implements Serializable
{
	int type, xp;
	float maxHp, hp, dmgOnContact;
	boolean damaged;
	boolean dead;
	protected Drawable appearance;
	RefillingStore bubbleTimer;
	private Venom venom;
	private float visibility;
	private Collectable dropItem;
	private boolean invulnerable;
	private boolean damgeOnContact;
	private boolean solid;
	
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
		visibility = 1.f;
		bubbleTimer = new RefillingStore(0.5f, 1.f);
		dropItem = null;
		invulnerable = false;
		damgeOnContact = true;
	}
	
	public boolean isInside(Vector2f thePosition)
	{
		boolean inside = true;
		
		if (thePosition.getX()>getPosition().getX()+getSizeX()) inside = false;
		if (thePosition.getX()<getPosition().getX()) inside = false;
		if (thePosition.getY()>getPosition().getY()+getSizeY()) inside = false;
		if (thePosition.getY()<getPosition().getY()) inside = false;
//		if (inside) Print.outln("inside of button "+getName());
		
		return inside;
	}
	
	public Enemy(Enemy e)
	{
		this();
		this.setPosition(e.getPosition());
		this.setSizeX(e.getSizeX());
		this.setSizeY(e.getSizeY());
		this.setType(e.getType());
		this.appearance = e.appearance;
		this.type = e.type;
		this.hp = e.hp;
		this.maxHp = e.maxHp;
		this.xp = e.xp;
		this.dmgOnContact = e.dmgOnContact;
		this.bubbleTimer = e.bubbleTimer;
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
	
	public void draw()
	{
		Darklord.chars.begin();
		GL11.glPushMatrix();
		GL11.glTranslated(getSizeX()/2.f, getSizeY()/2.f, 0.f);
		GL11.glRotated(getAngle(), 0., 0., 1.);
		GL11.glTranslated(-getSizeX()/2.f, -getSizeY()/2.f, 0.f);
		GL11.glScaled(getSizeX(), getSizeY(), 1.f);
		if (venom == null)
		{
			appearance.drawColor(1.f, 1.f, 1.f, visibility);
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
		if (!invulnerable)
		{
			hp -= f;
			damaged = true;
			if (hp<=0)
			{
				dead = true;
				
				return true;
			}
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

	public abstract Enemy createNew();
	
	public void setDmgOnContact(float dmgOnContact) {
		this.dmgOnContact = dmgOnContact;
	}
	
	public void finalAction(GameEngine engine)
	{
		// drop item
		if (isDead() && dropItem != null)
		{
			Random rnd = new Random();
			float rndX = 0.10f*(1.f*rnd.nextInt()/Integer.MAX_VALUE);
			float rndY = 0.10f*(1.f*rnd.nextInt()/Integer.MAX_VALUE);
//			System.out.println(rndX);
			
			dropItem.setPosX(getPosX()+0.1f+rndX);
			dropItem.setPosY(getPosY()+0.1f+rndY);
			engine.map.getCollectableObjects().add(dropItem);
		}
	}
	
	public void update(float dt, GameEngine engine)
	{
		damaged = false;
		bubbleTimer.increase(dt);
		if (venom != null)
		{
			venom.update(dt, this);
			if (!venom.isActive()) venom = null;
		}
		
		if (dead) finalAction(engine);
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

	public float getVisibility() {
		return visibility;
	}

	public void setVisibility(float visibility) {
		this.visibility = visibility;
	}

	public float getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(float maxHp) {
		this.maxHp = maxHp;
		this.hp = maxHp;
	}

	public Collectable getDropItem() {
		return dropItem;
	}

	public void setDropItem(Collectable dropItem) {
		this.dropItem = dropItem;
	}

	public boolean isInvulnerable() {
		return invulnerable;
	}

	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}

	public boolean isDamgeOnContact() {
		return damgeOnContact;
	}

	public void setDamgeOnContact(boolean damgeOnContact) {
		this.damgeOnContact = damgeOnContact;
	}
}