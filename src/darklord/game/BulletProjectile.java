package darklord.game;

import darklord.math.Vector2f;

public class BulletProjectile extends Projectile
{
	public BulletProjectile(Vector2f pos, Vector2f dir, int type)
	{
		super(pos, dir, new Vector2f(0.3f, 0.3f), type);
	}
}
