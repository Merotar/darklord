package game;

public class TimeStore extends StoreObject<Float>
{

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
