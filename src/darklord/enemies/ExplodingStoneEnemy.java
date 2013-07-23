package darklord.enemies;

import java.io.Serializable;

import darklord.collectables.Collectable;
import darklord.collectables.CollectableType;
import darklord.collectables.OrbBlueCollectable;
import darklord.collectables.OrbYellowCollectable;
import darklord.game.GameEngine;
import darklord.game.Print;
import darklord.game.RefillingStore;
import darklord.game.ReturningProjectile;
import darklord.game.TimeStore;
import darklord.game.Timer;
import darklord.math.Vector2f;
import darklord.media.Animation;
import darklord.media.Sprite;
import darklord.media.TextureRegion;
import darklord.media.TimedAnimation;
import darklord.media.TimedTextureRegion;

public class ExplodingStoneEnemy extends Enemy implements Serializable
{
	enum ExplodingStoneEnemyState
	{
		SLEEPING, WAKING_UP, START_EXPLOSION, EXPLODING;
	}
	
	Sprite sleeping;
	ExplodingStoneEnemyState state;
	TimedAnimation timedAnimation;
	private float wakeUpRadius;
	private float explodingTime;
	TimeStore explodingTimeStore;
	
	public ExplodingStoneEnemy()
	{
		this(1.f, 1.f);
	}
	
	public ExplodingStoneEnemy(float x, float y)
	{
		this(x, y, 1);
	}
	
	public ExplodingStoneEnemy(float x, float y, int type)
	{
		super(x, y, type);
		setXp(3);
		timedAnimation = new TimedAnimation();
		state = ExplodingStoneEnemyState.SLEEPING;
		wakeUpRadius = 2.f;
		explodingTime = 2.f;
		setDropItem(new OrbBlueCollectable());
		
//		appearance = new Animation();
		sleeping = new Sprite();
		((Sprite)sleeping).setTextureRegion(new TextureRegion(0, 6, 128));
		appearance = sleeping;
		explodingTimeStore = new TimeStore(explodingTime);
		setDamgeOnContact(false);
		
		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(1, 6, 128), new RefillingStore(0.f, 0.3f, 1.f)));
		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(2, 6, 128), new RefillingStore(0.f, 0.3f, 1.f)));
		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(3, 6, 128), new RefillingStore(0.f, 0.3f, 1.f)));
	}
	
	public ExplodingStoneEnemy createNew()
	{
		return new ExplodingStoneEnemy();
	}
	
	public void update(float dt, GameEngine engine)
	{
		super.update(dt, engine);
		
		// wake up?
		if (state == ExplodingStoneEnemyState.SLEEPING && (engine.mainPlayer.getCenter().sub(this.getCenter())).squaredLength() < wakeUpRadius*wakeUpRadius)
		{
			state = ExplodingStoneEnemyState.WAKING_UP;
		}
		
		// set appearance
		if (state != ExplodingStoneEnemyState.SLEEPING)
		{
			timedAnimation.update(dt);
			((Sprite)appearance).setTextureRegion(timedAnimation.getCurrentTextureRegion());
		}
		
//		if (state == ExplodingStoneEnemyState.WAKING_UP)
//		{
//			setVisibility(getVisibility()-dt*0.3f);
//		}
		
		// chasing
//		if (state == ExplodingStoneEnemyState.CHASING)
//		{
//			direction = engine.mainPlayer.getCenter().sub(this.getCenter());
//			float distance = direction.length();
//			
//			if (distance > 0.3f)
//			{
//				direction.normalize();
//				this.setPosition(this.getPosition().add(direction.mul(speed*dt)));
//			}
//
//		}
		
		// start chasing?
		if (state == ExplodingStoneEnemyState.WAKING_UP && timedAnimation.isFinished())
		{
			state = ExplodingStoneEnemyState.START_EXPLOSION;
		}
		
		if (state == ExplodingStoneEnemyState.START_EXPLOSION)
		{
			Vector2f position = new Vector2f(getPosition());
			
			setVisibility(0.f);
			setSolid(false);
			
			position.addY(0.5f);
			engine.hostileProjectiles.add(new ReturningProjectile(position, new Vector2f(-1.f, 1.f), new Vector2f(0.5f, 0.5f), 10, explodingTime));
			position.addX(0.5f);
			engine.hostileProjectiles.add(new ReturningProjectile(position, new Vector2f(1.f, 1.f), new Vector2f(0.5f, 0.5f), 11, explodingTime));
			position.addY(-0.5f);
			engine.hostileProjectiles.add(new ReturningProjectile(position, new Vector2f(1.f, -1.f), new Vector2f(0.5f, 0.5f), 12, explodingTime));
			position.addX(-0.5f);
			engine.hostileProjectiles.add(new ReturningProjectile(position, new Vector2f(-1.f, -1.f), new Vector2f(0.5f, 0.5f), 13, explodingTime));
			
			state = ExplodingStoneEnemyState.EXPLODING;
		}
		
		if (state == ExplodingStoneEnemyState.EXPLODING)
		{
			if (explodingTimeStore.add(dt))
			{
				state = ExplodingStoneEnemyState.SLEEPING;
				explodingTimeStore.setCurrent(0.f);
				timedAnimation.reset();
				appearance = sleeping;
				setVisibility(1.f);
				setSolid(true);
			}
		}
	}

}
