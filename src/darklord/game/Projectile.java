package darklord.game;


import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import darklord.media.Drawable;
import darklord.media.Sprite;
import darklord.math.Vector2f;

/**
 * description of an projectile
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Projectile extends Collidable
{
//	private Vector2f position, direction;
	private Vector2f direction;
//	private float lifeTime;
//	private Timer timer;
	protected TimeStore time;
	private boolean active;
	private float speed;
//	private float timePassed;
	private float size;
	private int type;
	private float damage;
	protected Drawable appearance;
	private float energyCosts;
	private float range;
	
	public Projectile()
	{
//		position = new Vector2f();
		direction = new Vector2f();
//		lifeTime = 1000;
//		timer = new Timer();
//		timer.start();
		active = true;
		
		range = 7.f;	// use variable instead of modifying lifetime
		speed = 4.f;
		time = new TimeStore(range/speed);
		setSize(0.3f);
		setDamage(1);
		appearance = new Sprite();
		setType(0);
		energyCosts = 1.f;
	}
	
	public Projectile(int t)
	{
		this();
		setType(t);
	}
	
//	public Projectile(float posX, float posY)
//	{
//		this();
//		position.setX(posX);
//		position.setY(posY);
//	}
	
	public Projectile(Vector2f pos, Vector2f dir, Vector2f theSize, int type)
	{
		this(type);
		setSizeX(theSize.getX());
		setSizeY(theSize.getY());
		setPosX(pos.getX());
		setPosY(pos.getY());
		direction = dir;
	}
	
	public Projectile(Vector2f pos, Vector2f dir, int type)
	{
		this(pos, dir, new Vector2f(0.3f, 0.3f), type);
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		
		try{
			switch (type){
			case 0:		// red
				((Sprite)appearance).setTextureRegion(7*128, 1*128+0*32, 32, 32);
				break;
			case 1:		// blue
				((Sprite)appearance).setTextureRegion(7*128, 1*128+1*32, 32, 32);
				break;
			case 2:		// green
				((Sprite)appearance).setTextureRegion(7*128, 1*128+2*32, 32, 32);
				break;
			case 3:		// ice
				((Sprite)appearance).setTextureRegion(7*128, 1*128+3*32, 32, 32);
				break;
			default:
//				texture = null;
				((Sprite)appearance).setTextureRegion(0*128, 10*128+0*32, 128, 128);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
		this.setSizeX(size);
		this.setSizeY(size);
	}
	
	public void setSizeVec(Vector2f theSize) {
		this.setSizeX(theSize.getX());
		this.setSizeY(theSize.getY());
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
	
	public void setDirection(Vector2f theDirection)
	{
		this.direction = theDirection;
	}
	
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
		Darklord.sprites01.begin();
		GL11.glTranslated(getPosX(), getPosY(), 0.);
		appearance.draw(0, 0, getSizeX(), getSizeY());
		Darklord.sprites01.end();
//		GL11.glEnable(GL11.GL_TEXTURE_2D);  
//		Color.white.bind();
//		
//		Darklords.textures.projectile.get(getType()).bind();
//		
////		texture.bind();
//		GL11.glBegin(GL11.GL_QUADS);
//		GL11.glTexCoord2f(0.f, 1.f);
//		GL11.glVertex2f(0.f, size);
//		GL11.glTexCoord2f(1.f, 1.f);
//		GL11.glVertex2f(size, size);
//		GL11.glTexCoord2f(1.f, 0.f);
//		GL11.glVertex2f(size, 0.f);
//		GL11.glTexCoord2f(0f, 0f);
//		GL11.glVertex2f(0.f, 0.f);
//		GL11.glEnd();
//		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public boolean collideWithBlock(float x, float y)
	{
		boolean collision;
		
		collision = Collider.collideBorders(getPosX(), getPosX()+getSize(), getPosY(), getPosY()+getSize(), x, x+1.f, y, y+1.f);
		
		return collision;
	}
	
	public void update(float dt)
	{
//		float time = timer.getTimeDelta();
		
		if (time.add(Darklord.dt))
		{
			active = false;
		}
		if (active)
		{
			setPos(getPosition().add(direction.mul(dt*speed)));
		}
		
		// move
//		while ((time-timePassed) > 1.f/Darklords.maxFPS)
//		{
//			timePassed = time;
//			setPos(getPos().add(direction.mul(Darklords.dt*speed)));
////			position = position.add(direction.mul(speed*0.1f));
//		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float f) {
		this.damage = f;
	}

	public Vector2f getDirection() {
		return direction;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
		time.setMax(range/speed);
	}

	public float getEnergyCosts() {
		return energyCosts;
	}

	public void setEnergyCosts(float energyCosts) {
		this.energyCosts = energyCosts;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
		time.setMax(range/speed);
	}
}