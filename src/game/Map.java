package game;

import java.io.Serializable;
import java.util.Vector;

/**
 * describes the grid and collectable objects of the map
 * 
 * TODO: reorganize, merge with Level/Grid
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Map implements Serializable
{
//	public int dimX, dimY;
	Vector2f start, exit;
	private Grid worldGrid;
//	private int gridRightDimX =1;
//	private int gridRightDimY =1;
//	private Block grid[][];
	public Vector<Collectable> collectableObjects;
//	public Vector<Enemy> enemies;


	
	public Map()
	{
		start = new Vector2f(3.f, 3.f);
		exit = new Vector2f();
//		dimX = 10;
//		dimY = 10;
		worldGrid = new Grid(10, 10);
		collectableObjects = new Vector<Collectable>();
//		enemies = new Vector<Enemy>();
		
//		initTest();
	}
	
	public Map(int x, int y)
	{
		start = new Vector2f(3.f, 3.f);
		exit = new Vector2f();
//		dimX = x;
//		dimY = y;
		worldGrid = new Grid(x, y);
		collectableObjects = new Vector<Collectable>();
//		enemies = new Vector<Enemy>();
		
//		initTest();
	}
	
	public Vector<Enemy> getEnemies()
	{
		return worldGrid.getEnemies();
	}
	
	public int getMapSizeX()
	{
		return worldGrid.getGridSizeX();
	}
	
	public int getMapSizeY()
	{
		return worldGrid.getGridSizeY();
	}
	
	public Map(Map orig)
	{
//		dimX = orig.dimX;
//		dimY = orig.dimY;
//		start = new Vector2f(orig.start);
//		exit = new Vector2f(orig.exit);
//		enemies = new Vector<Enemy>();
//		worldGrid = new Grid[worldGridSize][worldGridSize];
//		
//		for (int i=0;i<dimX;i++)
//		{
//			for (int j=0;j<dimY;j++)
//			{
//				  grid[i][j] = new Block(orig.grid[i][j].getType());
//			}
//		}
//		
//		collectableObjects = new Vector<Collectable>();
//		for (int i=0;i<orig.collectableObjects.size();i++)
//		{
//			collectableObjects.add(new Collectable(orig.collectableObjects.get(i).getType()));
//			collectableObjects.get(i).setPos(orig.collectableObjects.get(i).getPos());
//		}
//		  
	}
	
//	public void initTest()
//	{
//		Random rnd = new Random();
//		
//		for (int i=0;i<dimX;i++)
//		{
//			for (int j=0;j<dimY;j++)
//			{
//				grid[i][j] = new Block(2);
//			}
//		}
//		
//		for (int i=0;i<dimX;i++)
//		{
//			for (int j=0;j<dimY;j++)
//			{
////				grid[i][j] = new Block();
//				if ((i==0) || (j==0) || (i==dimX-1) || (j==dimY-1))
//				{
//					grid[i][j].setType(1);
//				} else // inner level
//				{
//					if (rnd.nextGaussian() < 0.5)
//					{
//						grid[i][j].setType(2);
//					}
//					if (rnd.nextGaussian() > 1.0)
//					{
//						int rndType = (int)Math.floor((1.f*rnd.nextInt()/Integer.MAX_VALUE+1)*0.5*3+3);
////						System.out.println(rndType);
//						grid[i][j].setType(rndType);
//						if (rnd.nextGaussian() > 0.3)
//						{
//							if ((i+1)<dimX-1)
//							{
////								System.out.println(i+1+ ", "+j);
//								grid[i+1][j].setType(rndType);
//							}
//						}
//					}
//				}
//			}
//		}
//		grid[1][4].setType(0);
//		grid[1][3].setType(0);
//		grid[2][3].setType(0);
////		enemies.add(new StaticEnemy(1.f, 4.f));
//		enemies.add(new EnemyRandomMove(1.f, 4.f));
////		grid[(int)Math.round(mainPlayer.getPosX())][(int)Math.round(mainPlayer.getPosY())].setType(0);
//		
////		collectableObjects.add(new Collectable(CollectableType.ABILITY_TELEPORT, 7.25f, 3.25f));
////		collectableObjects.add(new Collectable(CollectableType.ABILITY_DIGGING, 6.25f, 2.25f));
//	}

	
	public Block getBlockAt(int x, int y)
	{
		return worldGrid.getBlockAt(x, y);
	}
	
	
	public void writeToFile(String fileName)
	{
//		ObjectOutputStream oos = null;
//		FileOutputStream fos = null;
//		try {
//		  fos = new FileOutputStream(fileName);
//		  oos = new ObjectOutputStream(fos);
//		  oos.writeInt(dimX);
//		  oos.writeInt(dimY);
////		  oos.writeObject(start.x);
////		  oos.writeObject(start.y);
////		  oos.writeObject(exit.x);
////		  oos.writeObject(exit.y);
//		  oos.writeObject(start);
//		  oos.writeObject(exit);
//		  
//		  for (int i=0;i<dimX;i++)
//		  {
//			  for (int j=0;j<dimY;j++)
//			  {
//				  oos.writeInt(grid[i][j].getType());
//			  }
//		  }
//		  
//		  oos.writeInt(collectableObjects.size());
//		  for (int i=0;i<collectableObjects.size();i++)
//		  {
//			  oos.writeObject(collectableObjects.get(i).getType());
//			  oos.writeObject(collectableObjects.get(i).getPos());
//		  }
//
//		}
//		catch (IOException e) {
//		  e.printStackTrace();
//		}
//		finally {
//		  if (oos != null) try { oos.close(); } catch (IOException e) {}
//		  if (fos != null) try { fos.close(); } catch (IOException e) {}
//		}
	}
	
	public void readFile(String fileName)
	{
//		ObjectInputStream ois = null;
//		FileInputStream fis = null;
//		try {
//		  fis = new FileInputStream(fileName);
//		  ois = new ObjectInputStream(fis);
//		  dimX = ois.readInt();
//		  dimY = ois.readInt();
//
//		  start = (Vector2f)ois.readObject();
//		  exit = (Vector2f)ois.readObject();
//		  
//		  grid = null;  
//		  grid = new Block[dimX][dimY];
//		  for (int i=0;i<dimX;i++)
//		  {
//			  for (int j=0;j<dimY;j++)
//			  {
//				  grid[i][j] = new Block(ois.readInt());
//			  }
//		  }
//		  
//		  int size = ois.readInt();
//		  collectableObjects.clear();
//		  for (int i=0;i<size;i++)
//		  {
//			  collectableObjects.add(new Collectable((CollectableType)ois.readObject()));
//			  collectableObjects.get(i).setPos((Vector2f)ois.readObject());
//		  }
//
//		}
//		catch (Exception e) {
//		  e.printStackTrace();
//		}
//		finally {
//		  if (ois != null) try { ois.close(); } catch (IOException e) {}
//		  if (fis != null) try { fis.close(); } catch (IOException e) {}
//		}
	}
	
	public void writeToFile()
	{
		writeToFile("defaultMap.ser");
	}
	
	public void readFile()
	{
		readFile("defaultMap.ser");
	}

	public void setBlock(int x_int, int y_int, int t)
	{
		if ((x_int > 0) && (x_int < worldGrid.gridSizeX) && (y_int > 0) && (y_int < worldGrid.gridSizeY))
		{
			worldGrid.setTypeAt(x_int, y_int, t);
		}
	}
}