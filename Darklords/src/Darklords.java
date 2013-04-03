import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import java.awt.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;

public class Darklords {
	
	static int resX, resY;
	int maxFPS;
	static float ratio;
	Level world;
	boolean isLeftMouseDown;
	Vector2f posLeftMouseDown;
	Vector2f mousePos;
	Timer worldTimer;
	static DTextures textures;
	KeyboardSettings myKeyboard;
	
	public void init()
	{
		resX = 800;
		resY = 600;
		maxFPS = 30;
		isLeftMouseDown = false;
		posLeftMouseDown = new Vector2f(0.f, 0.f);
		mousePos = new Vector2f(0.f, 0.f);
		ratio = 1f*resX/resY;
		myKeyboard = new KeyboardSettings();
	}

	public void start()
	{	
		try {
			Display.setDisplayMode(new DisplayMode(resX, resY));
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
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
        
		world = new Level(20,20);
		
		worldTimer = new Timer();
		Timer fpsTimer = new Timer();
		worldTimer.start();
		fpsTimer.start();
		
		while (!Display.isCloseRequested()) {
			// this is the main loop repeated while the window is not closed
			
			//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
			//GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

			//world.attack.update();
			checkMouse();
			checkKeyboard();
			
//			Vector2f posOld = world.mainPlayer.getPos();
			
			if (isLeftMouseDown) world.mouseDownReaction(posLeftMouseDown);
			world.mousePositionReaction(mousePos);
			float playerPosOld_x = world.mainPlayer.getPosX();
			float playerPosOld_y = world.mainPlayer.getPosY();
			world.update();
			
			if (world.mainPlayer.moveRight())
			{
				boolean collide = false;
				collide = world.collideWithBlock((float)Math.ceil(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				collide = collide || world.collideWithBlock((float)Math.ceil(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()));
				if (collide) world.mainPlayer.setPosX(playerPosOld_x);
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
				if (collide) world.mainPlayer.setPosX(playerPosOld_x);
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
				if (collide) world.mainPlayer.setPosY(playerPosOld_y);	
			}
			
			if (world.mainPlayer.moveDown())
			{
				boolean collide = false;
				collide = world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				collide = collide || world.collideWithBlock((float)Math.floor(world.mainPlayer.getPosX()+1.f), (float)Math.floor(world.mainPlayer.getPosY()+1.f));
				if (collide) world.mainPlayer.setPosY(playerPosOld_y);
			}
			
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
		}

		Display.destroy();
		
	}
	
	public void checkMouse()
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
					posLeftMouseDown.set(pos[0], pos[1]);
					
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
			} else
			{
				isLeftMouseDown = false;
			}
		}
	}
	
	public void checkKeyboard()
	{
		Keyboard.enableRepeatEvents(true);
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				// Spieler bewegen
				if (Keyboard.isKeyDown(myKeyboard.KEY_ESCAPE))
				{
					System.exit(0);
				}
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
					world.mainPlayer.startDown();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_A))
				{
					world.mainPlayer.startLeft();
				}
				
				if (Keyboard.isKeyDown(myKeyboard.KEY_D))
				{
					world.mainPlayer.startRight();
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
	
	public void draw()
	{
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		world.draw();
	}
	
	public static void main(String[] argv)
	{
		Darklords darklords = new Darklords();
		darklords.init();
		darklords.start();
	}
}