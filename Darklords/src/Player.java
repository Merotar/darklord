import java.io.FileInputStream;
import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

enum PlayerAbility
{
	NONE, TELEPORT
}

public class Player
{
	private float posX, posY, sizeX, sizeY;
	private float hp, maxHp;
	private Vector<Texture> texture;
	private float speed;
	private boolean moveUp, moveDown, moveLeft, moveRight;
	private boolean teleportUp, teleportDown, teleportLeft, teleportRight;
	private Timer attackBlockTimer;
	private float blockAttackSpeed;
	private Timer animationTimer;
	private float animationInterval;
	private Vector<PlayerAbility> abilities;
	private PlayerAbility activeAbility;
	private float teleportStep = 3.f;
	
	public Player()
	{
		posX = 3.f;
		posY = 3.f;
		sizeX = 0.8f;
		sizeY = 0.8f;
		speed = 0.2f;
		moveUp = moveDown = moveLeft = moveRight = false;
		teleportUp = teleportDown = teleportLeft = teleportRight = false;
		attackBlockTimer = new Timer();
		texture = new Vector<Texture>();
		animationTimer = new Timer();
		animationTimer.start();
		animationInterval = 500.f;
		blockAttackSpeed = 200.f;
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
		abilities = new Vector<PlayerAbility>();
		abilities.add(PlayerAbility.NONE);
		abilities.add(PlayerAbility.TELEPORT);
		activeAbility = abilities.get(1);
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
		return new Vector2f(posX, posY);
	}
	
	public void setPos(Vector2f v)
	{
		this.posX = v.getX();
		this.posY = v.getY();
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}
	
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
	}
	
	private void updatePosition()
	{
		if (moveUp)
		{
//			System.out.println("UP!");
			posY -= speed;
		}
		
		if (moveDown)
		{
			posY += speed;
//			System.out.println("posY: "+posY);
		}
		
		if (moveLeft)
		{
			posX -= speed;
		}
		
		if (moveRight)
		{
			posX += speed;
		}
	}
	
	public void draw()
	{
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
		
		GL11.glBegin(GL11.GL_QUADS);
//		float size = 1.0f;
		GL11.glTexCoord2f(0.f, 1.f);
		GL11.glVertex2f(0.f, sizeY);
		GL11.glTexCoord2f(1.f, 1.f);
		GL11.glVertex2f(sizeX, sizeY);
		GL11.glTexCoord2f(1.f, 0.f);
		GL11.glVertex2f(sizeX, 0.f);
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

	public float getSizeX() {
		return sizeX;
	}

	public void setSizeX(float sizeX) {
		this.sizeX = sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public void setSizeY(float sizeY) {
		this.sizeY = sizeY;
	}

	public boolean isTeleportUp() {
		return teleportUp;
	}

	public void setTeleportUp(boolean teleportUp) {
		this.teleportUp = teleportUp;
	}

	public boolean isTeleportDown() {
		return teleportDown;
	}

	public void setTeleportDown(boolean teleportDown) {
		this.teleportDown = teleportDown;
	}

	public boolean isTeleportLeft() {
		return teleportLeft;
	}

	public void setTeleportLeft(boolean teleportLeft) {
		this.teleportLeft = teleportLeft;
	}

	public boolean isTeleportRight() {
		return teleportRight;
	}

	public void setTeleportRight(boolean teleportRight) {
		this.teleportRight = teleportRight;
	}
}