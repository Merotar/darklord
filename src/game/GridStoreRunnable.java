package game;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GridStoreRunnable implements Runnable
{
	Grid gridTostore;
	String fileName;
	
	public GridStoreRunnable(Grid theGrid, String theFileName)
	{
		gridTostore = theGrid;
		fileName = theFileName;
	}
	
	public void writeGridToFile(Grid theGrid)
	{
		Print.outln("write file: "+fileName);
		
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		try {
		  fos = new FileOutputStream(fileName);
		  oos = new ObjectOutputStream(fos);
		  
//		  oos.writeObject(theGrid);
		  
		  oos.writeInt(theGrid.getGridSizeX());
		  oos.writeInt(theGrid.getGridSizeY());
		  
		  for (int i=0;i<theGrid.getGridSizeX();i++)
		  {
			  for (int j=0;j<theGrid.getGridSizeY();j++)
			  {
				  oos.writeObject(theGrid.getBlockAt(i, j));
			  }
		  }
		  
		  for (int i=0;i<theGrid.getGridSizeX()*theGrid.getFogDensity();i++)
		  {
			  for (int j=0;j<theGrid.getGridSizeY()*theGrid.getFogDensity();j++)
			  {
				  oos.writeFloat(theGrid.getFogAt(i, j));
			  }
		  }
		  
		  oos.writeInt(theGrid.collectableObjects.size());
		  for (int i=0;i<theGrid.collectableObjects.size();i++)
		  {
			  oos.writeObject(theGrid.collectableObjects.get(i));
		  }
		  
		  oos.writeInt(theGrid.getEnemies().size());
		  for (int i=0;i<theGrid.getEnemies().size();i++)
		  {
			  oos.writeObject(theGrid.getEnemies().get(i));
		  }
		  
		  oos.writeInt(theGrid.chests.size());
		  for (int i=0;i<theGrid.chests.size();i++)
		  {
			  oos.writeObject(theGrid.chests.get(i));
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
	
	@Override
	public void run()
	{
		writeGridToFile(gridTostore);
	}

}
