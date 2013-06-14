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
public class EnergyStore extends StoreObject<Float> implements Serializable
{
	float amountPerSecond;
	
	public EnergyStore(float value, float amount)
	{
		super(value, value);
		setAmountPerSecond(amount);
	}

	public boolean decrease(float amount)
	{
		if (getCurrent() >= amount)
		{
			current -= amount;
			return true;
		}
		return false;
	}
	
	public void increase(float dt)
	{
		current += getAmountPerSecond()*dt;
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
