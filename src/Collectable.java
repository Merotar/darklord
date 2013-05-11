import java.io.FileInputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;



public class Collectable
{
	private Vector2f pos;
	private Texture texture;
	private int type;
	private float size;
	
	public Collectable()
	{
		this.setType(1);
		this.pos = new Vector2f(0.f, 0.f);
		size = 0.5f;
	}
	
	public Collectable(int t)
	{
		this.setType(t);
		this.pos = new Vector2f(0.f, 0.f);
		size = 0.5f;
	}
	
	public Collectable(int t, float x, float y)
	{
		this.setType(t);
		this.pos = new Vector2f(x, y);
		size = 0.5f;
	}
	
//	public Collectable(float x, float y)
//	{
//		this.setType(1);
//		this.setPos(x, y);
//	}
	
	public void setPos(float x, float y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	public void setX(float x)
	{
		this.pos.setX(x);
	}
	
	public void setY(float y)
	{
		this.pos.setY(y);
	}
	
	public float getX()
	{
		return this.pos.getX();
	}
	
	public float getY()
	{
		return this.pos.getY();
	}
	
	public void setType(int type) {
		this.type = type;
		
		try{
			switch (type){
			case 0:
				this.texture = TextureLoader.getTexture("PNG", new FileInputStream("./img/background.png"));
				break;
			case 1:
				this.texture = TextureLoader.getTexture("PNG", new FileInputStream("./img/block_brown01.png"));
				break;
			case 2:
				this.texture = TextureLoader.getTexture("PNG", new FileInputStream("./img/block_red01.png"));
				break;
			default:
				texture = null;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void draw()
	{
		if (texture != null)
		{
			GL11.glEnable(GL11.GL_TEXTURE_2D);  
			Color.white.bind();
			texture.bind();
			GL11.glBegin(GL11.GL_QUADS);
//			float size = 0.5f;//Level.scale;
			GL11.glTexCoord2f(0.f, 1.f);
			GL11.glVertex2f(0.f, size);
			GL11.glTexCoord2f(1.f, 1.f);
			GL11.glVertex2f(size, size);
			GL11.glTexCoord2f(1.f, 0.f);
			GL11.glVertex2f(size, 0.f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(0.f, 0.f);
			GL11.glEnd();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}
}