package darklord.game;

import java.io.Serializable;

import darklord.math.Vector2f;

public class LevelStructure implements Serializable
{
	private Room gridCenter;
	private Room activeRoom;
	private int gridSizeX, gridSizeY;
//	private final int levelStructureSize = 20;
//	private int posX, posY;
	private int gridMapSize, centerX, centerY;
	Room[][] roomMap;
//	boolean[][] isDiscovered;
	
	public LevelStructure(int sizeX, int sizeY)
	{
		setGridSizeX(sizeX);
		setGridSizeY(sizeY);
		gridCenter = new Room(sizeX, sizeY, 0, 0, BlockType.BLOCK_NONE);
		activeRoom = gridCenter;
		
		gridMapSize = 4;
		centerX = gridMapSize / 2;
		centerY = gridMapSize / 2;
		roomMap = new Room[gridMapSize][gridMapSize];
		roomMap[centerX][centerY] = gridCenter;
		
//		grids = new Grid[levelStructureSize][levelStructureSize];
//		isDiscovered = new boolean[levelStructureSize][levelStructureSize];
//		for (int i=0;i<levelStructureSize;i++)
//		{
//			for (int j=0;j<levelStructureSize;j++)
//			{
//				isDiscovered[i][j] = false;
//			}
//		}
//		grids[posX][posY] = new Grid(sizeX, sizeY, 0, 0, BlockType.BLOCK_DIRT);
//		isDiscovered[posX][posY] = true;
//		activeGrid = gridCenter;
	}
	
	public boolean isGridTop()
	{
		return activeRoom.getGridTop() != null;
	}
	
	public boolean isGridBottom()
	{
		return activeRoom.getGridBottom() != null;
//		if ( posY-1 < 0) return false;
//		if (grids[posX][posY-1] != null) return true;
//		return false;
	}
	
	public boolean isGridLeft()
	{
		return activeRoom.getGridLeft() != null;
//		if (posX-1 < 0) return false;
//		if (grids[posX-1][posY] != null) return true;
//		return false;
	}
	
	public boolean isGridRight()
	{
		return activeRoom.getGridRight() != null;
//		if (posX+1 >= levelStructureSize) return false;
//		if (grids[posX+1][posY] != null) return true;
//		return false;
	}
	
	public boolean addGridTop()
	{
		if (activeRoom.getGridTop() == null)
		{
			int newPosX = activeRoom.getPosX();
			int newPosY = activeRoom.getPosY()+1;
			Room tmpGrid = new Room(gridSizeX, gridSizeY, newPosX, newPosY, BlockType.BLOCK_NONE);
			
			if (newPosX+centerX-1 < 0 || newPosX+centerX+1 >= gridMapSize || newPosY+centerY-1 < 0 || newPosY+centerY+1 >= gridMapSize)
			{
				increaseGridMapSize();
				newPosX = activeRoom.getPosX();
				newPosY = activeRoom.getPosY()+1;
			}
			setGridLinks(tmpGrid);
			activeRoom.setGridTop(tmpGrid);
			return true;
		}
		return false;
//		if (posY+1 >= levelStructureSize) return false;
//		grids[posX][posY+1] = new Grid(gridSizeX, gridSizeY, posX, posY+1, BlockType.BLOCK_DIRT);	
//		isDiscovered[posX][posY+1] = true;
//		return true;
	}
	
	public boolean addGridBottom()
	{
		if (activeRoom.getGridBottom() == null)
		{
			int newPosX = activeRoom.getPosX();
			int newPosY = activeRoom.getPosY()-1;
			Room tmpGrid = new Room(gridSizeX, gridSizeY, newPosX, newPosY, BlockType.BLOCK_NONE);
			
			if (newPosX+centerX-1 < 0 || newPosX+centerX+1 >= gridMapSize || newPosY+centerY-1 < 0 || newPosY+centerY+1 >= gridMapSize)
			{
				increaseGridMapSize();
				newPosX = activeRoom.getPosX();
				newPosY = activeRoom.getPosY()-1;
			}
			setGridLinks(tmpGrid);
			activeRoom.setGridBottom(tmpGrid);
			return true;
		}
		return false;
//		if (posY-1 < 0) return false;
//		grids[posX][posY-1] = new Grid(gridSizeX, gridSizeY, posX, posY-1, BlockType.BLOCK_DIRT);
//		isDiscovered[posX][posY-1] = true;
//		return true;
	}
	
