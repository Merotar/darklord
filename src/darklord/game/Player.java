package darklord.game;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;
//import game.CollectableType;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import darklord.enemies.Enemy;
import darklord.media.Animation;
import darklord.media.Drawable;
import darklord.media.Sprite;
import darklord.media.TextureRegion;
import darklord.math.Vector2f;

/**
 * enum to descrobe the players abilities
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
enum PlayerAbility implements Serializable
{
	TELEPORT(1), SHOOT(2), DIG(5);
			
	int id;
	
	PlayerAbility(int theId)
	{
		id = theId;
	}
}

/**
 * describes the player character
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Player extends Collidable implements Serializable
{
//	private float posX, posY, sizeX, sizeY;
	private int xp, maxXp;
	private float hp, maxHp;
//	private Vector<Texture> texture;
	private float speed, speedMultiplier;
	private boolean moveUp, moveDown, moveLeft, moveRight;
	private boolean teleportUp, teleportDown, teleportLeft, teleportRight;
	private Timer attackBlockTimer;
	private static float blockAttackSpeed = 200.f;
	private Timer invulnerableOnContactTimer;
//	private static float animationInterval = 500.f;
	private static float invulnerableOnContact = 500.f;
	private int[] abilities;
	private PlayerAbility activeAbility;
	private float teleportStep = 2.f;
	private int maxAbilities;
	private int crystalsRed, crystalsBlue, crystalsGreen, crystalsYellow;
//	private int energyRed, energyBlue, energyGreen, energyYellow;
//	private int energyRedMax, energyBlueMax, energyGreenMax, energyYellowMax;
	private RefillingStore energyRed, energyBlue, energyGreen, energyYellow;
//	private int activeProjectile; // 0: red, 1: blue, 2: green
	private static int maxProjectile = 2;
	private float visualRangeMax, visualRangeMin;
	private Drawable appearance;
	private Vector<Drawable> glow;
	private int score;
	private int level;
	private int maxProjectiles;
	private Beam beam;
	private float buildRadius;
	private int attackType;
	private Vector<ElectricAttack> electricAttacs;
	private boolean electricAttackActive;
//	private TimeStore electricAttackTimer;
	private float electricAttackRange;
	private int maxEnemiesElectricAttack;
	
	public static final int ATTACK_SHOT = 0;
	public static final int ATTACK_ELECTRIC = 1;
	public static final int ATTACK_POISON = 2;
	public static final int ATTACK_BEAM = 3;
//	private TimeStore redEnergyAdder;
	
	public int getMaxProjectiles() {
		return maxProjectiles;
	}

	public void setMaxProjectiles(int maxProjectiles) {
		this.maxProjectiles = maxProjectiles;
	}

//	public int getActiveProjectile() {
//		return activeProjectile;
//	}
//
//	public void setActiveProjectile(int activeProjectile) {
//		this.activeProjectile = activeProjectile;
//	}

	public Player()
	{
		setPosX(1.f);
		setPosX(4.f);
		setSizeX(0.75f);
		setSizeY(0.75f);
		setSpeed(4.5f);
		speedMultiplier = 1.f;
		setVisualRangeMin(2.f);//7.f);
		setVisualRangeMax(4.f);//15.f);
		setScore(0);
		
		appearance = new Animation();
		((Animation)appearance).addTextureRegion(new TextureRegion(0, 0, 128));
		((Animation)appearance).addTextureRegion(new TextureRegion(1, 0, 128));
		((Animation)appearance).addTextureRegion(new TextureRegion(2, 0, 128));
		((Animation)appearance).setSize(getSizeX(), getSizeY());
		((Animation)appearance).setAnimationInterval(200.f);
		
		glow = new Vector<Drawable>();
		glow.add(new Sprite());
		glow.add(new Sprite());
		glow.add(new Sprite());
		glow.add(new Sprite());
		((Sprite)glow.get(0)).setTextureRegion(new TextureRegion(4, 0, 128));
		((Sprite)glow.get(1)).setTextureRegion(new TextureRegion(5, 0, 128));
		((Sprite)glow.get(2)).setTextureRegion(new TextureRegion(6, 0, 128));
		((Sprite)glow.get(3)).setTextureRegion(new TextureRegion(7, 0, 128));
		
		beam = new Beam();
		electricAttacs = new Vector<ElectricAttack>();
		electricAttackActive = false;
//		electricAttackTimer = new TimeStore(0.3f);
		electricAttackRange = 4.f;
		maxEnemiesElectricAttack = 2;
		
		moveUp = moveDown = moveLeft = moveRight = false;
		teleportUp = teleportDown = teleportLeft = teleportRight = false;
		hp = maxHp = 100.f;
		attackBlockTimer = new Timer();
		invulnerableOnContactTimer = new Timer();
//		texture = new Vector<Texture>();
//		setActiveProjectile(0);
		setLevel(1);
		setMaxXp(10);
		setMaxProjectiles(5);
		
		buildRadius = 2.f;
		
		energyRed = new RefillingStore(3.f, 1.f);
		energyBlue = new RefillingStore(2.f, 0.2f);
		energyGreen = new RefillingStore(3.f, 1.f);
		energyYellow = new RefillingStore(1.f, 0.5f);
		
		crystalsRed = 10;
		crystalsBlue = 10;
		crystalsGreen = 10;
		crystalsYellow = 10;
		
		attackType = ATTACK_SHOT;
		
//		redEnergyAdder = new TimeStore(1.f);
		
//		animationInterval = 500.f;
//		blockAttackSpeed = 200.f;
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
	
	public Player(float thePosX, float thePosY)
	{
		this();
		setPosX(thePosX);
		setPosY(thePosY);
	}

	public void addElectrixAttack(Enemy theEnemy)
	{
		if (electricAttacs.size() >= maxEnemiesElectricAttack) return;
		boolean add = true;
		for (ElectricAttack att : electricAttacs)
		{
			if (att.getTargetEnemy() == theEnemy)
			{
				add = false;
				break;
			}
		}
		if (add) electricAttacs.add(new ElectricAttack(getPosition(), theEnemy));
	}
	
//	public void removeElectrixAttack(Enemy theEnemy)
//	{
//		for (Iterator<ElectricAttack> obj = electricAttacs.iterator();obj.hasNext();)
//		{
//			ElectricAttack att = obj.next();
//			
//			if (att.getTargetEnemy() == theEnemy)
//			{
//				obj.remove();
//				return;
//			}
//		}
//	}
	
	public Player(Player orig)
	{
//		setPosX(orig.getPosX());
//		setPosY(orig.getPosY());
//		setSizeX(orig.getSizeX());
//		setSizeY(orig.getSizeY());
//		hp = orig.hp;
//		maxHp = orig.maxHp;
//		speed = orig.getSpeed();
//		blocksRed = orig.blocksRed;
//		blocksBlue = orig.blocksBlue;
//		blocksGreen = orig.blocksGreen;
//		maxAbilities = orig.maxAbilities;
//		abilities = new int[maxAbilities];
//		setActiveProjectile(0);
//		for (int i=0;i<maxAbilities;i++)
//		{
//			abilities[i] = orig.abilities[i];
//		}
//		activeAbility = orig.activeAbility;
//		teleportStep = orig.teleportStep;
//
//		moveUp = moveDown = moveLeft = moveRight = false;
//		teleportUp = teleportDown = teleportLeft = teleportRight = false;
//		attackBlockTimer = new Timer();
//		texture = new Vector<Texture>();
//		animationTimer = new Timer();
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
	
	public void levelUp()
	{
		level++;
		setXp(0);
		setMaxXp(getMaxXp()+10);
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
	
	public Vector2f getPosition()
	{
		return new Vector2f(getPosX(), getPosY());
	}
	
	public void setPosition(Vector2f v)
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
	
	private void doElectricAttackDamage(float dt)
	{
		for (Iterator<ElectricAttack> obj = electricAttacs.iterator();obj.hasNext();)
		{
			ElectricAttack att = obj.next();
			if(att.damageEnemy(dt))
			{
				obj.remove();
			}
		}
	}
	
	public void update(float dt)
	{
		this.updatePosition();
		
		this.beam.update();
		
		if (!electricAttackActive) electricAttacs.clear();
		if (electricAttackActive)
		{
			if (!Darklord.sounds.electricity.isPlaying())
			{
				Darklord.sounds.electricity.playAsSoundEffect(1.f, Darklord.sounds.volumeMusic, false);
			}
			
			if (decreaseEnergyBlue(dt))
			{
//				electricAttackTimer.add(dt);
				doElectricAttackDamage(dt);
			}
		}
		energyRed.increase(dt);
		energyBlue.increase(dt);
		energyGreen.increase(dt);
		energyYellow.increase(dt);
		
		// level up?
		if (getXp() >= getMaxXp())
		{
			Print.outln("LEVEL UP!");
			setXp(getXp()-getMaxXp());
			levelUp();
		}
		
//		Print.outln("dt: "+ redEnergyAdder.getCurrent());

		
//		System.out.println("Player: ("+this.getPosX()+", "+this.getPosY()+")");
		
		if (attackBlockTimer.getTimeDelta() > blockAttackSpeed) this.stopAttackBlockTimer();
		if (invulnerableOnContactTimer.getTimeDelta() > invulnerableOnContact) this.stopInvulnerableOnContactTimer();
	}
	
	private void updatePosition()
	{
		if (moveUp)
		{
//			System.out.println("UP!");
			setPosY(getPosY() + speed*speedMultiplier*Darklord.dt);
		}
		
		if (moveDown)
		{
			setPosY(getPosY() - speed*speedMultiplier*Darklord.dt);
//			System.out.println("posY: "+posY);
		}
		
		if (moveLeft)
		{
			setPosX(getPosX() - speed*speedMultiplier*Darklord.dt);
		}
		
		if (moveRight)
		{
			setPosX(getPosX() + speed*speedMultiplier*Darklord.dt);
		}
	}
	
	public void draw()
	{
		Darklord.chars.begin();
		glow.get(attackType).draw(0.f, 0.f, getSizeX(), getSizeY());
		appearance.draw(0.f, 0.f, getSizeX(), getSizeY());
		Darklord.chars.end();
		
//		int textureNum = (int)Math.floor(animationTimer.getTimeDelta() / animationInterval);
////		System.out.println("dt: "+(animationTimer.getTimeDelta()));
//		if (textureNum >= Darklords.textures.player.size())
//		{
////			System.out.println("Reset timer!");
//			textureNum = 0;
//			animationTimer.reset();
//		}
//		
//		GL11.glEnable(GL11.GL_TEXTURE_2D);
//		Color.white.bind();
//		
//		Darklords.textures.shadows.get(getActiveProjectile()).bind();	
//		GL11.glBegin(GL11.GL_QUADS);
////		float size = 1.0f;
//		GL11.glTexCoord2f(0.f, 1.f);
//		GL11.glVertex2f(0.f, getSizeY());
//		GL11.glTexCoord2f(1.f, 1.f);
//		GL11.glVertex2f(getSizeX(), getSizeY());
//		GL11.glTexCoord2f(1.f, 0.f);
//		GL11.glVertex2f(getSizeX(), 0.f);
//		GL11.glTexCoord2f(0f, 0f);
//		GL11.glVertex2f(0.f, 0.f);
//		GL11.glEnd();
//
//		Darklords.textures.player.get(textureNum).bind();	
//		GL11.glBegin(GL11.GL_QUADS);
////		float size = 1.0f;
//		GL11.glTexCoord2f(0.f, 1.f);
//		GL11.glVertex2f(0.f, getSizeY());
//		GL11.glTexCoord2f(1.f, 1.f);
//		GL11.glVertex2f(getSizeX(), getSizeY());
//		GL11.glTexCoord2f(1.f, 0.f);
//		GL11.glVertex2f(getSizeX(), 0.f);
//		GL11.glTexCoord2f(0f, 0f);
//		GL11.glVertex2f(0.f, 0.f);
//		GL11.glEnd();
//		
//		GL11.glDisable(GL11.GL_TEXTURE_2D);
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
	
//	public void inreaseActiveProjectile()
//	{
//		activeProjectile++;
//		if (activeProjectile > maxProjectile)
//		{
//			activeProjectile = 0;
//		}
//	}

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
//			this.abilities[0]--;
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
//			this.abilities[0]--;
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
//			this.abilities[0]--;
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
//			this.abilities[0]--;
		} else
		{
			this.teleportRight = false;
		}
	}

	public void addItem(CollectableType type)
	{
		
		switch (type)
		{
		case BLOCK_RED:
			Darklord.sounds.pling.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
			crystalsRed++;
			break;
		case BLOCK_BLUE:
			Darklord.sounds.pling.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
			crystalsBlue++;
			break;
		case BLOCK_GREEN:
			Darklord.sounds.pling.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
			crystalsGreen++;
			break;
		case BLOCK_YELLOW:
			Darklord.sounds.pling.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
			crystalsYellow++;
			break;
		case ABILITY_TELEPORT:
			abilities[0]++;
			break;
		case ABILITY_DIGGING:
			abilities[5]++;
			break;
		case DIAMOND:
			score += 100;
			System.out.println("score: "+getScore());
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

	public float getVisualRangeMax() {
		return visualRangeMax;
	}

	public void setVisualRangeMax(float visualRange) {
		this.visualRangeMax = visualRange;
	}

	public float getVisualRangeMin() {
		return visualRangeMin;
	}
	
	public float getVisualRangeRelative() {
		return visualRangeMin/visualRangeMax;
	}

	public void setVisualRangeMin(float visualRangeMin) {
		this.visualRangeMin = visualRangeMin;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}
	
	public void addXp(int xp)
	{
		Print.outln("add "+xp+" XP");
		this.xp += xp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMaxXp() {
		return maxXp;
	}

	public void setMaxXp(int maxXp) {
		this.maxXp = maxXp;
	}

	public float getEnergyRed() {
		return energyRed.getCurrent();
	}

	public void setEnergyRed(float energyRed) {
		this.energyRed.setCurrent(energyRed);
	}
	
	public boolean decreaseEnergyRed(float decrease) {
		if (getEnergyRed() >= decrease)
		{
			this.energyRed.decrease(decrease);
			return true;
		}
		return false;
	}

	public float getEnergyBlue() {
		return energyBlue.getCurrent();
	}

	public void setEnergyBlue(float energyBlue) {
		this.energyBlue.setCurrent(energyBlue);
	}
	
	public boolean decreaseCrystalsRed(int amount)
	{
		if (crystalsRed - amount >=0)
		{
			crystalsRed -= amount;
			return true;
		}
		return false;
	}
	
	public boolean decreaseCrystalsBlue(int amount)
	{
		if (crystalsBlue - amount >=0)
		{
			crystalsBlue -= amount;
			return true;
		}
		return false;
	}
	
	public boolean decreaseCrystalsGreen(int amount)
	{
		if (crystalsGreen - amount >=0)
		{
			crystalsGreen -= amount;
			return true;
		}
		return false;
	}
	
	public boolean decreaseCrystalsYellow(int amount)
	{
		if (crystalsYellow - amount >=0)
		{
			crystalsYellow -= amount;
			return true;
		}
		return false;
	}
	
	public void addCrystalsRed(int amount)
	{
		crystalsRed += amount;
	}
	
	public void addCrystalsBlue(int amount)
	{
		crystalsBlue += amount;
	}
	
	public void addCrystalsGreen(int amount)
	{
		crystalsGreen += amount;
	}
	
	public void addCrystalsYellow(int amount)
	{
		crystalsYellow += amount;
	}
	
	public boolean decreaseEnergyBlue(float decrease) {
		if (getEnergyBlue() >= decrease)
		{
			this.energyBlue.decrease(decrease);
			return true;
		}
		return false;
	}
	
	public float getEnergyGreen() {
		return energyGreen.getCurrent();
	}

	public void setEnergyGreen(float energyGreen) {
		this.energyGreen.setCurrent(energyGreen);
	}

	public boolean decreaseEnergyGreen(float decrease) {
		if (getEnergyGreen() >= decrease)
		{
			this.energyGreen.decrease(decrease);
			return true;
		}
		return false;
	}
	
	public float getEnergyYellow() {
		return energyYellow.getCurrent();
	}

	public void setEnergyYellow(float energyYellow) {
		this.energyYellow.setCurrent(energyYellow);
	}

	public boolean decreaseEnergyYellow(float decrease) {
		if (getEnergyYellow() >= decrease)
		{
			this.energyYellow.decrease(decrease);
			return true;
		}
		return false;
	}
	
	public float getEnergyRedMax() {
		return energyRed.getMax();
	}

	public void setEnergyRedMax(float energyRedMax) {
		this.energyRed.setMax(energyRedMax);
	}

	public float getEnergyBlueMax() {
		return energyBlue.getMax();
	}

	public void setEnergyBlueMax(float energyBlueMax) {
		this.energyBlue.setMax(energyBlueMax);
	}

	public float getEnergyGreenMax() {
		return energyGreen.getMax();
	}

	public void setEnergyGreenMax(float energyGreenMax) {
		this.energyGreen.setMax(energyGreenMax);
	}

	public float getEnergyYellowMax() {
		return energyYellow.getMax();
	}

	public void setEnergyYellowMax(float energyYellowMax) {
		this.energyYellow.setMax(energyYellowMax);
	}

	public int getCrystalsRed() {
		return crystalsRed;
	}

	public void setCrystalsRed(int crystalsRed) {
		this.crystalsRed = crystalsRed;
	}

	public int getCrystalsBlue() {
		return crystalsBlue;
	}

	public void setCrystalsBlue(int crystalsBlue) {
		this.crystalsBlue = crystalsBlue;
	}

	public int getCrystalsGreen() {
		return crystalsGreen;
	}

	public void setCrystalsGreen(int crystalsGreen) {
		this.crystalsGreen = crystalsGreen;
	}

	public int getCrystalsYellow() {
		return crystalsYellow;
	}

	public void setCrystalsYellow(int crystalsYellow) {
		this.crystalsYellow = crystalsYellow;
	}

	public Beam getBeam() {
		return beam;
	}

	public void setBeam(Beam beam) {
		this.beam = beam;
	}

	public float getSpeedMultiplier() {
		return speedMultiplier;
	}

	public void setSpeedMultiplier(float speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}

	public float getBuildRadius() {
		return buildRadius;
	}

	public void setBuildRadius(float buildRadius) {
		this.buildRadius = buildRadius;
	}

	public int getAttackType() {
		return attackType;
	}

	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}

	public boolean isElectricAttackActive() {
		return electricAttackActive;
	}

	public void setElectricAttackActive(boolean electricAttackActive) {
		this.electricAttackActive = electricAttackActive;
	}

	public float getElectricAttackRange() {
		return electricAttackRange;
	}

	public void setElectricAttackRange(float electricAttackRange) {
		this.electricAttackRange = electricAttackRange;
	}

	public Vector<ElectricAttack> getElectricAttacs() {
		return electricAttacs;
	}

	public void setElectricAttacs(Vector<ElectricAttack> electricAttacs) {
		this.electricAttacs = electricAttacs;
	}
}