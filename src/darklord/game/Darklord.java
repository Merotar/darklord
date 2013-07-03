package darklord.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.text.Position;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import darklord.media.SoundLoader;
import darklord.media.SpriteSheet;
import darklord.media.TextDrawer;
import darklord.media.TextureRegion;
import darklord.ui.Button;
import darklord.ui.DevUI;
import darklord.ui.IngameStatus;
import darklord.ui.UI;
import darklord.math.Vector2f;

/**
 * Main class which contains the basic game flow 
 * 
 * @author Sebastian Artz
 * @version 0.1
 * @since 12-05-2013
 * 
 */
public class Darklord {
	
	static int resX, resY;
	static boolean fullscreen;
	static int maxFPS;
	static float ratio;
	GameEngine world;
	Vector<String> levelList;
//	Vector<Level> worldLife;
	boolean isLeftMouseDown, isRightMouseDown, isLeftMouseReleased;
	Vector2f posMouseDown, posRightMouseDown;
	Vector2f mousePos;
	Timer worldTimer, rightMouseDelayTimer;
	public static float dt;
	int rightMouseDelay = 200;
//	static DTextures textures;
	KeyboardSettings myKeyboard;
	static boolean devMode;
	int gameMode;
	private int program=0;
	boolean useShader;
	int locResX, locResY;
	Vector2f gameScreenPos; // (0, 0) ist top left
	float gameScreenScale, musicPosition;
	public static SpriteSheet sprites01, chars, ui;
	GameStatus gameStatus;
	UI mainMenu;
	DevUI devUI;
//	IngameUI ingameUI;
	public static SoundLoader sounds;
	public static TextDrawer textDrawer;
	
