package game;

import java.util.Random;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

/**
 * describes the architecture of an level with blocks and enemies
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Grid
{
	private Block[][] theGrid;
	private Vector<Enemy> enemies;
	public int gridSizeX, gridSizeY;
	private float[][] fogMap;
	private int fogDensity;
	
	public Grid()
	{
		this(10, 10);
	}
	
	public Grid(int x, int y)
	{
		gridSizeX = x;
		gridSizeY = y;
		theGrid = new Block[gridSizeX][gridSizeY];
		
		this.setFogDensity(5);
		fogMap = new float[getGridSizeX()*getFogDensity()][getGridSizeY()*getFogDensity()];
		for (int i=0;i<getGridSizeX()*getFogDensity();i++)
		{
			for (int j=0;j<getGridSizeY()*getFogDensity();j++)
			{
				fogMap[i][j] = 127;
			}
		}
		
		enemies = new Vector<Enemy>();
		initDefault();
//		initTest();
	}
	
	private void initDefault()
	{
		for (int i=0;i<gridSizeX;i++)
		{
			for (int j=0; j<gridSizeY;j++)
			{
				if ((i==0) || (j==0) || (i==gridSizeX-1) || (j==gridSizeY-1))
				{
					theGrid[i][j] = new Block(1);
				} else // inner level
				{
					theGrid[i][j] = new Block(0);
				}
			}
		}
	}
	
	public Block getBlockAt(int x, int y)
	{
		return theGrid[x][y];
	}
	
	private void initTest()
	{
		Random rnd = new Random();
		
		for (int i=0;i<gridSizeX;i++)
		{
			for (int j=0;j<gridSizeY;j++)
			{
				theGrid[i][j] = new Block(2);
			}
		}
		
		for (int i=0;i<gridSizeX;i++)
		{
			for (int j=0;j<gridSizeY;j++)
			{
//				grid[i][j] = new Block();
				if ((i==0) || (j==0) || (i==gridSizeX-1) || (j==gridSizeY-1))
				{
					theGrid[i][j].setType(1);
				} else // inner level
				{
					if (rnd.nextGaussian() < 0.5)
					{
						theGrid[i][j].setType(2);
					}
					if (rnd.nextGaussian() > 1.0)
					{
						int rndType = (int)Math.floor((1.f*rnd.nextInt()/Integer.MAX_VALUE+1)*0.5*3+3);
//						System.out.println(rndType);
						theGrid[i][j].setType(rndType);
						if (rnd.nextGaussian() > 0.3)
						{
							if ((i+1)<gridSizeX-1)
							{
//								System.out.println(i+1+ ", "+j);
								theGrid[i+1][j].setType(rndType);
							}
						}
					}
				}
			}
		}
		theGrid[1][4].setType(0);
		theGrid[1][3].setType(0);
		theGrid[2][3].setType(0);
		theGrid[7][7].setType(0);
//		enemies.add(new StaticEnemy(1.f, 4.f));
		enemies.add(new EnemyRandomMove(1.f, 4.f));
		enemies.add(new StaticEnemy(7.f, 7.f));
//		grid[(int)Math.round(mainPlayer.getPosX())][(int)Math.round(mainPlayer.getPosY())].setType(0);
		
//		collectableObjects.add(new Collectable(CollectableType.ABILITY_TELEPORT, 7.25f, 3.25f));
//		collectableObjects.add(new Collectable(CollectableType.ABILITY_DIGGING, 6.25f, 2.25f));
	}

	public void setTypeAt(int x_int, int y_int, int t)
	{
		theGrid[x_int][y_int].setType(t);
	}

	public Vector<Enemy> getEnemies()
	{
		return this.enemies;
	}

	public int getGridSizeX() {
		return gridSizeX;
	}

	public void setGridSizeX(int gridSizeX) {
		this.gridSizeX = gridSizeX;
	}

	public int getGridSizeY() {
		return gridSizeY;
	}

	public void setGridSizeY(int gridSizeY) {
		this.gridSizeY = gridSizeY;
	}

	public int getFogDensity() {
		return fogDensity;
	}

	public void setFogDensity(int fogDensity)
	{
		this.fogDensity = fogDensity;
	}

	public float getFogAt(int x, int y)
	{
		return fogMap[x][y];
	}

	public void setFogAt(int x, int y, float value)
	{
		if (value >= 1.f) value = 1.f;
		if (value < 0.f) value = 0.f;
		this.fogMap[x][y] = value;
	}
	
	public void drawFog()
	{
		GL11.glBegin(GL11.GL_QUADS);
		
		float posX = 0.f;
		float posY = 0.f;
		float sizeX = 1.f/getFogDensity();
		float sizeY = 1.f/getFogDensity();
				
		GL11.glVertex2f(posX, posY+sizeY);
		GL11.glVertex2f(posX+sizeX, posY+sizeY);
		GL11.glVertex2f(posX+sizeX, posY);
		GL11.glVertex2f(posX, posY);
		GL11.glEnd();
	}
	
}