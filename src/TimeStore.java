

import java.io.Serializable;

public class TimeStore extends StoreObject<Float> implements Serializable
{
	public TimeStore()
	{
		super(0.f);
	}

	public TimeStore(Float value)
	{
		super(0.f, value);
	}
	
	public boolean add(float dt)
	{
		current += dt;
		if (current >= max)
		{
			current -=max;
			return true;
		}
		return false;
	}

}