	/**
	 * initializes global variables
	 */
	public void init()
	{
		resX = 1280;
		resY = 800;
		gameScreenScale = 1.0f;
		gameScreenPos = new Vector2f(0.5f, 0.f);
		fullscreen = false;
		useShader = false;
		maxFPS = 30;
		gameMode = 1;
		devMode = false;
		isLeftMouseDown = false;
		isRightMouseDown = false;
		isLeftMouseReleased = false;
		posMouseDown = new Vector2f(0.f, 0.f);
		posRightMouseDown = new Vector2f(0.f, 0.f);
		mousePos = new Vector2f(0.f, 0.f);
//		worldLife = new Vector<Level>();
		ratio = 1.f*resX/resY;
		myKeyboard = new KeyboardSettings();
		gameStatus = GameStatus.MAIN_MENU;
		sounds = new SoundLoader();
		musicPosition = 0.f;
		
		// add levels
		levelList = new Vector<String>();
//		levelList.add(new String("defaultMap.map"));
//		levelList.add(new String("defaultMap.map"));
//		levelList.add(new String("test.txt"));
//		levelList.add(new String("level01.txt"));
		levelList.add(new String("test"));
		
	}
	
//	public void initGameUI()
//	{
//        ingameUI = new IngameUI(ratio);
//        ingameUI.init(world);
//        
////        ingameUI.setPosition(new Vector2f(-1.f, -1.f));
////        ingameUI.setSize(new Vector2f(2.f, 2.f));
////        
////        UIObject panelLeft = new UIObject(new TextureRegion(0.f, 0.f, 3*128, 6*128), "panel left");
////        panelLeft.setSize(new Vector2f(0.25f, 2.f));
////        panelLeft.setPosition(new Vector2f(0.f, 0.f));
////        ingameUI.addUIObject(panelLeft);
////        
////        UIBar energyBarRed = new UIBar(new TextureRegion(0.f, 6*128+0*32, 2*128, 32), "energy bar red");
////        energyBarRed.setSize(new Vector2f(0.8f*0.25f, .02f));
////        energyBarRed.setPosition(new Vector2f(0.1f*0.25f, 0.2f));
////        ingameUI.setEnergyBarRed(energyBarRed);
////        
////        UIBar energyBarBlue = new UIBar(new TextureRegion(0.f, 6*128+1*32, 2*128, 32), "energy bar red");
////        energyBarBlue.setSize(new Vector2f(0.8f*0.25f, .02f));
////        energyBarBlue.setPosition(new Vector2f(0.1f*0.25f, 0.25f));
////        ingameUI.setEnergyBarBlue(energyBarBlue);
////        
////        UIBar energyBarGreen = new UIBar(new TextureRegion(0.f, 6*128+2*32, 2*128, 32), "energy bar red");
////        energyBarGreen.setSize(new Vector2f(0.8f*0.25f, .02f));
////        energyBarGreen.setPosition(new Vector2f(0.1f*0.25f, 0.3f));
////        ingameUI.setEnergyBarGreen(energyBarGreen);
////        
////        UIBar energyBarYellow = new UIBar(new TextureRegion(0.f, 6*128+3*32, 2*128, 32), "energy bar red");
////        energyBarYellow.setSize(new Vector2f(0.8f*0.25f, .02f));
////        energyBarYellow.setPosition(new Vector2f(0.1f*0.25f, 0.35f));
////        ingameUI.setEnergyBarYellow(energyBarYellow);
////        
////        // add UIObjects
////        
////        UIObject crystalRed = new UIObject(new TextureRegion(4*128, 3*128, 128, 128), "crystal red");
////        crystalRed.setSize(new Vector2f(0.05f, .05f*ratio));
////        crystalRed.setPosition(new Vector2f(0.01f, .45f));
////        ingameUI.addUIObjectIngame(crystalRed);
////        
////        UIObject crystalBlue = new UIObject(new TextureRegion(5*128, 3*128, 128, 128), "crystal red");
////        crystalBlue.setSize(new Vector2f(0.05f, .05f*ratio));
////        crystalBlue.setPosition(new Vector2f(0.07f, .45f));
////        ingameUI.addUIObjectIngame(crystalBlue);
////        
////        UIObject crystalGreen = new UIObject(new TextureRegion(3*128, 3*128, 128, 128), "crystal red");
////        crystalGreen.setSize(new Vector2f(0.05f, .05f*ratio));
////        crystalGreen.setPosition(new Vector2f(0.13f, .45f));
////        ingameUI.addUIObjectIngame(crystalGreen);
////        
////        UIObject crystalYellow = new UIObject(new TextureRegion(2*128, 3*128, 128, 128), "crystal red");
////        crystalYellow.setSize(new Vector2f(0.05f, .05f*ratio));
////        crystalYellow.setPosition(new Vector2f(0.19f, .45f));
////        ingameUI.addUIObjectIngame(crystalYellow);
////        
////        // add texts
////        
////        StaticText<Integer> numCrystalsRed = new StaticText<Integer>("$", world.mainPlayer.getCrystalsRed());
////        numCrystalsRed.setSize(new Vector2f(0.05f, 0.05f*ratio));
////        numCrystalsRed.setPosition(new Vector2f(0.033f, 0.455f));
////        ingameUI.setNumCrystalsRed(numCrystalsRed);
////        
////        StaticText<Integer> numCrystalsBlue = new StaticText<Integer>("$", world.mainPlayer.getCrystalsRed());
////        numCrystalsBlue.setSize(new Vector2f(0.05f, 0.05f*ratio));
////        numCrystalsBlue.setPosition(new Vector2f(0.091f, 0.455f));
////        ingameUI.setNumCrystalsBlue(numCrystalsBlue);
////        
////        StaticText<Integer> numCrystalsGreen = new StaticText<Integer>("$", world.mainPlayer.getCrystalsRed());
////        numCrystalsGreen.setSize(new Vector2f(0.05f, 0.05f*ratio));
////        numCrystalsGreen.setPosition(new Vector2f(0.148f, 0.455f));
////        ingameUI.setNumCrystalsGreen(numCrystalsGreen);
////        
////        StaticText<Integer> numCrystalsYellow = new StaticText<Integer>("$", world.mainPlayer.getCrystalsRed());
////        numCrystalsYellow.setSize(new Vector2f(0.05f, 0.05f*ratio));
////        numCrystalsYellow.setPosition(new Vector2f(0.205f, 0.455f));
////        ingameUI.setNumCrystalsYellow(numCrystalsYellow);
////        
////        StaticText<Integer> numPlayerHealth = new StaticText<Integer>("$HP", (int)world.mainPlayer.getHp());
////        numPlayerHealth.setSize(new Vector2f(0.05f, 0.05f*ratio));
////        numPlayerHealth.setPosition(new Vector2f(0.125f, 0.1f));
////        ingameUI.setNumPlayerHealth(numPlayerHealth);
////        
////        StaticText<Integer> numPlayerXp = new StaticText<Integer>("$XP", world.mainPlayer.getXp());
////        numPlayerXp.setSize(new Vector2f(0.05f, 0.05f*ratio));
////        numPlayerXp.setPosition(new Vector2f(0.125f, 0.8f));
////        ingameUI.setNumPlayerXp(numPlayerXp);
////        
////        StaticText<Integer> numPlayerLevel = new StaticText<Integer>("LEVEL:$", world.mainPlayer.getLevel());
////        numPlayerLevel.setSize(new Vector2f(0.05f, 0.05f*ratio));
////        numPlayerLevel.setPosition(new Vector2f(0.125f, 0.7f));
////        ingameUI.setNumPlayerLevel(numPlayerLevel);
//	}
	
