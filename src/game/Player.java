/*
 * describes the player character
 * 
 * @author Sebastian Artz
 * 
 */

package game;
import java.io.FileInputStream;
import java.util.Vector;
//import game.CollectableType;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

enum PlayerAbility
{
	TELEPORT(1), SHOOT(2), DIG(5);
			
	int id;
	
	PlayerAbility(int theId)
	{
		id = theId;
	}
}

public class Player extends Collidable
{
//	private float posX, posY, sizeX, sizeY;
	private float hp, maxHp;
	private Vector<Texture> texture;
	private float speed;
	private boolean moveUp, moveDown, moveLeft, moveRight;
	private boolean teleportUp, teleportDown, teleportLeft, teleportRight;
	private Timer attackBlockTimer;
	private static float blockAttackSpeed = 200.f;
	private Timer animationTimer, invulnerableOnContactTimer;
	private static float animationInterval = 500.f;
	private static float invulnerableOnContact = 1000.f;
	private int[] abilities;
	private PlayerAbility activeAbility;
	private float teleportStep = 2.f;
	private int maxAbilities;
	private int blocksRed, blocksBlue, blocksGreen;
	
	public Player()
	{
		setPosX(1.f);
		setPosX(4.f);
		setSizeX(0.75f);
		setSizeY(0.75f);
		speed = 0.2f;
		blocksRed = blocksBlue = blocksGreen = 0;
		moveUp = moveDown = moveLeft = moveRight = false;
		teleportUp = teleportDown = teleportLeft = teleportRight = false;
		hp = maxHp = 10.f;
		attackBlockTimer = new Timer();
		invulnerableOnContactTimer = new Timer();
		texture = new Vector<Texture>();
		animationTimer = new Timer();
		animationTimer.start();
//		animationInterval = 500.f;
//		blockAttackSpeed = 200.f;
		try
		{
			texture.add(Darklords.textures.player.get(0));
			texture.add(Darklords.textures.player.get(1));
			texture.add(Darklords.textures.player.get(2));
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		// set start abilities
		maxAbilities = 6;
		abilities = new int[maxAbilities];
		abilities[0] = 3;	// teleport
		abilities[1] = 0;
		abilities[2] = 0;
		abilities[3] = 0;
		abilities[4] = 0;
		abilities[5] = -1;	// digging
		activeAbility = PlayerAbility.DIG;
	}
	
	public Player(int thePosX, int thePosY)
	{
		this();
		setPosX(thePosX);
		setPosY(thePosY);
	}

	public Player(Player orig)
	{
		setPosX(orig.getPosX());
		setPosY(orig.getPosY());
		setSizeX(orig.getSizeX());
		setSizeY(orig.getSizeY());
		hp = orig.hp;
		maxHp = orig.maxHp;
		speed = 0.2f;
		blocksRed = orig.blocksRed;
		blocksBlue = orig.blocksBlue;
		blocksGreen = orig.blocksGreen;
		maxAbilities = orig.maxAbilities;
		abilities = new int[maxAbilities];
		for (int i=0;i<maxAbilities;i++)
		{
			abilities[i] = orig.abilities[i];
		}
		activeAbility = orig.activeAbility;
		teleportStep = orig.teleportStep;

		moveUp = moveDown = moveLeft = moveRight = false;
		teleportUp = teleportDown = teleportLeft = teleportRight = false;
		attackBlockTimer = new Timer();
		texture = new Vector<Texture>();
		animationTimer = new Timer();
//		try
//		{
//			texture.add(Darklords.textures.player.get(0));
//			texture.add(Darklords.textures.player.get(1));
//			texture.add(Darklords.textures.player.get(2));
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//			System.exit(0);
//		}
		
		try
		{
			texture.add(Darklords.textures.player.get(0));
			texture.add(Darklords.textures.player.get(1));
			texture.add(Darklords.textures.player.get(2));
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public PlayerAbility getActiveAbility()
	{
		return activeAbility;
	}
	
	public void setActiveAbility(PlayerAbility a)
	{
		activeAbility = a;
	}
	
	public void switchActiveAbility()
	{
		if (activeAbility == PlayerAbility.DIG)
		{
			this.setActiveAbility(PlayerAbility.SHOOT);
			return;
		}
		if (activeAbility == PlayerAbility.SHOOT)
		{
			this.setActiveAbility(PlayerAbility.DIG);
			return;
		}
	}
	
	public void addBlockRed()
	{
		blocksRed++;
	}
	
	public int getBlockRed()
	{
		return blocksRed;
	}
	
	public boolean decreaseBlockRed()
	{
		if (blocksRed > 0)
		{
			blocksRed--;
			return true;
		}
		return false;
	}
	
	// TODO: blocksBlue, blocksGreen
	
	public int getDiggingNum()
	{
		return abilities[5];
	}
	
	public float getHp() {
		return hp;
	}

	public void setHp(float hp) {
		this.hp = hp;
	}

	public float getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(float maxHp) {
		this.maxHp = maxHp;
	}
	
	public Vector2f getPos()
	{
		return new Vector2f(getPosX(), getPosY());
	}
	
	public void setPos(Vector2f v)
	{
		setPosX(v.getX());
		setPosY(v.getY());
	}

//	public float getPosX() {
//		return super.getPosX();
//	}
//
//	public void setPosX(float posX) {
//		setPosX(posX);
//	}
//
//	public float getPosY() {
//		return super.getPosY();
//	}
//
//	public void setPosY(float posY) {
//		setPosY(posY);
//	}
	
	public boolean moveRight()
	{
		return moveRight;
	}
	
	public boolean moveUp()
	{
		return moveUp;
	}
	
	public boolean moveDown()
	{
		return moveDown;
	}
	
	public boolean moveLeft()
	{
		return moveLeft;
	}
	
	public void startUp()
	{
		moveUp = true;
		moveDown = false;
	}
	
	public void startDown()
	{
		moveDown = true;
		moveUp = false;
	}
	
	public void startLeft()
	{
		moveLeft = true;
		moveRight = false;
	}
	
	public void startRight()
	{
		moveRight = true;
		moveLeft = false;
	}
	
	public void stopUp()
	{
		moveUp = false;
	}
	
	public void stopDown()
	{
		moveDown = false;
	}
	
	public void stopLeft()
	{
		moveLeft = false;
	}
	
	public void stopRight()
	{
		moveRight = false;
	}
	
	public boolean isActiveAbility(PlayerAbility a)
	{
		return (a == this.activeAbility);
	}
	
	public void startAttackBlockTimer()
	{
		attackBlockTimer.start();
//		System.out.println(attackBlockTimer.getTimeDelta());
	}
	
	public void startInvulnerableOnContactTimer()
	{
		invulnerableOnContactTimer.start();
	}
	
	public boolean isInvulnerable()
	{
		return invulnerableOnContactTimer.running();
	}
	
	public void stopInvulnerableOnContactTimer()
	{
		invulnerableOnContactTimer.stop();
		invulnerableOnContactTimer.reset();
	}
	
	public boolean canAttackBlock()
	{
		return !attackBlockTimer.running();
	}
	
	public void stopAttackBlockTimer()
	{
		attackBlockTimer.stop();
		attackBlockTimer.reset();
	}
	
	public void update()
	{
		this.updatePosition();
//		System.out.println("Player: ("+this.getPosX()+", "+this.getPosY()+")");
		
		if (attackBlockTimer.getTimeDelta() > blockAttackSpeed) this.stopAttackBlockTimer();
		if (invulnerableOnContactTimer.getTimeDelta() > invulnerableOnContact) this.stopInvulnerableOnContactTimer();
	}
	
	private void updatePosition()
	{
		if (moveUp)
		{
//			System.out.println("UP!");
			setPosY(getPosY() - speed);
		}
		
		if (moveDown)
		{
			setPosY(getPosY() + speed);
//			System.out.println("posY: "+posY);
		}
		
		if (moveLeft)
		{
			setPosX(getPosX() - speed);
		}
		
		if (moveRight)
		{
			setPosX(getPosX() + speed);
		}
	}
	
	public void draw()
	{
		int textureNum = (int)Math.floor(animationTimer.getTimeDelta() / animationInterval);
//		System.out.println("dt: "+(animationTimer.getTimeDelta()));
		if (textureNum >= Darklords.textures.player.size())
		{
//			System.out.println("Reset timer!");
			textureNum = 0;
			animationTimer.reset();
		}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);  
		Color.white.bind();

		Darklords.textures.player.get(textureNum).bind();
		
		GL11.glBegin(GL11.GL_QUADS);
//		float size = 1.0f;
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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getTeleportStep() {
		return teleportStep;
	}

	public void setTeleportStep(float teleportStep) {
		this.teleportStep = teleportStep;
	}

//	public float getSizeX() {
//		return sizeX;
//	}
//
//	public void setSizeX(float sizeX) {
//		this.sizeX = sizeX;
//	}
//	
//	public Vector2f getSize() {
//		return new Vector2f(sizeX, sizeY);
//	}
//
//	public float getSizeY() {
//		return sizeY;
//	}
//
//	public void setSizeY(float sizeY) {
//		this.sizeY = sizeY;
//	}

	public boolean isTeleportUp() {
		return teleportUp;
	}

	public void setTeleportUp(boolean teleportUp)
	{
		if ((abilities[0] > 0) && teleportUp)
		{
			this.teleportUp = true;
			this.abilities[0]--;
		} else
		{
			this.teleportUp = false;
		}

	}

	public boolean isTeleportDown()
	{
		return teleportDown;
	}

	public void setTeleportDown(boolean teleportDown)
	{
		if ((abilities[0] > 0) && teleportDown)
		{
			this.teleportDown = true;
			this.abilities[0]--;
		} else
		{
			this.teleportDown = false;
		}
	}

	public boolean isTeleportLeft()
	{
		return teleportLeft;
	}

	public void setTeleportLeft(boolean teleportLeft)
	{
		if ((abilities[0] > 0) && teleportLeft)
		{
			this.teleportLeft = true;
			this.abilities[0]--;
		} else
		{
			this.teleportLeft = false;
		}
	}

	public boolean isTeleportRight() {
		return teleportRight;
	}

	public void setTeleportRight(boolean teleportRight)
	{
		if ((abilities[0] > 0) && teleportRight)
		{
			this.teleportRight = true;
			this.abilities[0]--;
		} else
		{
			this.teleportRight = false;
		}
	}

	public void addItem(CollectableType type)
	{
		switch (type)
		{
		case BLOCK_BROWN:
			// TODO: pick up block?
			break;
		case ABILITY_TELEPORT:
			abilities[0]++;
			break;
		case ABILITY_DIGGING:
			abilities[5]++;
			break;
		default:
			break;
		}
	}

	public void decreaseDiggingCount()
	{
		if (abilities[5] > 0) abilities[5]--;
	}

	/**
	 * 
	 * @param f
	 * @return	returns true if enemy has no hp left
	 */
	public boolean decreaseHp(float f)
	{
		hp -= f;
		if (hp <= 0) return true;
		return false;
	}
}