package darklord.game;

import java.io.Serializable;

import darklord.math.Vector2f;

public class LevelStructure implements Serializable
{
	private Grid gridCenter;
	private Grid activeGrid;
	private int gridSizeX, gridSizeY;
//	private final int levelStructureSize = 20;
//	private int posX, posY;
//	Grid[][] grids;
//	boolean[][] isDiscovered;
	
	public LevelStructure(int sizeX, int sizeY)
	{
		setGridSizeX(sizeX);
		setGridSizeY(sizeY);
		gridCenter = new Grid(sizeX, sizeY, 0, 0, BlockType.BLOCK_DIRT);
		activeGrid = gridCenter;
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
		return activeGrid.getGridTop() != null;
	}
	
	public boolean isGridBottom()
	{
		return activeGrid.getGridBottom() != null;
//		if ( posY-1 < 0) return false;
//		if (grids[posX][posY-1] != null) return true;
//		return false;
	}
	
	public boolean isGridLeft()
	{
		return activeGrid.getGridLeft() != null;
//		if (posX-1 < 0) return false;
//		if (grids[posX-1][posY] != null) return true;
//		return false;
	}
	
	public boolean isGridRight()
	{
		return activeGrid.getGridRight() != null;
//		if (posX+1 >= levelStructureSize) return false;
//		if (grids[posX+1][posY] != null) return true;
//		return false;
	}
	