	public void initMainMenu()
	{
        mainMenu = new UI("darklord/img/main_menu.png");
        mainMenu.setBackground(new TextureRegion(0.f, 0.f, 800, 600));
        mainMenu.setPosition(new Vector2f(-1.f, -1.f));
        mainMenu.setSize(new Vector2f(2.f, 2.f));
        
        // add start button
        Button startButton = new Button(new TextureRegion(0*64, 10*64, 3*64, 64),
        		new TextureRegion(3*64, 10*64, 3*64, 64),"start");
        startButton.setSize(new Vector2f(3.0f*0.1f, 0.1f*ratio));
        startButton.setPosition(new Vector2f(0.5f, 0.7f).sub(startButton.getSize().mul(0.5f)));
        mainMenu.addButton(startButton);
        
        // add quit button
        Button quitButton = new Button(new TextureRegion(0*64, 11*64, 3*64, 64), 
        		new TextureRegion(3*64, 11*64, 3*64, 64), "quit");
        quitButton.setSize(new Vector2f(3.0f*0.1f, 0.1f*ratio));
        quitButton.setPosition(new Vector2f(0.5f, 0.4f).sub(quitButton.getSize().mul(0.5f)));
        mainMenu.addButton(quitButton);
	}

	/**
	 * initializes graphics and runs main game loop
	 */
	public void start()
	{	
		int fragShader = 0;
		int vertShader = 0;
		
		try {
			if (fullscreen)
			{
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				resX = Display.getDisplayMode().getWidth();
				resY = Display.getDisplayMode().getHeight();
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
		
		if (useShader)
		{
			try {
	            fragShader = createShader("shaders/screen.frag",ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
	            vertShader = createShader("shaders/screen.vert",ARBVertexShader.GL_VERTEX_SHADER_ARB);
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
			
	    	ARBShaderObjects.glAttachObjectARB(program, vertShader);
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
	        
//	        useShader = true;
	        
			ARBShaderObjects.glUseProgramObjectARB(program);
	        
	        locResX = ARBShaderObjects.glGetUniformLocationARB(program,"resX");
	        ARBShaderObjects.glUniform1iARB(locResX,resX);
	        
	        locResY = ARBShaderObjects.glGetUniformLocationARB(program,"resY");
	        ARBShaderObjects.glUniform1iARB(locResY,resY);
		}
    	
		// load textures
//		textures = new DTextures();
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
//		GL11.glOrtho(0, resX, 0, resY, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glShadeModel(GL11.GL_SMOOTH);
//		GL11.glDisable(GL11.GL_DEPTH_TEST);
//		GL11.glDisable(GL11.GL_LIGHTING);
//		GL11.glClearDepth(1);
		
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // init other stuff
        
        sprites01 = new SpriteSheet("darklord/img/textures.png");
        chars = new SpriteSheet("darklord/img/chars.png");
        ui = new SpriteSheet("darklord/img/ui.png");
        
        world = new GameEngine(levelList.firstElement());
        
//        world.setResX((int)(resX-(gameScreenPos.getX()*resX/2.f)));
//        world.setResX((int)(resY-(gameScreenPos.getY()*resY/2.f)));
        
//      world = new Level(15,15);

		textDrawer = new TextDrawer("darklord/img/font.png");
        
		devUI = new DevUI(ratio);
		devUI.init(world);
		
        initMainMenu();
//        initGameUI();
        
		worldTimer = new Timer();
		rightMouseDelayTimer = new Timer();
//		Timer fpsTimer = new Timer();
		worldTimer.start();
		dt = 0;
//		sounds.mainTheme.playAsMusic(1.f, sounds.volumeMusic, true);
//		fpsTimer.start();
		
		while (!Display.isCloseRequested())
		{
			int glErr = GL11.glGetError();
			if (glErr != 0) Print.err("OpenglGL error: "+glErr);
			
			dt = worldTimer.getTimeDelta()/1000.f;
			worldTimer.reset();
			
			switch (gameStatus)
			{
			case MAIN_MENU:
				mainMenu();
				break;
			case INGAME:
				if (!devMode)
				{
					ingame();
				} else
				{
					ingameDev();
				}
				break;
			default:
				Print.err("wrong game status");
				break;
			}
			
//			long waitingTime = (int)Math.round((1000000.f/maxFPS - fpsTimer.getTimeDelta())/1000.);
//			if (waitingTime > 0)
//			{
//				// wait for 30 fps
//				try
//				{
//					Thread.sleep(waitingTime);
//				} catch(Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//			System.out.println("FPS: "+1000./waitingTime);
			Display.update();
			Display.sync(30);
//			fpsTimer.reset();
		}

		quitGame();
		
	}
	
	public void mainMenu()
	{
		checkMouseMainMenu();
		
		// update main menu

		if(isLeftMouseReleased)
		{
			for (Iterator<Button> buttonObj = mainMenu.getButtons().iterator();buttonObj.hasNext();)
			{
				Button tmpButton = buttonObj.next();

				if (tmpButton.isInside(mousePos))
				{
					if (tmpButton.isDown())
					{
						if (tmpButton.getName().equals("start"))
						{
							// start / resume game
							gameStatus = GameStatus.INGAME;
							isLeftMouseReleased = false;
							isLeftMouseDown = false;
						}
						
						if (tmpButton.getName().equals("quit"))
						{
							isLeftMouseReleased = false;
							quitGame();
						}
					}
				}
				tmpButton.setDown(false);
				isLeftMouseReleased = false;
			}
		}
		
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
//		GL11.glOrtho(0, resX, 0, resY, 1, -1);
//		GL11.glScalef(1.f, -1.f, 1.f);
//		GL11.glTranslatef(0.f, -resY, 0.f);
		mainMenu.draw();
		GL11.glPopMatrix();
	}
	
	public void quitGame()
	{
		world.finalize();
		Display.destroy();
		AL.destroy();
		System.exit(0);
	}
	
	public void ingameDev()
	{
		//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
		//GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

		//world.attack.update();

		checkMouseDev();
		checkKeyboardDev();

		if (isLeftMouseDown)
		{
			world.mouseDownReactionDev(globalToGamescreen(posMouseDown), 0, devUI);
			devUI.mouseDownReaction(screenToWorld(posMouseDown), 0, world.map.levelStructure);
		}
		if (isRightMouseDown)
		{
			world.mouseDownReactionDev(globalToGamescreen(posRightMouseDown), 1, devUI);
			isRightMouseDown = false;
		}

		world.mousePositionReaction(globalToGamescreen(mousePos));
		world.mousePositionReactionDev(globalToGamescreen(mousePos), devUI);
		
//		devUI.mousePositionReaction(globalToGamescreen(mousePos));
		devUI.update(world, dt);
		
		drawIngameDev();
		
//		world.mainUI.mousePositionReaction(screenToWorld(mousePos));
	}
	
	public void ingame()
	{
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

		
//		Vector2f posOld = world.mainPlayer.getPos();
		
		if (!devMode)
		{
			posMouseDown.set(Mouse.getX(), Mouse.getY());
//			if (isLeftMouseDown) System.out.println("left mouse down");
			if (isLeftMouseDown)
			{
				world.mouseDownReaction(globalToGamescreen(posMouseDown), 0);
				world.mainUI.mouseDownReaction(screenToWorld(posMouseDown), 0, world.map.levelStructure);
			}
			if (isRightMouseDown)
			{
				if (rightMouseDelayTimer.getTimeDelta() == 0)
				{
					rightMouseDelayTimer.reset();
					rightMouseDelayTimer.start();
					world.mouseDownReaction(globalToGamescreen(posMouseDown), 1);
				}
			}
		} else
		{
			if (isLeftMouseDown) world.mouseDownReactionDev(globalToGamescreen(posMouseDown), 0, devUI);
			if (isRightMouseDown)
			{
				world.mouseDownReactionDev(globalToGamescreen(posRightMouseDown), 1, devUI);
				isRightMouseDown = false;
			}
		}

		if (!world.isMapActive()) world.mousePositionReaction(globalToGamescreen(mousePos));
		world.mainUI.mousePositionReaction(screenToWorld(mousePos));
		
		float playerPosOld_x = world.mainPlayer.getPosX();
		float playerPosOld_y = world.mainPlayer.getPosY();
		float overlapLimit = 0.35f;
		
		if (!devMode) world.update(dt);
		
		// normal motion
		
		if (world.mainPlayer.moveRight())
		{
			boolean collide = false;
			boolean collide2 = false;
			float overlapY1, overlapY2;
			
			collide = world.collideWithBlock((float)Math.ceil(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
			collide2 = world.collideWithBlock((float)Math.ceil(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));

			if (!(collide && collide2))
			{
				if (collide)
				{
					overlapY1 = world.mainPlayer.getPosY()+world.mainPlayer.getSizeY() - (float)Math.floor(world.mainPlayer.getPosY()+1.f);
//					Print.outln("overlapY: "+overlapY);
					if (overlapY1 < overlapLimit)
					{
						world.mainPlayer.setPosY(world.mainPlayer.getPosY()- overlapY1);
//						collide = false;
					}
				}
				
				if (collide2)
				{
					overlapY2 = (float)Math.floor(world.mainPlayer.getPosY()+1.f) - world.mainPlayer.getPosY();
//					Print.outln("overlapY: "+overlapY);
					if (overlapY2 < overlapLimit)
					{
						world.mainPlayer.setPosY(world.mainPlayer.getPosY() + overlapY2);
//						collide2 = false;
					}
				}
			}
			
			if (collide || collide2) world.mainPlayer.setPosX((float)Math.ceil(playerPosOld_x)-world.mainPlayer.getSizeX());
//			if (Math.abs(world.mainPlayer.getPosX()+1-playerPosOld_x) < world.mainPlayer.getSpeed())
//			{
//				world.mainPlayer.setPosX(((float)Math.ceil(world.mainPlayer.getPosX())));
//			}
		}
		
		if (world.mainPlayer.moveLeft())
		{
			boolean collide = false;
			boolean collide2 = false;
			float overlapY1, overlapY2;
			
			collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
			collide2 = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
			
			if (!(collide && collide2))
			{
				if (collide)
				{
					overlapY1 = world.mainPlayer.getPosY()+world.mainPlayer.getSizeY() - (float)Math.floor(world.mainPlayer.getPosY()+1.f);
//					Print.outln("overlapY: "+overlapY);
					if (overlapY1 < overlapLimit)
					{
						world.mainPlayer.setPosY(world.mainPlayer.getPosY()- overlapY1);
//						collide = false;
					}
				}
				
				if (collide2)
				{
					overlapY2 = (float)Math.floor(world.mainPlayer.getPosY()+1.f) - world.mainPlayer.getPosY();
//					Print.outln("overlapY: "+overlapY);
					if (overlapY2 < overlapLimit)
					{
						world.mainPlayer.setPosY(world.mainPlayer.getPosY() + overlapY2);
//						collide2 = false;
					}
				}
			}
			
			if (collide || collide2) world.mainPlayer.setPosX((float)Math.floor(playerPosOld_x));
//			if (Math.abs(world.mainPlayer.getPosX()-playerPosOld_x) < world.mainPlayer.getSpeed())
//			{
//				world.mainPlayer.setPosX(((float)Math.floor(world.mainPlayer.getPosX())));
//			}
		}
		
		if (world.mainPlayer.moveUp())
		{
			boolean collide = false;
			boolean collide2 = false;
			float overlapX1, overlapX2;
			
			collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
			collide2 = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()+1.f));

			
			if (!(collide && collide2))
			{
				if (collide)
				{
					overlapX1 = (float)Math.floor(world.mainPlayer.getPosX()+1.f) - world.mainPlayer.getPosX();
//					Print.outln("overlapX: "+overlapX);
					if (overlapX1 < overlapLimit)
					{
						world.mainPlayer.setPosX(world.mainPlayer.getPosX() + overlapX1);
//						collide = false;
					}
				}
				
				if (collide2)
				{
					overlapX2 = world.mainPlayer.getPosX() + world.mainPlayer.getSizeX() - (float)Math.floor(world.mainPlayer.getPosX()+1.f);
//					Print.outln("overlapY: "+overlapY);
					if (overlapX2 < overlapLimit)
					{
						world.mainPlayer.setPosX(world.mainPlayer.getPosX() - overlapX2);
//						collide2 = false;
					}
				}
			}
			
			if (collide || collide2) world.mainPlayer.setPosY((float)Math.ceil(playerPosOld_y)-world.mainPlayer.getSizeY());	
		}
		
		if (world.mainPlayer.moveDown())
		{
			boolean collide = false;
			boolean collide2 = false;
			float overlapX1, overlapX2;
			
			collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
			collide2 = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()));

			if (!(collide && collide2))
			{
				if (collide)
				{
					overlapX1 = (float)Math.floor(world.mainPlayer.getPosX()+1.f) - world.mainPlayer.getPosX();
//					Print.outln("overlapX: "+overlapX);
					if (overlapX1 < overlapLimit)
					{
						world.mainPlayer.setPosX(world.mainPlayer.getPosX() + overlapX1);
//						collide = false;
					}
				}
				
				if (collide2)
				{
					overlapX2 = world.mainPlayer.getPosX() + world.mainPlayer.getSizeX() - (float)Math.floor(world.mainPlayer.getPosX()+1.f);
//					Print.outln("overlapY: "+overlapY);
					if (overlapX2 < overlapLimit)
					{
						world.mainPlayer.setPosX(world.mainPlayer.getPosX() - overlapX2);
//						collide2 = false;
					}
				}
			}
			
			if (collide || collide2) world.mainPlayer.setPosY((float)Math.floor(playerPosOld_y));
		}
		
		// teleport
		
		if (world.mainPlayer.isTeleportUp())
		{
			boolean collide = false;
			world.mainPlayer.setPosY(world.mainPlayer.getPosY()+world.mainPlayer.getTeleportStep());
			if (world.mainPlayer.getPosY() > world.getDimY()) collide = true;
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()));
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
			if (collide) world.mainPlayer.setPosY(playerPosOld_y);
			world.mainPlayer.setTeleportDown(false);
		}
		
		if (world.mainPlayer.isTeleportDown())
		{
			boolean collide = false;
			world.mainPlayer.setPosY(world.mainPlayer.getPosY()-world.mainPlayer.getTeleportStep());
			if (world.mainPlayer.getPosY() < 0.f) collide = true;
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()));
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
			if (collide) world.mainPlayer.setPosY(playerPosOld_y);
			world.mainPlayer.setTeleportUp(false);
		}
		
		if (world.mainPlayer.isTeleportLeft())
		{
			boolean collide = false;
			world.mainPlayer.setPosX(world.mainPlayer.getPosX()-world.mainPlayer.getTeleportStep());
			if (world.mainPlayer.getPosX() < 0.f) collide = true;
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
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
			if (world.mainPlayer.getPosX() > world.getDimX()) collide = true;
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()));
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
			collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
			if (collide) world.mainPlayer.setPosX(playerPosOld_x);
			world.mainPlayer.setTeleportRight(false);
		}
		
