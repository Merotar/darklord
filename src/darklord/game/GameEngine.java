package darklord.game;

import org.lwjgl.opengl.GL11;

import darklord.media.MovingSprite;
import darklord.media.MovingText;
import darklord.media.TextureRegion;
import darklord.ui.IngameStatus;
import darklord.ui.IngameUI;
//import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.CryptoPrimitive;
import java.util.Random;
import java.util.Vector;
import java.util.Iterator;
import darklord.math.Vector2f;

/**
 * describes the whole level including grid and player informations
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class GameEngine
{	
//	private int dimX, dimY;
	private static float playerBorderX = 0.3f;
	private static float playerBorderY = 0.3f; // in percent
	private float posX, posY;
	private int resX, resY;
//	private Block grid[][];
	public static float scale = 0.2f;
	public static  float gridSize = 80.f;
//	Vector<Collectable> collectableObjects;
	public Player mainPlayer;
	SelectBox mainSelectBox;
	public Map map;
	public Vector<Projectile> projectiles;
	public Vector<Projectile> hostileProjectiles;
	public Vector<MovingSprite> bubbles;
	public static final int drawSize = 13;
	private Vector<MovingText> movingTexts;
	IngameStatus ingameStatus;
	IngameUI mainUI;
	private CountdownTimer buildTimer;
	public final float maxUpdateDistance = 12.f;
	public final float highwaySpeedMultiplyer = 1.7f;
	public float zoom;
	public final float zoomMin = 0.1f;
	public final float zoomMax = 1.2f;
//	public StaticEnemyCrystal testEnemy;

	public GameEngine()
	{
//		this.dimX = 20;
//		this.dimY = 20;
		posX = posY =1.f;
//		grid = new Block[dimX][dimY];
//		collectableObjects = new Vector<Collectable>();
		map = new Map();
		mainPlayer = new Player();
		mainSelectBox = new SelectBox();
		projectiles = new Vector<Projectile>();
		hostileProjectiles = new Vector<Projectile>();
		bubbles = new Vector<MovingSprite>();
		movingTexts = new Vector<MovingText>();
		
//		initTest();
	}
	
//	public Level(int sizeX, int sizeY)
//	{
////		this.dimX = sizeX;
////		this.dimY = sizeY;
//		posX = 1.f;
//		posY = 1.f;
//		
//        map = new Map(sizeX, sizeY, "default");
//		
////		grid = new Block[dimX][dimY];
////		collectableObjects = new Vector<Collectable>();
//		mainPlayer = new Player(map.start.getX(), map.start.getY());
////		Print.out("created Player at "); map.start.print();
//		map.getBlockAt((int) mainPlayer.getPosX(), (int) mainPlayer.getPosY()).setType(BlockType.BLOCK_NONE);
//		mainSelectBox = new SelectBox();
//		projectiles = new Vector<Projectile>();
//		hostileProjectiles = new Vector<Projectile>();
//		bubbles = new Vector<MovingSprite>();
//		movingTexts = new Vector<MovingText>();
//		
////		map.readTextFile("test.txt");
////		mainPlayer.setPos(map.getStart());
//		
////		initTest();
//}
	
	public GameEngine(String name)
	{
		posX = 1.f;
		posY = 1.f;
		
		zoom = 1.f;
		
		resX = Darklord.resX;
		resY = Darklord.resY;
		
		buildTimer = new CountdownTimer(0.1f);
		
		ingameStatus = IngameStatus.DEFAULT;
		
		File dir = new File("./"+name);
		boolean isNewMap = true;// !dir.exists();
		
		map = new Map(20, 20, name, isNewMap);
		
		if (isNewMap)
		{
			dir.mkdir();
			mainPlayer = new Player(map.start.getX(), map.start.getY());
			map.getBlockAt((int) mainPlayer.getPosX(), (int) mainPlayer.getPosY()).setType(BlockType.BLOCK_NONE);

		} else
		{
			mainPlayer = readPlayerFromFile();
		}		
		
//		testEnemy = new StaticEnemyCrystal(mainPlayer.getPosX(), mainPlayer.getPosY());
//		testEnemy.setSize(3.f);
		
		mainSelectBox = new SelectBox();
		projectiles = new Vector<Projectile>();
		hostileProjectiles = new Vector<Projectile>();
		bubbles = new Vector<MovingSprite>();
		movingTexts = new Vector<MovingText>();
		
        mainUI = new IngameUI(1.f*resX/resY);
        mainUI.init(this);
	}
	
	// copy constructor
	public GameEngine(GameEngine orig)
	{
		posX = orig.posX;
		posY = orig.posY;
		
		map = new Map(orig.map);
		mainPlayer = new Player(orig.mainPlayer);
		mainSelectBox = new SelectBox();
		projectiles = new Vector<Projectile>();
		hostileProjectiles = new Vector<Projectile>();
	}
	
//	public void initTest()
//	{
//		Random rnd = new Random();
//		
//		for (int i=0;i<dimX;i++)
//		{
//			for (int j=0;j<dimY;j++)
//			{
//				grid[i][j] = new Block();
//				if ((i==0) || (j==0) || (i==dimX-1) || (j==dimY-1))
//				{
//					grid[i][j].setType(2);
//				} else // inner level
//				{
//					if (rnd.nextGaussian() < 0.5)
//					{
//						grid[i][j].setType(1);
//					}
//				}
//			}
//		}
//		grid[1][1].setType(1);
//		grid[1][2].setType(1);
//		grid[1][3].setType(1);
//		grid[2][2].setType(1);
//		grid[2][3].setType(1);
//		grid[7][2].setType(1);
//		grid[3][3].setType(1);
//		grid[(int)Math.round(mainPlayer.getPosX())][(int)Math.round(mainPlayer.getPosY())].setType(0);
//		
//		collectableObjects.add(new Collectable(1, 7.25f, 3.25f));
//	}
	
//	public Vector2f gridToScreen(Vector2f pos)
//	{
//		Vector2f posScreen = new Vector2f();
//		
//		posScreen.setX((float)pos.getX()*scale/Darklords.resX - 1.f);
//		posScreen.setY((float)pos.getY()*scale/Darklords.resY - 1.f);
//		
//		return posScreen;
//	}

	public int getDimX() {
		return map.getMapSizeX();
	}

//	public void setDimX(int dimX) {
//		map.dimX = dimX;
//	}

	public int getDimY() {
		return map.getMapSizeY();
	}

//	public void setDimY(int dimY) {
//		map.dimY = dimY;
//	}
	
	public boolean isBlockSolid(int x, int y)
	{
//		if ((x < 0) || (y < 0) || (x >= map.getMapSizeX()) || (y >= map.getMapSizeY())) return false;
		Block tmpBlock = map.getBlockAt(x, y);
		if (tmpBlock != null) return tmpBlock.isSolid();
		
		return false;
	}

	public boolean collideWithBlock(int x, int y)
	{
		boolean collision = false;
//		if ((Math.abs(mainPlayer.getPosX() - x) < 1.) && (Math.abs(mainPlayer.getPosY() - y) < 1.))
//		{
//			collision = true;
//		}
		
		if (isBlockSolid(x, y))
		{
//			System.out.println("test!");
			collision = Collider.collideBorders(mainPlayer.getPosX(), mainPlayer.getPosX()+mainPlayer.getSizeX(), mainPlayer.getPosY(), mainPlayer.getPosY()+mainPlayer.getSizeY(), x, x+1.f, y, y+1.f);
		}

		if (!collision) return false;
//		System.out.println("collision ("+x+", "+y+")");
		
		
//		if (mainPlayer.canAttackBlock())
//		{
//			this.grid[x][y].attack();
//			mainPlayer.startAttackBlockTimer();
//		}
		return true;
	}
	
	public void finalize()
	{
		map.finalize();
		writePlayerToFile(mainPlayer);
	}
	
	/**
	 * effect if player attacks a block
	 * 
	 * @param x x position
	 * @param y y position
	 */
	public void attackBlock(int x, int y)
	{
		if (mainPlayer.canAttackBlock())
		{
//			BlockType type = map.getBlockAt(x, y).getType();
			Block tmpBlock = map.getBlockAt(x, y);
			if (tmpBlock != null)
			{
				CollectableType tmpCollectableType = tmpBlock.dropOnDestroy();
				
				if (tmpBlock.isDestroyable() && tmpBlock.attack())
				{
					//destroyed
//					System.out.println("Block destroyed!");
//					if (type == BlockType.BLOCK_ROCK) mainPlayer.decreaseDiggingCount();
					Random rnd = new Random();
					float rndX = 0.10f*(1.f*rnd.nextInt()/Integer.MAX_VALUE);
					float rndY = 0.10f*(1.f*rnd.nextInt()/Integer.MAX_VALUE);
//					System.out.println(rndX);
					
					if (tmpCollectableType != CollectableType.NONE)
					{
						map.getCollectableObjects().add(new Collectable(tmpCollectableType, x+0.1f+rndX, y+0.1f+rndY));
					}
				}
				mainPlayer.startAttackBlockTimer();
			}
		}
	}
	
	public boolean collideWithBlock(float x, float y)
	{
		return collideWithBlock((int)Math.floor(x), (int)Math.floor(y));
	}
	
	public boolean isBlockSolid(float x, float y)
	{
		return map.getBlockAt((int)Math.floor(x), (int)Math.floor(y)).isSolid();
	}
	
	/**
	 * @return returns true if block is solid
	 */
	public boolean checkSolid()
	{
		return isBlockSolid((int)Math.round(mainPlayer.getPosX()+0.5), (int)Math.round(mainPlayer.getPosY()+0.5));
	}

	public void draw()
	{
		GL11.glPushMatrix();
		
		float posXgrid = posX*(gridSize/Darklord.resX)*2.f;
		float posYgrid = posY*(gridSize/Darklord.resY)*2.f;
		GL11.glTranslatef(posXgrid, posYgrid, 0.f);
		
		// TODO: fix orientation
//		GL11.glScaled(1., -1., 1.);
		GL11.glTranslated(-1., -1., 0.);
//		GL11.glTranslated(-2.f*posX/(Darklords.resX), -2.f*posY/(Darklords.resY), 0.);
		
		GL11.glScaled(2.f*gridSize/(Darklord.resX), 2.f*gridSize/(Darklord.resY), 1.);

		GL11.glTranslated(mainPlayer.getPosX(), mainPlayer.getPosY(), 0.);
		GL11.glScaled(zoom,  zoom, 1.);
		GL11.glTranslated(-mainPlayer.getPosX(), -mainPlayer.getPosY(), 0.);
		
		// draw blocks
		
		Block backgroundBlock = new Block(BlockType.BLOCK_NONE);
		
		
//		int minX = (int)mainPlayer.getPosX()-drawSize;
//		int maxX = (int)mainPlayer.getPosX()+drawSize;
//		int minY =(int)mainPlayer.getPosY()-drawSize;
//		int maxY =(int)mainPlayer.getPosY()+drawSize;

		int tmpDrawSize = (int)Math.ceil(drawSize / zoom);
		
		int minX = (int)-posX-tmpDrawSize/2;
		int maxX = (int)-posX+tmpDrawSize;
		int minY =(int)-posY-tmpDrawSize/2;
		int maxY =(int)-posY+tmpDrawSize;
		
		Darklord.sprites01.begin();
		
		// draw blocks
		for (int i=minX;i<maxX;i++)
		{
			for (int j=minY;j<maxY;j++)
			{
//		for (int i=0;i<map.dimX;i++)
//		{
//			for (int j=0;j<map.dimY;j++)
//			{
				GL11.glPushMatrix();
//				GL11.glTranslated((float)i*gridSize, (float)j*gridSize, 0.f);
				GL11.glTranslated(i, j, 0.);

//				GL11.glScaled(1., -1., 1.);
//				GL11.glScaled(1./Darklords.resX, 1./Darklords.resY, 1.);
//				Vector2f pos = gridToScreen(new Vector2f(i,j));
//				pos.print();
//				GL11.glTranslatef(pos.getX(), pos.getY(), 0.f);

//				backgroundBlock.draw();
				Block tmpBlock = map.getBlockAt(i, j);
				if (tmpBlock != null) tmpBlock.draw();
//				map.getBlockAt(i, j).draw();
				GL11.glPopMatrix();
			}
		}
		
		Darklord.sprites01.end();
		
		// draw electric attacks
		for (ElectricAttack att : mainPlayer.getElectricAttacs())
		{
			att.draw();
		}
		
		// draw collectable objects
		for (Iterator<Collectable> object = map.getCollectableObjects().iterator(); object.hasNext();)
		{
			Collectable tmp = object.next();
			
			GL11.glPushMatrix();
//			GL11.glTranslated((float)i*gridSize, (float)j*gridSize, 0.f);
			GL11.glTranslated(tmp.getPosX(), tmp.getPosY(), 0.);
//			GL11.glScaled(1., -1., 1.);
//			GL11.glScaled(1./Darklords.resX, 1./Darklords.resY, 1.);
//			Vector2f pos = gridToScreen(new Vector2f(i,j));
//			pos.print();
//			GL11.glTranslatef(pos.getX(), pos.getY(), 0.f);

			tmp.draw();
			GL11.glPopMatrix();
		}
		
		// draw chests objects
		for (Iterator<Chest> object = map.getChests().iterator(); object.hasNext();)
		{
			Chest tmp = object.next();
			
			GL11.glPushMatrix();
//			GL11.glTranslated((float)i*gridSize, (float)j*gridSize, 0.f);
			GL11.glTranslated(tmp.getPosX(), tmp.getPosY(), 0.);
//			GL11.glScaled(1., -1., 1.);
//			GL11.glScaled(1./Darklords.resX, 1./Darklords.resY, 1.);
//			Vector2f pos = gridToScreen(new Vector2f(i,j));
//			pos.print();
//			GL11.glTranslatef(pos.getX(), pos.getY(), 0.f);

			tmp.draw();
			GL11.glPopMatrix();
		}
		
		// draw enemies
		for (Iterator<Enemy> object = map.getEnemies().iterator(); object.hasNext();)
		{
			Enemy tmp = object.next();
			
			GL11.glPushMatrix();
			GL11.glTranslated(tmp.getPos().getX(), tmp.getPos().getY(), 0.);
			tmp.draw();
			GL11.glPopMatrix();
		}
		
		// draw selection
		GL11.glPushMatrix();
		GL11.glTranslated(mainSelectBox.getX(), mainSelectBox.getY(), 0.f);
		mainSelectBox.draw();
		GL11.glPopMatrix();
		
		// draw the players beam
		if (mainPlayer.getBeam().isActive())
		{
			GL11.glPushMatrix();
			mainPlayer.getBeam().draw();
			GL11.glPopMatrix();
		}
		
		// draw player
		GL11.glPushMatrix();
		GL11.glTranslated(mainPlayer.getPosX(), mainPlayer.getPosY(), 0.);
		mainPlayer.draw();
		GL11.glPopMatrix();
		
//		GL11.glPushMatrix();
//		GL11.glTranslated(testEnemy.getPosX(), testEnemy.getPosY(), 0.);
//		testEnemy.draw();
//		GL11.glPopMatrix();
		
		// draw projectiles
		for (Iterator<Projectile> object = projectiles.iterator(); object.hasNext();)
		{
			Projectile tmp = object.next();
			
			GL11.glPushMatrix();
			tmp.draw();
			GL11.glPopMatrix();
		}
		
		// draw hostile projectiles
		for (Iterator<Projectile> object = hostileProjectiles.iterator(); object.hasNext();)
		{
			Projectile tmp = object.next();
			
			GL11.glPushMatrix();
//			GL11.glTranslated(tmp.getPosX(), tmp.getPosY(), 0.);
			tmp.draw();
			GL11.glPopMatrix();
		}
		
		// draw fog
		if (!Darklord.devMode)
		{
			minX *= map.getFogDensity();
			maxX *= map.getFogDensity();
//			if (maxX >= map.getMapSizeX()*map.getFogDensity()) maxX = map.getMapSizeX()*map.getFogDensity()-1;
			minY *= map.getFogDensity();
			maxY *= map.getFogDensity();
//			if (maxY >= map.getMapSizeY()*map.getFogDensity()) maxY = map.getMapSizeY()*map.getFogDensity()-1;
			
			for (int i=minX;i<=maxX;i++)
			{
				for (int j=minY;j<=maxY;j++)
				{
					GL11.glPushMatrix();
					GL11.glTranslated(1.f*i/map.getFogDensity(), 1.f*j/map.getFogDensity(), 0.);
//					GL11.glColor4f(0.f, 0.f, 0.f, map.getFogAt(i, j));
					map.drawFog(i, j);
					GL11.glPopMatrix();
				}
			}
		}
		
		// draw bubbles
		for (Iterator<MovingSprite> object = bubbles.iterator(); object.hasNext();)
		{
			MovingSprite tmp = object.next();
			
			GL11.glPushMatrix();
			tmp.draw();
//			tmp.draw(tmp.getPosition().getX(), tmp.getPosition().getY(),tmp.getSize().getX(), tmp.getSize().getX());
			GL11.glPopMatrix();
		}
		
		// draw text
		
		for (Iterator<MovingText> object = movingTexts.iterator();object.hasNext();)
		{
			MovingText tmpText = object.next();
			tmpText.draw(Darklord.textDrawer);
		}
		
//		if (showMap)
//		{
//			map.drawMap();
//		}
		
		// draw ui
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
//		GL11.glScaled(1., -1., 1.);
		mainUI.draw();
		GL11.glPopMatrix();
		
//		GL11.glPushMatrix();
//		GL11.glTranslated(2.,2., 0.);
//		GL11.glColor3f(1.f, 0.f, 1.f);
//		Darklords.textDrawer.draw(Float.toString(mainPlayer.getHp()));
//		GL11.glPopMatrix();
		
//		GL11.glPopMatrix();
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
	
	/**
	 * method which handles mouse position dependent events
	 * @param pos
	 */
	public void mousePositionReaction(Vector2f pos)
	{
		Vector2f mouseGrid = new Vector2f(pos.getX()/this.gridSize, pos.getY()/this.gridSize);
		// translate grid
		mouseGrid.setX(mouseGrid.getX()-this.getPosX());
		mouseGrid.setY(mouseGrid.getY()-this.getPosY());
		
//		System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
		int x_int = (int)Math.floor(mouseGrid.getX());
		int y_int = (int)Math.floor(mouseGrid.getY());
		
//		if (x_int < map.getMapSizeX() && x_int >= 0 && y_int < map.getMapSizeY() && y_int >= 0)
//		{
			if (this.isBlockSolid(x_int, y_int))
			{
				mainSelectBox.setPos(x_int, y_int);
				mainSelectBox.show();
			} else
			{
				mainSelectBox.hide();
			}
//		} else
//		{
//			mainSelectBox.hide();
//		}
	}
	
//	public int getNumerOfPojectiles(int type)
//	{
//		int amount = 0;
//		for (Iterator<Projectile> object = projectiles.iterator(); object.hasNext();)
//		{
//			Projectile p = object.next();
//			if (p.getType() == type) amount++;
//		}
//		return amount;
//	}
	
	public int getNumerOfPojectiles()
	{
		return projectiles.size();
	}
	
	/**
	 * reaction to a pressed mouse button
	 * @param pos
	 * @param button
	 */
	public void mouseDownReaction(Vector2f pos, int button) // 0: left, 1: right
	{			
		if (!isMapActive())
		{
			switch (ingameStatus)
			{
			case DEFAULT:
				mouseDownReactionDefault(pos, button);
				break;
			case BUILDING:
				if (buildTimer.isDone())
				{
					buildTimer.reset();
					mouseDownReactionBuild(pos, button);
				}
				break;
			default:
				mouseDownReactionDefault(pos, button);
			}
		}
	}
	
	public void mouseReleaseReaction()
	{
		mainPlayer.setElectricAttackActive(false);
	}
	
	/**
	 * reaction to a pressed mouse button
	 * @param pos
	 * @param button
	 */
	public void mouseDownReactionDefault(Vector2f pos, int button) // 0: left, 1: right
	{
		Vector2f mouseGrid = new Vector2f(pos.getX()/this.gridSize, pos.getY()/this.gridSize);
		// translate grid
//		mouseGrid.print();
		mouseGrid.setX(mouseGrid.getX()-this.getPosX());
		mouseGrid.setY(mouseGrid.getY()-this.getPosY());
//		mouseGrid.print();
//
//		System.out.println("pos: ("+this.getPosX()+", "+this.getPosY()+")");
		
//		System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
		int x_int = (int)Math.floor(mouseGrid.getX());
		int y_int = (int)Math.floor(mouseGrid.getY());
		
		if (button == 0)
		{
			switch (mainPlayer.getActiveAbility())
			{
				case DIG:
					if (((mainPlayer.getDiggingNum()!=0) || map.getBlockAt(x_int, y_int).getType() != BlockType.BLOCK_ROCK) && (Math.abs(this.mainPlayer.getPosX()+0.5f-mouseGrid.getX()) < 1.5f) && (Math.abs(this.mainPlayer.getPosY()+0.5f-mouseGrid.getY()) < 1.5f))
					{
						this.attackBlock(x_int, y_int);
					}
					break;
					default:
						// do nothing
					break;
			}
		}
		
		if (button == 1)
		{
			switch (mainPlayer.getAttackType())
			{
			case Player.ATTACK_SHOT:
				startProjectileRed(mouseGrid);
				break;
			case Player.ATTACK_ELECTRIC:
				startElectricAttack();
				break;
			case Player.ATTACK_POISON:
				startProjectileGreen(mouseGrid);
				break;
			case Player.ATTACK_BEAM:
				startBeam(mouseGrid);
				break;
			default:
				startProjectileRed(mouseGrid);
			}
		}
	}
	
	public void mouseDownReactionBuild(Vector2f pos, int button) // 0: left, 1: right
	{
		Vector2f mouseGrid = new Vector2f(pos.getX()/this.gridSize, pos.getY()/this.gridSize);
		// translate grid
//		mouseGrid.print();
		mouseGrid.setX(mouseGrid.getX()-this.getPosX());
		mouseGrid.setY(mouseGrid.getY()-this.getPosY());
		
		
		// exit function if cursor too far away
		// TODO: round distance
		if ((mainPlayer.getCenter().sub(mouseGrid)).length() > mainPlayer.getBuildRadius()) return;
		
//		mouseGrid.print();
//
//		System.out.println("pos: ("+this.getPosX()+", "+this.getPosY()+")");
		
//		System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
		int x_int = (int)Math.floor(mouseGrid.getX());
		int y_int = (int)Math.floor(mouseGrid.getY());
		
		if (button == 0)
		{
			if (mainUI.getActiveBuildable() != null)
			{
				if (mainUI.getActiveBuildable().equals("build_wall"))
				{
					float blockX = mouseGrid.getX() - x_int;
					float blockY = mouseGrid.getY() - y_int;
					
					int type = Wall.WALL_TOP;
					
					if (blockY < blockX && blockY < (1.-blockX))
					{
						type = Wall.WALL_BOTTOM;
					}
					
					if (blockX < blockY && blockX < (1.-blockY))
					{
						type = Wall.WALL_LEFT;
					}
					
					if ((1.-blockX) < blockY && (1.-blockX) < (1.-blockY))
					{
						type = Wall.WALL_RIGHT;
					}
					
					if (map.setWall(x_int, y_int, type))
					{
						if (!Darklord.sounds.build.isPlaying())
						{
							Darklord.sounds.build.playAsSoundEffect(1.f, Darklord.sounds.volumeMusic, false);
						}
					}
				}
				
				if (mainUI.getActiveBuildable().equals("build_floor_crystal_red"))
				{
					map.setFloorCrystalRed(x_int, y_int);
				}
				
				if (mainUI.getActiveBuildable().equals("build_floor_stone"))
				{
					map.setFloorStone(x_int, y_int);
				}
				
				if (mainUI.getActiveBuildable().equals("build_block_glass"))
				{
					boolean collision = Collider.collideBorders(mainPlayer.getPosX(), mainPlayer.getPosX()+mainPlayer.getSizeX(), mainPlayer.getPosY(), mainPlayer.getPosY()+mainPlayer.getSizeY(), x_int, x_int+1.f, y_int, y_int+1.f);

					if (!collision)
					{
						map.buildBlockGlass(x_int, y_int);
					}
				}
			}
		}
		
		if (button == 1)
		{
			map.getBlockAt(x_int, y_int).removeOverlay();
		}

	}
	
	/**
	 * starts an projectile if ammo is available
	 * @param pos start position of the projectile
	 */
	public void startProjectileRed(Vector2f pos)
	{
		float ammo = mainPlayer.getEnergyRed();

//		if (mainPlayer.getActiveProjectile() == 2) ammo = mainPlayer.getEnergyGreen();
		
		if (mainPlayer.getMaxProjectiles() > getNumerOfPojectiles() && ammo >= 1)
		{
			// shoot
			Vector2f dir = new Vector2f();
			dir = pos.sub(mainPlayer.getCenter());
			dir.normalize();
			Vector2f position =  new Vector2f(mainPlayer.getCenter());
//			position = position.add(mainPlayer.getSize().mul(0.5f));
			position = position.add(dir.mul(0.5f));
			
			BulletProjectile tmpProjectile = new BulletProjectile(position, dir, 0);
			projectiles.add(tmpProjectile);
			mainPlayer.decreaseEnergyRed(tmpProjectile.getEnergyCosts());
			Darklord.sounds.shot.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
//			System.out.println("Create Projectile at ("+pos.getX()+", "+pos.getY()+")");;
//			mainPlayer.switchActiveAbility();	
		}
	}
	
	public void startProjectileGreen(Vector2f pos)
	{
		float ammo = mainPlayer.getEnergyGreen();

//		if (mainPlayer.getActiveProjectile() == 2) ammo = mainPlayer.getEnergyGreen();
		
		if (mainPlayer.getMaxProjectiles() > getNumerOfPojectiles() && ammo >= 1)
		{
			// shoot
			Vector2f dir = new Vector2f();
			dir = pos.sub(mainPlayer.getCenter());
			dir.normalize();
			Vector2f position =  new Vector2f(mainPlayer.getCenter());
//			position = position.add(mainPlayer.getSize().mul(0.5f));
//			position = position.add(dir.mul(0.5f));
			
			Projectile tmpProjectile = new ShockWave(position, dir, 0);
			tmpProjectile.setPos(position.sub(new Vector2f(tmpProjectile.getSizeX()/2.f, tmpProjectile.getSizeY()/2.f)));
			
			projectiles.add(tmpProjectile);
			mainPlayer.decreaseEnergyGreen(tmpProjectile.getEnergyCosts());
			Darklord.sounds.shot.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
//			System.out.println("Create Projectile at ("+pos.getX()+", "+pos.getY()+")");;
//			mainPlayer.switchActiveAbility();	
		}
	}
	
	public void startElectricAttack()
	{
		for (Enemy e : map.getEnemies())
		{
			if ((e.getCenter().sub(mainPlayer.getCenter())).length() < mainPlayer.getElectricAttackRange())
			{
				mainPlayer.addElectrixAttack(e);
			}
		}
		mainPlayer.setElectricAttackActive(true);
	}
	
	public void startBeam(Vector2f pos)
	{
		if (!mainPlayer.getBeam().isActive())
		{
			float ammo = mainPlayer.getEnergyYellow();
			
			if (ammo >= 1)
			{
				mainPlayer.decreaseEnergyYellow(1);
				// start beam
				Darklord.sounds.laser.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
				Vector2f dir = pos.sub(mainPlayer.getCenter());
				mainPlayer.getBeam().setStart(mainPlayer.getCenter());
				mainPlayer.getBeam().setDirection(dir);
				mainPlayer.getBeam().calcEnd(map);
				mainPlayer.getBeam().setActive(true);
				mainPlayer.getBeam().resetLifetime();
				
				mainPlayer.getBeam().damageEnemies(map.getEnemies());
			}
		}
	}
	
	/**
	 * reaction to a pressed mouse button if in developer mode
	 * @param pos
	 * @param button
	 */
	public void mouseDownReactionDev(Vector2f pos, int button)
	{
		Vector2f mouseGrid = new Vector2f(pos.getX()/this.gridSize, pos.getY()/this.gridSize);
		// translate grid
//		mouseGrid.print();
		mouseGrid.setX(mouseGrid.getX()-this.getPosX());
		mouseGrid.setY(mouseGrid.getY()-this.getPosY());
//		mouseGrid.print();
//
//		System.out.println("pos: ("+this.getPosX()+", "+this.getPosY()+")");
		
//		System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
		int x_int = (int)Math.floor(mouseGrid.getX());
		int y_int = (int)Math.floor(mouseGrid.getY());

		// left mouse button
		if (button == 0)
		{
			this.map.getBlockAt(x_int, y_int).setType(DevModeSettings.getActiveBLock());
		}
		
		// right mouse button
		if (button == 1)
		{
			map.setBlock(x_int, y_int, BlockType.BLOCK_NONE);
			switch (DevModeSettings.getActiveEnemy())
			{
			case 0:
				map.getEnemies().add(new EnemyRandomMove(x_int, y_int));
				break;
			case 1:
				map.getEnemies().add(new StaticEnemyCrystal(x_int, y_int));
				break;
			default:
				map.getEnemies().add(new EnemyRandomMove(x_int, y_int));
				break;
			}
//			System.out.println("add collectable "+DevModeSettings.activeCollectable+ "at x: "+(x_int+0.25f)+", y: "+(y_int+0.25f));
//			this.map.collectableObjects.add(new Collectable(DevModeSettings.activeCollectable, x_int+0.25f, y_int+0.25f));
		
		}
	}
	
	/**
	 * handles collision of a projectile with an block
	 * @param p the projectile
	 * @param x the blocks x component in the grid
	 * @param y the blocks y component in the grid
	 * @return return true if projectile and block collide
	 */
	public boolean collideProjectileWithBlock(Projectile p, int x, int y)
	{
		boolean collide = false;
		
//		if ((x<0)||(x>=map.getMapSizeX())||(y<0)||(y>=map.getMapSizeY())) return false;
		
		Block tmpBlock = map.getBlockAt(x, y);
		
		if (tmpBlock == null)
		{
			return true;
		}
		if (tmpBlock.isSolid() && !tmpBlock.isTransparent())
		{
			collide = p.collideWithBlock(x, y);
		}
		
		return collide;
	}
	
	public void writePlayerToFile(Player thePlayer)
	{
		Print.outln("saving player");
		
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		
		String fileName = "./"+map.getName()+"/player.ser";
		
		try {
		  fos = new FileOutputStream(fileName);
		  oos = new ObjectOutputStream(fos);
		  
		  oos.writeObject(thePlayer);
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (oos != null) try { oos.close(); } catch (IOException e) {}
		  if (fos != null) try { fos.close(); } catch (IOException e) {}
		}
	}
	
	public void zoomIn()
	{
		zoom *= 1.2;
		if (zoom > zoomMax) zoom = zoomMax;
	}
	
	public void zoomOut()
	{
		zoom /= 1.2;
		if (zoom < zoomMin) zoom = zoomMin;
	}
	
	public Player readPlayerFromFile()
	{
		Player tmpPlayer = null;
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		
		Print.outln("load player");
		
		String fileName = "./"+map.getName()+"/player.ser";
		try {
			  fis = new FileInputStream(fileName);
			  ois = new ObjectInputStream(fis);

			  tmpPlayer = (Player)ois.readObject();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
			  if (ois != null) try { ois.close(); } catch (IOException e) {}
			  if (fis != null) try { fis.close(); } catch (IOException e) {}
			}
		return tmpPlayer;
	}
	
	public void updatePlayersProjectiles(float dt)
	{
		// update players projectiles
		for (Iterator<Projectile> object = projectiles.iterator(); object.hasNext();)
		{
			Projectile tmp = object.next();
			tmp.update(dt);
			if (!tmp.isActive())
			{
				object.remove();
				continue;
			}
			
			int projectileX = (int)Math.floor(tmp.getPosX());
			int projectileY = (int)Math.floor(tmp.getPosY());
			
			boolean destroyed = false;
			
			// check for collision with block
			
			if (tmp instanceof BulletProjectile)
			{
				int tmpProjectileX, tmpProjectileY;
				// check block top left
				tmpProjectileX = projectileX;
				tmpProjectileY = projectileY;
				if (collideProjectileWithBlock(tmp, tmpProjectileX, tmpProjectileY))
				{
					object.remove();
					destroyed = true;
					

					if (tmp.getType() == 1 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == BlockType.BLOCK_CRYSTAL)	// blue projectile
					{
						map.setBlock(tmpProjectileX, tmpProjectileY, BlockType.BLOCK_NONE);
//						System.out.println("ice crystal destroyed");
//						mainPlayer.decreaseBlocksBlue();
					}
					
					if (tmp.getType() == 2 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == BlockType.BLOCK_PLANTS)	// green projectile
					{
						map.setBlock(tmpProjectileX, tmpProjectileY, BlockType.BLOCK_NONE);
//						System.out.println("plants destroyed");
//						mainPlayer.decreaseBlocksGreen();
					}
					continue;
				}
				
				// check block top right
				tmpProjectileX = projectileX+1;
				tmpProjectileY = projectileY;
				if (collideProjectileWithBlock(tmp, tmpProjectileX, tmpProjectileY))
				{
					object.remove();
					destroyed = true;
					

					if (tmp.getType() == 1 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == BlockType.BLOCK_CRYSTAL)	// blue projectile
					{
						map.setBlock(tmpProjectileX, tmpProjectileY, BlockType.BLOCK_NONE);
//						System.out.println("ice crystal destroyed");
//						mainPlayer.decreaseBlocksBlue();
					}
					
					if (tmp.getType() == 2 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == BlockType.BLOCK_PLANTS)	// green projectile
					{
						map.setBlock(tmpProjectileX, tmpProjectileY, BlockType.BLOCK_NONE);
						System.out.println("plants destroyed");
//						mainPlayer.decreaseBlocksGreen();
					}
					continue;
				}
				
				// check block bottom left
				tmpProjectileX = projectileX;
				tmpProjectileY = projectileY+1;
				if (collideProjectileWithBlock(tmp, tmpProjectileX, tmpProjectileY))
				{
					object.remove();
					destroyed = true;
					

					if (tmp.getType() == 1 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == BlockType.BLOCK_CRYSTAL)	// blue projectile
					{
						map.setBlock(tmpProjectileX, tmpProjectileY, BlockType.BLOCK_NONE);
//						System.out.println("ice crystal destroyed");
//						mainPlayer.decreaseBlocksBlue();
					}
					
					if (tmp.getType() == 2 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == BlockType.BLOCK_PLANTS)	// green projectile
					{
						map.setBlock(tmpProjectileX, tmpProjectileY, BlockType.BLOCK_NONE);
//						System.out.println("plants destroyed");
//						mainPlayer.decreaseBlocksGreen();
					}
					continue;
				}
				
				// check block bottom right
				tmpProjectileX = projectileX+1;
				tmpProjectileY = projectileY+1;
				if (collideProjectileWithBlock(tmp, tmpProjectileX, tmpProjectileY))
				{
					object.remove();
					destroyed = true;
					

					if (tmp.getType() == 1 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == BlockType.BLOCK_CRYSTAL)	// blue projectile
					{
						map.setBlock(tmpProjectileX, tmpProjectileY, BlockType.BLOCK_NONE);
//						System.out.println("ice crystal destroyed");
//						mainPlayer.decreaseBlocksBlue();
					}
					
					if (tmp.getType() == 2 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == BlockType.BLOCK_PLANTS)	// green projectile
					{
						map.setBlock(tmpProjectileX, tmpProjectileY, BlockType.BLOCK_NONE);
//						System.out.println("plants destroyed");
//						mainPlayer.decreaseBlocksGreen();
					}
					continue;
				}
			}
			
//			destroy |= collideProjectileWithBlock(tmp, projectileX, projectileY);
//			destroy |= collideProjectileWithBlock(tmp, projectileX+1, projectileY);
//			destroy |= collideProjectileWithBlock(tmp, projectileX, projectileY+1);
//			destroy |= collideProjectileWithBlock(tmp, projectileX+1, projectileY+1);
			
			if (!destroyed)
			{
				// check for collision with enemy
				for (Iterator<Enemy> obj2 = map.getEnemies().iterator(); obj2.hasNext();)
				{
					Enemy e = obj2.next();
					boolean collideWithEnemy;
					if (tmp instanceof ShockWave)
					{
						if (e.collideWithRotation(tmp))
						{
							if (e.addVenom(new Venom(1.f, 0.5f, 4.f)))
							{
								addBamBubble(e);
								Darklord.sounds.explosion.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
							}
						}
					} else
					{
						if (e.collide(tmp))
						{
							Darklord.sounds.explosion.playAsSoundEffect(1.f, Darklord.sounds.volumeEffects, false);
							
//								System.out.println("Enemy damaged by projectile");
							
//								mainPlayer.decreaseBlocksRed();
							if (e.decreaseHp(tmp)) 
							{
								mainPlayer.addXp(e.getXp());
								Vector2f tmpPos = new Vector2f(mainPlayer.getCenter());
								tmpPos.addY(-1.0f);
								movingTexts.add(new MovingText("+"+e.getXp()+"XP", tmpPos));
								obj2.remove();
							}
							
//							if (tmp.getType() == 0 && e instanceof StaticEnemyCrystal)	// red projectile
//							{
//								System.out.println("crystal damaged by projectile");
////								mainPlayer.decreaseBlocksBlue();
//								if (e.decreaseHp(tmp.getDamage())) obj2.remove();	
//							}
							
							object.remove();
							break;
						}
					}
					
					
				}
			}
		}
	}
	
	public void addBamBubble(Enemy e)
	{
		Vector2f position = e.getCenter();
		Vector2f direction = RandomGenerator.getRandomVector();
		direction.normalize();
		Vector2f start = position.add(direction.mul(0.5f));
		Vector2f stop = start.add(direction.mul(3));
		MovingSprite tmpBubble = new MovingSprite(start, stop);
		tmpBubble.setTextureRegion(new TextureRegion(6*128, 3*128, 2*128, 1*128));
		bubbles.add(tmpBubble);
		e.resetBubbleTimer();
	}
	
	public void updateHostileProjectiles(float dt)
	{
		// update hostile projectiles
		for (Iterator<Projectile> object = hostileProjectiles.iterator(); object.hasNext();)
		{
			Projectile tmp = object.next();
			tmp.update(dt);
			
			if (!tmp.isActive())
			{
				object.remove();
				continue;
			}
			
			int projectileX = (int)Math.floor(tmp.getPosX());
			int projectileY = (int)Math.floor(tmp.getPosY());
			
			// check for collision with block
			boolean destroyed = false;
			
			int tmpProjectileX, tmpProjectileY;
			// check block top left
			tmpProjectileX = projectileX;
			tmpProjectileY = projectileY;
			if (collideProjectileWithBlock(tmp, tmpProjectileX, tmpProjectileY))
			{
				object.remove();
				destroyed = true;
				continue;
			}
			
			if (tmp.collide(mainPlayer))
			{
				mainPlayer.decreaseHp(tmp.getDamage());
				Print.outln("player lost "+tmp.getDamage()+" HP (hit by projectile)");
				object.remove();
				destroyed = true;
			}
			
			// check block top right
			tmpProjectileX = projectileX+1;
			tmpProjectileY = projectileY;
			if (collideProjectileWithBlock(tmp, tmpProjectileX, tmpProjectileY))
			{
				object.remove();
				destroyed = true;
				continue;
			}
			
			// check block bottom left
			tmpProjectileX = projectileX;
			tmpProjectileY = projectileY+1;
			if (collideProjectileWithBlock(tmp, tmpProjectileX, tmpProjectileY))
			{
				object.remove();
				destroyed = true;
				continue;
			}
			
			// check block bottom right
			tmpProjectileX = projectileX+1;
			tmpProjectileY = projectileY+1;
			if (collideProjectileWithBlock(tmp, tmpProjectileX, tmpProjectileY))
			{
				object.remove();
				destroyed = true;
				continue;
			}
		}
	}
	
	public void updateCollectibles()
	{
		// collect Collectables
		for (Iterator<Collectable> object = map.getCollectableObjects().iterator(); object.hasNext();)
		{
			Collectable tmp = object.next();
			
			if((tmp.getPos().sub(mainPlayer.getPos())).length() > maxUpdateDistance) continue;
			
//			if (Collider.collideBorders(mainPlayer.getPosX(), mainPlayer.getPosX()+mainPlayer.getSizeX(), mainPlayer.getPosY(), mainPlayer.getPosY()+mainPlayer.getSizeY(),
//					tmp.getX(), tmp.getX()+tmp.getSize(), tmp.getY(), tmp.getY()+tmp.getSize()))
			if (mainPlayer.collide(tmp))
			{
//				System.out.println("collide with collectable");
				mainPlayer.addItem(tmp.getType());
				object.remove();
			}
		}
	}
	
	public void updateEnemies(float dt)
	{
		// collide player with enemies & move enemies
		for (Iterator<Enemy> obj2 = map.getEnemies().iterator(); obj2.hasNext();)
		{
			Enemy e = obj2.next();
			
//			if (e.collideWithRotation(testEnemy)) Print.outln("collide test!");
			Print.outln("e.canGenerateBubble(): "+e.canGenerateBubble());
			
			if (e.isDamaged() && e.canGenerateBubble())
			{
				// generate "BAM" bubble
				addBamBubble(e);
			}
			
			if (e.isDead())
			{
				obj2.remove();
				continue;
			}
			
			if((e.getPos().sub(mainPlayer.getPos())).length() > maxUpdateDistance) continue;
			
			if (e instanceof EnemyRandomMove)
			{
				if(!((EnemyRandomMove)e).isMoving())
				{
					moveRandom((EnemyRandomMove)e);
				}
			}
			
			if (mainPlayer.collide(e))
			{
				if (!mainPlayer.isInvulnerable())
				{
					mainPlayer.startInvulnerableOnContactTimer();
					if (mainPlayer.decreaseHp(e.getDmgOnContact()))
					{
						System.out.println("Player died!");
					}
					System.out.println("add "+e.getDmgOnContact()+" dmg to player ("+mainPlayer.getHp()+" hp)");
				}
			}
			
			e.update(dt);
			
			// special update for StaticEnemyCrystal to be able to spawn projectiles
			if (e instanceof StaticEnemyCrystal) ((StaticEnemyCrystal) e).update(dt, this);
		}
	}
	
	public void updateBlocks()
	{
		int minX = (int)Math.floor(mainPlayer.getPosX()-maxUpdateDistance);
		int maxX = (int)Math.ceil(mainPlayer.getPosX()+maxUpdateDistance);
		int minY = (int)Math.floor(mainPlayer.getPosY()-maxUpdateDistance);
		int maxY = (int)Math.ceil(mainPlayer.getPosY()+maxUpdateDistance);
		

		for (int i=minX;i<maxX;i++)
		{
			for (int j=minY;j<maxY;j++)
			{
				Block tmpBlock = map.getBlockAt(i, j);
				if (tmpBlock != null)
				{
					tmpBlock.update();
				}
			}
		}
	}
	
	public void updateFog()
	{
		// update fog
		Vector2f playerPos =mainPlayer.getCenter().mul(map.getFogDensity());
		float range = mainPlayer.getVisualRangeMax();
//		int xLower = (int)Math.round(playerPos.getX()-range);
//		if (xLower < 0) xLower = 0;
//		int xUpper = (int)Math.round(playerPos.getX()+range);
//		if (xUpper >= map.getMapSizeX()*map.getFogDensity()) xUpper = map.getMapSizeX()*map.getFogDensity()-1;

		int xLower = (int)Math.round(playerPos.getX()-range);
		int xUpper = (int)Math.round(playerPos.getX()+range);

		
		for (int i=xLower;i<=xUpper;i++)
		{
			// pythagoras
			double sqrt = Math.sqrt(range*range-(playerPos.getX()-i)*(playerPos.getX()-i));
//			int yLower = (int)Math.round(playerPos.getY() - sqrt);
//			if (yLower < 0) yLower = 0;
//			int yUpper = (int)Math.round(playerPos.getY() + sqrt);
//			if (yUpper >= map.getMapSizeY()*map.getFogDensity()) yUpper = map.getMapSizeY()*map.getFogDensity()-1;

			int yLower = (int)Math.round(playerPos.getY() - sqrt);
			int yUpper = (int)Math.round(playerPos.getY() + sqrt);

			
			for (int j=yLower;j<=yUpper;j++)
			{
				Vector2f fogTile = new Vector2f(i, j);
				float relativeRadius = (playerPos.sub(fogTile)).length()/range;
				float newFogValue;
				if (relativeRadius < mainPlayer.getVisualRangeRelative())
				{
					newFogValue = 0.f;
				} else
				{
					newFogValue= (float)(((playerPos.sub(fogTile)).length()-mainPlayer.getVisualRangeMin())/(mainPlayer.getVisualRangeMax()-mainPlayer.getVisualRangeMin()));
				}
				
				if (newFogValue < this.map.getFogAt(i, j))
				{
					this.map.setFogAt(i, j, newFogValue);
				}
			}
		}
	}
	
	public boolean checkHighwayLeftRight(int posX, int posY)
	{
		boolean noHighway;
		
		// left/right highway
		Block tmpBlock = map.getBlockAt(posX, posY);
		if (tmpBlock != null)
		{
			noHighway = !(tmpBlock.getOverlay() instanceof FloorCrystalRed);
			if (noHighway) return false;
			 
			noHighway = !(tmpBlock.getBackground() instanceof FloorStone);
			if (noHighway) return false;
		}

		tmpBlock = map.getBlockAt(posX, posY-1);
		if (tmpBlock != null)
		{
			noHighway = !(tmpBlock.getBackground() instanceof FloorStone);
			if (noHighway) return false;
		}
		
		tmpBlock = map.getBlockAt(posX, posY+1);
		if (tmpBlock != null)
		{
			noHighway = !(tmpBlock.getBackground() instanceof FloorStone);
			if (noHighway) return false;
		}
		
		tmpBlock = map.getBlockAt(posX, posY-2);
		if (tmpBlock != null)
		{
			noHighway = !(tmpBlock.getOverlay() instanceof Wall);
			if (noHighway) return false;
			noHighway = !((Wall)tmpBlock.getOverlay()).isTop();
			if (noHighway) return false;
		}
		
		tmpBlock = map.getBlockAt(posX, posY+2);
		if (tmpBlock != null)
		{
			noHighway = !(tmpBlock.getOverlay() instanceof Wall);
			if (noHighway) return false;
			noHighway = !((Wall)tmpBlock.getOverlay()).isBottom();
			if (noHighway) return false;
		}
		
		return true;
	}

	public boolean checkHighwayUpDown(int posX, int posY)
	{
		boolean noHighway;
		
		// left/right highway
		noHighway = !(map.getBlockAt(posX, posY).getOverlay() instanceof FloorCrystalRed);
		if (noHighway) return false;
		 
		noHighway = !(map.getBlockAt(posX, posY).getBackground() instanceof FloorStone);
		if (noHighway) return false;
		
		noHighway = !(map.getBlockAt(posX-1, posY).getBackground() instanceof FloorStone);
		if (noHighway) return false;
		
		noHighway = !(map.getBlockAt(posX+1, posY).getBackground() instanceof FloorStone);
		if (noHighway) return false;
		
		noHighway = !(map.getBlockAt(posX-2, posY).getOverlay() instanceof Wall);
		if (noHighway) return false;
		noHighway = !((Wall)map.getBlockAt(posX-2, posY).getOverlay()).isRight();
		if (noHighway) return false;
		
		noHighway = !(map.getBlockAt(posX+2, posY).getOverlay() instanceof Wall);
		if (noHighway) return false;
		noHighway = !((Wall)map.getBlockAt(posX+2, posY).getOverlay()).isLeft();
		if (noHighway) return false;
		
		return true;
	}
	
	public boolean checkHighway()
	{
		int posX = (int)Math.floor(mainPlayer.getCenter().getX());
		int posY = (int)Math.floor(mainPlayer.getCenter().getY());
		
		if (checkHighwayLeftRight(posX, posY)) return true;
		if (checkHighwayUpDown(posX, posY)) return true;

		return false;
	}
	
	public void setActive(boolean theActive)
	{
		mainUI.setMapActive(theActive, map.levelStructure);
	}
	
	public void toggleMapActive()
	{
		if (mainUI.isMapActive())
		{
			mainUI.setMapActive(false, map.levelStructure);
		} else
		{
			mainUI.setMapActive(true, map.levelStructure);
		}
	}
	
	public boolean isMapActive()
	{
		return mainUI.isMapActive();
	}
	
	public void update(float dt)
	{
		// update mouse click timer
		buildTimer.update(dt);
		
		// update level position
		
		float playerScreenX = mainPlayer.getPosX() + this.getPosX();
		float playerScreenY = mainPlayer.getPosY() + this.getPosY();
		
//		System.out.println(mainPlayer.getPosX());
//		System.out.println(this.getPlayerBorderX()*(Darklords.resX/gridSize));
		
		float diffLeft = playerScreenX - this.getPlayerBorderX()*(resX/gridSize);
		float diffRight = playerScreenX - (mainPlayer.getSizeX() - this.getPlayerBorderX())*(resX/gridSize);
		float diffTop = playerScreenY - (mainPlayer.getSizeY() - this.getPlayerBorderY())*(resY/gridSize);
		float diffBottom =  playerScreenY - this.getPlayerBorderY()*(resY/gridSize);
		
		if (diffLeft < 0.f)
		{
			this.setPosX(this.getPosX() - diffLeft);
		}
		
		if (diffRight > 0.f)
		{
			this.setPosX(this.getPosX() - diffRight);
		}
		
		if (diffTop > 0.f)
		{
			this.setPosY(this.getPosY() - diffTop);
		}
		
		if (diffBottom < 0.f)
		{
			this.setPosY(this.getPosY() - diffBottom);
		}
		
		mainPlayer.update(dt);
		
//		testEnemy.setPos(mainPlayer.getPos());
		
		// TODO: reintegrate
//		if (checkHighway())
//		{
//			mainPlayer.setSpeedMultiplier(highwaySpeedMultiplyer);
//		} else
//		{
//			mainPlayer.setSpeedMultiplier(1.f);
//		}
		
//		if (mainPlayer.getPosX() < map.getCurrentGridX()*map.getGridSizeX())
//		{
//			map.decreaseCurrentGridX();
//		} else
//		{
//			if (mainPlayer.getPosX() > (map.getCurrentGridX()+1)*map.getGridSizeX())
//			{
//				map.increaseCurrentGridX();
//			}
//		}
//		
//		if (mainPlayer.getPosY() < map.getCurrentGridY()*map.getGridSizeY())
//		{
//			map.decreaseCurrentGridY();
//		} else
//		{
//			if (mainPlayer.getPosY() > (map.getCurrentGridY()+1)*map.getGridSizeY())
//			{
//				map.increaseCurrentGridY();
//			}
//		}

		updateCollectibles();
		
		// collide with chests
		for (Iterator<Chest> object = map.getChests().iterator(); object.hasNext();)
		{
			Chest tmp = object.next();
//			if (Collider.collideBorders(mainPlayer.getPosX(), mainPlayer.getPosX()+mainPlayer.getSizeX(), mainPlayer.getPosY(), mainPlayer.getPosY()+mainPlayer.getSizeY(),
//					tmp.getX(), tmp.getX()+tmp.getSize(), tmp.getY(), tmp.getY()+tmp.getSize()))
			if (mainPlayer.collide(tmp))
			{
//				System.out.println("collide with collectable");
				tmp.onCollision(mainPlayer);
				object.remove();
			}
		}
		
		updatePlayersProjectiles(dt);
		
		updateHostileProjectiles(dt);
		
		updateEnemies(dt);
		
		// update blocks
		
		updateBlocks();
		
		// update bubbles
		for (Iterator<MovingSprite> object = bubbles.iterator(); object.hasNext();)
		{
			MovingSprite tmp = object.next();
			tmp.update(dt);
			if (!tmp.isAlive()) object.remove();
		}
		
		// update map
		map.update(this);
		
		// update moving texts
		for (Iterator<MovingText> object = movingTexts.iterator();object.hasNext();)
		{
			MovingText tmpText = object.next();
			tmpText.update(dt);
			if (!tmpText.isAlive())
			{
				object.remove();
			}
		}
		
		updateFog();
		
		// update ui
		mainUI.update(this, dt);
	}

	public float getPlayerBorderX() {
		return playerBorderX;
	}

	public void setPlayerBorderX(float playerBorderX) {
		this.playerBorderX = playerBorderX;
	}

	public float getPlayerBorderY() {
		return playerBorderY;
	}

	public void setPlayerBorderY(float playerBorderY) {
		this.playerBorderY = playerBorderY;
	}
	
	/**
	 * moves an EnemyRandomMove in a random direction
	 * @param e enemy to move
	 */
	void moveRandom(EnemyRandomMove e)
	{
		int posX = (int)Math.round(e.getPosX());
		int posY = (int)Math.round(e.getPosY());
//		e.getPos().print();
		
		Vector2f dir = RandomGenerator.getRandomDirection();
		
//		System.out.print("move to dir: ");dir.print();
		
		Block tmpBlock = map.getBlockAt(posX+(int)dir.getX(), posY+(int)dir.getY());
		if (tmpBlock != null && !tmpBlock.isSolid())
		{
//			System.out.println("test");
			e.startMotion(dir);
		}
	}

	public int getResX() {
		return resX;
	}

	public void setResX(int resX) {
		this.resX = resX;
	}

	public int getResY() {
		return resY;
	}

	public void setResY(int resY) {
		this.resY = resY;
	}

	public void mouseWheelReaction(int wheel)
	{
		if (isMapActive())
		{
			if (wheel < 0)
	    	{
	    		mainUI.zoomMapOut();
	    	} else if (wheel > 0)
	    	{
	    		mainUI.zoomMapIn();
	    	}
		} else
		{
			if (wheel < 0)
	    	{
	    		zoomOut();
	    	} else if (wheel > 0)
	    	{
	    		zoomIn();
	    	}
		}
	}
	
//	public Level createCopy()
//	{
//		Level tmp = new Level(this);
//		return tmp;
//	}
}