package darklord.game;


import java.io.Serializable;

import darklord.media.Drawable;
import darklord.media.Sprite;

/**
 * description of a chest
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
public class Chest extends Collidable implements Serializable
{
	Drawable appearance;
	
	public Chest()
	{
		this(0.f, 0.f);
		((Sprite)appearance).setTextureRegion(4*128, 1*128, 1*128, 1*128);
	}
	
	public Chest (float x, float y)
	{
		super();
		setPosX(x);
		setPosY(y);
		setSizeX(1.f);
		setSizeY(1.f);
		appearance = new Sprite();
		((Sprite)appearance).setTextureRegion(1*128, 1*128, 1*128, 1*128);
	}
	
	public void onCollision(Player player)
	{
		// show content of chest in upper area of screen
		Darklord.sounds.pling.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
		player.setEnergyRedMax(player.getEnergyRedMax()+1);
	}
	
	public void draw()
	{
		Darklord.sprites01.begin();
		appearance.draw();
		Darklord.sprites01.end();
	}
}
