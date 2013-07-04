package darklord.collectables;


import java.io.Serializable;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import darklord.game.Collidable;
import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.math.Vector2f;
import darklord.media.Drawable;
import darklord.media.Sprite;
import darklord.media.TextureRegion;

/**
 * Describes objects which are collectable by the player
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public abstract class Collectable extends Collidable implements Serializable
{
//	private Vector2f pos;
//	private Texture texture;
	private CollectableType type;
//	private float size;
	protected Drawable appearance;
	
	public Collectable()
	{
		this(0.f, 0.f);
	}
	
//	public Collectable(CollectableType t)
//	{
//		this();
//		this.setType(t);
//	}
	
	/**
	 * 
	 * @param t type of the collectable
	 * @param x x position
	 * @param y y position
	 */
	public Collectable(float x, float y)
	{
		appearance = new Sprite();
		
		setPosX(x);
		setPosY(y);
		setSizeX(0.8f);
		setSizeY(0.8f);
	}
	
//	public void setType(CollectableType type) {
//		this.type = type;
//		
//		try{
//			switch (type){
//			case NONE:		// background
//				((Sprite)appearance).setTextureRegion(0*128, 0*128, 128, 128);
//				break;
//			case BLOCK_ROCK:		// rock
//				((Sprite)appearance).setTextureRegion(1*128, 0*128, 128, 128);
//				break;
//			case BLOCK_BROWN:		// dirt
//				((Sprite)appearance).setTextureRegion(2*128, 0*128, 128, 128);
//				break;
//			case BLOCK_RED:		// red
//				((Sprite)appearance).setTextureRegion(4*128, 3*128, 128, 128);
//				break;
//			case BLOCK_BLUE:		// blue
//				((Sprite)appearance).setTextureRegion(5*128, 3*128, 128, 128);
//				break;
//			case BLOCK_GREEN:		// green
//				((Sprite)appearance).setTextureRegion(3*128, 3*128, 128, 128);
//				break;
//			case BLOCK_YELLOW:		// green
//				((Sprite)appearance).setTextureRegion(2*128, 3*128, 128, 128);
//				break;
//			case DIAMOND:		// diamond
//				((Sprite)appearance).setTextureRegion(3*128, 1*128, 128, 128);
//				break;
//			case ORB_BLUE:
//				((Sprite)appearance).setTextureRegion(1*128, 4*128, 128, 128);
//				break;
//			case ORB_GREEN:
//				((Sprite)appearance).setTextureRegion(2*128, 4*128, 128, 128);
//				break;
//			case ORB_YELLOW:
//				((Sprite)appearance).setTextureRegion(3*128, 4*128, 128, 128);
//				break;
//			default:
//				((Sprite)appearance).setTextureRegion(0*128, 10*128, 128, 128);
//				break;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(0);
//		}
//	}
	
	public abstract void onCollect(GameEngine engine);
	
	public abstract Collectable createNew();
	
//	public boolean isInside(Vector2f thePosition)
//	{		
//		if (thePosition.getX() > getPosition().getX()+getSizeX()) return false;
//		if (thePosition.getX() < getPosition().getX()) return false;
//		if (thePosition.getY() > getPosition().getY()+getSizeY()) return false;
//		if (thePosition.getY() < getPosition().getY()) return false;
//		
//		return true;
//	}
	
	public void draw()
	{
		Darklord.textures.begin();
		appearance.draw(0, 0, getSizeX(), getSizeY());
		Darklord.textures.end();
	}

	public CollectableType getType() {
		return type;
	}
}