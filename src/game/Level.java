package game;
import org.lwjgl.opengl.GL11;
//import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.util.Random;
import java.util.Vector;
import java.util.Iterator;

/**
 * describes the whole level including grid and player informations
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Level
{	
//	private int dimX, dimY;
	private static float playerBorderX = 0.3f;
	private static float playerBorderY = 0.3f; // in percent
	private float posX, posY;
//	private Block grid[][];
	public static float scale = 0.2f;
	public static  float gridSize = 80.f;
//	Vector<Collectable> collectableObjects;
	public Player mainPlayer;
	SelectBox mainSelectBox;
	public Map map;
	public Vector<Projectile> projectiles;
	public Vector<Projectile> hostileProjectiles;
	public static final int drawSize = 15;
	String name;

	public Level()
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
		name = "defaultMap.map";
		
//		initTest();
	}
	
	public Level(int sizeX, int sizeY)
	{
//		this.dimX = sizeX;
//		this.dimY = sizeY;
		posX = 1.f;
		posY = 1.f;
		name = "defaultMap.map";
		
        File f = new File(getName());
        if (f.exists())
        {
        	map = new Map();
        	map.readFile(getName());
        } else
        {
        	map = new Map(sizeX, sizeY);
        }
		
//		grid = new Block[dimX][dimY];
//		collectableObjects = new Vector<Collectable>();
		mainPlayer = new Player(map.start.getX(), map.start.getY());
//		Print.out("created Player at "); map.start.print();
		map.getBlockAt((int) mainPlayer.getPosX(), (int) mainPlayer.getPosY()).setType(0);
		mainSelectBox = new SelectBox();
		projectiles = new Vector<Projectile>();
		hostileProjectiles = new Vector<Projectile>();
		
//		map.readTextFile("test.txt");
//		mainPlayer.setPos(map.getStart());
		
//		initTest();
}
	
	public Level(String filename)
	{
		posX = 1.f;
		posY = 1.f;
		setName(filename);
		String fileExtension = filename.substring(filename.length()-3, filename.length());
		
        File f = new File(filename);
        if (f.exists())
        {
        	map = new Map();
        	if (fileExtension.equals("txt"))
        	{
        		map.readTextFile(filename);
        	} else
        	{
            	map.readFile(filename);
        	}
        } else
        {
        	map = new Map(10, 10);
        }
		
		mainPlayer = new Player(map.start.getX(), map.start.getY());
		map.getBlockAt((int) mainPlayer.getPosX(), (int) mainPlayer.getPosY()).setType(0);
		mainSelectBox = new SelectBox();
		projectiles = new Vector<Projectile>();
		hostileProjectiles = new Vector<Projectile>();
	}
	
	// copy constructor
	public Level(Level orig)
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
		if ((x < 0) || (y < 0) || (x >= map.getMapSizeX()) || (y >= map.getMapSizeY())) return false;
		return map.getBlockAt(x, y).isSolid();
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
			int type = map.getBlockAt(x, y).getType();
			if (map.getBlockAt(x, y).isDestroyable() && map.getBlockAt(x, y).attack())
			{
				//destroyed
				System.out.println("Block destroyed!");
				if (type == 1) mainPlayer.decreaseDiggingCount();
				Random rnd = new Random();
				float rndX = 0.20f*(1.f*rnd.nextInt()/Integer.MAX_VALUE);
				float rndY = 0.20f*(1.f*rnd.nextInt()/Integer.MAX_VALUE);
				System.out.println(rndX);
				map.collectableObjects.add(new Collectable(CollectableType.values()[type], x+0.25f+rndX, y+0.25f+rndY));
			}
			mainPlayer.startAttackBlockTimer();
		}
	}
	
	public boolean  collideWithBlock(float x, float y)
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
		
		float posXgrid = posX*(gridSize/Darklords.resX)*2.f;
		float posYgrid = -posY*(gridSize/Darklords.resY)*2.f;
		GL11.glTranslatef(posXgrid, posYgrid, 0.f);
		
		// TODO: fix orientation
		GL11.glScaled(1., -1., 1.);
		GL11.glTranslated(-1., -1., 0.);
//		GL11.glTranslated(-2.f*posX/(Darklords.resX), -2.f*posY/(Darklords.resY), 0.);
		GL11.glScaled(2.f*gridSize/(Darklords.resX), 2.f*gridSize/(Darklords.resY), 1.);
		
		// draw blocks
		
		Block backgroundBlock = new Block(0);
		
		int minX = Math.max(0, (int)mainPlayer.getPosX()-drawSize);
		int maxX = Math.min(map.getMapSizeX(), (int)mainPlayer.getPosX()+drawSize);
		int minY = Math.max(0, (int)mainPlayer.getPosY()-drawSize);
		int maxY = Math.min(map.getMapSizeY(), (int)mainPlayer.getPosY()+drawSize);
		
		
		Darklords.sprites01.begin();
		
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

				backgroundBlock.draw();
				map.getBlockAt(i, j).draw();
				GL11.glPopMatrix();
			}
		}
		
		Darklords.sprites01.end();
		
		// draw collectable objects
		for (Iterator<Collectable> object = map.collectableObjects.iterator(); object.hasNext();)
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
		
		// draw player
		GL11.glPushMatrix();
		GL11.glTranslated(mainPlayer.getPosX(), mainPlayer.getPosY(), 0.);
		mainPlayer.draw();
		GL11.glPopMatrix();
		
		// draw projectiles
		for (Iterator<Projectile> object = projectiles.iterator(); object.hasNext();)
		{
			Projectile tmp = object.next();
			
			GL11.glPushMatrix();
			GL11.glTranslated(tmp.getPosX(), tmp.getPosY(), 0.);
			tmp.draw();
			GL11.glPopMatrix();
		}
		
		// draw hostile projectiles
		for (Iterator<Projectile> object = hostileProjectiles.iterator(); object.hasNext();)
		{
			Projectile tmp = object.next();
			
			GL11.glPushMatrix();
			GL11.glTranslated(tmp.getPosX(), tmp.getPosY(), 0.);
			tmp.draw();
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
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
		Vector2f mouseGrid = new Vector2f(pos.getX()/this.gridSize, (Darklords.resY-pos.getY())/this.gridSize);
		// translate grid
		mouseGrid.setX(mouseGrid.getX()-this.getPosX());
		mouseGrid.setY(mouseGrid.getY()-this.getPosY());
		
//		System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
		int x_int = (int)Math.floor(mouseGrid.getX());
		int y_int = (int)Math.floor(mouseGrid.getY());
		
		if (x_int < map.getMapSizeX() && x_int >= 0 && y_int < map.getMapSizeY() && y_int >= 0)
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
	
	public int getNumerOfPojectiles(int type)
	{
		int amount = 0;
		for (Iterator<Projectile> object = projectiles.iterator(); object.hasNext();)
		{
			Projectile p = object.next();
			if (p.getType() == type) amount++;
		}
		return amount;
	}
	
	/**
	 * reaction to a pressed mouse button
	 * @param pos
	 * @param button
	 */
	public void mouseDownReaction(Vector2f pos, int button) // 0: left, 1: right
	{
		Vector2f mouseGrid = new Vector2f(pos.getX()/this.gridSize, (Darklords.resY-pos.getY())/this.gridSize);
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
					if (((mainPlayer.getDiggingNum()!=0) || map.getBlockAt(x_int, y_int).getType() != 1) && (Math.abs(this.mainPlayer.getPosX()+0.5f-mouseGrid.getX()) < 1.5f) && (Math.abs(this.mainPlayer.getPosY()+0.5f-mouseGrid.getY()) < 1.5f))
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
			startProjectile(mouseGrid);
		}

	}
	
	/**
	 * starts an projectile if ammo is available
	 * @param pos start position of the projectile
	 */
	public void startProjectile(Vector2f pos)
	{
		int ammo = 0;
		if (mainPlayer.getActiveProjectile() == 0) ammo = mainPlayer.getBlocksRed();
		if (mainPlayer.getActiveProjectile() == 1) ammo = mainPlayer.getBlocksBlue();
		if (mainPlayer.getActiveProjectile() == 2) ammo = mainPlayer.getBlocksGreen();
		
		if (ammo > getNumerOfPojectiles(mainPlayer.getActiveProjectile()))
		{
			// shoot
			Vector2f dir = new Vector2f();
			dir = pos.sub(mainPlayer.getCenter());
			dir.normalize();
			Vector2f position =  new Vector2f(mainPlayer.getCenter());
//			position = position.add(mainPlayer.getSize().mul(0.5f));
			position = position.add(dir.mul(0.5f));
			
			projectiles.add(new Projectile(position, dir, mainPlayer.getActiveProjectile()));
			System.out.println("Create Projectile at ("+pos.getX()+", "+pos.getY()+")");;
//			mainPlayer.switchActiveAbility();	
		}
	}
	
	/**
	 * reaction to a pressed mouse button if in developer mode
	 * @param pos
	 * @param button
	 */
	public void mouseDownReactionDev(Vector2f pos, int button)
	{
		Vector2f mouseGrid = new Vector2f(pos.getX()/this.gridSize, (Darklords.resY-pos.getY())/this.gridSize);
		// translate grid
		mouseGrid.setX(mouseGrid.getX()-this.getPosX());
		mouseGrid.setY(mouseGrid.getY()-this.getPosY());
		
//		System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
		int x_int = (int)Math.floor(mouseGrid.getX());
		int y_int = (int)Math.floor(mouseGrid.getY());

		// left mouse button
		if (button == 0)
		{
			this.map.setBlock(x_int, y_int, DevModeSettings.getActiveBLock());
		}
		
		// right mouse button
		if (button == 1)
		{
			map.setBlock(x_int, y_int, 0);
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
		
		if ((x<0)||(x>=map.getMapSizeX())||(y<0)||(y>=map.getMapSizeY())) return false;
		
		if (map.getBlockAt(x, y).isSolid())
		{
			collide = p.collideWithBlock(x, y);
		}
		
		return collide;
	}
	
	public void update()
	{
		// update level position
		
		float playerScreenX = mainPlayer.getPosX() + this.getPosX();
		float playerScreenY = mainPlayer.getPosY() + this.getPosY();
		
//		System.out.println(mainPlayer.getPosX());
//		System.out.println(this.getPlayerBorderX()*(Darklords.resX/gridSize));
		
		float diffLeft = playerScreenX - this.getPlayerBorderX()*(Darklords.resX/gridSize);
		float diffRight = playerScreenX - (mainPlayer.getSizeX() - this.getPlayerBorderX())*(Darklords.resX/gridSize);
		float diffTop = playerScreenY - this.getPlayerBorderY()*(Darklords.resY/gridSize);
		float diffBottom = playerScreenY - (mainPlayer.getSizeY() - this.getPlayerBorderY())*(Darklords.resY/gridSize);
		
		if (diffLeft < 0.f)
		{
			this.setPosX(this.getPosX() - diffLeft);
		}
		
		if (diffRight > 0.f)
		{
			this.setPosX(this.getPosX() - diffRight);
		}
		
		if (diffTop < 0.f)
		{
			this.setPosY(this.getPosY() - diffTop);
		}
		
		if (diffBottom > 0.f)
		{
			this.setPosY(this.getPosY() - diffBottom);
		}
		
		mainPlayer.update();
	
		// collect Collectables
		for (Iterator<Collectable> object = map.collectableObjects.iterator(); object.hasNext();)
		{
			Collectable tmp = object.next();
//			if (Collider.collideBorders(mainPlayer.getPosX(), mainPlayer.getPosX()+mainPlayer.getSizeX(), mainPlayer.getPosY(), mainPlayer.getPosY()+mainPlayer.getSizeY(),
//					tmp.getX(), tmp.getX()+tmp.getSize(), tmp.getY(), tmp.getY()+tmp.getSize()))
			if (mainPlayer.collide(tmp))
			{
				System.out.println("collide with collectable");
				mainPlayer.addItem(tmp.getType());
				object.remove();
			}
		}
		
		// update players projectiles
		for (Iterator<Projectile> object = projectiles.iterator(); object.hasNext();)
		{
			Projectile tmp = object.next();
			tmp.update();
			
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
				

				if (tmp.getType() == 1 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == 6)	// blue projectile
				{
					map.setBlock(tmpProjectileX, tmpProjectileY, 0);
					System.out.println("ice crystal destroyed");
					mainPlayer.decreaseBlocksBlue();
				}
				
				if (tmp.getType() == 2 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == 7)	// green projectile
				{
					map.setBlock(tmpProjectileX, tmpProjectileY, 0);
					System.out.println("plants destroyed");
					mainPlayer.decreaseBlocksGreen();
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
				

				if (tmp.getType() == 1 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == 6)	// blue projectile
				{
					map.setBlock(tmpProjectileX, tmpProjectileY, 0);
					System.out.println("ice crystal destroyed");
					mainPlayer.decreaseBlocksBlue();
				}
				
				if (tmp.getType() == 2 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == 7)	// green projectile
				{
					map.setBlock(tmpProjectileX, tmpProjectileY, 0);
					System.out.println("plants destroyed");
					mainPlayer.decreaseBlocksGreen();
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
				

				if (tmp.getType() == 1 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == 6)	// blue projectile
				{
					map.setBlock(tmpProjectileX, tmpProjectileY, 0);
					System.out.println("ice crystal destroyed");
					mainPlayer.decreaseBlocksBlue();
				}
				
				if (tmp.getType() == 2 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == 7)	// green projectile
				{
					map.setBlock(tmpProjectileX, tmpProjectileY, 0);
					System.out.println("plants destroyed");
					mainPlayer.decreaseBlocksGreen();
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
				

				if (tmp.getType() == 1 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == 6)	// blue projectile
				{
					map.setBlock(tmpProjectileX, tmpProjectileY, 0);
					System.out.println("ice crystal destroyed");
					mainPlayer.decreaseBlocksBlue();
				}
				
				if (tmp.getType() == 2 && map.getBlockAt(tmpProjectileX, tmpProjectileY).getType() == 7)	// green projectile
				{
					map.setBlock(tmpProjectileX, tmpProjectileY, 0);
					System.out.println("plants destroyed");
					mainPlayer.decreaseBlocksGreen();
				}
				continue;
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
					if (e.collide(tmp))
					{
						// red projectiles
						if (tmp.getType() == 0 && e instanceof EnemyRandomMove)	// red projectile
						{
							System.out.println("Enemy damaged by projectile");
							mainPlayer.decreaseBlocksRed();
							if (e.decreaseHp(tmp.getDamage())) obj2.remove();	
						}
						
						// blue projectiles
						if (tmp.getType() == 1 && e instanceof StaticEnemyCrystal)	// red projectile
						{
							System.out.println("crystal damaged by projectile");
							mainPlayer.decreaseBlocksBlue();
							if (e.decreaseHp(tmp.getDamage())) obj2.remove();	
						}
						
						object.remove();
						break;
					}
				}
			}
		}
		
		// update hostile projectiles
		for (Iterator<Projectile> object = hostileProjectiles.iterator(); object.hasNext();)
		{
			Projectile tmp = object.next();
			tmp.update();
			
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
		
		// collide player with enemies & move enemies
		for (Iterator<Enemy> obj2 = map.getEnemies().iterator(); obj2.hasNext();)
		{
			Enemy e = obj2.next();
			
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
			
			e.update();
			
			// special update for StaticEnemyCrystal to be able to spawn projectiles
			if (e instanceof StaticEnemyCrystal) ((StaticEnemyCrystal) e).update(this);
		}
		
		// update blocks
		
		for (int i=0;i<map.getMapSizeX();i++)
		{
			for (int j=0;j<map.getMapSizeY();j++)
			{
				map.getBlockAt(i, j).update();
				// workaround
//				if (map.getBlockAt(i, j).getType() == 6)	// ice block
//				{
//					Vector2f pos = new Vector2f(i, j);
//					Vector2f dir = new Vector2f(1.f, 0.f);
//					hostileProjectiles.add(new Projectile(pos, dir, 3));
//				}
			}
		}
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
		
		if (!map.getBlockAt(posX+(int)dir.getX(), posY+(int)dir.getY()).isSolid())
		{
//			System.out.println("test");
			e.startMotion(dir);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
//	public Level createCopy()
//	{
//		Level tmp = new Level(this);
//		return tmp;
//	}
}