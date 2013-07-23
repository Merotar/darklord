package darklord.game;

import darklord.math.Vector2f;

/**
 * description of an returningprojectile
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 18-07-2013
 * 
 */
public class ReturningProjectile extends Projectile
{
	private float returnLength;
	private float currentPathLength;
	private boolean backwards;
//	private Vector2f startPosition;
	
	public ReturningProjectile(Vector2f pos, Vector2f dir, Vector2f theSize, int type, float theExplodingTime)
	{
		super(pos, dir, theSize, type);
		speed = 5.f;
		returnLength = (theExplodingTime / 2) * speed;
		active = true;
		currentPathLength = 0.f;
		backwards = false;
//		startPosition = new Vector2f(pos);
		time.setMax(2*returnLength/speed);
		setSolid(false);
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
			if (currentPathLength > returnLength)
			{
				backwards = true;

			}
			
			if (!backwards)
			{
				Vector2f tmpPath = direction.mul(dt*speed);
				setPosition(getPosition().add(tmpPath));
				currentPathLength += tmpPath.length();
				// TODO: implement rotation
			} else
			{
				Vector2f tmpPath = direction.mul(dt*speed);
				setPosition(getPosition().sub(tmpPath));
				currentPathLength -= tmpPath.length();
			}
		}
		
//		if (backwards && (startPosition.sub(getPosition())).squaredLength() <= 0.1f)
//		{
//			active = false;
//		}
	}
}