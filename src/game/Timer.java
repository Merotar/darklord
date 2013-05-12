package game;

/**
 * class used for timing 
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Timer{
	long time0;
	boolean running;
	
	public Timer(){
		time0 = getTime();
		running = false;
	}
	
	public long getTime() {
	    return System.nanoTime() / 1000000l;
	}
	
	public int getTimeDelta(){
		if (running){
			long time = getTime();
		    int delta = (int) (time - time0);
		    return delta;
		} else {
			return 0;
		}
		
	}
	
	public void start(){
		running = true;
		time0 = getTime();
	}
	
	public void stop(){
		running = false;
		time0 = 0;
	}
	
	public void reset(){
		time0 = getTime();
	}
	
	public boolean running(){
		return running;
	}
	
}