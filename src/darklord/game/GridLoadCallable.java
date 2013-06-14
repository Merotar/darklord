package darklord.game;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.Callable;

/**
 * callable used to load grids
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
public class GridLoadCallable implements Callable<Grid>
{
	String fileName;
	
	public GridLoadCallable(String theFileName)
	{
		fileName = theFileName;
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
			  
//			  Print.outln("enemy:"+tmpGrid.getEnemies().get(0).dmgOnContact);
			  
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
				e.printStackTrace();
			}
			finally {
			  if (ois != null) try { ois.close(); } catch (IOException e) {}
			  if (fis != null) try { fis.close(); } catch (IOException e) {}
			}
		return tmpGrid;
	}

	@Override
	public Grid call() throws Exception
	{
		return readGridFromFile(fileName);
	}

}
