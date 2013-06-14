package darklord.game;


/**
 * used for easier debugging
 * 
 * @author sartz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Print
{
	private static boolean debugMode;
	
	public Print()
	{
		debugMode = false;
	}
	
	public static void out(String s)
	{
		System.out.print(s);
	}
	
	public static void outln(String s)
	{
		System.out.println(s);
	}
	
	public static void debug(String s)
	{
		if (isDebugMode())
		{
			System.out.println(s);
		}
	}
	
	public static void err(String s)
	{
		System.err.println(s);
	}

	public static boolean isDebugMode() {
		return debugMode;
	}

	public static void setDebugMode(boolean debugMode) {
		Print.debugMode = debugMode;
	}
	
	
}