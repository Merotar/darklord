package darklord.rules;

import org.newdawn.slick.openal.Audio;

import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.game.Player;
import darklord.media.SoundLoader;

public class PlaySoundReaction implements Reaction
{
	
	public PlaySoundReaction()
	{
		
	}

	public void apply(GameEngine engine, Player thePlayer)
	{
		Darklord.sounds.pling.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
	}

}
