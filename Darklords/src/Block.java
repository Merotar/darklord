

import java.io.FileInputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Block
{
	private boolean visible, solid;
	private Texture texture;
	private int id, type, hp, maxHp;
	private boolean destroyable;
	
	
	public Block()
	{
		setType(0);
	}
	
	public Block(int t)
	{
		setType(t);
	}


	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public boolean isSolid() {
		return solid;
	}


	public void setSolid(boolean solid) {
		this.solid = solid;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getHp() {
		return hp;
	}


	public void setHp(int life) {
		this.hp = life;
	}


	public int getMaxHp() {
		return maxHp;
	}


	public void setMaxHp(int maxLife) {
		this.maxHp = maxLife;
		this.hp = maxLife;
	}

	public void decreaseHp(int damage)
	{
		this.hp -= damage;
	}
	
	public void update()
	{
		if (this.getHp() <= 0)
		{
			this.setType(0);
		}
	}
	
	public int getType() {
		return type;
	}
	
	// @return: returns if block is destroyed
	// TODO: mit javadoc auseinandersetzen!
	public boolean attack()
	{
		if (this.type != 2)
		{
			boolean destroyed = false;
			this.decreaseHp(1);
			if (this.getHp() <= 0) destroyed=true;
			this.update();
			if (destroyed) return true;
		}
		
		return false;
	}


	public void setType(int type) {
		this.type = type;
		
		try{
			switch (type){
			case 0:
				this.texture = Darklords.textures.block.get(0); // TextureLoader.getTexture("PNG", new FileInputStream("../Darklords/img/background.png"));
				solid = false;
				destroyable = false;
				setMaxHp(1);
				break;
			case 1:
				this.texture = Darklords.textures.block.get(1); //TextureLoader.getTexture("PNG", new FileInputStream("../Darklords/img/block_brown01.png"));
				solid = true;
				destroyable = true;
				setMaxHp(3);
				break;
			case 2:
				this.texture = Darklords.textures.block.get(2); //TextureLoader.getTexture("PNG", new FileInputStream("../Darklords/img/block_red01.png"));
				solid = true;
				destroyable = true;
				setMaxHp(5);
				break;
			default:
				texture = null;
				solid = true;
				setMaxHp(1);
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
			float size = 1.0f;//Level.scale;
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

	public boolean isDestroyable() {
		return destroyable;
	}

	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}
}