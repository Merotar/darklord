package darklord.game;


import java.io.Serializable;

/**
 * class to handle refillable resource
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
public class RefillingStore extends StoreObject<Float> implements Serializable
{
	float amountPerSecond;
	
	public RefillingStore(float maxValue, float amountPersecond)
	{
		super(maxValue, maxValue);
		setAmountPerSecond(amountPersecond);
	}
	
	public RefillingStore(float startValue, float maxValue, float amountPersecond)
	{
		super(startValue, maxValue);
		setAmountPerSecond(amountPersecond);
	}

	public boolean decrease(float amount)
	{
		current -= amount;
		if (current <= 0)
		{
			current = 0.f;
			return true;
		}
		return false;
	}
	
	public boolean increase(float dt)
	{
		current += getAmountPerSecond()*dt;
		if (current > max)
		{
			current = max;
			return false;
		}
		return true;
	}
	
	public void addConstant(float theAmount)
	{
		current += theAmount;
		if (current > max) current = max;
	}
	
//	public void increase()
//	{
//		current += getAmountPerSecond();
//		if (current > max) current = max;
//	}

	public float getAmountPerSecond() {
		return amountPerSecond;
	}

	public void setAmountPerSecond(float amountPerSecond) {
		this.amountPerSecond = amountPerSecond;
	}
}
