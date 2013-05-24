package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;
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
		if (x >= 0 && y >= 0 && x < worldGrid.getGridSizeX() && y < worldGrid.getGridSizeY())
		{
			return worldGrid.getBlockAt(x, y);
		}
		return null;
	}
	
	public float getFogAt(int x, int y)
	{
		return worldGrid.getFogAt(x, y);
	}
	
	public void setFogAt(int x, int y, float value)
	{
		worldGrid.setFogAt(x, y, value);
	}
	
	public void drawFog()
	{
		worldGrid.drawFog();
	}
	
	public int getFogDensity()
	{
		return worldGrid.getFogDensity();
	}
	
	public void writeToFile(String fileName, Vector2f startPos)
	{
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		try {
		  fos = new FileOutputStream(fileName);
		  oos = new ObjectOutputStream(fos);
		  oos.writeInt(getMapSizeX());
		  oos.writeInt(getMapSizeY());
//		  oos.writeObject(start.x);
//		  oos.writeObject(start.y);
//		  oos.writeObject(exit.x);
//		  oos.writeObject(exit.y);
		  oos.writeObject(startPos);
		  oos.writeObject(exit);
		  
		  for (int i=0;i<getMapSizeX();i++)
		  {
			  for (int j=0;j<getMapSizeY();j++)
			  {
				  oos.writeInt(worldGrid.getBlockAt(i, j).getType());
			  }
		  }
		  
		  oos.writeInt(collectableObjects.size());
		  for (int i=0;i<collectableObjects.size();i++)
		  {
			  oos.writeObject(collectableObjects.get(i).getType());
			  oos.writeObject(collectableObjects.get(i).getPos());
		  }
		  
		  oos.writeInt(getEnemies().size());
		  for (int i=0;i<getEnemies().size();i++)
		  {
			  oos.writeInt(getEnemies().get(i).getType());
			  oos.writeObject(getEnemies().get(i).getPos());
		  }

		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (oos != null) try { oos.close(); } catch (IOException e) {}
		  if (fos != null) try { fos.close(); } catch (IOException e) {}
		}
	}
	
	public void readTextFile(String fileName)
	{
		File file = new File(fileName);
		try
		{
			Scanner scanner = new Scanner(file);
			int lineCtr = 0;
			String line;
			int tmpX = 0;
			while (scanner.hasNextLine())
			{
                tmpX = scanner.nextLine().length();
                lineCtr++;
            }
//			Print.outln("size: "+tmpX+", "+lineCtr);
			worldGrid = new Grid(tmpX, lineCtr);
			
			scanner.close();
			scanner = new Scanner(file);
			
			lineCtr = 0;
			while (scanner.hasNextLine())
			{
                line = scanner.nextLine();
                for (int i=0;i<line.length();i++)
                {
                	char type = line.charAt(i);
                	
    				Print.outln("pos: "+i+", "+lineCtr);
                	switch (type)
                	{
                	case 'S':
                		setStart(new Vector2f(i, lineCtr));
                		break;
                	case '#':
                		worldGrid.setTypeAt(i, lineCtr, 1);
                		break;
                	case 'D':
                		worldGrid.setTypeAt(i, lineCtr, 2);
                		break;
                	case 'R':
                		worldGrid.setTypeAt(i, lineCtr, 3);
                		break;
                	case 'B':
                		worldGrid.setTypeAt(i, lineCtr, 4);
                		break;
                	case 'G':
                		worldGrid.setTypeAt(i, lineCtr, 5);
                		break;
                	case 'C':
                		worldGrid.getEnemies().add(new StaticEnemyCrystal(i, lineCtr));
                		break;
                	case 'P':
                		worldGrid.setTypeAt(i, lineCtr, 7);
                		break;
                	case '*':
                		worldGrid.setTypeAt(i, lineCtr, 8);
                		break;
                	case '1':
                		worldGrid.getEnemies().add(new EnemyRandomMove(i, lineCtr, 0));
                		break;
                	default:
                		worldGrid.setTypeAt(i, lineCtr, 0);
                		break;
                	}
                }
                lineCtr++;
            }
			
			
			scanner.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		  
	}

	public void writeToTextFile(String fileName, Vector2f startPos)
	{
		PrintStream stream = null;
		int type;
		char symbol;
		
	  try {
		  stream = new PrintStream(fileName);
		  
		  for (int y=0;y<getMapSizeY();y++)
		  {
			  for (int x=0;x<getMapSizeX();x++)
			  {
				type = worldGrid.getBlockAt(x, y).getType();
				  
				switch (type)
              	{
              	case 0:
              		symbol = '_';
              		break;
              	case 1:
              		symbol = '#';
              		break;
              	case 2:
              		symbol = 'D';
              		break;
              	case 3:
              		symbol = 'R';
              		break;
              	case 4:
              		symbol = 'B';
              		break;
              	case 5:
              		symbol = 'G';
              		break;
              	case 7:
              		symbol = 'P';
              		break;
              	case 8:
              		symbol = '*';
              		break;
              	default:
              		symbol = '_';
              		break;
              	}
				
				if(x == (int)Math.round(startPos.getX()) && y == (int)Math.round(startPos.getY()))
				{
					symbol = 'S';
				}
				
				  for (int i=0;i<collectableObjects.size();i++)
				  {
					  Vector2f pos = collectableObjects.get(i).getPos();
					  if (x == (int)Math.round(pos.getX()) && y == (int)Math.round(pos.getY()))
					  {
						  switch (collectableObjects.get(i).getType())
						  {
						  // TODO: implement
						  }
					  }
				  }
				  
				  for (int i=0;i<getEnemies().size();i++)
				  {
					  Vector2f pos = getEnemies().get(i).getPos();
					  if (x == (int)Math.round(pos.getX()) && y == (int)Math.round(pos.getY()))
					  {
						  if (getEnemies().get(i) instanceof EnemyRandomMove)
						  {
							  symbol = '1';
						  } else
						  {
							  if (getEnemies().get(i) instanceof StaticEnemyCrystal)
							  {
								  symbol = 'C';
							  }
						  }
					  }
				  }

				
				stream.write(symbol);
			  }
			  stream.println();
		  }
		  
		  stream.close();
		  
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

			
	}
	
	public void readFile(String fileName)
	{
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		try {
		  fis = new FileInputStream(fileName);
		  ois = new ObjectInputStream(fis);
		  int dimX, dimY;
		  dimX = ois.readInt();
		  dimY = ois.readInt();

		  start = (Vector2f)ois.readObject();
		  exit = (Vector2f)ois.readObject();
		  
		  worldGrid = new Grid(dimX, dimY);
		  for (int i=0;i<dimX;i++)
		  {
			  for (int j=0;j<dimY;j++)
			  {
				  worldGrid.setTypeAt(i, j, ois.readInt());
			  }
		  }
		  
		  int size = ois.readInt();
		  collectableObjects.clear();
		  for (int i=0;i<size;i++)
		  {
			  collectableObjects.add(new Collectable((CollectableType)ois.readObject()));
			  collectableObjects.get(i).setPos((Vector2f)ois.readObject());
		  }
		  
		  int sizeEnemies = ois.readInt();
		  getEnemies().clear();
		  for (int i=0;i<sizeEnemies;i++)
		  {
			  int t = ois.readInt();
			  Vector2f tmpPos = (Vector2f)ois.readObject();
			  switch (t)
			  {
			  case 0:
				  getEnemies().add(new StaticEnemy(tmpPos.getX(), tmpPos.getY()));
				  break;
			  case 1:
				  getEnemies().add(new EnemyRandomMove(tmpPos.getX(), tmpPos.getY(), t));
				  break;
			  case 2:
				  getEnemies().add(new StaticEnemyCrystal(tmpPos.getX(), tmpPos.getY()));
				  break;
			  default:
				  getEnemies().add(new EnemyRandomMove(tmpPos.getX(), tmpPos.getY(), t));
				  break;
			  }
		  }

		}
		catch (Exception e) {
		  e.printStackTrace();
		}
		finally {
		  if (ois != null) try { ois.close(); } catch (IOException e) {}
		  if (fis != null) try { fis.close(); } catch (IOException e) {}
		}
	}
	
	public void writeToFile(Vector2f start)
	{
		writeToFile("defaultMap.map", start);
	}
	
	public void readFile()
	{
		readFile("defaultMap.map");
	}

	public void setBlock(int x_int, int y_int, int t)
	{
		if ((x_int > 0) && (x_int < worldGrid.gridSizeX) && (y_int > 0) && (y_int < worldGrid.gridSizeY))
		{
			worldGrid.setTypeAt(x_int, y_int, t);
		}
	}

	public Vector2f getStart() {
		return start;
	}

	public void setStart(Vector2f start) {
		this.start = start;
	}

	public Vector2f getExit() {
		return exit;
	}

	public void setExit(Vector2f exit) {
		this.exit = exit;
	}
}