	public boolean addGridLeft()
	{
		if (activeRoom.getGridLeft() == null)
		{
			int newPosX = activeRoom.getPosX()-1;
			int newPosY = activeRoom.getPosY();
			Room tmpGrid = new Room(gridSizeX, gridSizeY, newPosX, newPosY, BlockType.BLOCK_NONE);
			
			if (newPosX+centerX-1 < 0 || newPosX+centerX+1 >= gridMapSize || newPosY+centerY-1 < 0 || newPosY+centerY+1 >= gridMapSize)
			{
				increaseGridMapSize();
				newPosX = activeRoom.getPosX()-1;
				newPosY = activeRoom.getPosY();
			}
			setGridLinks(tmpGrid);
			activeRoom.setGridLeft(tmpGrid);
			return true;
		}
		return false;
//		if (posX-1 < 0) return false;
//		grids[posX-1][posY] = new Grid(gridSizeX, gridSizeY, posX-1, posY, BlockType.BLOCK_DIRT);	
//		isDiscovered[posX-1][posY] = true;
//		return true;
	}
	
	public boolean addGridRight()
	{
		if (activeRoom.getGridRight() == null)
		{
			int newPosX = activeRoom.getPosX()+1;
			int newPosY = activeRoom.getPosY();
			Room tmpGrid = new Room(gridSizeX, gridSizeY, newPosX, newPosY, BlockType.BLOCK_NONE);
			
			if (newPosX+centerX-1 < 0 || newPosX+centerX+1 >= gridMapSize || newPosY+centerY-1 < 0 || newPosY+centerY+1 >= gridMapSize)
			{
				increaseGridMapSize();
				newPosX = activeRoom.getPosX()+1;
				newPosY = activeRoom.getPosY();
			}
			setGridLinks(tmpGrid);
			
			activeRoom.setGridRight(tmpGrid);
			return true;
		}
		return false;
//		if (posX+1 >= levelStructureSize) return false;
//		grids[posX+1][posY] = new Grid(gridSizeX, gridSizeY, posX+1, posY, BlockType.BLOCK_DIRT);	
//		isDiscovered[posX+1][posY] = true;
//		return true;
	}
	
	public void increaseGridMapSize()
	{
		Room[][] oldMap = roomMap;
		int oldSize = gridMapSize;
		gridMapSize *=2;
		centerX = gridMapSize / 2;
		centerY = gridMapSize / 2;
		
		roomMap = new Room[gridMapSize][gridMapSize];
		roomMap[centerX][centerY] = gridCenter;
		
		for (int i=0;i<oldSize;i++)
		{
			for (int j=0;j<oldSize;j++)
			{
				roomMap[oldSize/2+i][oldSize/2+j] = oldMap[i][j];
			}
		}
		Print.outln("increased map: new size: "+gridMapSize);
	}
	
	public void setGridLinks(Room theGrid)
	{
		theGrid.setGridTop(roomMap[theGrid.getPosX()+centerX][theGrid.getPosY()+centerY+1]);
		theGrid.setGridBottom(roomMap[theGrid.getPosX()+centerX][theGrid.getPosY()+centerY-1]);
		theGrid.setGridLeft(roomMap[theGrid.getPosX()+centerX-1][theGrid.getPosY()+centerY]);
		theGrid.setGridRight(roomMap[theGrid.getPosX()+centerX+1][theGrid.getPosY()+centerY]);
		
		roomMap[theGrid.getPosX()+centerX][theGrid.getPosY()+centerY] = theGrid;
		
		if (roomMap[theGrid.getPosX()+centerX][theGrid.getPosY()+centerY+1] != null)
		{
			roomMap[theGrid.getPosX()+centerX][theGrid.getPosY()+centerY+1].setGridBottom(theGrid);
		}
		if (roomMap[theGrid.getPosX()+centerX][theGrid.getPosY()+centerY-1] != null)
		{
			roomMap[theGrid.getPosX()+centerX][theGrid.getPosY()+centerY-1].setGridTop(theGrid);
		}
		if (roomMap[theGrid.getPosX()+centerX-1][theGrid.getPosY()+centerY] != null)
		{
			roomMap[theGrid.getPosX()+centerX-1][theGrid.getPosY()+centerY].setGridRight(theGrid);
		}
		if (roomMap[theGrid.getPosX()+centerX+1][theGrid.getPosY()+centerY] != null)
		{
			roomMap[theGrid.getPosX()+centerX+1][theGrid.getPosY()+centerY].setGridLeft(theGrid);
		}
	}
	
	public Room getGridTop(Room theGrid)
	{
		if (isGridTop()) return theGrid.getGridTop();
		
		Room tmpGrid = theGrid;
		while (tmpGrid.getGridBottom() != null)
		{
			tmpGrid = tmpGrid.getGridBottom();
		}
		
		return tmpGrid;
//		if (grids[x][y+1] != null) return grids[x][y+1];
//		
//		while (grids[x][y] != null)
//		{
//			y--;
//		}
//		
//		return grids[x][y+1];
	}
	
