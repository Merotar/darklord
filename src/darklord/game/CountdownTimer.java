package darklord.game;

public class CountdownTimer extends StoreObject<Float>
{
	public CountdownTimer(float maxValue)
	{
		super(maxValue, maxValue);
	}
	
	public void update(float dt)
	{
		current -= dt;
		
		if (current < 0) current = 0.f;
	}
	
	public void reset()
	{
		current = max;
	}
	
	public boolean isDone()
	{
		if (current == 0.f) return true;
		return false;
	}
}
