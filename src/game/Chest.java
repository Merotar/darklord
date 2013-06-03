package game;

import java.io.Serializable;

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
		Darklords.sounds.chest.playAsSoundEffect(1.f, Darklords.sounds.volumeEffects, false);
		player.setEnergyRedMax(player.getEnergyRedMax()+1);
	}
	
	public void draw()
	{
		Darklords.sprites01.begin();
		appearance.draw();
		Darklords.sprites01.end();
	}
}
