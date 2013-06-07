
/**
 * used to load sounds
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SoundLoader
{
	public Audio digSound, shot, explosion, mainTheme, chest;
	public float volumeMusic, volumeEffects;
	
	public SoundLoader()
	{
		volumeEffects = 0.3f;
		volumeMusic = 1.f;
		
		try
		{
			digSound = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("./sound/dig.ogg"));
			shot = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("./sound/shot.ogg"));
			explosion = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("./sound/explosion.ogg"));
			mainTheme = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("./sound/mainTheme.ogg"));
			chest = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("./sound/chest.ogg"));

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
