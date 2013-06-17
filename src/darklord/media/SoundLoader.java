package darklord.media;

/**
 * used to load sounds
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import darklord.game.Print;

public class SoundLoader
{
	public Audio digSound, shot, explosion, mainTheme, chest, build, laser, electricity;
	public float volumeMusic, volumeEffects;
	
	public SoundLoader()
	{
		volumeEffects = 0.3f;
		volumeMusic = 1.f;
		
		try
		{
			digSound = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("darklord/sound/dig.ogg"));
			shot = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("darklord/sound/shot.ogg"));
			explosion = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("darklord/sound/explosion.ogg"));
			mainTheme = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("darklord/sound/mainTheme.ogg"));
			chest = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("darklord/sound/chest.ogg"));
			build = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("darklord/sound/build.ogg"));
			laser = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("darklord/sound/laser.ogg"));
			electricity = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("darklord/sound/electricity.ogg"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