//		if (world.checkSolid())
//		{
//			posOld.round();
//			world.mainPlayer.setPos(posOld);
//		}
		
		// check if level is finished
		float tmpX = (float)Math.floor(world.mainPlayer.getPosX());
		float tmpY = (float)Math.floor(world.mainPlayer.getPosY());
		
		// player dies
		if (world.mainPlayer.getHp()<=0)
		{
			Print.outln("You died, try again!");
			world = new GameEngine(levelList.firstElement());
		}
		
//		Block tmpBlock = world.map.getBlockAt((int)tmpX, (int) tmpY);
//		if ( tmpBlock!= null && tmpBlock.getType() == BlockType.BLOCK_GOAL)
//		{
//			Print.outln("level finished!");
//			if (levelList.size() > 1)
//			{
//				world = new Level(levelList.get(1));
//				levelList.removeElementAt(0);
//			} else
//			{
//				Print.outln("game finished!");
//				quitGame();
//			}
//		}
		
//		ingameUI.update(world, dt);
		
		drawIngame();
//		drawUI();
		
		if (rightMouseDelayTimer.getTimeDelta() > rightMouseDelay)
		{
			rightMouseDelayTimer.stop();
			rightMouseDelayTimer.reset();
		}
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
    	Vector2f tmp = mousePos.sub(new Vector2f(gameScreenPos.getX()*resX/2.f, gameScreenPos.getY()*resY/2.f));
