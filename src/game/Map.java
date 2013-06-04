package game;

//import java.awt.List;
import java.util.List;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;

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
//	private Grid worldGrid;
	private int startDim = 3;
	private int currentGridX, currentGridY;
	private final int currentGridSize = 3;
	private Grid currentGrid[][];
	Vector<Enemy> currentEnemies;
	Vector<Chest> currentChests;
	Vector<Collectable> currentCollectables;
	Vector<GridLoader> gridLoaders;
	ExecutorService saveGridExecutor;
	Vector<Future<Boolean>> savedGridFutures;
	String name;
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
//		worldGrid = new Grid(10, 10);

//		enemies = new Vector<Enemy>();
		
//		initTest();
	}
	
	public Map(int x, int y, String theName)
	{
		setName(theName);
		start = new Vector2f(2.f, 2.f);
		exit = new Vector2f();
//		dimX = x;
//		dimY = y;
//		worldGrid = new Grid(x, y, BlockType.BLOCK_DIRT);
		
		currentGrid = new Grid[currentGridSize][currentGridSize];
//		gridBottomRight = new Grid[startDim][startDim];
//		gridBottomLeft = new Grid[startDim][startDim];
//		gridTopRight = new Grid[startDim][startDim];
//		gridTopLeft = new Grid[startDim][startDim];
		
		gridSizeX = x;
		gridSizeY = y;
		
		currentGridX = ((int)Math.floor(start.getX())) / gridSizeX;
		currentGridY = ((int)Math.floor(start.getY())) / gridSizeY;
//		Print.outln("currentGrid: "+currentGridX+" "+currentGridX);
		
		currentEnemies = new Vector<Enemy>();
		currentChests = new Vector<Chest>();
		currentCollectables = new Vector<Collectable>();
		
		gridLoaders = new Vector<GridLoader>();
		saveGridExecutor = Executors.newCachedThreadPool();
		savedGridFutures = new Vector<Future<Boolean>>();
		
		File dir = new File("./"+getName());
		boolean isNewMap = !dir.exists();
		
		if (isNewMap)
		{
			dir.mkdir();
		}
		
		for (int i=-1;i<2;i++)
		{
			for (int j=-1;j<2;j++)
			{
//				loadGridAt(1+i, 1+j, currentGridX+i, currentGridY+j);
				currentGrid[1+i][1+j] = getGridAtInstant(currentGridX+i, currentGridY+j);
				addAllToCurrent(currentGrid[1+i][1+j]);
			}
		}
		
		if (isNewMap)
		{
			for (int i=-1;i<2;i++)
			{
				for (int j=-1;j<2;j++)
				{
					saveGrid(currentGrid[1+i][1+j], getFileName(currentGridX+i, currentGridY+j));
//					writeGridToFile(currentGrid[1+i][1+j], getFileName(currentGridX+i, currentGridY+j));
				}
			}
		}
		
		// init current grid
//		currentGrid[0][0] = getGridAt(currentGridX-1, currentGridY-1);
//		currentGrid[0][1] = getGridAt(currentGridX-1, currentGridY);
//		currentGrid[0][2] = getGridAt(currentGridX-1, currentGridY+1);
//		currentGrid[1][0] = getGridAt(currentGridX, currentGridY-1);
//		currentGrid[1][1] = getGridAt(currentGridX, currentGridY);
//		currentGrid[1][2] = getGridAt(currentGridX, currentGridY+1);
//		currentGrid[2][0] = getGridAt(currentGridX+1, currentGridY-1);
//		currentGrid[2][1] = getGridAt(currentGridX+1, currentGridY);
//		currentGrid[2][2] = getGridAt(currentGridX+1, currentGridY+1);
	
		
//		currentGrid[currentGridX-1][currentGridY-1] = new Grid(x, y, BlockType.BLOCK_DIRT);

		
//		initDungeon(currentGrid[1][1]);
//		initDungeon(currentGrid[currentGridX-1][currentGridY-1]);
		
		currentGrid[currentGridX][currentGridY].setTypeAt((int)start.getX(), (int)start.getY(), BlockType.BLOCK_NONE);
		
//		enemies = new Vector<Enemy>();
		
//		initTest();
	}
	
	private void initDungeon(Grid theGrid, int theX, int theY)
	{
//		if (theGrid == null) theGrid = new Grid(getGridSizeX(), getGridSizeY(), BlockType.BLOCK_DIRT);
		
		float corridorPropability = 0.03f;
		int maxCorridorLength = 20;
		float veinProbabilityRed = 0.03f;
		float veinProbabilityBlue = 0.02f;
		float veinProbabilityGreen = 0.01f;
		float enemyRandomMovePropability = 0.1f;
		float chestPropability = 0.1f;
		
		for (int x=1;x<theGrid.getGridSizeX()-1;x++)
		{
			for (int y=1;y<theGrid.getGridSizeY()-1;y++)
			{
				float rnd = RandomGenerator.getRandomZeroToOne();
				
				// generate corridors
				if (rnd < corridorPropability)
				{
//					worldGrid.setTypeAt(x, y, 2);
					generateCorridor(theGrid, new Vector2f(x, y), maxCorridorLength);
				}
				
				//generate red veins
				rnd = RandomGenerator.getRandomZeroToOne();
				if (rnd < veinProbabilityRed)
				{
					generateVein(theGrid, new Vector2f(x, y), 7, BlockType.BLOCK_RED);
				}
				
				//generate blue veins
				rnd = RandomGenerator.getRandomZeroToOne();
				if (rnd < veinProbabilityBlue)
				{
					generateVein(theGrid, new Vector2f(x, y), 5, BlockType.BLOCK_BLUE);
				}
				
				//generate green veins
				rnd = RandomGenerator.getRandomZeroToOne();
				if (rnd < veinProbabilityGreen)
				{
					generateVein(theGrid, new Vector2f(x, y), 3, BlockType.BLOCK_GREEN);
				}
				
				//generate random move enemies
				rnd = RandomGenerator.getRandomZeroToOne();
				if (rnd < enemyRandomMovePropability)
				{
					if (theGrid.getBlockAt(x, y).getType() == BlockType.BLOCK_NONE)
					{
						theGrid.getEnemies().add(new EnemyRandomMove(x+theX*getGridSizeX(), y+theY*getGridSizeY()));
					}
				}
				
				if (rnd > 0.5 && rnd < chestPropability+0.5)
				{
					if (theGrid.getBlockAt(x, y).getType() == BlockType.BLOCK_NONE)
					{
						theGrid.addChest(new Chest(x+theX*getGridSizeX(), y+theY*getGridSizeY()));
					}
				}
			}
		}
	}
	
	void generateCorridor(Grid theGrid, Vector2f position, int maxLength)
	{
		int length = (int)Math.round(RandomGenerator.getRandomZeroToOne()*maxLength);
		Vector2f direction = null;
		
		theGrid.setTypeAt((int)position.getX(), (int)position.getY(), BlockType.BLOCK_NONE);
		
		for (int step=0;step<length;step++)
		{
			direction = RandomGenerator.getRandomDirection(direction);
			position = position.add(direction);
			position.round();
//			path.print();
			theGrid.setTypeAt((int)position.getX(), (int)position.getY(), BlockType.BLOCK_NONE);
		}

	}
	
	void generateVein(Grid theGrid, Vector2f position, int maxLength, BlockType type)
	{
		int length = (int)Math.round(RandomGenerator.getRandomZeroToOne()*maxLength);
		Vector2f direction = null;
		
		theGrid.setTypeAt((int)position.getX(), (int)position.getY(), BlockType.BLOCK_NONE);
		
		for (int step=0;step<length;step++)
		{
			direction = RandomGenerator.getRandomDirection(direction);
			position = position.add(direction);
			position.round();
//			path.print();
			theGrid.setTypeAt((int)position.getX(), (int)position.getY(), type);
		}
	}
	
	
	public Vector<Enemy> getEnemies()
	{
//		Vector<Enemy> tmp = currentGrid[1][1].getEnemies();
		
//		if (currentGrid[0][1] != null) tmp.addAll(currentGrid[0][1].getEnemies());
//		if (currentGrid[1][1] != null) tmp.addAll(currentGrid[0][1].getEnemies());

		return currentEnemies;
	}
	
	Vector<Collectable> getCollectableObjects()
	{
		return currentCollectables;
	}
	
	Vector<Chest> getChests()
	{
		return currentChests;
	}
	
	public int getMapSizeX()
	{
		return currentGrid[1][1].getGridSizeX();
	}
	
	public int getMapSizeY()
	{
		return currentGrid[1][1].getGridSizeY();
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

	
	public String getFileName(int x, int y)
	{
		return "./"+getName()+"/"+"gridx"+x+"y"+y+".ser";
	}
	
	public Grid getGridAtInstant(int x, int y)
	{
		Grid tmpGrid;

		String fileName = getFileName(x, y);
		File file = new File(fileName);
		
		if (file.exists())
		{
			tmpGrid = readGridFromFile(fileName);
		} else
		{
			tmpGrid = new Grid(getGridSizeX(), getGridSizeY(), x, y, BlockType.BLOCK_DIRT);
			initDungeon(tmpGrid, x, y);
		}
		
		return tmpGrid;
	}
	
	public void loadGridAt(int x, int y, int currentX, int currentY)
	{
		String fileName = getFileName(currentX, currentY);
		File file = new File(fileName);
		
		if (file.exists())
		{
//			tmpGrid = readGridFromFile(fileName);
			gridLoaders.add(new GridLoader(fileName, x, y, currentX, currentY));
		} else
		{
			currentGrid[x][y] = new Grid(getGridSizeX(), getGridSizeY(), currentX, currentY, BlockType.BLOCK_DIRT);
			initDungeon(currentGrid[x][y], currentX, currentY);
			currentEnemies.addAll(currentGrid[x][y].getEnemies());
		}
		
	}
	
	public Block getBlockAt(int x, int y)
	{
//		if (x >= 0 && y >= 0 && x < worldGrid.getGridSizeX() && y < worldGrid.getGridSizeY())
//		{
//			return worldGrid.getBlockAt(x, y);
//		}
//		return null;
		
		int gridX = (int)Math.floor(1.f * x / getGridSizeX());
		int gridY = (int)Math.floor(1.f * y / getGridSizeY());
		
		int localX =  x - gridX*getGridSizeX();
		int localY =  y - gridY*getGridSizeY();
		
		if (localX < 0) localX += getGridSizeX();
		if (localY < 0) localY += getGridSizeY();
		
		gridX = gridX - currentGridX + 1;
		gridY = gridY - currentGridY + 1;
		
//		Print.outln("x: "+x+ ", y: "+y);
//		Print.outln("gridX: "+gridX+ ", gridY: "+gridY);
//		Print.outln("localX: "+localX+ ", localY: "+localY);
		
		if (gridX >=0 && gridX < currentGridSize && gridY >= 0 && gridY < currentGridSize)
		{
//			if (currentGrid[gridX][gridY] == null)
//			{
////				Print.outln("grid not initialized: ("+gridX+", "+gridY+")");
//				currentGrid[gridX][gridY] = new Grid(getMapSizeX(), getMapSizeY(), BlockType.BLOCK_DIRT);
//			}
			return currentGrid[gridX][gridY].getBlockAt(localX, localY);
		}
		return null;
	}
	
	public float getFogAt(int x, int y)
	{
		int gridX = (int)Math.floor(1.f * x / (getGridSizeX()*getFogDensity()));
		int gridY = (int)Math.floor(1.f * y / (getGridSizeY()*getFogDensity()));
		
		int localX =  x - gridX*(getGridSizeX()*getFogDensity());
		int localY =  y - gridY*(getGridSizeY()*getFogDensity());
		
		if (localX < 0) localX += getGridSizeX()*getFogDensity();
		if (localY < 0) localY += getGridSizeY()*getFogDensity();
		
		gridX = gridX - currentGridX + 1;
		gridY = gridY - currentGridY + 1;
		
//		Print.outln("x: "+x+ ", y: "+y);
//		Print.outln("gridX: "+gridX+ ", gridY: "+gridY);
//		Print.outln("localX: "+localX+ ", localY: "+localY);
		
		if (gridX >=0 && gridX < currentGridSize && gridY >= 0 && gridY < currentGridSize)
		{
//			if (currentGrid[gridX][gridY] == null)
//			{
////				Print.outln("grid not initialized: ("+gridX+", "+gridY+")");
//				currentGrid[gridX][gridY] = new Grid(getMapSizeX(), getMapSizeY(), BlockType.BLOCK_DIRT);
//			}
			return currentGrid[gridX][gridY].getFogAt(localX, localY);
		}
		return 0.f;
	}
	
	public void setFogAt(int x, int y, float value)
	{
		int gridX = (int)Math.floor(1.f * x / (getGridSizeX()*getFogDensity()));
		int gridY = (int)Math.floor(1.f * y / (getGridSizeY()*getFogDensity()));
		
		int localX =  x - gridX*(getGridSizeX()*getFogDensity());
		int localY =  y - gridY*(getGridSizeY()*getFogDensity());
		
		if (localX < 0) localX += getGridSizeX()*getFogDensity();
		if (localY < 0) localY += getGridSizeY()*getFogDensity();
		
		gridX = gridX - currentGridX + 1;
		gridY = gridY - currentGridY + 1;
		
//		Print.outln("x: "+x+ ", y: "+y);
//		Print.outln("gridX: "+gridX+ ", gridY: "+gridY);
//		Print.outln("localX: "+localX+ ", localY: "+localY);
		
		if (gridX >=0 && gridX < currentGridSize && gridY >= 0 && gridY < currentGridSize)
		{
//			if (currentGrid[gridX][gridY] == null)
//			{
////				Print.outln("grid not initialized: ("+gridX+", "+gridY+")");
//				currentGrid[gridX][gridY] = new Grid(getMapSizeX(), getMapSizeY(), BlockType.BLOCK_DIRT);
//			}
			currentGrid[gridX][gridY].setFogAt(localX, localY, value);
		}
	}
	
	public void drawFog(int x, int y)
	{
		int gridX = (int)Math.floor(1.f * x / (getGridSizeX()*getFogDensity()));
		int gridY = (int)Math.floor(1.f * y / (getGridSizeY()*getFogDensity()));
		
		int localX =  x - gridX*(getGridSizeX()*getFogDensity());
		int localY =  y - gridY*(getGridSizeY()*getFogDensity());
		
		if (localX < 0) localX += getGridSizeX()*getFogDensity();
		if (localY < 0) localY += getGridSizeY()*getFogDensity();
		
		gridX = gridX - currentGridX + 1;
		gridY = gridY - currentGridY + 1;
		
//		Print.outln("x: "+x+ ", y: "+y);
//		Print.outln("gridX: "+gridX+ ", gridY: "+gridY);
//		Print.outln("localX: "+localX+ ", localY: "+localY);
		
		if (gridX >=0 && gridX < currentGridSize && gridY >= 0 && gridY < currentGridSize)
		{
//			if (currentGrid[gridX][gridY] == null)
//			{
////				Print.outln("grid not initialized: ("+gridX+", "+gridY+")");
//				currentGrid[gridX][gridY] = new Grid(getMapSizeX(), getMapSizeY(), BlockType.BLOCK_DIRT);
//			}
			currentGrid[gridX][gridY].drawFog(localX, localY);
		}
	}
	
	public int getFogDensity()
	{
		return currentGrid[1][1].getFogDensity();
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
	
//	public void readTextFile(String fileName)
//	{
//		File file = new File(fileName);
//		try
//		{
//			Scanner scanner = new Scanner(file);
//			int lineCtr = 0;
//			String line;
//			int tmpX = 0;
//			while (scanner.hasNextLine())
//			{
//                tmpX = scanner.nextLine().length();
//                lineCtr++;
//            }
////			Print.outln("size: "+tmpX+", "+lineCtr);
//			worldGrid = new Grid(tmpX, lineCtr);
//			
//			scanner.close();
//			scanner = new Scanner(file);
//			
//			lineCtr = 0;
//			while (scanner.hasNextLine())
//			{
//                line = scanner.nextLine();
//                for (int i=0;i<line.length();i++)
//                {
//                	char type = line.charAt(i);
//                	
////    				Print.outln("pos: "+i+", "+lineCtr);
//                	switch (type)
//                	{
//                	case 'S':
//                		setStart(new Vector2f(i, lineCtr));
//                		break;
//                	case '#':
//                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_ROCK);
//                		break;
//                	case 'D':
//                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_DIRT);
//                		break;
//                	case 'R':
//                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_RED);
//                		break;
//                	case 'B':
//                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_BLUE);
//                		break;
//                	case 'G':
//                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_GREEN);
//                		break;
//                	case 'Y':
//                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_YELLOW);
//                		break;
//                	case 'C':
//                		worldGrid.getEnemies().add(new StaticEnemyCrystal(i, lineCtr));
//                		break;
//                	case 'P':
//                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_PLANTS);
//                		break;
//                	case '*':
//                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_GOAL);
//                		break;
//                	case '1':
//                		worldGrid.getEnemies().add(new EnemyRandomMove(i, lineCtr, 0));
//                		break;
//                	case '+':
//                		Collectable tmpCollectable = new Collectable(CollectableType.DIAMOND);
//                		tmpCollectable.setSize(0.8f);
//                		float addPos = (1-tmpCollectable.getSizeX())/2.f;
//                		tmpCollectable.setPos(new Vector2f(i+addPos, lineCtr+addPos));
//                		worldGrid.addCollectable(tmpCollectable);
//                		break;
//                	default:
//                		worldGrid.setTypeAt(i, lineCtr, BlockType.BLOCK_NONE);
//                		break;
//                	}
//                }
//                lineCtr++;
//            }
//			
//			
//			scanner.close();
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		  
//	}

//	public void writeToTextFile(String fileName, Vector2f startPos)
//	{
//		PrintStream stream = null;
//		BlockType type;
//		char symbol;
//		
//	  try {
//		  stream = new PrintStream(fileName);
//		  
//		  for (int y=0;y<getMapSizeY();y++)
//		  {
//			  for (int x=0;x<getMapSizeX();x++)
//			  {
//				type = worldGrid.getBlockAt(x, y).getType();
//				  
//				switch (type)
//              	{
//              	case BLOCK_NONE:
//              		symbol = '_';
//              		break;
//              	case BLOCK_ROCK:
//              		symbol = '#';
//              		break;
//              	case BLOCK_DIRT:
//              		symbol = 'D';
//              		break;
//              	case BLOCK_RED:
//              		symbol = 'R';
//              		break;
//              	case BLOCK_BLUE:
//              		symbol = 'B';
//              		break;
//              	case BLOCK_GREEN:
//              		symbol = 'G';
//              		break;
//              	case BLOCK_YELLOW:
//              		symbol = 'Y';
//              		break;
//              	case BLOCK_PLANTS:
//              		symbol = 'P';
//              		break;
//              	case BLOCK_GOAL:
//              		symbol = '*';
//              		break;
//              	default:
//              		symbol = '_';
//              		break;
//              	}
//				
//				if(x == (int)Math.round(startPos.getX()) && y == (int)Math.round(startPos.getY()))
//				{
//					symbol = 'S';
//				}
//				
//				  for (int i=0;i<worldGrid.getCollectableObjects().size();i++)
//				  {
//					  Vector2f pos = worldGrid.getCollectableObjects().get(i).getPos();
//					  if (x == (int)Math.round(pos.getX()) && y == (int)Math.round(pos.getY()))
//					  {
//						  switch (worldGrid.getCollectableObjects().get(i).getType())
//						  {
//						  	case DIAMOND:
//						  		symbol = '+';
//							  break;
//							default:
//								symbol = '+';
//							break;
//						  }
//					  }
//				  }
//				  
//				  for (int i=0;i<getEnemies().size();i++)
//				  {
//					  Vector2f pos = getEnemies().get(i).getPos();
//					  if (x == (int)Math.round(pos.getX()) && y == (int)Math.round(pos.getY()))
//					  {
//						  if (getEnemies().get(i) instanceof EnemyRandomMove)
//						  {
//							  symbol = '1';
//						  } else
//						  {
//							  if (getEnemies().get(i) instanceof StaticEnemyCrystal)
//							  {
//								  symbol = 'C';
//							  }
//						  }
//					  }
//				  }
//
//				
//				stream.write(symbol);
//			  }
//			  stream.println();
//		  }
//		  
//		  stream.close();
//		  
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//
//			
//	}
	
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
	
	public void saveGrid(Grid theGrid, String fileName)
	{
//		saveGridThreads.add(new Thread(new GridStoreRunnable(theGrid, fileName)));
//		saveGridThreads.lastElement().start();
		savedGridFutures.add(saveGridExecutor.submit(new GridStoreCallable(theGrid, fileName)));
	}
	
//	public Grid loadGrid()
//	{
////		Callable<Grid> tmpCallable1 = new GridLoadCallable("test1.ser");
////		Callable<Grid> tmpCallable2 = new GridLoadCallable("test2.ser");
////		
////		Vector<Callable<Grid>> tmpList = new Vector<Callable<Grid>>();
////		tmpList.add(tmpCallable1);
////		tmpList.add(tmpCallable2);
////		
////		ExecutorService executor = Executors.newCachedThreadPool();
////		List<Future<Grid>> results = executor.invokeAll(tmpList);
////		
////		if (results.get(0).isDone())
////		{
////			Grid returnGrid = result.get();	
////		}
//
//		Callable<Grid> tmpCallable1 = new GridLoadCallable("test1.ser");
//		
//		ExecutorService executor = Executors.newCachedThreadPool();
//		Future<Grid> result = executor.submit(tmpCallable1);
//		
//		if (result.isDone())
//		{
//			try {
//				return result.get();
//			} catch (InterruptedException | ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//		}
//		return null;
//	}
	
	public void finalize()
	{
		saveCurrentGrid();
		
		while(savedGridFutures.size() != 0)
		{
			update();
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
	void saveCurrentGrid()
	{
		for (int i=-1;i<2;i++)
		{
			for (int j=-1;j<2;j++)
			{
				saveAllToGrid(currentGrid[1+i][1+j]);
				saveGrid(currentGrid[1+i][1+j], getFileName(currentGridX+i, currentGridY+j));
//				Thread tmpThread = new Thread(new GridStoreRunnable(currentGrid[1+i][1+j], getFileName(currentGridX+i, currentGridY+j)));
//				tmpThread.start();
//				writeGridToFile(currentGrid[1+i][1+j], getFileName(currentGridX+i, currentGridY+j));
			}
		}
	}
	
	public void readFile()
	{
//		readFile("defaultMap.map");
	}

	public void setBlock(int x_int, int y_int, BlockType t)
	{
		if ((x_int > 0) && (x_int < currentGrid[currentGridX][currentGridY].gridSizeX) && (y_int > 0) && (y_int < currentGrid[currentGridX][currentGridY].gridSizeY))
		{
			currentGrid[currentGridX][currentGridY].setTypeAt(x_int, y_int, t);
		}
	}
	
	public void increaseCurrentGridX()
	{
		saveAllToGrid(currentGrid[0][0]);
		saveAllToGrid(currentGrid[0][1]);
		saveAllToGrid(currentGrid[0][2]);
		
		saveGrid(currentGrid[0][0], getFileName(currentGridX-1, currentGridY-1));
		saveGrid(currentGrid[0][1], getFileName(currentGridX-1, currentGridY));
		saveGrid(currentGrid[0][2], getFileName(currentGridX-1, currentGridY+1));
		
		currentGridX += 1;
		
		currentGrid[0][0] = currentGrid[1][0];
		currentGrid[0][1] = currentGrid[1][1];
		currentGrid[0][2] = currentGrid[1][2];
		
		currentGrid[1][0] = currentGrid[2][0];
		currentGrid[1][1] = currentGrid[2][1];
		currentGrid[1][2] = currentGrid[2][2];
		
		loadGridAt(2, 0, currentGridX+1, currentGridY-1);
		loadGridAt(2, 1, currentGridX+1, currentGridY);
		loadGridAt(2, 2, currentGridX+1, currentGridY+1);
		
//		currentGrid[2][0] = getGridAt(currentGridX+1, currentGridY-1);
//		currentGrid[2][1] = getGridAt(currentGridX+1, currentGridY);
//		currentGrid[2][2] = getGridAt(currentGridX+1, currentGridY+1);
	}
	
	public void decreaseCurrentGridX()
	{
//		Print.outln("numEnemies 1: "+currentEnemies.size());
		saveAllToGrid(currentGrid[2][0]);
		saveAllToGrid(currentGrid[2][1]);
		saveAllToGrid(currentGrid[2][2]);
//		Print.outln("numEnemies 2: "+currentEnemies.size());
		
		saveGrid(currentGrid[2][0], getFileName(currentGridX+1, currentGridY-1));
		saveGrid(currentGrid[2][1], getFileName(currentGridX+1, currentGridY));
		saveGrid(currentGrid[2][2], getFileName(currentGridX+1, currentGridY+1));

		currentEnemies.removeAll(currentGrid[2][0].getEnemies());
		currentEnemies.removeAll(currentGrid[2][1].getEnemies());
		currentEnemies.removeAll(currentGrid[2][2].getEnemies());
		
		currentGridX -= 1;
		
		currentGrid[2][0] = currentGrid[1][0];
		currentGrid[2][1] = currentGrid[1][1];
		currentGrid[2][2] = currentGrid[1][2];
		
		currentGrid[1][0] = currentGrid[0][0];
		currentGrid[1][1] = currentGrid[0][1];
		currentGrid[1][2] = currentGrid[0][2];
		
		loadGridAt(0, 0, currentGridX-1, currentGridY-1);
		loadGridAt(0, 1, currentGridX-1, currentGridY);
		loadGridAt(0, 2, currentGridX-1, currentGridY+1);
		
//		currentGrid[0][0] = getGridAt(currentGridX-1, currentGridY-1);
//		currentGrid[0][1] = getGridAt(currentGridX-1, currentGridY);
//		currentGrid[0][2] = getGridAt(currentGridX-1, currentGridY+1);

	}
	
	public void increaseCurrentGridY()
	{
		saveAllToGrid(currentGrid[0][0]);
		saveAllToGrid(currentGrid[1][0]);
		saveAllToGrid(currentGrid[2][0]);
		
		saveGrid(currentGrid[0][0], getFileName(currentGridX-1, currentGridY-1));
		saveGrid(currentGrid[1][0], getFileName(currentGridX, currentGridY-1));
		saveGrid(currentGrid[2][0], getFileName(currentGridX+1, currentGridY-1));

		
		currentGridY += 1;
		
		currentGrid[0][0] = currentGrid[0][1];
		currentGrid[1][0] = currentGrid[1][1];
		currentGrid[2][0] = currentGrid[2][1];
		
		currentGrid[0][1] = currentGrid[0][2];
		currentGrid[1][1] = currentGrid[1][2];
		currentGrid[2][1] = currentGrid[2][2];
		
		loadGridAt(0, 2, currentGridX-1, currentGridY+1);
		loadGridAt(1, 2, currentGridX, currentGridY+1);
		loadGridAt(2, 2, currentGridX+1, currentGridY+1);
		
//		currentGrid[0][2] = getGridAt(currentGridX-1, currentGridY+1);
//		currentGrid[1][2] = getGridAt(currentGridX, currentGridY+1);
//		currentGrid[2][2] = getGridAt(currentGridX+1, currentGridY+1);
	}
	
	public void decreaseCurrentGridY()
	{
		saveAllToGrid(currentGrid[0][2]);
		saveAllToGrid(currentGrid[1][2]);
		saveAllToGrid(currentGrid[2][2]);
		
		saveGrid(currentGrid[0][2], getFileName(currentGridX-1, currentGridY+1));
		saveGrid(currentGrid[1][2], getFileName(currentGridX, currentGridY+1));
		saveGrid(currentGrid[2][2], getFileName(currentGridX+1, currentGridY+1));
		
		currentGridY -= 1;
		
		currentGrid[0][2] = currentGrid[0][1];
		currentGrid[1][2] = currentGrid[1][1];
		currentGrid[2][2] = currentGrid[2][1];
		
		currentGrid[0][1] = currentGrid[0][0];
		currentGrid[1][1] = currentGrid[1][0];
		currentGrid[2][1] = currentGrid[2][0];
		
		loadGridAt(0, 0, currentGridX-1, currentGridY-1);
		loadGridAt(1, 0, currentGridX, currentGridY-1);
		loadGridAt(2, 0, currentGridX+1, currentGridY-1);
		
//		currentGrid[0][0] = getGridAt(currentGridX-1, currentGridY-1);
//		currentGrid[1][0] = getGridAt(currentGridX, currentGridY-1);
//		currentGrid[2][0] = getGridAt(currentGridX+1, currentGridY-1);
	}
	
	public void saveAllToGrid(Grid theGrid)
	{
		saveEnemiesToGrid(theGrid);
		saveChestsToGrid(theGrid);
		saveCollectablesToGrid(theGrid);
	}
	
	public void addAllToCurrent(Grid theGrid)
	{
		currentEnemies.addAll(theGrid.getEnemies());
		currentCollectables.addAll(theGrid.getCollectableObjects());
		currentChests.addAll(theGrid.getChests());
	}
	
	public void saveEnemiesToGrid(Grid theGrid)
	{
		theGrid.getEnemies().removeAllElements();
		
		for (Iterator<Enemy> object = currentEnemies.iterator();object.hasNext();)
		{
			Enemy tmpEnemy = object.next();
			
			int gridX = (int)Math.floor(1.f * tmpEnemy.getPosX() / getGridSizeX());
			int gridY = (int)Math.floor(1.f * tmpEnemy.getPosY() / getGridSizeY());
			
			if (gridX >= currentGridX-1 && gridX <= currentGridX+1
					&& gridY >= currentGridY-1 && gridY <= currentGridY+1)	// is in currentGrid
			{
				if (gridX == theGrid.getPosX() && gridY == theGrid.getPosY())	//is in the chosen grid
				{
					theGrid.getEnemies().add(tmpEnemy);
					object.remove();
				}
			} else
			{
				object.remove();
			}
		}		
	}
	
	public void saveChestsToGrid(Grid theGrid)
	{
		theGrid.getChests().removeAllElements();
		
		for (Iterator<Chest> object = currentChests.iterator();object.hasNext();)
		{
			Chest tmpChest = object.next();
			
			int gridX = (int)Math.floor(1.f * tmpChest.getPosX() / getGridSizeX());
			int gridY = (int)Math.floor(1.f * tmpChest.getPosY() / getGridSizeY());
			
			if (gridX >= currentGridX-1 && gridX <= currentGridX+1
					&& gridY >= currentGridY-1 && gridY <= currentGridY+1)	// is in currentGrid
			{
				if (gridX == theGrid.getPosX() && gridY == theGrid.getPosY())	//is in the chosen grid
				{
					theGrid.getChests().add(tmpChest);
					object.remove();
				}
			} else
			{
				object.remove();
			}
		}		
	}
	
	public void saveCollectablesToGrid(Grid theGrid)
	{
		theGrid.getCollectableObjects().removeAllElements();
		
		for (Iterator<Collectable> object = currentCollectables.iterator();object.hasNext();)
		{
			Collectable tmpCollectable = object.next();
			
			int gridX = (int)Math.floor(1.f * tmpCollectable.getPosX() / getGridSizeX());
			int gridY = (int)Math.floor(1.f * tmpCollectable.getPosY() / getGridSizeY());
			
			if (gridX >= currentGridX-1 && gridX <= currentGridX+1
					&& gridY >= currentGridY-1 && gridY <= currentGridY+1)	// is in currentGrid
			{
				if (gridX == theGrid.getPosX() && gridY == theGrid.getPosY())	//is in the chosen grid
				{
					theGrid.getCollectableObjects().add(tmpCollectable);
					object.remove();
				}
			} else
			{
				object.remove();
			}
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Grid readGridFromFile(String fileName)
	{
		Grid tmpGrid = null;
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		
		Print.outln("load file: "+fileName);
		try {
			  fis = new FileInputStream(fileName);
			  ois = new ObjectInputStream(fis);

			  
			  tmpGrid = (Grid)ois.readObject();
//			  int tmpX, tmpY;
//			  
//			  tmpX = ois.readInt();
//			  tmpY = ois.readInt();
//			  
//			  tmpGrid = new Grid(tmpX, tmpY);
//			  
//			  for (int i=0;i<tmpGrid.getGridSizeX();i++)
//			  {
//				  for (int j=0;j<tmpGrid.getGridSizeY();j++)
//				  {
//					  tmpGrid.setBlockAt(i, j, (Block)ois.readObject());
//				  }
//			  }
//			  
//			  for (int i=0;i<tmpGrid.getGridSizeX()*tmpGrid.getFogDensity();i++)
//			  {
//				  for (int j=0;j<tmpGrid.getGridSizeY()*tmpGrid.getFogDensity();j++)
//				  {
//					  tmpGrid.setFogAt(i, j, ois.readFloat());
//				  }
//			  }
//			  
//			  int tmpNum = ois.readInt();
//			  for (int i=0;i<tmpNum;i++)
//			  {
//				  tmpGrid.addCollectable((Collectable)ois.readObject());
//			  }
//			  
//			  tmpNum = ois.readInt();
//			  for (int i=0;i<tmpNum;i++)
//			  {
//				  tmpGrid.addEnemy((Enemy)ois.readObject());
//			  }
//			  
//			  tmpNum = ois.readInt();
//			  for (int i=0;i<tmpNum;i++)
//			  {
//				  tmpGrid.addChest((Chest)ois.readObject());
//			  }
			}
			catch (Exception e) {
				Print.err("error loading file: "+fileName);
				e.printStackTrace();
			}
			finally {
			  if (ois != null) try { ois.close(); } catch (IOException e) {}
			  if (fis != null) try { fis.close(); } catch (IOException e) {}
			}
		return tmpGrid;
	}
	
	public void update()
	{
		for (Iterator<GridLoader> object=gridLoaders.iterator();object.hasNext();)
		{
			GridLoader tmp = object.next();
			tmp.update();
			if (tmp.isActive() && tmp.isDone())
			{
				currentGrid[tmp.getX()][tmp.getY()] = tmp.getResultGrid();
				addAllToCurrent(currentGrid[tmp.getX()][tmp.getY()]);
				object.remove();
			}
		}
		
//		Print.outln("grids to save: "+savedGridFutures.size());
		
		for (Iterator<Future<Boolean>> object = savedGridFutures.iterator();object.hasNext();)
		{
			Future<Boolean> tmpFuture = object.next();
			if (tmpFuture.isDone()) object.remove();
		}
	}
}