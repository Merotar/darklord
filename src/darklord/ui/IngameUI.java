package darklord.ui;
import org.lwjgl.opengl.GL11;

import darklord.game.CountdownTimer;
import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.game.LevelStructure;
import darklord.media.SpriteSheet;
import darklord.math.Vector2f;

public class IngameUI
{
	private Vector2f position, size;
	private IngameUIStatus statusUI;
	private IngameUIBuild buildUI;
	private IngameUIMap mapUI;
	private UI activeUI;
	private SpriteSheet uiSpriteSheet;
	private CountdownTimer clickTimer;

	public IngameUI(float theRatio)
	{
		position = new Vector2f(-1.f, -1.f);
		size = new Vector2f(2.f, 2.f);
		clickTimer = new CountdownTimer(0.2f);
		uiSpriteSheet = Darklord.ui;//new SpriteSheet("darklord/img/ui.png");
		statusUI = new IngameUIStatus(uiSpriteSheet, theRatio);
		buildUI = new IngameUIBuild(uiSpriteSheet, theRatio);
		mapUI = new IngameUIMap(uiSpriteSheet, theRatio);
		activeUI = statusUI;
	}
	
	public void setStatus()
	{
		activeUI = statusUI;
	}
	
	public void setBuild()
	{
		activeUI = buildUI;
	}
	
	public void init(GameEngine world)
	{
		statusUI.init(world);
		buildUI.init(world);
	}
	
	public Vector2f globalToLocal(Vector2f globalPos)
	{
		Vector2f localPos = globalPos.sub(position);
		localPos.setX(localPos.getX()/getSize().getX());
		localPos.setY(localPos.getY()/getSize().getY());
		
		return localPos;
	}
	
	public String getActiveBuildable()
	{
		return buildUI.getActiveBuildable();
	}
	
	public void mousePositionReaction(Vector2f globalPos)
	{
		if (mapUI.isActive()) mapUI.mousePositionReaction(globalToLocal(globalPos));
	}
	
	public void mouseDownReaction(Vector2f globalPos, int button, LevelStructure level)
	{
		if (clickTimer.isDone())
		{
			activeUI.mouseDownReaction(globalToLocal(globalPos), button);
			mapUI.mouseDownReaction(globalToLocal(globalPos), button, level);
			clickTimer.reset();
		}
	}
	
	public void update(GameEngine world, float dt)
	{
		activeUI.update(world);
		clickTimer.update(dt);
		if (mapUI.isActive()) mapUI.update(world, dt);
	}
	
	public void setMapActive(boolean theActive, LevelStructure level)
	{
		mapUI.setActive(theActive);
		if (theActive) mapUI.generateMap(level);
	}
	
	public boolean isMapActive()
	{
		return mapUI.isActive();
	}
	
	public void draw()
	{
		GL11.glPushMatrix();
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), 0.);
		GL11.glScaled(getSize().getX(), getSize().getY(), 1.);
		activeUI.draw();
		
		if (mapUI.isActive()) mapUI.draw();
		GL11.glPopMatrix();
	}
	
	public void switchActiveUI()
	{
		if (activeUI instanceof IngameUIStatus)
		{
			activeUI = buildUI;
		} else
		{
			if (activeUI instanceof IngameUIBuild) activeUI = statusUI;
		}
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getSize() {
		return size;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}

	public void zoomMapOut()
	{
		mapUI.zoomOut();
	}
	
	public void zoomMapIn()
	{
		mapUI.zoomIn();
	}
}