//		mousePos.set(Mouse.getX()-gameScreenPos.getX()*resX/2.f, Mouse.getY()-gameScreenPos.getY()*resY/2.f);
    	return tmp;
    }
    
    public Vector2f screenToWorld(Vector2f mousePos)
    {
    	Vector2f tmp = new Vector2f(mousePos.getX()*2.f/resX, mousePos.getY()*2.f/resY);
    	tmp = tmp.add(new Vector2f(-1.f, -1.f));
    	return tmp;
    }
    
    public void checkMouseWheel()
    {
    	int wheel = Mouse.getDWheel();
    	world.mouseWheelReaction(wheel);
    }
    
    /**
     * checks mouse input
     */
	public void checkMouse()
	{
		checkMouseWheel();
		
		while (Mouse.next())
		{
			mousePos.set(Mouse.getX(), Mouse.getY());
//			mousePos = globalToGamescreen(new Vector2f(Mouse.getX(),Mouse.getY()));
			
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
				
		        if(Mouse.isButtonDown(2))
		        {
		        	world.zoom = 1.f;
		        }
			} else
			{
				world.mouseReleaseReaction();
			}
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
	
	public void checkMouseMainMenu()
	{		
		while (Mouse.next())
		{
//			mousePos = globalToGamescreen(new Vector2f(Mouse.getX(),Mouse.getY()));
			
			mousePos = new Vector2f(1.f*Mouse.getX()/resX,1.f*Mouse.getY()/resY);

			if (Mouse.getEventButtonState())
			{
//				Print.outln("down?");
				if (Mouse.isButtonDown(0))
				{
					// calculate mouse position (left top=(0,0), right bottom=(1,1))
//					mousePos.print();
					isLeftMouseDown = true;
					
					for (Iterator<Button> buttonObj = mainMenu.getButtons().iterator();buttonObj.hasNext();)
					{
						Button tmpButton = buttonObj.next();
						if (tmpButton.isInside(mousePos))
						{
							tmpButton.setDown(true);
						}
					}
				}
			} else
			{
				// check if mouse is released
				if (isLeftMouseDown && !Mouse.isButtonDown(0))
				{
//					Print.outln("up?");
					isLeftMouseReleased = true;
				}
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
//			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.isKeyDown(myKeyboard.KEY_ESCAPE))
				{
					gameStatus = GameStatus.MAIN_MENU;
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_F5))
				{
					System.out.println("edit mode on");
					devMode = true;
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_F1))
				{
					world.mainUI.setStatus();
					world.ingameStatus = IngameStatus.DEFAULT;
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_F2))
				{
					world.mainUI.setBuild();
					world.ingameStatus = IngameStatus.BUILDING;
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_TAB))
				{
					world.toggleMapActive();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_1))
				{
					world.mainPlayer.setAttackType(Player.ATTACK_SHOT);
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_2))
				{
					world.mainPlayer.setAttackType(Player.ATTACK_ELECTRIC);
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_3))
				{
					world.mainPlayer.setAttackType(Player.ATTACK_POISON);
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_4))
				{
					world.mainPlayer.setAttackType(Player.ATTACK_BEAM);
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
					if (Keyboard.isKeyDown(myKeyboard.KEY_LSHIFT))					{
						world.mainPlayer.setTeleportUp(true);
					} else
					{
						world.mainPlayer.startUp();
					}
				} else
				{
					world.mainPlayer.stopUp();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_S))
				{
					if (Keyboard.isKeyDown(myKeyboard.KEY_LSHIFT))
					{
						world.mainPlayer.setTeleportDown(true);
					} else
					{
						world.mainPlayer.startDown();
					}
				} else
				{
					world.mainPlayer.stopDown();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_A))
				{
					if (Keyboard.isKeyDown(myKeyboard.KEY_LSHIFT))					{
						world.mainPlayer.setTeleportLeft(true);
					} else
					{
						world.mainPlayer.startLeft();
					}
				} else
				{
					world.mainPlayer.stopLeft();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_D))
				{
					if (Keyboard.isKeyDown(myKeyboard.KEY_LSHIFT))					{
						world.mainPlayer.setTeleportRight(true);
					} else
					{
						world.mainPlayer.startRight();
					}
				} else
				{
					world.mainPlayer.stopRight();
				}
