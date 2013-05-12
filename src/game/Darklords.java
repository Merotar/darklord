package game;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Main class which contains the basic game flow 
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Darklords {
	
	static int resX, resY;
	static boolean fullscreen;
	static int maxFPS;
	static float ratio;
	Level world;
	Vector<Level> worldLife;
	boolean isLeftMouseDown, isRightMouseDown;
	Vector2f posMouseDown, posRightMouseDown;
	Vector2f mousePos;
	Timer worldTimer, rightMouseDelayTimer;
	int rightMouseDelay = 200;
	static DTextures textures;
	KeyboardSettings myKeyboard;
	boolean devMode;
	int gameMode;
	private int program=0;
	boolean useShader;
	int locResX, locResY;
	Vector2f gameScreenPos;
	float gameScreenScale;
	
	/**
	 * initializes global variables
	 */
	public void init()
	{
		resX = 800;
		resY = 600;
		gameScreenPos = new Vector2f(0.0f, 0.0f);
		gameScreenScale = 1.0f;
		fullscreen = false;
		maxFPS = 30;
		gameMode = 1;
		devMode = false;
		isLeftMouseDown = false;
		isRightMouseDown = false;
		posMouseDown = new Vector2f(0.f, 0.f);
		posRightMouseDown = new Vector2f(0.f, 0.f);
		mousePos = new Vector2f(0.f, 0.f);
		worldLife = new Vector<Level>();
		ratio = 1f*resX/resY;
		myKeyboard = new KeyboardSettings();
	}

	/**
	 * initializes graphics and runs main game loop
	 */
	public void start()
	{	
		int fragShader = 0;
		
		try {
			if (fullscreen)
			{
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				Display.setFullscreen(true);
			} else 
			{
				Display.setDisplayMode(new DisplayMode(resX, resY));
			}
			Display.setVSyncEnabled(true);
			Display.create();
			
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
    	try {
            fragShader = createShader("shaders/screen.frag",ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
    	}
    	catch(Exception exc) {
    		exc.printStackTrace();
    		return;
    	}
    	finally {
    		if(fragShader == 0)
    		{
    			System.err.println("initialization of shaders failed");
    			return;
    		}
    	}
    	
    	program = ARBShaderObjects.glCreateProgramObjectARB();
    	
    	if(program == 0) return;
		
//    	ARBShaderObjects.glAttachObjectARB(program, vertShader);
        ARBShaderObjects.glAttachObjectARB(program, fragShader);
        
        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.err.println(getLogInfo(program));
            return;
        }
        
        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE)
        {
        	System.err.println(getLogInfo(program));
        	return;
        }
        
        useShader = true;
        
		ARBShaderObjects.glUseProgramObjectARB(program);
        
        locResX = ARBShaderObjects.glGetUniformLocationARB(program,"resX");
        ARBShaderObjects.glUniform1iARB(locResX,resX);
        
        locResY = ARBShaderObjects.glGetUniformLocationARB(program,"resY");
        ARBShaderObjects.glUniform1iARB(locResY,resY);

    	
		// load textures
		textures = new DTextures();
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//GL11.glOrtho(0, resX, 0, resY, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glClearDepth(1);
		
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // init other stuff
        
		world = new Level(10,10);
		
		worldTimer = new Timer();
		rightMouseDelayTimer = new Timer();
		Timer fpsTimer = new Timer();
		worldTimer.start();
		fpsTimer.start();
		
		while (!Display.isCloseRequested())
		{
			// main loop repeated while the window is not closed
			
			//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
			//GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

			//world.attack.update();
			if (!devMode)
			{
				checkMouse();
				checkKeyboard();
			} else
			{
				checkMouseDev();
				checkKeyboardDev();
			}

			
//			Vector2f posOld = world.mainPlayer.getPos();
			
			if (!devMode)
			{
				posMouseDown.set(Mouse.getX(), Mouse.getY());
//				if (isLeftMouseDown) System.out.println("left mouse down");
				if (isLeftMouseDown) world.mouseDownReaction(posMouseDown, 0);
				if (isRightMouseDown)
				{
					if (rightMouseDelayTimer.getTimeDelta() == 0)
					{
						rightMouseDelayTimer.reset();
						rightMouseDelayTimer.start();
						world.mouseDownReaction(posMouseDown, 1);
					}
				}
			} else
			{
				if (isLeftMouseDown) world.mouseDownReactionDev(posMouseDown, 0);
				if (isRightMouseDown)
				{
					world.mouseDownReactionDev(posRightMouseDown, 1);
					isRightMouseDown = false;
				}
			}

			world.mousePositionReaction(mousePos);
			float playerPosOld_x = world.mainPlayer.getPosX();
			float playerPosOld_y = world.mainPlayer.getPosY();
			if (!devMode) world.update();
			
			// normal motion
			
			if (world.mainPlayer.moveRight())
			{
				boolean collide = false;
				collide = world.collideWithBlock((float)Math.ceil(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				collide = collide || world.collideWithBlock((float)Math.ceil(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
				if (collide) world.mainPlayer.setPosX((float)Math.ceil(playerPosOld_x)-world.mainPlayer.getSizeX());
//				if (Math.abs(world.mainPlayer.getPosX()+1-playerPosOld_x) < world.mainPlayer.getSpeed())
//				{
//					world.mainPlayer.setPosX(((float)Math.ceil(world.mainPlayer.getPosX())));
//				}
			}
			
			if (world.mainPlayer.moveLeft())
			{
				boolean collide = false;
				collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
				if (collide) world.mainPlayer.setPosX((float)Math.floor(playerPosOld_x));
//				if (Math.abs(world.mainPlayer.getPosX()-playerPosOld_x) < world.mainPlayer.getSpeed())
//				{
//					world.mainPlayer.setPosX(((float)Math.floor(world.mainPlayer.getPosX())));
//				}
			}
			
			if (world.mainPlayer.moveUp())
			{
				boolean collide = false;
				collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()));
				if (collide) world.mainPlayer.setPosY((float)Math.floor(playerPosOld_y));	
			}
			
			if (world.mainPlayer.moveDown())
			{
				boolean collide = false;
				collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				if (collide) world.mainPlayer.setPosY((float)Math.ceil(playerPosOld_y)-world.mainPlayer.getSizeY());
			}
			
			// teleport
			
			if (world.mainPlayer.isTeleportUp())
			{
				boolean collide = false;
				world.mainPlayer.setPosY(world.mainPlayer.getPosY()-world.mainPlayer.getTeleportStep());
				collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				if (collide) world.mainPlayer.setPosY(playerPosOld_y);
				world.mainPlayer.setTeleportUp(false);
			}
			
			if (world.mainPlayer.isTeleportDown())
			{
				boolean collide = false;
				world.mainPlayer.setPosY(world.mainPlayer.getPosY()+world.mainPlayer.getTeleportStep());
				collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				if (collide) world.mainPlayer.setPosY(playerPosOld_y);
				world.mainPlayer.setTeleportDown(false);
			}
			
			if (world.mainPlayer.isTeleportLeft())
			{
				boolean collide = false;
				world.mainPlayer.setPosX(world.mainPlayer.getPosX()-world.mainPlayer.getTeleportStep());
				collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				if (collide) world.mainPlayer.setPosX(playerPosOld_x);
				world.mainPlayer.setTeleportLeft(false);
			}
			
			if (world.mainPlayer.isTeleportRight())
			{
				boolean collide = false;
				world.mainPlayer.setPosX(world.mainPlayer.getPosX()+world.mainPlayer.getTeleportStep());
				collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				if (collide) world.mainPlayer.setPosX(playerPosOld_x);
				world.mainPlayer.setTeleportRight(false);
			}
			
//			if (world.checkSolid())
//			{
//				posOld.round();
//				world.mainPlayer.setPos(posOld);
//			}
			
			draw();
			long waitingTime = (int)Math.round((1000000.f/maxFPS - fpsTimer.getTimeDelta())/1000.);
			if (waitingTime > 0)
			{
				// wait for 30 fps
				try
				{
					Thread.sleep(waitingTime);
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
//			System.out.println("FPS: "+1000./waitingTime);
			Display.update();
			fpsTimer.reset();
			
			if (rightMouseDelayTimer.getTimeDelta() > rightMouseDelay)
			{
				rightMouseDelayTimer.stop();
				rightMouseDelayTimer.reset();
			}
		}

		Display.destroy();
		
	}
	
    /**
    * With the exception of syntax, setting up vertex and fragment shaders
    * is the same.
    * @param the name and path to the vertex shader
    */
    private int createShader(String filename, int shaderType) throws Exception {
    	int shader = 0;
    	try {
	        shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
	        
	        if(shader == 0)
	        	return 0;
	        
	        ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
	        ARBShaderObjects.glCompileShaderARB(shader);
	        
	        if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
	            throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
	        
	        return shader;
    	}
    	catch(Exception exc) {
    		ARBShaderObjects.glDeleteObjectARB(shader);
    		throw exc;
    	}
    }
    
    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
    
    private String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();
        
        FileInputStream in = new FileInputStream(filename);
        
        Exception exception = null;
        
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            
            Exception innerExc= null;
            try {
            	String line;
                while((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            }
            catch(Exception exc) {
            	exception = exc;
            }
            finally {
            	try {
            		reader.close();
            	}
            	catch(Exception exc) {
            		if(innerExc == null)
            			innerExc = exc;
            		else
            			exc.printStackTrace();
            	}
            }
            
            if(innerExc != null)
            	throw innerExc;
        }
        catch(Exception exc) {
        	exception = exc;
        }
        finally {
        	try {
        		in.close();
        	}
        	catch(Exception exc) {
        		if(exception == null)
        			exception = exc;
        		else
					exc.printStackTrace();
        	}
        	
        	if(exception != null)
        		throw exception;
        }
        
        return source.toString();
    }
	
    /**
     * transforms the mouse position into position in game screen area
     * 
     * @param mousePos global mouse position
     * @return mouse position in game screen area
     */
    public Vector2f globalToGamescreen(Vector2f mousePos)
    {
    	Vector2f tmp = mousePos.sub(new Vector2f(gameScreenPos.getX()*resX/2.f, Mouse.getY()-gameScreenPos.getY()*resY/2.f));
//		mousePos.set(Mouse.getX()-gameScreenPos.getX()*resX/2.f, Mouse.getY()-gameScreenPos.getY()*resY/2.f);
    	return tmp;
    }
    
    /**
     * checks mouse input
     */
	public void checkMouse()
	{		
		while (Mouse.next())
		{
//			mousePos.set(Mouse.getX()-gameScreenPos.getX()*resX/2.f, Mouse.getY()-gameScreenPos.getY()*resY/2.f);
			mousePos = globalToGamescreen(new Vector2f(Mouse.getX(),Mouse.getY()));
			
			if (Mouse.getEventButtonState())
			{
				
				if (Mouse.isButtonDown(0))
				{
//					float[] pos = new float[2];
//					pos[0] = Mouse.getX();
//					pos[1] = Mouse.getY();
					
					isLeftMouseDown= true;
//					posLeftMouseDown.set(pos[0], pos[1]);
					
//					System.out.println("Mouse Pressed: "+pos[0]+", "+pos[1]);
					
//					Vector2f mouseGrid = new Vector2f(pos[0]/world.gridSize, (resY-pos[1])/world.gridSize);
//					// translate grid
//					mouseGrid.setX(mouseGrid.getX()-world.getPosX());
//					mouseGrid.setY(mouseGrid.getY()-world.getPosY());
//					
//					System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
//					int x_int = (int)Math.floor(mouseGrid.getX());
//					int y_int = (int)Math.floor(mouseGrid.getY());
//					if ((Math.abs(world.mainPlayer.getPosX()+0.5f-mouseGrid.getX()) < 1.5f) && (Math.abs(world.mainPlayer.getPosY()+0.5f-mouseGrid.getY()) < 1.5f))
//					{
//						world.attackBlock(x_int, y_int);
//					}
				}
				
				if (Mouse.isButtonDown(1))
				{
					isRightMouseDown= true;
				}
			} //else
//			{
//				isLeftMouseDown = false;
//			}
			if (!Mouse.getEventButtonState() && !Mouse.isButtonDown(0))
			{
				isLeftMouseDown = false;
			}
			
			if (!Mouse.getEventButtonState() && !Mouse.isButtonDown(1))
			{
				isRightMouseDown = false;
			}
		}
	}
	
	/**
	 * checks keybaord input
	 */
	public void checkKeyboard()
	{
		Keyboard.enableRepeatEvents(true);
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.isKeyDown(myKeyboard.KEY_ESCAPE))
				{
					System.exit(0);
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_F1))
				{
					System.out.println("edit mode on");
					devMode = true;
				}
//				
//				if (Keyboard.isKeyDown(Keyboard.KEY_F))
//				{
//					worldLife.add(new Level(world));
//				}
				
//				if (Keyboard.isKeyDown(Keyboard.KEY_G) && (worldLife.size() > 0))
//				{
//					world = worldLife.lastElement();
//					worldLife.remove(worldLife.size()-1);
//				}
				
				// basic movement
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_W))
				{
					if (Keyboard.isKeyDown((myKeyboard.KEY_1)) && world.mainPlayer.isActiveAbility(PlayerAbility.TELEPORT))
					{
						world.mainPlayer.setTeleportUp(true);
					} else
					{
						world.mainPlayer.startUp();
					}
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_S))
				{
					if (Keyboard.isKeyDown((myKeyboard.KEY_1)) && world.mainPlayer.isActiveAbility(PlayerAbility.TELEPORT))
					{
						world.mainPlayer.setTeleportDown(true);
					} else
					{
						world.mainPlayer.startDown();
					}
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_A))
				{
					if (Keyboard.isKeyDown((myKeyboard.KEY_1)) && world.mainPlayer.isActiveAbility(PlayerAbility.TELEPORT))
					{
						world.mainPlayer.setTeleportLeft(true);
					} else
					{
						world.mainPlayer.startLeft();
					}
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_D))
				{
					if (Keyboard.isKeyDown((myKeyboard.KEY_1)) && world.mainPlayer.isActiveAbility(PlayerAbility.TELEPORT))
					{
						world.mainPlayer.setTeleportRight(true);
					} else
					{
						world.mainPlayer.startRight();
					}
				}
			} else
			{
				world.mainPlayer.stopUp();
				world.mainPlayer.stopDown();
				world.mainPlayer.stopLeft();
				world.mainPlayer.stopRight();
			}
		}
	}
	
	/**
	 * checks mosue input if in developer mode
	 */
	public void checkMouseDev()
	{		
		while (Mouse.next())
		{
			mousePos.set(Mouse.getX(), Mouse.getY());
			
			if (Mouse.getEventButtonState())
			{
				if (Mouse.isButtonDown(0))
				{
					float[] pos = new float[2];
					pos[0] = Mouse.getX();
					pos[1] = Mouse.getY();
					
					isLeftMouseDown= true;
					posMouseDown.set(pos[0], pos[1]);
					
//					System.out.println("Mouse Pressed: "+pos[0]+", "+pos[1]);
					
//					Vector2f mouseGrid = new Vector2f(pos[0]/world.gridSize, (resY-pos[1])/world.gridSize);
//					// translate grid
//					mouseGrid.setX(mouseGrid.getX()-world.getPosX());
//					mouseGrid.setY(mouseGrid.getY()-world.getPosY());
//					
//					System.out.println("Mouse Pressed: "+mouseGrid.getX()+", "+mouseGrid.getY());
//					int x_int = (int)Math.floor(mouseGrid.getX());
//					int y_int = (int)Math.floor(mouseGrid.getY());
//					if ((Math.abs(world.mainPlayer.getPosX()+0.5f-mouseGrid.getX()) < 1.5f) && (Math.abs(world.mainPlayer.getPosY()+0.5f-mouseGrid.getY()) < 1.5f))
//					{
//						world.attackBlock(x_int, y_int);
//					}
				}
				if (Mouse.isButtonDown(1))
				{
					float[] pos = new float[2];
					pos[0] = Mouse.getX();
					pos[1] = Mouse.getY();
					
					isRightMouseDown = true;
					posRightMouseDown.set(pos[0], pos[1]);
				}
			} else
			{
				isLeftMouseDown = false;
				isRightMouseDown = false;
			}
		}
	}
	
	/**
	 * used to check keyboard input if in developer mode
	 */
	public void checkKeyboardDev()
	{
		Keyboard.enableRepeatEvents(false);
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.isKeyDown(myKeyboard.KEY_ESCAPE))
				{
					System.exit(0);
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_F1))
				{
					System.out.println("edit mode off");
					devMode = false;
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_PERIOD) && devMode)
				{
					world.map.writeToFile();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_COMMA) && devMode)
				{
//					System.out.println("test: "+Keyboard.getEventKey());
					world.map.readFile();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_W))
				{
					world.setPosY(world.getPosY()+1.f);
				}
				if (Keyboard.isKeyDown(myKeyboard.KEY_S))
				{
					world.setPosY(world.getPosY()-1.f);
				}
				if (Keyboard.isKeyDown(myKeyboard.KEY_A))
				{
					world.setPosX(world.getPosX()+1.f);
				}
				if (Keyboard.isKeyDown(myKeyboard.KEY_D))
				{
					world.setPosX(world.getPosX()-1.f);
				}
				if (Keyboard.isKeyDown(myKeyboard.KEY_Q))
				{
					DevModeSettings.increaseActiveBlock();
					System.out.println("active block: "+DevModeSettings.getActiveBLock());
				}
				if (Keyboard.isKeyDown(myKeyboard.KEY_E))
				{
					DevModeSettings.increaseActiveCollectable();
					System.out.println("active collectable: "+DevModeSettings.getActiveCollectable());
				}
			}
		}
	}
	
	/**
	 * draws the whole scene
	 */
	public void draw()
	{
//		if (useShader)
//		{
//			ARBShaderObjects.glUseProgramObjectARB(program);
//		}
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glScalef(gameScreenScale, gameScreenScale, 1.f);
		GL11.glTranslatef(gameScreenPos.getX(), gameScreenPos.getY(), 0.f);
		GL11.glClearColor(0.f, 0.f, 0.f, 1.f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		world.draw();
//		if(useShader)
//		{
//			ARBShaderObjects.glUseProgramObjectARB(0);
//		}
	}
	
	public static void main(String[] argv)
	{
		Darklords darklords = new Darklords();
		darklords.init();
		darklords.start();
	}
}