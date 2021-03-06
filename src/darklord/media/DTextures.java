package darklord.media;

import java.io.FileInputStream;
import java.util.Vector;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * class which loads and stores the used textures, no longer used
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * @deprecated
 * 
 */
public class DTextures
{
	public Vector<Texture> player;
	public Vector<Texture> block;
	public Texture crack;
	public Vector<Texture> projectile;
	public Vector<Texture> enemies;
	public Vector<Texture> shadows;
	public Texture panelBottom;
	
	public DTextures()
	{
		// player textures
		player = new Vector<Texture>();
		enemies = new Vector<Texture>();
		projectile = new Vector<Texture>();
		shadows = new Vector<Texture>();
		
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
		
		// block & other textures
		block = new Vector<Texture>();
		
		try{
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/background.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_rock01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_brown01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_red01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_blue01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_green01.png")));
//			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_door_red01.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_ice.png")));
			block.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/block_plants.png")));
			
			shadows.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/glow_red.png")));
			shadows.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/glow_blue.png")));
			shadows.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/glow_green.png")));
			
			crack = TextureLoader.getTexture("PNG", new FileInputStream("./img/crack01.png"));
			
			panelBottom = TextureLoader.getTexture("PNG", new FileInputStream("./img/panel_bottom.png"));
			
			projectile.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/projectile01_01.png")));
			projectile.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/projectile02_01.png")));
			projectile.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/projectile03_01.png")));
			projectile.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/projectile04_01.png")));
			
			enemies.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/enemy01_01.png")));
			enemies.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/enemy02_01.png")));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	}
}