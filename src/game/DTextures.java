package game;
import java.io.FileInputStream;
import java.util.Vector;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * class which loads and stores the used textures
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class DTextures
{
	public Vector<Texture> player;
	public Vector<Texture> block;
	public Texture crack;
	public Texture projectile01;
	public Vector<Texture> enemies;
	
	public DTextures()
	{
		// player textures
		player = new Vector<Texture>();
		enemies = new Vector<Texture>();
		
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
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_rock01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_brown01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_red01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_blue01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_green01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_door_red01.png")));
			crack = TextureLoader.getTexture("PNG", new FileInputStream("./img/crack01.png"));
			projectile01 = TextureLoader.getTexture("PNG", new FileInputStream("./img/projectile01_01.png"));
			enemies.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/enemy01_01.png")));
			enemies.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/enemy02_01.png")));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	}
}