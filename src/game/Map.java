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
 * TODO: reorganize, merge with Grid
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
	private int gridSizeX, gridSizeY;
	private Grid worldGrid;
	private int startDim = 3;
	private int currentGridX, currentGridY;
	private final int currentGridSize = 3;
	private Grid currentGrid[][];
//	private Grid gridBottomRight[][];
//	private Grid gridBottomLeft[][];
//	private Grid gridTopRight[][];
//	private Grid gridTopLeft[][];

//	public Vector<Enemy> enemies;


	
	public Map()
	{
		start = new Vector2f(3.f, 3.f);
		exit = new Vector2f();
//		dimX = 10;
//		dimY = 10;
		worldGrid = new Grid(10, 10);

//		enemies = new Vector<Enemy>();
		
//		initTest();
	}
	
	public Map(int x, int y)
	{
		start = new Vector2f(15.f, 15.f);
		exit = new Vector2f();
//		dimX = x;
//		dimY = y;
		worldGrid = new Grid(x, y, BlockType.BLOCK_DIRT);
		
		currentGrid = new Grid[currentGridSize][currentGridSize];
//		gridBottomRight = new Grid[startDim][startDim];
//		gridBottomLeft = new Grid[startDim][startDim];
//		gridTopRight = new Grid[startDim][startDim];
//		gridTopLeft = new Grid[startDim][startDim];
		currentGridX = 1;
		currentGridY = 1;
		
		gridSizeX = x;
		gridSizeY = y;
		
		currentGrid[currentGridX][currentGridY] = new Grid(x, y, BlockType.BLOCK_DIRT);
		
		initDungeon();
		worldGrid.setTypeAt((int)start.getX(), (int)start.getY(), BlockType.BLOCK_NONE);
		
//		enemies = new Vector<Enemy>();
		
//		initTest();
	}
	
	private void initDungeon()
	{
		float corridorPropability = 0.03f;
		int maxCorridorLength = 20;
		float veinProbabilityRed = 0.03f;
		float veinProbabilityBlue = 0.02f;
		float veinProbabilityGreen = 0.01f;
		float enemyRandomMovePropability = 0.1f;
		float chestPropability = 0.1f;
		
		for (int x=1;x<worldGrid.getGridSizeX()-1;x++)
		{
			for (int y=1;y<worldGrid.getGridSizeY()-1;y++)
			{
				float rnd = RandomGenerator.getRandomZeroToOne();
				
				// generate corridors
				if (rnd < corridorPropability)
				{
//					worldGrid.setTypeAt(x, y, 2);
					generateCorridor(new Vector2f(x, y), maxCorridorLength);
				}
				
				//generate red veins
				rnd = RandomGenerator.getRandomZeroToOne();
				if (rnd < veinProbabilityRed)
				{
					generateVein(new Vector2f(x, y), 7, BlockType.BLOCK_RED);
				}
				
				//generate blue veins
				rnd = RandomGenerator.getRandomZeroToOne();
				if (rnd < veinProbabilityBlue)
				{
					generateVein(new Vector2f(x, y), 5, BlockType.BLOCK_BLUE);
				}
				
				//generate green veins
				rnd = RandomGenerator.getRandomZeroToOne();
				if (rnd < veinProbabilityGreen)
				{
					generateVein(new Vector2f(x, y), 3, BlockType.BLOCK_GREEN);
				}
				
				//generate random move enemies
				rnd = RandomGenerator.getRandomZeroToOne();
				if (rnd < enemyRandomMovePropability)
				{
					if (worldGrid.getBlockAt(x, y).getType() == BlockType.BLOCK_NONE)
					{
						worldGrid.getEnemies().add(new EnemyRandomMove(x, y));
					}
				}
				
				if (rnd > 0.5 && rnd < chestPropability+0.5)
				{
					if (worldGrid.getBlockAt(x, y).getType() == BlockType.BLOCK_NONE)
					{
						worldGrid.addChest(new Chest(x, y));
					}
				}
			}
		}
	}
	
	void generateCorridor(Vector2f position, int maxLength)
	{
		int length = (int)Math.round(RandomGenerator.getRandomZeroToOne()*maxLength);
		Vector2f direction = null;
		
		worldGrid.setTypeAt((int)position.getX(), (int)position.getY(), BlockType.BLOCK_NONE);
		
		for (int step=0;step<length;step++)
		{
			direction = RandomGenerator.getRandomDirection(direction);
			position = position.add(direction);
			position.round();
//			path.print();
			worldGrid.setTypeAt((int)position.getX(), (int)position.getY(), BlockType.BLOCK_NONE);
		}

	}
	
	void generateVein(Vector2f position, int maxLength, BlockType type)
	{
		int length = (int)Math.round(RandomGenerator.getRandomZeroToOne()*maxLength);
		Vector2f direction = null;
		
		worldGrid.setTypeAt((int)position.getX(), (int)position.getY(), BlockType.BLOCK_NONE);
		
		for (int step=0;step<length;step++)
		{
			direction = RandomGenerator.getRandomDirection(direction);
			position = position.add(direction);
			position.round();
//			path.print();
			worldGrid.setTypeAt((int)position.getX(), (int)position.getY(), type);
		}
	}
	
	
	public Vector<Enemy> getEnemies()
	{
		return worldGrid.getEnemies();
	}
	
	Vector<Collectable> getCollectableObjects()
	{
		return worldGrid.getCollectableObjects();
	}
	
	Vector<Chest> getChests()
	{
		return worldGrid.getChests();
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
//		if (x >= 0 && y >= 0 && x < worldGrid.getGridSizeX() && y < worldGrid.getGridSizeY())
//		{
//			return worldGrid.getBlockAt(x, y);
//		}
//		return null;
		
		int gridX = x / getGridSizeX();
		int gridY = y / getGridSizeY();
		
		int localX =  x - gridX*getGridSizeX();
		int localY =  y - gridY*getGridSizeY();
		
		gridX = gridX - currentGridX + 1;
		gridY = gridY - currentGridY + 1;
		
//		Print.outln("x: "+x+ ", y: "+y);
//		Print.outln("gridX: "+gridX+ ", gridY: "+gridY);
//		Print.outln("localX: "+localX+ ", localY: "+localY);
		
		if (gridX >=0 && gridX < currentGridSize && gridY >= 0 && gridY < currentGridSize)
		{
			if (currentGrid[gridX][gridY] == null)
			{
				Print.outln("grid not initialized: ("+gridX+", "+gridY+")");
				currentGrid[gridX][gridY] = new Grid(getMapSizeX(), getMapSizeY(), BlockType.BLOCK_DIRT);
			}
			return currentGrid[gridX][gridY].getBlockAt(localX, localY);
		}
		return null;
	}
	
	public float getFogAt(int x, int y)
	{
		int gridX = x / (getGridSizeX()*getFogDensity());
		int gridY = y / (getGridSizeY()*getFogDensity());
		
		int localX =  x - gridX*(getGridSizeX()*getFogDensity());
		int localY =  y - gridY*(getGridSizeY()*getFogDensity());
		
		gridX = gridX - currentGridX + 1;
		gridY = gridY - currentGridY + 1;
		
//		Print.outln("x: "+x+ ", y: "+y);
//		Print.outln("gridX: "+gridX+ ", gridY: "+gridY);
//		Print.outln("localX: "+localX+ ", localY: "+localY);
		
		if (gridX >=0 && gridX < currentGridSize && gridY >= 0 && gridY < currentGridSize)
		{
			if (currentGrid[gridX][gridY] == null)
			{
				Print.outln("grid not initialized: ("+gridX+", "+gridY+")");
				currentGrid[gridX][gridY] = new Grid(getMapSizeX(), getMapSizeY(), BlockType.BLOCK_DIRT);
			}
			return currentGrid[gridX][gridY].getFogAt(localX, localY);
		}
		return 0.f;
	}
	
	public void setFogAt(int x, int y, float value)
	{
		int gridX = x / (getGridSizeX()*getFogDensity());
		int gridY = y / (getGridSizeY()*getFogDensity());
		
		int localX =  x - gridX*(getGridSizeX()*getFogDensity());
		int localY =  y - gridY*(getGridSizeY()*getFogDensity());
		
		gridX = gridX - currentGridX + 1;
		gridY = gridY - currentGridY + 1;
		
//		Print.outln("x: "+x+ ", y: "+y);
//		Print.outln("gridX: "+gridX+ ", gridY: "+gridY);
//		Print.outln("localX: "+localX+ ", localY: "+localY);
		
		if (gridX >=0 && gridX < currentGridSize && gridY >= 0 && gridY < currentGridSize)
		{
			if (currentGrid[gridX][gridY] == null)
			{
				Print.outln("grid not initialized: ("+gridX+", "+gridY+")");
				currentGrid[gridX][gridY] = new Grid(getMapSizeX(), getMapSizeY(), BlockType.BLOCK_DIRT);
			}
			currentGrid[gridX][gridY].setFogAt(localX, localY, value);
		}
	}
	
	public void drawFog(int x, int y)
	{
		int gridX = x / (getGridSizeX()*getFogDensity());
		int gridY = y / (getGridSizeY()*getFogDensity());
		
		int localX =  x - gridX*(getGridSizeX()*getFogDensity());
		int localY =  y - gridY*(getGridSizeY()*getFogDensity());
		
		gridX = gridX - currentGridX + 1;
		gridY = gridY - currentGridY + 1;
		
//		Print.outln("x: "+x+ ", y: "+y);
//		Print.outln("gridX: "+gridX+ ", gridY: "+gridY);
//		Print.outln("localX: "+localX+ ", localY: "+localY);
		
		if (gridX >=0 && gridX < currentGridSize && gridY >= 0 && gridY < currentGridSize)
		{
			if (currentGrid[gridX][gridY] == null)
			{
				Print.outln("grid not initialized: ("+gridX+", "+gridY+")");
				currentGrid[gridX][gridY] = new Grid(getMapSizeX(), getMapSizeY(), BlockType.BLOCK_DIRT);
			}
			currentGrid[gridX][gridY].drawFog(localX, localY);
		}
	}
	
	public int getFogDensity()
	{
		return worldGrid.getFogDensity();
	}
	