	public boolean addGridTop()
	{
		if (activeGrid.getGridTop() == null)
		{
			Grid tmpGrid = new Grid(gridSizeX, gridSizeY, activeGrid.getPosX(), activeGrid.getPosY()+1, BlockType.BLOCK_DIRT);
			tmpGrid.setGridBottom(activeGrid);
			activeGrid.setGridTop(tmpGrid);
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
		if (activeGrid.getGridBottom() == null)
		{
			Grid tmpGrid = new Grid(gridSizeX, gridSizeY, activeGrid.getPosX(), activeGrid.getPosY()-1, BlockType.BLOCK_DIRT);
			tmpGrid.setGridTop(activeGrid);
			activeGrid.setGridBottom(tmpGrid);
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
		if (activeGrid.getGridLeft() == null)
		{
			Grid tmpGrid = new Grid(gridSizeX, gridSizeY, activeGrid.getPosX()-1, activeGrid.getPosY(), BlockType.BLOCK_DIRT);
			tmpGrid.setGridRight(activeGrid);
			activeGrid.setGridLeft(tmpGrid);
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
		if (activeGrid.getGridRight() == null)
		{
			Grid tmpGrid = new Grid(gridSizeX, gridSizeY, activeGrid.getPosX()+1, activeGrid.getPosY(), BlockType.BLOCK_DIRT);
			tmpGrid.setGridLeft(activeGrid);
			activeGrid.setGridRight(tmpGrid);
			return true;
		}
		return false;
//		if (posX+1 >= levelStructureSize) return false;
//		grids[posX+1][posY] = new Grid(gridSizeX, gridSizeY, posX+1, posY, BlockType.BLOCK_DIRT);	
//		isDiscovered[posX+1][posY] = true;
//		return true;
	}
	
	public Grid getGridTop(Grid theGrid)
	{
		if (isGridTop()) return theGrid.getGridTop();
		
		Grid tmpGrid = theGrid;
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
	
	public Grid getGridBottom(Grid theGrid)
	{
		if (isGridBottom()) return theGrid.getGridBottom();
		
		Grid tmpGrid = theGrid;
		while (tmpGrid.getGridTop() != null)
		{
			tmpGrid = tmpGrid.getGridTop();
		}
		
		return tmpGrid;
	}
	
	public Grid getGridLeft(Grid theGrid)
	{
		if (isGridLeft()) return theGrid.getGridLeft();
		
		Grid tmpGrid = theGrid;
		while (tmpGrid.getGridRight() != null)
		{
			tmpGrid = tmpGrid.getGridRight();
		}
		
		return tmpGrid;
	}
	
	public Grid getGridRight(Grid theGrid)
	{
		if (isGridRight()) return theGrid.getGridRight();
		
		Grid tmpGrid = theGrid;
		while (tmpGrid.getGridLeft() != null)
		{
			tmpGrid = tmpGrid.getGridLeft();
		}
		
		return tmpGrid;
	}
	
	public Block getBlockAt(int x, int y)
	{
		int gridX = (int)Math.floor(1.f * x / getGridSizeX());
		int gridY = (int)Math.floor(1.f * y / getGridSizeY());
		
		int localX =  x - gridX*getGridSizeX();
		int localY =  y - gridY*getGridSizeY();
		
		if (localX < 0) localX += getGridSizeX();
		if (localY < 0) localY += getGridSizeY();
		
		gridX = gridX - activeGrid.getPosX();
		gridY = gridY - activeGrid.getPosY();
		
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
		
		if (gridX == 0 && gridY == 0) return activeGrid.getBlockAt(localX, localY);
		
		if (gridX == -1 && gridY == 0)
		{
			return getGridLeft(activeGrid).getBlockAt(localX, localY);
		}
		
		if (gridX == 1 && gridY == 0)
		{
			return getGridRight(activeGrid).getBlockAt(localX, localY);
		}
		
		if (gridX == 0 && gridY == -1)
		{
			return getGridBottom(activeGrid).getBlockAt(localX, localY);
		}
		
		if (gridX == 0 && gridY == 1)
		{
			return getGridTop(activeGrid).getBlockAt(localX, localY);
		}
		
		if (gridX == -1 && gridY == -1)
		{
			if (isGridLeft())
			{
				Grid tmpGrid = getGridLeft(activeGrid).getGridBottom();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
			if (isGridBottom())
			{
				Grid tmpGrid = getGridBottom(activeGrid).getGridLeft();
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
				Grid tmpGrid = getGridLeft(activeGrid).getGridTop();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
			if (isGridTop())
			{
				Grid tmpGrid = getGridTop(activeGrid).getGridLeft();
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
				Grid tmpGrid = getGridRight(activeGrid).getGridBottom();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
			if (isGridBottom())
			{
				Grid tmpGrid = getGridBottom(activeGrid).getGridRight();
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
				Grid tmpGrid = getGridTop(activeGrid).getGridRight();
				if (tmpGrid != null)
				{
					return tmpGrid.getBlockAt(localX, localY);
				}
			}
			if (isGridRight())
			{
				Grid tmpGrid = getGridRight(activeGrid).getGridTop();
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

	public Grid getActiveGrid() {
		return activeGrid;
	}
//
//	public void setActiveGrid(Grid activeGrid) {
//		this.activeGrid = activeGrid;
//	}
	
	public Vector2f generateStartPosition()
	{
		return new Vector2f(activeGrid.getPosX()*gridSizeX+gridSizeX/2.f, activeGrid.getPosY()*gridSizeY+gridSizeY/2.f);
	}
	
	public void update(GameEngine engine)
	{
		Player player = engine.mainPlayer;
		
		int gridX = (int)Math.floor(1.f * player.getPosX() / getGridSizeX());
		int gridY = (int)Math.floor(1.f * player.getPosY() / getGridSizeY());
		
		int diffX = gridX - activeGrid.getPosX();
		int diffY = gridY - activeGrid.getPosY();
		
//		Print.outln("active grid: ("+activeGrid.getPosX()+"; "+activeGrid.getPosY()+")");
//		Print.outln("diff: ("+diffX+"; "+diffY+")");
		
		if (diffY == 1)
		{
			 int tmpY = activeGrid.getPosY();
			 boolean addPlayerPos = !isGridTop();
			activeGrid = getGridTop(activeGrid);
			 if (addPlayerPos)
			 {
				 player.setPosY(player.getPosY()+gridSizeY*(activeGrid.getPosY()-tmpY-1));
				 engine.setPosY(engine.getPosY()-gridSizeY*(activeGrid.getPosY()-tmpY-1));
			 }
			Print.outln("active grid: ("+activeGrid.getPosX()+"; "+activeGrid.getPosY()+")");
		} else if (diffY == -1)
		{
			 int tmpY = activeGrid.getPosY();
			 boolean addPlayerPos = !isGridBottom();
			activeGrid = getGridBottom(activeGrid);
			 if (addPlayerPos)
			 {
				 player.setPosY(player.getPosY()+gridSizeY*(activeGrid.getPosY()-tmpY+1));
				 engine.setPosY(engine.getPosY()-gridSizeY*(activeGrid.getPosY()-tmpY+1));
			 }
			Print.outln("active grid: ("+activeGrid.getPosX()+"; "+activeGrid.getPosY()+")");
		}
		
		if (diffX == 1)
		{
			 int tmpX = activeGrid.getPosX();
			 boolean addPlayerPos = !isGridRight();
			activeGrid = getGridRight(activeGrid);
			 if (addPlayerPos)
			 {
				 player.setPosX(player.getPosX()+gridSizeX*(activeGrid.getPosX()-tmpX-1));
				 engine.setPosX(engine.getPosX()-gridSizeX*(activeGrid.getPosX()-tmpX-1));
			 }
			Print.outln("active grid: ("+activeGrid.getPosX()+"; "+activeGrid.getPosY()+")");
		} else if (diffX == -1)
		{
			 int tmpX = activeGrid.getPosX();
			 boolean addPlayerPos = !isGridLeft();
			activeGrid = getGridLeft(activeGrid);
			 if (addPlayerPos)
			 {
				 player.setPosX(player.getPosX()+gridSizeX*(activeGrid.getPosX()-tmpX+1));
				 engine.setPosX(engine.getPosX()-gridSizeX*(activeGrid.getPosX()-tmpX+1));
			 }
			Print.outln("active grid: ("+activeGrid.getPosX()+"; "+activeGrid.getPosY()+")");
		}
		
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
}
