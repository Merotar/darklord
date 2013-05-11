/*
 * describes the architecture of an level with blocks and enemies
 * 
 * @author Sebastian Artz
 * 
 */

package game;

import java.util.Random;
import java.util.Vector;

public class Grid
{
	private Block[][] theGrid;
	private Vector<Enemy> enemies;
	public static final int gridSize = 100;
	
	public Grid()
	{
		theGrid = new Block[gridSize][gridSize];
		enemies = new Vector<Enemy>();
		initTest();
	}
	
	private void initDefault()
	{
		for (int i=0;i<gridSize;i++)
		{
			for (int j=0; j<gridSize;j++)
			{
				theGrid[i][j] = new Block(0);
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
		
		for (int i=0;i<gridSize;i++)
		{
			for (int j=0;j<gridSize;j++)
			{
				theGrid[i][j] = new Block(2);
			}
		}
		
		for (int i=0;i<gridSize;i++)
		{
			for (int j=0;j<gridSize;j++)
			{
//				grid[i][j] = new Block();
				if ((i==0) || (j==0) || (i==gridSize-1) || (j==gridSize-1))
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
							if ((i+1)<gridSize-1)
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
//		enemies.add(new StaticEnemy(1.f, 4.f));
		enemies.add(new EnemyRandomMove(1.f, 4.f));
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
	
}