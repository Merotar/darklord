import java.io.FileInputStream;
import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;


public class SelectBox
{
	private Vector<Texture> texture;
	private boolean visible;
	private Vector2f pos;
	private Timer animationTimer;
	private float animationInterval;
	
	public SelectBox()
	{
		texture = new Vector<Texture>();
		visible = false;
		pos = new Vector2f(0.f, 0.f);
		animationTimer = new Timer();
		animationTimer.start();
		animationInterval = 100.f;
		
		try
		{
			texture.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/select_block01.png")));
			texture.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/select_block02.png")));
			texture.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/select_block03.png")));
			texture.add(TextureLoader.getTexture("PNG", new FileInputStream("./img/select_block04.png")));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void show()
	{
		this.visible = true;
//		animationTimer.start();
	}
	
	public void hide()
	{
		this.visible = false;
//		animationTimer.stop();
	}
	
	public void setPos(float x, float y)
	{
		pos.setX(x);
		pos.setY(y);
	}
	
	public float getX()
	{
		return pos.getX();
	}
	
	public float getY()
	{
		return pos.getY();
	}
	
	public void draw()
	{
		if (!visible) return;
		
		int textureNum = (int)Math.floor(animationTimer.getTimeDelta() / animationInterval);
//		System.out.println("dt: "+(animationTimer.getTimeDelta()));
		if (textureNum >= texture.size())
		{
//			System.out.println("Reset timer!");
			textureNum = 0;
			animationTimer.reset();
		}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D); 
		Color.white.bind();
		texture.get(textureNum).bind();
		
		float x1, x2, y1, y2;
		float size = 1.0f;
		x1 = 0.f; x2 = size; y1 = 0.f; y2 = size;
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.f, 1.0f);
		GL11.glVertex2f(x1, y2);
		GL11.glTexCoord2f(1.f, 1.f);
		GL11.glVertex2f(x2, y2);
		GL11.glTexCoord2f(1.f, 0.f);
		GL11.glVertex2f(x2, y1);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(x1, y1);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
}