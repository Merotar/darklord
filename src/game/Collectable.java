package game;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

/**
 * Describes objects which are collectable by the player
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Collectable extends Collidable
{
//	private Vector2f pos;
//	private Texture texture;
	private CollectableType type;
//	private float size;
	
	public Collectable()
	{
		this.setType(CollectableType.BLOCK_BROWN);
		setPosX(0.f);
		setPosY(0.f);
		setSizeX(0.5f);
		setSizeY(0.5f);
	}
	
	public Collectable(CollectableType t)
	{
		this();
		this.setType(t);
	}
	
	/**
	 * 
	 * @param t type of the collectable
	 * @param x x position
	 * @param y y position
	 */
	public Collectable(CollectableType t, float x, float y)
	{
		this(t);
		setPosX(x);
		setPosY(y);
	}
	
//	public Collectable(float x, float y)
//	{
//		this.setType(1);
//		this.setPos(x, y);
//	}
	
//	public void setPos(float x, float y)
//	{
//		this.setX(x);
//		this.setY(y);
//	}
	
//	public void setX(float x)
//	{
//		this.pos.setX(x);
//	}
//	
//	public void setY(float y)
//	{
//		this.pos.setY(y);
//	}
//	
//	public float getX()
//	{
//		return this.pos.getX();
//	}
//	
//	public float getY()
//	{
//		return this.pos.getY();
//	}
	
	public void setType(CollectableType type) {
		this.type = type;
		
//		try{
//			switch (type){
//			case NONE:
//				this.texture = TextureLoader.getTexture("PNG", new FileInputStream("../Darklords/img/background.png"));
//				break;
//			case BLOCK_BROWN:
//				this.texture = TextureLoader.getTexture("PNG", new FileInputStream("../Darklords/img/block_brown01.png"));
//				break;
//			case BLOCK_RED:
//				this.texture = TextureLoader.getTexture("PNG", new FileInputStream("../Darklords/img/block_red01.png"));
//				break;
//			case ABILITY_TELEPORT:
//				this.texture = TextureLoader.getTexture("PNG", new FileInputStream("../Darklords/img/ability_teleport01.png"));
//				break;
//			case ABILITY_DIGGING:
//				this.texture = TextureLoader.getTexture("PNG", new FileInputStream("../Darklords/img/ability_digging01.png"));
//				break;
//			default:
//				texture = null;
//				break;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(0);
//		}
	}
	

	
	public void draw()
	{
//		if (texture != null)
		{
			GL11.glEnable(GL11.GL_TEXTURE_2D);  
			Color.white.bind();
			
			Darklords.textures.block.get(type.id).bind();
//			texture.bind();
			GL11.glBegin(GL11.GL_QUADS);
//			float size = 0.5f;//Level.scale;
			GL11.glTexCoord2f(0.f, 1.f);
			GL11.glVertex2f(0.f, getSizeY());
			GL11.glTexCoord2f(1.f, 1.f);
			GL11.glVertex2f(getSizeX(), getSizeY());
			GL11.glTexCoord2f(1.f, 0.f);
			GL11.glVertex2f(getSizeX(), 0.f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(0.f, 0.f);
			GL11.glEnd();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
	}

//	public float getSize() {
//		return size;
//	}
//
//	public void setSize(float size) {
//		this.size = size;
//	}

	public CollectableType getType() {
		return type;
	}

//	public Vector2f getPos() {
//		return pos;
//	}
//
//	public void setPos(Vector2f pos) {
//		this.pos = pos;
//	}
}