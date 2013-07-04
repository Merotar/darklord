package darklord.enemies;

import java.io.Serializable;

import darklord.collectables.Collectable;
import darklord.collectables.CollectableType;
import darklord.collectables.OrbYellowCollectable;
import darklord.game.GameEngine;
import darklord.game.RefillingStore;
import darklord.game.Timer;
import darklord.math.Vector2f;
import darklord.media.Animation;
import darklord.media.Sprite;
import darklord.media.TextureRegion;
import darklord.media.TimedAnimation;
import darklord.media.TimedTextureRegion;

public class ChasingBlockEnemy extends Enemy implements Serializable
{
	enum ChasingBlockEnemyState
	{
		SLEEPING, WAKING_UP, CHASING;
	}
	
	Sprite sleeping;
	ChasingBlockEnemyState state;
	TimedAnimation timedAnimation;
	private float wakeUpRadius;
	private Vector2f direction;
	private float speed;
	private float visibility;
	
	public ChasingBlockEnemy()
	{
		this(1.f, 1.f);
	}
	
	public ChasingBlockEnemy(float x, float y)
	{
		this(x, y, 1);
	}
	
	public ChasingBlockEnemy(float x, float y, int type)
	{
		super(x, y, type);
		setXp(3);
		timedAnimation = new TimedAnimation();
		state = ChasingBlockEnemyState.SLEEPING;
		wakeUpRadius = 2.f;
		direction = new Vector2f(0.f, 0.f);
		speed = 4.f;
		visibility = 1.f;
		setDropItem(new OrbYellowCollectable());
		
//		appearance = new Animation();
		sleeping = new Sprite();
		((Sprite)sleeping).setTextureRegion(new TextureRegion(0, 4, 128));
		appearance = sleeping;
		
		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(1, 4, 128), new RefillingStore(0.f, 0.3f, 1.f)));
		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(2, 4, 128), new RefillingStore(0.f, 0.3f, 1.f)));
		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(3, 4, 128), new RefillingStore(0.f, 0.4f, 1.f)));
		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(4, 4, 128), new RefillingStore(0.f, 0.3f, 1.f)));
		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(5, 4, 128), new RefillingStore(0.f, 0.3f, 1.f)));
//		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(4, 4, 128), new RefillingStore(0.f, 0.3f, 1.f)));
//		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(5, 4, 128), new RefillingStore(0.f, 0.3f, 1.f)));
		timedAnimation.addTimedTextureRegion(new TimedTextureRegion(new TextureRegion(6, 4, 128), new RefillingStore(0.f, 0.01f, 1.f)));
	}
	
	public ChasingBlockEnemy createNew()
	{
		return new ChasingBlockEnemy();
	}
	
	public void update(float dt, GameEngine engine)
	{
		super.update(dt, engine);
		
		// wake up?
		if (state == ChasingBlockEnemyState.SLEEPING && (engine.mainPlayer.getCenter().sub(this.getCenter())).squaredLength() < wakeUpRadius*wakeUpRadius)
		{
			state = ChasingBlockEnemyState.WAKING_UP;
		}
		
		// set appearance
		if (state != ChasingBlockEnemyState.SLEEPING)
		{
			timedAnimation.update(dt);
			((Sprite)appearance).setTextureRegion(timedAnimation.getCurrentTextureRegion());
		}
		
		if (state == ChasingBlockEnemyState.WAKING_UP)
		{
			setVisibility(getVisibility()-dt*0.3f);
		}
		
		// chasing
		if (state == ChasingBlockEnemyState.CHASING)
		{
			direction = engine.mainPlayer.getCenter().sub(this.getCenter());
			float distance = direction.length();
			
			if (distance > 0.3f)
			{
				direction.normalize();
				this.setPosition(this.getPosition().add(direction.mul(speed*dt)));
			}

		}
		
		// start chasing?
		if (timedAnimation.isFinished())
		{
			state = ChasingBlockEnemyState.CHASING;
		}
	}

}
