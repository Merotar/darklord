package darklord.game;

import org.lwjgl.opengl.GL11;

import darklord.media.Animation;
import darklord.media.Drawable;
import darklord.media.TextureRegion;
import darklord.math.Vector2f;

/**
 * describes a crystal enemy
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 16-05-2013
 * 
 */
public class StaticEnemyCrystal extends Enemy
{
	TimeStore time;
//	private Drawable appearance;
	
	public StaticEnemyCrystal()
	{
		super();
		time = new TimeStore(2.f);
		setHp(4.f);
		setXp(2);
		setType(2);
		appearance = new Animation();
//		((Animation)appearance).addTextureRegion(new TextureRegion(5*128, 0*128, 128, 128));
		((Animation)appearance).addTextureRegion(new TextureRegion(0*128, 2*128, 128, 128));
		((Animation)appearance).addTextureRegion(new TextureRegion(1*128, 2*128, 128, 128));
		((Animation)appearance).addTextureRegion(new TextureRegion(2*128, 2*128, 128, 128));
	}
	
	public StaticEnemyCrystal(float x, float y)
	{
		this();
		this.setPosX(x);
		this.setPosY(y);
	}
	
	public void update(float dt, GameEngine level)
	{
		super.update(dt);
		if (time.add(dt))
		{
			Vector2f pos, dir;
			Projectile tmpProjectile;
			
			// add right projectile
			tmpProjectile = new Projectile(3);
			pos = this.getCenter().sub(new Vector2f(tmpProjectile.getSize()/2.f, tmpProjectile.getSize()/2.f));
			dir = new Vector2f(1.f, 0.f);
			tmpProjectile.setPos(pos);
			tmpProjectile.setDirection(dir);
			level.hostileProjectiles.add(tmpProjectile);
			
			// add left projectile
			tmpProjectile = new Projectile(3);
			pos = this.getCenter().sub(new Vector2f(tmpProjectile.getSize()/2.f, tmpProjectile.getSize()/2.f));
			dir = new Vector2f(-1.f, 0.f);
			tmpProjectile.setPos(pos);
			tmpProjectile.setDirection(dir);
			level.hostileProjectiles.add(tmpProjectile);
			
			// add up projectile
			tmpProjectile = new Projectile(3);
			pos = this.getCenter().sub(new Vector2f(tmpProjectile.getSize()/2.f, tmpProjectile.getSize()/2.f));
			dir = new Vector2f(0.f, -1.f);
			tmpProjectile.setPos(pos);
			tmpProjectile.setDirection(dir);
			level.hostileProjectiles.add(tmpProjectile);
			
			// add down projectile
			tmpProjectile = new Projectile(3);
			pos = this.getCenter().sub(new Vector2f(tmpProjectile.getSize()/2.f, tmpProjectile.getSize()/2.f));
			dir = new Vector2f(0.f, 1.f);
			tmpProjectile.setPos(pos);
			tmpProjectile.setDirection(dir);
			level.hostileProjectiles.add(tmpProjectile);
			
//			Print.outln("crystal: shoot!");
		}
	}
	
	public void draw()
	{
		super.draw();
	}

	public StaticEnemyCrystal createNew()
	{
		return new StaticEnemyCrystal();
	}
}