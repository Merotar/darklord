import java.io.FileInputStream;
import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class DTextures
{
	public Vector<Texture> player;
	public Vector<Texture> block;
	
	public DTextures()
	{
		// player textures
		player = new Vector<Texture>();
		
		try
		{
			player.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/player01.png")));
			player.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/player02.png")));
			player.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/player03.png")));
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		// block textures
		block = new Vector<Texture>();
		
		try{
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/background.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_brown01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_red01.png")));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}