//				if (Keyboard.isKeyDown(myKeyboard.KEY_E))
//				{
////					world.testEnemy.setAngle(world.testEnemy.getAngle()+10);
////					world.mainPlayer.inreaseActiveProjectile();
//				}
			}// else
//			{
//				world.mainPlayer.stopUp();
//				world.mainPlayer.stopDown();
//				world.mainPlayer.stopLeft();
//				world.mainPlayer.stopRight();
//			}
		}
	}
	
	/**
	 * checks mosue input if in developer mode
	 */
	public void checkMouseDev()
	{		
	    checkMouseWheel();
		
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
					gameStatus = GameStatus.MAIN_MENU;
//					System.exit(0);
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_F5))
				{
					System.out.println("edit mode off");
					devMode = false;
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_C))
				{
					if (world.map.getEnemies().size() != 0)
					{
						world.map.getEnemies().removeElementAt(world.map.getEnemies().size()-1);
					}
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_NUMPAD2))
				{
					world.map.levelStructure.addRoomBottom();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_NUMPAD8))
				{
					world.map.levelStructure.addRoomTop();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_NUMPAD4))
				{
					world.map.levelStructure.addRoomLeft();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_NUMPAD6))
				{
					world.map.levelStructure.addRoomRight();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_1))
				{
					devUI.setBlocksUI();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_2))
				{
					devUI.setEnemiesUI();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_R))
				{
					mousePos.set(Mouse.getX(), Mouse.getY());
					world.rotateActiveEnemy(globalToGamescreen(mousePos));
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_LCONTROL) && Keyboard.isKeyDown(myKeyboard.KEY_S))
				{
					Scanner s = new Scanner(System.in);
					Print.outln("save room as: ");
					String fileName = s.nextLine();
					if (fileName.length() > 0)
					{
//						world.map.writeToFile(fileName, world.mainPlayer.getPos());
//						world.map.writeToTextFile(fileName, world.mainPlayer.getPos());
//						Print.outln("level saved as "+ fileName+"!");
//						world.map.writeActiveRoomToFile(fileName);
						world.map.writeActiveRoomToTextFile(fileName);
					} else 
					{
						Print.outln("level could not be saved!");
					}
				}
