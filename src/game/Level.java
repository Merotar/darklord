/*
 * describes the whole level including grid and player informations
 * 
 * @author Sebastian Artz
 * 
 */

package game;
import org.lwjgl.opengl.GL11;
//import org.lwjgl.util.vector.Vector2f;

import game.EnemyRandomMove.Direction;

import java.util.Random;
import java.util.Vector;
import java.util.Iterator;



public class Level
{
	public static class DevModeSettings
	{
		
		static int maxActiveBlock = 4;
//		static int maxActiveCollectable = 2;
		static int activeBlock = 1;
		static CollectableType activeCollectable = CollectableType.ABILITY_DIGGING;
		
		static void increaseActiveBlock()
		{
			activeBlock++;
			if (activeBlock > maxActiveBlock) activeBlock = 0;
		}
		
		static int getActiveBLock()
		{
			return activeBlock;
		}
		
		static void increaseActiveCollectable()
		{
			if (activeCollectable == CollectableType.ABILITY_TELEPORT)
			{
				activeCollectable = CollectableType.BLOCK_BROWN;
				return;
			}
			if (activeCollectable == CollectableType.BLOCK_BROWN)
			{
				activeCollectable = CollectableType.BLOCK_RED;
				return;
			}			
			if (activeCollectable == CollectableType.BLOCK_RED)
			{
				activeCollectable = CollectableType.ABILITY_DIGGING;
				return;
			}
			if (activeCollectable == CollectableType.ABILITY_DIGGING)
			{
				activeCollectable = CollectableType.ABILITY_TELEPORT;
				return;
			}
		}
		
		static CollectableType getActiveCollectable()
		{
			return activeCollectable;
		}
	}
	
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
	public static final int drawSize = 7;
	
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
		
//		initTest();
	}
	
	public Level(int sizeX, int sizeY)
	{
//		this.dimX = sizeX;
//		this.dimY = sizeY;
		posX = 1.f;
		posY = 1.f;
		map = new Map(sizeX, sizeY);
//		grid = new Block[dimX][dimY];
//		collectableObjects = new Vector<Collectable>();
		mainPlayer = new Player(Grid.gridSize/2, Grid.gridSize/2);
		map.getBlockAt((int) mainPlayer.getPosX(), (int) mainPlayer.getPosY()).setType(0);
		mainSelectBox = new SelectBox();
		projectiles = new Vector<Projectile>();
		
//		initTest();
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
		return map.dimX;
	}

	public void setDimX(int dimX) {
		map.dimX = dimX;
	}

	public int getDimY() {
		return map.dimY;
	}

	public void setDimY(int dimY) {
		map.dimY = dimY;
	}
	
	public boolean isBlockSolid(int x, int y)
	{
		if ((x < 0) || (y < 0) || (x >= Grid.gridSize) || (y >= Grid.gridSize)) return false;
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
		
		GL11.glScaled(1., -1., 1.);
		GL11.glTranslated(-1., -1., 0.);
//		GL11.glTranslated(-2.f*posX/(Darklords.resX), -2.f*posY/(Darklords.resY), 0.);
		GL11.glScaled(2.f*gridSize/(Darklords.resX), 2.f*gridSize/(Darklords.resY), 1.);
		
		// draw blocks
		
		Block backgroundBlock = new Block(0);
		
		int minX = Math.max(0, (int)mainPlayer.getPosX()-drawSize);
		int maxX = Math.min(Grid.gridSize, (int)mainPlayer.getPosX()+drawSize);
		int minY = Math.max(0, (int)mainPlayer.getPosY()-drawSize);
		int maxY = Math.min(Grid.gridSize, (int)mainPlayer.getPosY()+drawSize);
		
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
		
		for (Iterator<Projectile> object = projectiles.iterator(); object.hasNext();)
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
	
	public void mousePositionReaction(Vector2f pos)
	{
		Vector2f mouseGrid = new Vector2f(pos.getX()/this.gridSize, (Darklords.resY-pos.getY())/this.gridSize);
		// translate grid
		mouseGrid.setX(mouseGrid.getX()-this.getPosX());
		mouseGrid.setY(mouseGrid.getY()-this.getPosY());
		
//		System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
		int x_int = (int)Math.floor(mouseGrid.getX());
		int y_int = (int)Math.floor(mouseGrid.getY());
		
//		if (x_int < map.dimX && x_int >= 0 && y_int < map.dimY && y_int >= 0)
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
			Vector2f dir = new Vector2f();
			dir = mouseGrid.sub(mainPlayer.getCenter());
			dir.normalize();
			Vector2f position =  new Vector2f(mainPlayer.getCenter());
//			position = position.add(mainPlayer.getSize().mul(0.5f));
			position = position.add(dir.mul(0.5f));
			
			projectiles.add(new Projectile(position, dir));
			System.out.println("Create Projectile at ("+mouseGrid.getX()+", "+mouseGrid.getY()+")");;
//			mainPlayer.switchActiveAbility();
		}

	}
	
	public void mouseDownReactionDev(Vector2f pos, int button)
	{
		Vector2f mouseGrid = new Vector2f(pos.getX()/this.gridSize, (Darklords.resY-pos.getY())/this.gridSize);
		// translate grid
		mouseGrid.setX(mouseGrid.getX()-this.getPosX());
		mouseGrid.setY(mouseGrid.getY()-this.getPosY());
		
//		System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
		int x_int = (int)Math.floor(mouseGrid.getX());
		int y_int = (int)Math.floor(mouseGrid.getY());

		if (button == 0)
		{
			this.map.setBlock(x_int, y_int, DevModeSettings.getActiveBLock());
		}
		if (button == 1)
		{
			System.out.println("add collectable "+DevModeSettings.activeCollectable+ "at x: "+(x_int+0.25f)+", y: "+(y_int+0.25f));
			this.map.collectableObjects.add(new Collectable(DevModeSettings.activeCollectable, x_int+0.25f, y_int+0.25f));
		}
	}
	
	public boolean collideProjectileWithBlock(Projectile p, int x, int y)
	{
		boolean collide = false;
		
		if ((x<0)||(x>=Grid.gridSize)||(y<0)||(y>=Grid.gridSize)) return false;
		
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
		
		// update projectiles
		
		for (Iterator<Projectile> object = projectiles.iterator(); object.hasNext();)
		{
			Projectile tmp = object.next();
			tmp.update();
			
			int projectileX = (int)Math.floor(tmp.getPosX());
			int projectileY = (int)Math.floor(tmp.getPosY());
			
//			System.out.println(projectileX+", "+projectileY);
			boolean destroy = false;
			destroy |= collideProjectileWithBlock(tmp, projectileX, projectileY);
			destroy |= collideProjectileWithBlock(tmp, projectileX+1, projectileY);
			destroy |= collideProjectileWithBlock(tmp, projectileX, projectileY+1);
			destroy |= collideProjectileWithBlock(tmp, projectileX+1, projectileY+1);
			
			if (destroy)
			{
				object.remove();
				System.out.println("Projectile destroyed by block");
			} else
			{
				// check for collision with enemy
				for (Iterator<Enemy> obj2 = map.getEnemies().iterator(); obj2.hasNext();)
				{
					Enemy e = obj2.next();
					if (e.collide(tmp))
					{
						System.out.println("Enemy destroyed by projectile");
						if (e.decreaseHp(1)) obj2.remove();
						object.remove();
						break;
					}
				}
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
	
//	public Level createCopy()
//	{
//		Level tmp = new Level(this);
//		return tmp;
//	}
}