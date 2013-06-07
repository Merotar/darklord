

import java.util.Random;

/**
 * generator for various random variables
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class RandomGenerator
{
	private static Random rnd = new Random();
	
	public RandomGenerator()
	{
		
	}
	
	public static float getRandomZeroToOne()
	{
		return (rnd.nextInt()*1.f/Integer.MAX_VALUE+1.f)/2.f;
	}
	
	/**
	 * 
	 * @return returns random unity vector from {-1,-1} to {1,1}
	 */
	public static Vector2f getRandomVector()
	{
		float angle = (float)(Math.PI*(rnd.nextInt()*1.f/Integer.MAX_VALUE+1.f));
		return new Vector2f((float)Math.cos(angle), (float)Math.sin(angle));
	}
	
	public static Vector2f getRandomDirection(Vector2f vec)
	{
		if (vec != null)
		{
			float rndVar = getRandomZeroToOne();
			Vector2f dir = new Vector2f(vec).mul(-1.f);
			
			float angle = 0;
			if (rndVar < 0.33f)
			{
				angle = (float)Math.PI/2.f;
			} else
			{
				if (rndVar < 0.66f)
				{
					angle = (float)Math.PI;
				} else
				{
					angle = (float)(3.f*Math.PI/2.f);
				}
			}
			dir.rotate(angle);
			return dir;		
		}

		return getRandomDirection();
	}
	
	public static Vector2f getRandomDirection()
	{
		float rndVar = (rnd.nextInt()*1.f/Integer.MAX_VALUE+1.f)/2.f;
		
		Vector2f dir = new Vector2f();
//		System.out.println("rndVar: "+rndVar);
		if (rndVar < 0.25f)
			{
				dir.set(0.f, -1.f);
			} else
			{
				if (rndVar < 0.5f)
				{
					dir.set(0.f, 1.f);
				} else
				{
					if (rndVar < 0.75f)
					{
						dir.set(-1.f, 0.f);
					} else
					{
						dir.set(1.f, 0.f);
					}
				}
			}
		return dir;
	}
}