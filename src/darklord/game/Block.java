package darklord.game;


import java.io.Serializable;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import darklord.media.Drawable;
import darklord.media.Sprite;
import darklord.media.TextureRegion;

/**
 * Description of a block
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Block implements Serializable
{
	private boolean visible, solid, transparent;
	private int id, hp, maxHp;
	private boolean destroyable;
//	private TextureRegion textureRegion;
	private Drawable background;
	private Drawable appearance;
	private static Drawable crack = new Sprite();
	private BlockType type;
	private Buildable overlay;
	private float fogValue;
	
	
	public Block()
	{
		this(BlockType.BLOCK_NONE);
	}
	
	public Block(BlockType t)
	{
//		textureRegion = new TextureRegion();
		overlay = null;
		background = null;
		appearance = new Sprite();
		((Sprite)crack).setTextureRegion(new TextureRegion(5, 1, 128));
		setType(t);
		fogValue = 1.f;
	}
	
	public void drawFog(int x, int y)
	{
		GL11.glBegin(GL11.GL_QUADS);
		
		float posX = 0.f;
		float posY = 0.f;
		float sizeX = 1.f;
		float sizeY = 1.f;
				
		GL11.glColor4f(0.f, 0.f, 0.f, fogValue);

//		GL11.glColor4f(0.f, 0.f, 0.f, getFogAt(x, y+1));
		GL11.glVertex2f(posX, posY+sizeY);
//		GL11.glColor4f(0.f, 0.f, 0.f, getFogAt(x+1, y+1));
		GL11.glVertex2f(posX+sizeX, posY+sizeY);
//		GL11.glColor4f(0.f, 0.f, 0.f, getFogAt(x+1, y));
		GL11.glVertex2f(posX+sizeX, posY);
//		GL11.glColor4f(0.f, 0.f, 0.f, getFogAt(x, y));
		GL11.glVertex2f(posX, posY);
		GL11.glEnd();
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
	
	/**
	 * sets block to empty block if HP < 0
	 */
	public void update()
	{
		
		if (this.getHp() <= 0)
		{
			this.setType(BlockType.BLOCK_NONE);
		}
	}
	
	public BlockType getType() {
		return type;
	}
	
	public void setOverlay(Buildable theOverlay)
	{
		overlay = theOverlay;
		setDestroyable(false);
	}
	
	public void removeOverlay()
	{
		overlay = null;
		setType(getType());
	}
	
	/**
	 * @return returns true if object is destoyed
	 */
	public boolean attack()
	{
		if (this.destroyable)
		{
			boolean destroyed = false;
			this.decreaseHp(1);
			Darklord.sounds.digSound.playAsSoundEffect(1.f, 1.f, false);
			if (this.getHp() <= 0) destroyed=true;
			this.update();
			if (destroyed) return true;
		}
		
		return false;
	}
	
	public CollectableType dropOnDestroy()
	{
		switch (getType())
		{
		case BLOCK_NONE:
			return CollectableType.NONE;
		case BLOCK_ROCK:
			return CollectableType.NONE;
		case BLOCK_DIRT:
			return CollectableType.NONE;
		case BLOCK_RED:
			return CollectableType.BLOCK_RED;
		case BLOCK_BLUE:
			return CollectableType.BLOCK_BLUE;
		case BLOCK_GREEN:
			return CollectableType.BLOCK_GREEN;
		case BLOCK_YELLOW:
			return CollectableType.BLOCK_YELLOW;
		default:
			return CollectableType.NONE;
		}
	}


	public void setType(BlockType type) {
		this.type = type;
		
		try{
			switch (type){
			case BLOCK_NONE:		// background
				solid = false;
				destroyable = false;
				transparent = false;
				background = new Sprite();
				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(7*128, 7*128, 128, 128);
				setMaxHp(1);
				break;
			case BLOCK_ROCK:		// rock
				solid = true;
				destroyable = false;
				transparent = false;
//				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(1*128, 0*128, 128, 128);
				setMaxHp(1);
				break;
			case BLOCK_DIRT:		// dirt
				solid = true;
				destroyable = true;
				transparent = false;
//				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(2*128, 0*128, 128, 128);
				setMaxHp(2);
				break;
			case BLOCK_RED:		// red
				solid = true;
				destroyable = true;
				transparent = false;
//				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(3*128, 0*128, 128, 128);
				setMaxHp(2);
				break;
			case BLOCK_BLUE:		// blue
				solid = true;
				destroyable = true;
				transparent = false;
//				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(4*128, 0*128, 128, 128);
				setMaxHp(2);
				break;
			case BLOCK_GREEN:		// green
				solid = true;
				destroyable = true;
				transparent = false;
//				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(5*128, 0*128, 128, 128);
				setMaxHp(2);
				break;
			case BLOCK_YELLOW:		// free
				solid = true;
				destroyable = true;
				transparent = false;
//				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(0*128, 1*128, 128, 128);
				setMaxHp(2);
				break;
			case BLOCK_PLANTS:		// plants
				solid = true;
				destroyable = false;
				transparent = false;
//				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(7*128, 0*128, 128, 128);
				setMaxHp(1);
				break;
			case BLOCK_GLASS:		// glass
				solid = true;
				destroyable = true;
				transparent = true;
//				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(6*128, 1*128, 128, 128);
				setMaxHp(2);
				break;
			case BLOCK_WHITE:		// glass
				solid = true;
				destroyable = true;
				transparent = false;
//				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(6*128, 0*128, 128, 128);
				setMaxHp(2);
				break;
			default:
//				texture = null;
				solid = true;
				destroyable = false;
				transparent = false;
//				((Sprite)background).setTextureRegion(0*128, 0*128, 128, 128);
				((Sprite)appearance).setTextureRegion(0*128, 10*128, 128, 128);
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
		if (background != null) background.draw();
		appearance.draw();
		
		if (this.hp < this.maxHp)
		{
			crack.draw();
		}
		
		if (overlay != null)
		{
			overlay.draw();
		}
		
//		Darklords.sprites01.draw(getTextureRegion());
////		if (texture != null)
//		{
//			GL11.glEnable(GL11.GL_TEXTURE_2D);  
//			Color.white.bind();
//			
//			Darklords.textures.block.get(type).bind();
//			
////			texture.bind();
//			GL11.glBegin(GL11.GL_QUADS);
//			float size = 1.0f;//Level.scale;
//			GL11.glTexCoord2f(0.f, 1.f);
//			GL11.glVertex2f(0.f, size);
//			GL11.glTexCoord2f(1.f, 1.f);
//			GL11.glVertex2f(size, size);
//			GL11.glTexCoord2f(1.f, 0.f);
//			GL11.glVertex2f(size, 0.f);
//			GL11.glTexCoord2f(0f, 0f);
//			GL11.glVertex2f(0.f, 0.f);
//			GL11.glEnd();
//			GL11.glDisable(GL11.GL_TEXTURE_2D);
//			
//			if (this.hp < this.maxHp)
//			{
//				GL11.glEnable(GL11.GL_TEXTURE_2D);  
//				Color.white.bind();
//				
//				Darklords.textures.crack.bind();
//				
////				texture.bind();
//				GL11.glBegin(GL11.GL_QUADS);
//				size = 1.0f;//Level.scale;
//				GL11.glTexCoord2f(0.f, 1.f);
//				GL11.glVertex2f(0.f, size);
//				GL11.glTexCoord2f(1.f, 1.f);
//				GL11.glVertex2f(size, size);
//				GL11.glTexCoord2f(1.f, 0.f);
//				GL11.glVertex2f(size, 0.f);
//				GL11.glTexCoord2f(0f, 0f);
//				GL11.glVertex2f(0.f, 0.f);
//				GL11.glEnd();
//				GL11.glDisable(GL11.GL_TEXTURE_2D);
//			}
//		}
	}

	public boolean isDestroyable() {
		return destroyable;
	}

	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}
	
	public void print()
	{
		System.out.println("Block type: "+type);
	}

	public Sprite getOverlay() {
		return overlay;
	}

	public Drawable getBackground() {
		return background;
	}

	public void setBackground(Drawable background) {
		this.background = background;
	}

	public boolean isTransparent() {
		return transparent;
	}

	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	public float getFogValue() {
		return fogValue;
	}

	public void setFogValue(float fogValue) {
		this.fogValue = fogValue;
	}
}