//				
//				if (Keyboard.isKeyDown(myKeyboard.KEY_LCONTROL) && Keyboard.isKeyDown(myKeyboard.KEY_N))
//				{
//					System.out.println("Generating new level");
//					
//					Scanner s = new Scanner(System.in);
//					System.out.println("size x:");
//					int sizeX = s.nextInt();
//					System.out.println("size y:");
//					int sizeY = s.nextInt();
//					
//					world = null;
//					world = new Level(sizeX, sizeY);
//				}
//				
				if (Keyboard.isKeyDown(myKeyboard.KEY_LCONTROL) && Keyboard.isKeyDown(myKeyboard.KEY_O))
				{
					System.out.println("file to load: ");
					Scanner s = new Scanner(System.in);
					String fileName = s.nextLine();
					
//					world.map.readTextFile(fileName);
//					world.mainPlayer.setPos(world.map.getStart());
//					world.map.readActiveRoomFromFile(fileName);
//					world.map.levelStructure.setActiveGrid(world.map.readRoomFromTextFile(fileName));
					world.map.loadActiveRoom(fileName);
				}
				
//				if (Keyboard.isKeyDown(myKeyboard.KEY_PERIOD) && devMode)
//				{
//					world.map.writeToFile(world.getName(), world.mainPlayer.getPos());
//					Print.outln("level saved as "+world.getName());
//				}
//				
//				if (Keyboard.isKeyDown(myKeyboard.KEY_COMMA) && devMode)
//				{
////					System.out.println("test: "+Keyboard.getEventKey());
//					world.map.readFile();
//				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_W))
				{
					world.setPosY(world.getPosY()-1.f);
				}
				if (Keyboard.isKeyDown(myKeyboard.KEY_S))
				{
					world.setPosY(world.getPosY()+1.f);
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
//					DevModeSettings.increaseActiveCollectable();
//					System.out.println("active collectable: "+DevModeSettings.getActiveCollectable());
					DevModeSettings.increaseActiveEnemy();
					System.out.println("active enemy: "+DevModeSettings.getActiveEnemy());
				}
			}
		}
	}
	
	/**
	 * draws the UI
	 */
//	public void drawUI()
//	{
//		GL11.glMatrixMode(GL11.GL_MODELVIEW);
//		GL11.glLoadIdentity();
////		GL11.glScaled(1., -1., 1.);
//		ingameUI.draw();
//		GL11.glPopMatrix();
//		
////		spritesUI.begin();
////		spritesUI.draw(UILeft);
////		spritesUI.end();
//	}
	
	/**
	 * draws the whole scene
	 */
	public void drawIngame()
	{
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glClearColor(0.f, 0.f, 0.f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
		
		GL11.glPushMatrix();
		GL11.glTranslatef(gameScreenPos.getX(), gameScreenPos.getY(), 0.f);
		world.draw(devMode);
		GL11.glPopMatrix();
		
	}
	
	public void drawIngameDev()
	{
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glClearColor(0.f, 0.f, 0.f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
		
		GL11.glPushMatrix();
		GL11.glTranslatef(gameScreenPos.getX(), gameScreenPos.getY(), 0.f);
		world.draw(devMode);
		
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		devUI.draw();
		GL11.glPopMatrix();

		GL11.glPopMatrix();
		
	}
	
	public static void main(String[] argv)
	{
		Darklord darklords = new Darklord();
		darklords.init();
		darklords.start();
	}
}