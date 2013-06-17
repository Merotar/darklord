package darklord.ui;

import org.lwjgl.opengl.GL11;

import darklord.game.GameEngine;
import darklord.game.LevelStructure;
import darklord.game.Print;
import darklord.game.SawToothFunction;
import darklord.game.TimeStore;
import darklord.math.Vector2f;
import darklord.media.SpriteSheet;
import darklord.media.TextureRegion;

public class IngameUIMap extends UI
{
	private boolean active;
	private MapButton[][] map;
	private Vector2f center;
	private Vector2f mousePos;
	private SawToothFunction activePotentialFactor;
	private float zoom;
	private final float zoomMin = 0.2f;
	private final float zoomMax = 1.2f;
	
	public IngameUIMap(SpriteSheet s, float theRatio)
	{
		super(s, theRatio);
		
        setPosition(new Vector2f(.5f, .5f));
        setSize(new Vector2f(1.f, 1.f));
		active = false;
		setSize(new Vector2f(.08f, .08f*theRatio));
		center = new Vector2f();
		mousePos = new Vector2f();
		activePotentialFactor = new SawToothFunction(1.f);
		zoom = 1.f;
	}

	public void zoomIn()
	{
		zoom *= 1.2;
		if (zoom > zoomMax) zoom = zoomMax;
	}
	
