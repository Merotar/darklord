package darklord.game;

import java.io.Serializable;

public class Venom implements Serializable
{
//	protected long id;
	private RefillingStore lifeTime;
	private TimeStore tick;
	private float damagePerTick;
	
	public Venom(float damagePerSecond, float tickTime, float theLifetime)
	{
//		id = System.currentTimeMillis();
		lifeTime = new RefillingStore(theLifetime, 1.f);
		lifeTime.setCurrent(0.f);
		tick = new TimeStore(tickTime);
		damagePerTick = damagePerSecond * tickTime;
	}
	
	public boolean isActive()
	{
		return (lifeTime.getMax() != lifeTime.getCurrent());
	}

	public void update(float dt, Enemy theEnemy)
	{
		lifeTime.increase(dt);
//		Print.outln("time: "+lifeTime.getCurrent());
		if (tick.add(dt))
		{
			// poisen should not affect ability to damage enemy in another way
			boolean tmpDamaged = theEnemy.isDamaged();
			theEnemy.decreaseHp(damagePerTick);
			theEnemy.setDamaged(tmpDamaged);
		}
	}

	public TimeStore getTick() {
		return tick;
	}

	public void setTick(TimeStore tick) {
		this.tick = tick;
	}
}