	public Room getGridBottom(Room theGrid)
	{
		if (isGridBottom()) return theGrid.getGridBottom();
		
		Room tmpGrid = theGrid;
		while (tmpGrid.getGridTop() != null)
		{
			tmpGrid = tmpGrid.getGridTop();
		}
		
		return tmpGrid;
	}
	
	public Room getGridLeft(Room theGrid)
	{
		if (isGridLeft()) return theGrid.getGridLeft();
		
		Room tmpGrid = theGrid;
		while (tmpGrid.getGridRight() != null)
		{
			tmpGrid = tmpGrid.getGridRight();
		}
		
		return tmpGrid;
	}
	
	public Room getGridRight(Room theGrid)
	{
		if (isGridRight()) return theGrid.getGridRight();
		
		Room tmpGrid = theGrid;
		while (tmpGrid.getGridLeft() != null)
		{
			tmpGrid = tmpGrid.getGridLeft();
		}
		
		return tmpGrid;
	}
	
	public Block getLocalBlockAt(int x, int y)
	{
		if (x >= 0 && x < gridSizeX && y >= 0 && y < gridSizeY)
		{
			return activeRoom.getBlockAt(x, y);
		}
		return null;
	}
	
	public Block getBlockAt(int x, int y)
	{
		int gridX = (int)Math.floor(1.f * x / getGridSizeX());
		int gridY = (int)Math.floor(1.f * y / getGridSizeY());
		
		int localX =  x - gridX*getGridSizeX();
		int localY =  y - gridY*getGridSizeY();
		
		if (localX < 0) localX += getGridSizeX();
		if (localY < 0) localY += getGridSizeY();
		
		gridX = gridX - activeRoom.getPosX();
		gridY = gridY - activeRoom.getPosY();
		
//		Print.outln("x: "+x+ ", y: "+y);
//		Print.outln("gridX: "+gridX+ ", gridY: "+gridY);
//		Print.outln("localX: "+localX+ ", localY: "+localY);
		
//		if (gridX >= 0 && gridX <levelStructureSize && gridY >=0 && gridY < levelStructureSize)
//		{
//			if (grids[gridX][gridY] != null)
//			{
//				return grids[gridX][gridY].getBlockAt(localX, localY);
//			}
//		}
//		return null;
		
		
		if (localX >= getGridSizeX() || localY >= getGridSizeY())
		{
			Print.err("local variable too big ("+localX+", "+localY+")");
			return null;
		}
		
		if (gridX == 0 && gridY == 0) return activeRoom.getBlockAt(localX, localY);
		
		if (gridX == -1 && gridY == 0)
		{
			return getGridLeft(activeRoom).getBlockAt(localX, localY);
		}
		
		if (gridX == 1 && gridY == 0)
		{
			return getGridRight(activeRoom).getBlockAt(localX, localY);
		}
		
		if (gridX == 0 && gridY == -1)
		{
			return getGridBottom(activeRoom).getBlockAt(localX, localY);
		}
		
		if (gridX == 0 && gridY == 1)
		{
			return getGridTop(activeRoom).getBlockAt(localX, localY);
		}
		
		if (gridX == -1 && gridY == -1)
		{
			if (isGridLeft())
			{
				Room tmpGrid = getGridLeft(activeRoom).getGridBottom();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
			if (isGridBottom())
			{
				Room tmpGrid = getGridBottom(activeRoom).getGridLeft();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
		}
		
		if (gridX == -1 && gridY == 1)
		{
			if (isGridLeft())
			{
				Room tmpGrid = getGridLeft(activeRoom).getGridTop();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
			if (isGridTop())
			{
				Room tmpGrid = getGridTop(activeRoom).getGridLeft();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
		}
		
		if (gridX == 1 && gridY == -1)
		{
			if (isGridRight())
			{
				Room tmpGrid = getGridRight(activeRoom).getGridBottom();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
			if (isGridBottom())
			{
				Room tmpGrid = getGridBottom(activeRoom).getGridRight();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
		}
		
		if (gridX == 1 && gridY == 1)
		{
			if (isGridTop())
			{
				Room tmpGrid = getGridTop(activeRoom).getGridRight();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
			if (isGridRight())
			{
				Room tmpGrid = getGridRight(activeRoom).getGridTop();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
		}

		return null;
	}
	
	public float getFogAt(int x, int y)
	{
		Block tmpBlock = getBlockAt(x, y);
		if (tmpBlock != null) return tmpBlock.getFogValue();
		return 1.f;
	}
	
	public void drawFog(int x, int y)
	{
		Block tmpBlock = getBlockAt(x, y);
		if (tmpBlock != null) tmpBlock.drawFog(x, y);
	}
	
	public void drawMap()
	{
		
	}
	
	public void setFogAt(int x, int y, float f)
	{
		Block tmpBlock = getBlockAt(x, y);
		if (tmpBlock != null) tmpBlock.setFogValue(f);
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

	public Room getActiveGrid() {
		return activeRoom;
	}
	
	public void setActiveGrid(Room theRoom)
	{
		activeRoom = theRoom;
	}
//
//	public void setActiveGrid(Grid activeGrid) {
//		this.activeGrid = activeGrid;
//	}
	
	public Vector2f generateStartPosition()
	{
		return new Vector2f(activeRoom.getPosX()*gridSizeX+gridSizeX/2.f, activeRoom.getPosY()*gridSizeY+gridSizeY-1.f);
	}
	
	public void update(GameEngine engine)
	{
		Player player = engine.mainPlayer;
		
		int gridX = (int)Math.floor(1.f * player.getPosX() / getGridSizeX());
		int gridY = (int)Math.floor(1.f * player.getPosY() / getGridSizeY());
		
		int diffX = gridX - activeRoom.getPosX();
		int diffY = gridY - activeRoom.getPosY();
		
//		Print.outln("active grid: ("+activeGrid.getPosX()+"; "+activeGrid.getPosY()+")");
//		Print.outln("diff: ("+diffX+"; "+diffY+")");
		
		if (diffY == 1)
		{
			 int tmpY = activeRoom.getPosY();
			 boolean addPlayerPos = !isGridTop();
			activeRoom = getGridTop(activeRoom);
			 if (addPlayerPos)
			 {
				 player.setPosY(player.getPosY()+gridSizeY*(activeRoom.getPosY()-tmpY-1));
				 engine.setPosY(engine.getPosY()-gridSizeY*(activeRoom.getPosY()-tmpY-1));
			 }
//			Print.outln("active grid: ("+activeGrid.getPosX()+"; "+activeGrid.getPosY()+")");
		} else if (diffY == -1)
		{
			 int tmpY = activeRoom.getPosY();
			 boolean addPlayerPos = !isGridBottom();
			activeRoom = getGridBottom(activeRoom);
			 if (addPlayerPos)
			 {
				 player.setPosY(player.getPosY()+gridSizeY*(activeRoom.getPosY()-tmpY+1));
				 engine.setPosY(engine.getPosY()-gridSizeY*(activeRoom.getPosY()-tmpY+1));
			 }
//			Print.outln("active grid: ("+activeGrid.getPosX()+"; "+activeGrid.getPosY()+")");
		}
		
		if (diffX == 1)
		{
			 int tmpX = activeRoom.getPosX();
			 boolean addPlayerPos = !isGridRight();
			activeRoom = getGridRight(activeRoom);
			 if (addPlayerPos)
			 {
				 player.setPosX(player.getPosX()+gridSizeX*(activeRoom.getPosX()-tmpX-1));
				 engine.setPosX(engine.getPosX()-gridSizeX*(activeRoom.getPosX()-tmpX-1));
			 }
//			Print.outln("active grid: ("+activeGrid.getPosX()+"; "+activeGrid.getPosY()+")");
		} else if (diffX == -1)
		{
			 int tmpX = activeRoom.getPosX();
			 boolean addPlayerPos = !isGridLeft();
			activeRoom = getGridLeft(activeRoom);
			 if (addPlayerPos)
			 {
				 player.setPosX(player.getPosX()+gridSizeX*(activeRoom.getPosX()-tmpX+1));
				 engine.setPosX(engine.getPosX()-gridSizeX*(activeRoom.getPosX()-tmpX+1));
			 }
//			Print.outln("active grid: ("+activeGrid.getPosX()+"; "+activeGrid.getPosY()+")");
		}
		
		activeRoom.update(engine, engine.mainPlayer);
		
//		int localX =  x - gridX*getGridSizeX();
//		int localY =  y - gridY*getGridSizeY();
//		
//		if (localX < 0) localX += getGridSizeX();
//		if (localY < 0) localY += getGridSizeY();
		
//		gridX = gridX - activeGrid.getPosX();
//		gridY = gridY - activeGrid.getPosY();
//		
//		if (gridX == -1) activeGrid = activeGrid.getGridLeft();
//		if (gridX == 1) activeGrid = activeGrid.getGridRight();
//		if (gridY == -1) activeGrid = activeGrid.getGridBottom();
//		if (gridY == -1) activeGrid = activeGrid.getGridTop();
	}

	public Room[][] getGridMap() {
		return roomMap;
	}

	public void setGridMap(Room[][] gridMap) {
		this.roomMap = gridMap;
	}

	public int getGridMapSize() {
		return gridMapSize;
	}

	public void setGridMapSize(int gridMapSize) {
		this.gridMapSize = gridMapSize;
	}

	public Room getGridCenter() {
		return gridCenter;
	}

	public void setGridCenter(Room gridCenter) {
		this.gridCenter = gridCenter;
	}
}
