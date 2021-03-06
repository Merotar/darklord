package darklord.enemies;

import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.media.Drawable;
import darklord.media.Sprite;


/**
 * describes a static enemy
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class StaticEnemy extends Enemy
{
	private Drawable appearance;
	
	public StaticEnemy()
	{
		super();
		appearance = new Sprite();
		((Sprite)appearance).setTextureRegion(0*128, 6*128, 128, 128);
	}
	
	public StaticEnemy(float x, float y)
	{
		super(x, y, 0);
		appearance = new Sprite();
		((Sprite)appearance).setTextureRegion(0*128, 6*128, 128, 128);
	}
	
	public void draw()
	{
		Darklord.textures.begin();
		appearance.draw();
		Darklord.textures.end();
	}
	
	public void update(float dt, GameEngine engine)
	{
		super.update(dt, engine);
	}

	public StaticEnemy createNew()
	{
		return new StaticEnemy();
	}
}