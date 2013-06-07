
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
	Timer shootTimer;
	float shootDelay;
	private Drawable appearance;
	
	public StaticEnemyCrystal()
	{
		super();
		shootTimer = new Timer();
		shootTimer.start();
		shootDelay = 2000;
		setHp(1.f);
		setType(2);
		appearance = new Sprite();
		((Sprite)appearance).setTextureRegion(6*128, 0*128, 128, 128);
	}
	
	public StaticEnemyCrystal(float x, float y)
	{
		this();
		this.setPosX(x);
		this.setPosY(y);
	}
	
	public void update(float dt, Level level)
	{
		if (shootTimer.getTimeDelta() >= shootDelay)
		{
			shootTimer.reset();
			
			
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
		Darklords.sprites01.begin();
		appearance.draw();
		Darklords.sprites01.end();
	}
}