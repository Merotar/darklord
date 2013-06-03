package game;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GridLoader
{
	private Grid resultGrid;
	private boolean done, active;
	private int x, y, currentX, currentY;
	Future<Grid> result;
	
	public GridLoader(String fileName, int theX, int theY, int theCurrentX, int theCurrentY)
	{
		x = theX;
		y = theY;
		currentX = theCurrentX;
		currentY = theCurrentY;
		active = true;
		initCallable(fileName);
	}
	
	public void initCallable(String fileName)
	{
		done = false;
		Callable<Grid> tmpCallable = new GridLoadCallable(fileName);
		
		ExecutorService executor = Executors.newCachedThreadPool();
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
