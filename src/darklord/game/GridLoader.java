package darklord.game;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * class to handle the callable used to load a grid
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 07-06-2013
 * 
 */
public class GridLoader
{
	private Grid resultGrid;
	private boolean done, active;
	private int x, y, currentX, currentY;
	Future<Grid> result;
	ExecutorService executor;
	
	public GridLoader(String fileName, int theX, int theY, int theCurrentX, int theCurrentY, ExecutorService theExecutor)
	{
		x = theX;
		y = theY;
		currentX = theCurrentX;
		currentY = theCurrentY;
		active = true;
		executor = theExecutor;
		initCallable(fileName);
	}
	
	public void initCallable(String fileName)
	{
		done = false;
		Callable<Grid> tmpCallable = new GridLoadCallable(fileName);

		result = executor.submit(tmpCallable);
	}
	
	public boolean update()
	{
		if (result.isDone())
		{
			try {
				done = true;
				resultGrid = result.get();
				return true;
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return false;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Grid getResultGrid() {
		active = false;
		return resultGrid;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