//	public void writeToFile(String fileName, Vector2f startPos)
//	{
//		ObjectOutputStream oos = null;
//		FileOutputStream fos = null;
//		try {
//		  fos = new FileOutputStream(fileName);
//		  oos = new ObjectOutputStream(fos);
//		  oos.writeInt(getMapSizeX());
//		  oos.writeInt(getMapSizeY());
////		  oos.writeObject(start.x);
////		  oos.writeObject(start.y);
////		  oos.writeObject(exit.x);
////		  oos.writeObject(exit.y);
//		  oos.writeObject(startPos);
//		  oos.writeObject(exit);
//		  
//		  for (int i=0;i<getMapSizeX();i++)
//		  {
//			  for (int j=0;j<getMapSizeY();j++)
//			  {
//				  oos.writeObject(worldGrid.getBlockAt(i, j).getType());
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
//		  oos.writeInt(getEnemies().size());
//		  for (int i=0;i<getEnemies().size();i++)
//		  {
//			  oos.writeInt(getEnemies().get(i).getType());
//			  oos.writeObject(getEnemies().get(i).getPos());
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
//	}
	
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
                	
//    				Print.outln("pos: "+i+", "+lineCtr);
                	switch (type)
                	{
                	case 'S':
                		setStart(new Vector2f(i, lineCtr));
                		break;
                	case '#':
                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_ROCK);
                		break;
                	case 'D':
                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_DIRT);
                		break;
                	case 'R':
                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_RED);
                		break;
                	case 'B':
                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_BLUE);
                		break;
                	case 'G':
                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_GREEN);
                		break;
                	case 'Y':
                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_YELLOW);
                		break;
                	case 'C':
                		worldGrid.getEnemies().add(new StaticEnemyCrystal(i, lineCtr));
                		break;
                	case 'P':
                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_PLANTS);
                		break;
                	case '*':
                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_GOAL);
                		break;
                	case '1':
                		worldGrid.getEnemies().add(new EnemyRandomMove(i, lineCtr, 0));
                		break;
                	case '+':
                		Collectable tmpCollectable = new Collectable(CollectableType.DIAMOND);
                		tmpCollectable.setSize(0.8f);
                		float addPos = (1-tmpCollectable.getSizeX())/2.f;
                		tmpCollectable.setPos(new Vector2f(i+addPos, lineCtr+addPos));
                		worldGrid.addCollectable(tmpCollectable);
                		break;
                	default:
                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_NONE);
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
		BlockType type;
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
              	case BLOCK_NONE:
              		symbol = '_';
              		break;
              	case BLOCK_ROCK:
              		symbol = '#';
              		break;
              	case BLOCK_DIRT:
              		symbol = 'D';
              		break;
              	case BLOCK_RED:
              		symbol = 'R';
              		break;
              	case BLOCK_BLUE:
              		symbol = 'B';
              		break;
              	case BLOCK_GREEN:
              		symbol = 'G';
              		break;
              	case BLOCK_YELLOW:
              		symbol = 'Y';
              		break;
              	case BLOCK_PLANTS:
              		symbol = 'P';
              		break;
              	case BLOCK_GOAL:
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
				
				  for (int i=0;i<worldGrid.getCollectableObjects().size();i++)
				  {
					  Vector2f pos = worldGrid.getCollectableObjects().get(i).getPos();
					  if (x == (int)Math.round(pos.getX()) && y == (int)Math.round(pos.getY()))
					  {
						  switch (worldGrid.getCollectableObjects().get(i).getType())
						  {
						  	case DIAMOND:
						  		symbol = '+';
							  break;
							default:
								symbol = '+';
							break;
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
	
//	public void readFile(String fileName)
//	{
//		ObjectInputStream ois = null;
//		FileInputStream fis = null;
//		try {
//		  fis = new FileInputStream(fileName);
//		  ois = new ObjectInputStream(fis);
//		  int dimX, dimY;
//		  dimX = ois.readInt();
//		  dimY = ois.readInt();
//
//		  start = (Vector2f)ois.readObject();
//		  exit = (Vector2f)ois.readObject();
//		  
//		  worldGrid = new Grid(dimX, dimY);
//		  for (int i=0;i<dimX;i++)
//		  {
//			  for (int j=0;j<dimY;j++)
//			  {
//				  worldGrid.setTypeAt(i, j, (BlockType)ois.readObject());
//			  }
//		  }
//		  
//		  int size = ois.readInt();
//		  worldGrid.getCollectableObjects().clear();
//		  for (int i=0;i<size;i++)
//		  {
//			  collectableObjects.add(new Collectable((CollectableType)ois.readObject()));
//			  collectableObjects.get(i).setPos((Vector2f)ois.readObject());
//		  }
//		  
//		  int sizeEnemies = ois.readInt();
//		  getEnemies().clear();
//		  for (int i=0;i<sizeEnemies;i++)
//		  {
//			  int t = ois.readInt();
//			  Vector2f tmpPos = (Vector2f)ois.readObject();
//			  switch (t)
//			  {
//			  case 0:
//				  getEnemies().add(new StaticEnemy(tmpPos.getX(), tmpPos.getY()));
//				  break;
//			  case 1:
//				  getEnemies().add(new EnemyRandomMove(tmpPos.getX(), tmpPos.getY(), t));
//				  break;
//			  case 2:
//				  getEnemies().add(new StaticEnemyCrystal(tmpPos.getX(), tmpPos.getY()));
//				  break;
//			  default:
//				  getEnemies().add(new EnemyRandomMove(tmpPos.getX(), tmpPos.getY(), t));
//				  break;
//			  }
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
//	}
	
	public void writeToFile(Vector2f start)
	{
//		writeToFile("defaultMap.map", start);
	}
	
	public void readFile()
	{
//		readFile("defaultMap.map");
	}

	public void setBlock(int x_int, int y_int, BlockType t)
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

	public int getCurrentGridX() {
		return currentGridX;
	}

	public void setCurrentGridX(int currentGridX) {
		this.currentGridX = currentGridX;
	}

	public int getCurrentGridY() {
		return currentGridY;
	}

	public void setCurrentGridY(int currentGridY) {
		this.currentGridY = currentGridY;
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
}