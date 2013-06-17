package darklord.game;

import java.io.Serializable;

/**
 * used to time events
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
public class SawToothFunction extends StoreObject<Float> implements Serializable
{
	boolean increase;
	
	public SawToothFunction()
	{
		this(0.f);
	}

	public SawToothFunction(Float value)
	{
		super(0.f, value);
		increase = true;
	}
	
	public void add(float dt)
	{
		if (increase)
		{
			current += dt;
			if (current >= max)
			{
				increase = false;
			}
		} else
		{
			current -= dt;
			if (current <= 0)
			{
				increase = true;
			}
		}

	}

}