	public void zoomOut()
	{
		zoom /= 1.2;
		if (zoom < zoomMin) zoom = zoomMin;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void generateMap(LevelStructure level)
	{
		int minX = level.getGridMapSize();
		int maxX = 0;
		int minY = level.getGridMapSize();
		int maxY = 0;
		
		if (map == null || map.length != level.getGridMapSize()) map = new MapButton[level.getGridMapSize()][level.getGridMapSize()];
		
//		center.setX((level.getActiveGrid().getPosX()+level.getGridCenter().getPosX())/2.f);
//		center.setY((level.getActiveGrid().getPosY()+level.getGridCenter().getPosY())/2.f);
		
		center.setX(-level.getActiveGrid().getPosX());
		center.setY(-level.getActiveGrid().getPosY());
		
		for (int i=0;i<level.getGridMapSize();i++)
		{
			for (int j=0;j<level.getGridMapSize();j++)
			{
				if (map[i][j] != null && map[i][j].getName().equals("potential map tile")) map[i][j] = null;
			}
		}
		
		for (int i=0;i<level.getGridMapSize();i++)
		{
			for (int j=0;j<level.getGridMapSize();j++)
			{
				if (level.getGridMap()[i][j] != null)
				{
					if (map[i][j] == null) map[i][j] = new MapButton(new TextureRegion(2*128, 6*128, 128, 128), "map tile");
					Vector2f tmpPos = center.add(new Vector2f(-level.getGridMapSize()/2.f+i, -level.getGridMapSize()/2.f+j));
					map[i][j].setType(MapButton.TYPE_DEFAULT);
					map[i][j].setPosition(tmpPos);
					map[i][j].setSize(new Vector2f(1.f, 1.f));
					
					if (level.getGridMap()[i][j] == level.getGridCenter()) map[i][j].setType(MapButton.TYPE_CENTER);
					
					if (level.getGridMap()[i][j] == level.getActiveGrid())
					{
						map[i][j].setType(MapButton.TYPE_ACTIVE);
						// set potential map tile right
						if (i+1 < level.getGridMapSize() && level.getGridMap()[i+1][j] == null)
						{
							tmpPos = center.add(new Vector2f(-level.getGridMapSize()/2.f+i+1, -level.getGridMapSize()/2.f+j));
							map[i+1][j] = new MapButton(new TextureRegion(2*128, 6*128, 128, 128), "potential map tile");
							map[i+1][j].setPosition(tmpPos);
							map[i+1][j].setSize(new Vector2f(1.f, 1.f));
							map[i+1][j].setType(MapButton.TYPE_POTENTIAL);
							if (map[i+1][j].isInside(mousePos)) map[i+1][j].setType(MapButton.TYPE_ACTIVE_POTENTIAL);
						}
						// set potential map tile left
						if (i-1 >= 0 && level.getGridMap()[i-1][j] == null)
						{
							tmpPos = center.add(new Vector2f(-level.getGridMapSize()/2.f+i-1, -level.getGridMapSize()/2.f+j));
							map[i-1][j] = new MapButton(new TextureRegion(2*128, 6*128, 128, 128), "potential map tile");
							map[i-1][j].setPosition(tmpPos);
							map[i-1][j].setSize(new Vector2f(1.f, 1.f));
							map[i-1][j].setType(MapButton.TYPE_POTENTIAL);
							if (map[i-1][j].isInside(mousePos)) map[i-1][j].setType(MapButton.TYPE_ACTIVE_POTENTIAL);
						}
						// set potential map tile top
						if (j+1 < level.getGridMapSize() && level.getGridMap()[i][j+1] == null)
						{
							tmpPos = center.add(new Vector2f(-level.getGridMapSize()/2.f+i, -level.getGridMapSize()/2.f+j+1));
							map[i][j+1] = new MapButton(new TextureRegion(2*128, 6*128, 128, 128), "potential map tile");
							map[i][j+1].setPosition(tmpPos);
							map[i][j+1].setSize(new Vector2f(1.f, 1.f));
							map[i][j+1].setType(MapButton.TYPE_POTENTIAL);
							if (map[i][j+1].isInside(mousePos)) map[i][j+1].setType(MapButton.TYPE_ACTIVE_POTENTIAL);
						}
						// set potential map tile bottom
						if (j-1 >= 0 && level.getGridMap()[i][j-1] == null)
						{
							tmpPos = center.add(new Vector2f(-level.getGridMapSize()/2.f+i, -level.getGridMapSize()/2.f+j-1));
							map[i][j-1] = new MapButton(new TextureRegion(2*128, 6*128, 128, 128), "potential map tile");
							map[i][j-1].setPosition(tmpPos);
							map[i][j-1].setSize(new Vector2f(1.f, 1.f));
							map[i][j-1].setType(MapButton.TYPE_POTENTIAL);
							if (map[i][j-1].isInside(mousePos)) map[i][j-1].setType(MapButton.TYPE_ACTIVE_POTENTIAL);
						}
					}
				}
			}
		}
	}
	
	public void draw()
	{
		super.draw();
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(0.f, 0.f, 0.f, 0.5f);
		GL11.glVertex2d(0.f, 0.f);
		GL11.glVertex2d(0.f, 1.f);
		GL11.glVertex2d(1.f, 1.f);
		GL11.glVertex2d(1.f, 0.f);
		GL11.glEnd();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(getPosition().getX(), getPosition().getY(), 0.f);
		GL11.glScalef(getSize().getX()*zoom, getSize().getY()*zoom, 1.f);
		spriteSheet.begin();
		float r, g, b;
		for (int i=0;i<map.length;i++)
		{
			for (int j=0;j<map[0].length;j++)
			{
				if (map[i][j] != null)
				{
					
					switch (map[i][j].getType())
					{
					case MapButton.TYPE_DEFAULT:
						r = g = b = 0.7f;
						break;
					case MapButton.TYPE_ACTIVE:
						r = g = b = 1.f;
						break;
					case MapButton.TYPE_POTENTIAL:
						r = g = b = .3f;
						break;
					case MapButton.TYPE_ACTIVE_POTENTIAL:
						r = g = b = activePotentialFactor.getCurrent();
						break;
					case MapButton.TYPE_CENTER:
						r = 1.f;
						g = b = 0.6f;
						break;
					default:
						r = g = b = 0.f;
					}

					spriteSheet.draw(map[i][j].getTextureRegion(), 
							map[i][j].getPosition().getX(), 
							map[i][j].getPosition().getY(), 
							map[i][j].getSize().getX(), 
							map[i][j].getSize().getY(),
							r, g, b, 0.8f);

				}
			}
		}
		
//		spriteSheet.draw(map[10][10].getTextureRegion(), 0, 0, 1, 1, 0.f, 0.f, 1.f, 0.8f);
		spriteSheet.end();
		GL11.glPopMatrix();
	}
	
	public Vector2f globalToLocal(Vector2f globalPos)
	{
		Vector2f localPos = globalPos.sub(position);
		localPos.setX(localPos.getX()/(getSize().getX()*zoom));
		localPos.setY(localPos.getY()/(getSize().getY()*zoom));
		
		return localPos;
	}
	
	public void mousePositionReaction(Vector2f globalPos)
	{
		mousePos = globalToLocal(globalPos);
//		localPos.print();
//		for (int i=0;i<map.length;i++)
//		{
//			for (int j=0;j<map[0].length;j++)
//			{
//				if (map[i][j] != null)
//				{
//					if (map[i][j].isInside(localPos))
//					{
//						map[i][j].setType(MapButton.TYPE_ACTIVE_POTENTIAL);
//					}
//				}
//			}
//		}
	}
	
	public void mouseDownReaction(Vector2f globalPos, int button, LevelStructure level)
	{
		Vector2f localPos = globalToLocal(globalPos);
		if (map == null) return;
		for (int i=0;i<map.length;i++)
		{
			for (int j=0;j<map[0].length;j++)
			{
				if (map[i][j] != null)
				{
					if (map[i][j].isInside(localPos))
					{
						if (button == 0 && map[i][j].getType() == MapButton.TYPE_ACTIVE_POTENTIAL)
						{
							if (i-1 >= 0 &&	level.getGridMap()[i-1][j] == level.getActiveGrid())
							{
								level.addGridRight();
							}
							if (i+1 < level.getGridMapSize() && level.getGridMap()[i+1][j] == level.getActiveGrid())
							{
								level.addGridLeft();
							}
							if (j-1 >= 0 &&	level.getGridMap()[i][j-1] == level.getActiveGrid())
							{
								level.addGridTop();
							}
							if (j+1 < level.getGridMapSize() && level.getGridMap()[i][j+1] == level.getActiveGrid())
							{
								level.addGridBottom();
							}
						}
					}
				}
			}
		}	
	}
	
	public void update(GameEngine level, float dt)
	{
		generateMap(level.map.levelStructure);
		activePotentialFactor.add(dt);
	}
}
