import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.util.Random;
import java.util.Vector;
import java.util.Iterator;



public class Level
{
	private int dimX, dimY;
	private float playerBorderX, playerBorderY; // in percent
	private float posX, posY;
	private Block grid[][];
	public static float scale = 0.2f;
	public float gridSize = 80.f;
	Vector<Collectable> collectableObjects;
	public Player mainPlayer;
	SelectBox mainSelectBox;
	
	
	public Level()
	{
		this.dimX = 20;
		this.dimY = 20;
		playerBorderX = 0.2f;
		playerBorderY = 0.2f;
		posX = posY =0.f;
		grid = new Block[dimX][dimY];
		collectableObjects = new Vector<Collectable>();
		mainPlayer = new Player();
		mainSelectBox = new SelectBox();
		
		initTest();
	}
	
	public Level(int sizeX, int sizeY)
	{
		this.dimX = sizeX;
		this.dimY = sizeY;
		posX = 1.f;
		posY = 1.f;
		playerBorderX = 0.3f;
		playerBorderY = 0.3f;
		grid = new Block[dimX][dimY];
		collectableObjects = new Vector<Collectable>();
		mainPlayer = new Player();
		mainSelectBox = new SelectBox();
		
		initTest();
	}
	
	public void initTest()
	{
		Random rnd = new Random();
		
		for (int i=0;i<dimX;i++)
		{
			for (int j=0;j<dimY;j++)
			{
				grid[i][j] = new Block();
				if ((i==0) || (j==0) || (i==dimX-1) || (j==dimY-1))
				{
					grid[i][j].setType(2);
				} else // inner level
				{
					if (rnd.nextGaussian() < 0.5)
					{
						grid[i][j].setType(1);
					}
				}
			}
		}
		grid[1][1].setType(1);
		grid[1][2].setType(1);
		grid[1][3].setType(1);
		grid[2][2].setType(1);
		grid[2][3].setType(1);
		grid[7][2].setType(1);
		grid[3][3].setType(1);
		grid[(int)Math.round(mainPlayer.getPosX())][(int)Math.round(mainPlayer.getPosY())].setType(0);
		
		collectableObjects.add(new Collectable(1, 7.25f, 3.25f));
	}
	
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
		return dimX;
	}

	public void setDimX(int dimX) {
		this.dimX = dimX;
	}

	public int getDimY() {
		return dimY;
	}

	public void setDimY(int dimY) {
		this.dimY = dimY;
	}
	
	public boolean isBlockSolid(int x, int y)
	{
		if ((x < 0) || (y < 0) || (x >= this.dimX) || (y >= this.dimY)) return false;
		return grid[x][y].isSolid();
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
			collision = Collider.collideBorders(mainPlayer.getPosX(), mainPlayer.getPosX()+mainPlayer.getSizeX(), mainPlayer.getPosY(), mainPlayer.getPosY()+mainPlayer.getSizeY(), x, x+1.f, y, y+1.f);
		}

		if (!collision) return false;
		System.out.println("collision ("+x+", "+y+")");
		
		
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
			if (this.grid[x][y].isDestroyable() && this.grid[x][y].attack())
			{
				//destroyed
				System.out.println("Block destroyed!");
				collectableObjects.add(new Collectable(1, x+0.25f, y+0.25f));
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
		return grid[(int)Math.floor(x)][(int)Math.floor(y)].isSolid();
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
		GL11.glTranslated(-2.f*posX/(Darklords.resX), -2.f*posY/(Darklords.resY), 0.);
		GL11.glScaled(2.f*gridSize/(Darklords.resX), 2.f*gridSize/(Darklords.resY), 1.);
		
		// draw blocks
		for (int i=0;i<dimX;i++)
		{
			for (int j=0;j<dimY;j++)
			{
				GL11.glPushMatrix();
//				GL11.glTranslated((float)i*gridSize, (float)j*gridSize, 0.f);
				GL11.glTranslated(i, j, 0.);
//				GL11.glScaled(1., -1., 1.);
//				GL11.glScaled(1./Darklords.resX, 1./Darklords.resY, 1.);
//				Vector2f pos = gridToScreen(new Vector2f(i,j));
//				pos.print();
//				GL11.glTranslatef(pos.getX(), pos.getY(), 0.f);

				grid[i][j].draw();
				GL11.glPopMatrix();
			}
		}
		
		// draw collectable objects
		for (Iterator<Collectable> object = collectableObjects.iterator(); object.hasNext();)
		{
			Collectable tmp = object.next();
			
			GL11.glPushMatrix();
//			GL11.glTranslated((float)i*gridSize, (float)j*gridSize, 0.f);
			GL11.glTranslated(tmp.getX(), tmp.getY(), 0.);
//			GL11.glScaled(1., -1., 1.);
//			GL11.glScaled(1./Darklords.resX, 1./Darklords.resY, 1.);
//			Vector2f pos = gridToScreen(new Vector2f(i,j));
//			pos.print();
//			GL11.glTranslatef(pos.getX(), pos.getY(), 0.f);

			tmp.draw();
			GL11.glPopMatrix();
		}
		
		// draw player
		GL11.glPushMatrix();
		GL11.glTranslated(mainPlayer.getPosX(), mainPlayer.getPosY(), 0.);
		
		mainPlayer.draw();
		GL11.glPopMatrix();
		
		// draw selction
		GL11.glPushMatrix();
		GL11.glTranslated(mainSelectBox.getX(), mainSelectBox.getY(), 0.f);
		mainSelectBox.draw();
		GL11.glPopMatrix();
		
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
		
		if (x_int < dimX && x_int >= 0 && y_int < dimY && y_int >= 0)
		{
			if (this.isBlockSolid(x_int, y_int))
			{
				mainSelectBox.setPos(x_int, y_int);
				mainSelectBox.show();
			} else
			{
				mainSelectBox.hide();
			}
		} else
		{
			mainSelectBox.hide();
		}
	}
	
	public void mouseDownReaction(Vector2f pos)
	{
		Vector2f mouseGrid = new Vector2f(pos.getX()/this.gridSize, (Darklords.resY-pos.getY())/this.gridSize);
		// translate grid
		mouseGrid.setX(mouseGrid.getX()-this.getPosX());
		mouseGrid.setY(mouseGrid.getY()-this.getPosY());
		
//		System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
		int x_int = (int)Math.floor(mouseGrid.getX());
		int y_int = (int)Math.floor(mouseGrid.getY());
		if ((Math.abs(this.mainPlayer.getPosX()+0.5f-mouseGrid.getX()) < 1.5f) && (Math.abs(this.mainPlayer.getPosY()+0.5f-mouseGrid.getY()) < 1.5f))
		{
			this.attackBlock(x_int, y_int);
		}
	}
	
	public void update()
	{
		// update level position
		
		float playerScreenX = mainPlayer.getPosX() + this.getPosX();
		float playerScreenY = mainPlayer.getPosY() + this.getPosY();
		
//		System.out.println(mainPlayer.getPosX());
//		System.out.println(this.getPlayerBorderX()*(Darklords.resX/gridSize));
		
		float diffLeft = playerScreenX - this.getPlayerBorderX()*(Darklords.resX/gridSize);
		float diffRight = playerScreenX - (1.f - this.getPlayerBorderX())*(Darklords.resX/gridSize);
		float diffTop = playerScreenY - this.getPlayerBorderY()*(Darklords.resY/gridSize);
		float diffBottom = playerScreenY - (1.f - this.getPlayerBorderY())*(Darklords.resY/gridSize);
		
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
		for (Iterator<Collectable> object = collectableObjects.iterator(); object.hasNext();)
		{
			Collectable tmp = object.next();
			if (Collider.collideBorders(mainPlayer.getPosX(), mainPlayer.getPosX()+mainPlayer.getSizeX(), mainPlayer.getPosY(), mainPlayer.getPosY()+mainPlayer.getSizeY(),
					tmp.getX(), tmp.getX()+tmp.getSize(), tmp.getY(), tmp.getY()+tmp.getSize()))
			{
				System.out.println("collide with collectable");
				object.remove();
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
}