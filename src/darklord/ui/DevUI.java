package darklord.ui;

import org.lwjgl.opengl.GL11;

import darklord.game.Block;
import darklord.game.Buildable;
import darklord.game.CountdownTimer;
import darklord.game.GameEngine;
import darklord.game.LevelStructure;
import darklord.math.Vector2f;
import darklord.media.SpriteSheet;

public class DevUI
{
	private Vector2f position, size;
	private DevUIBlocks blocksUI;
	private UI activeUI;
	private SpriteSheet uiSpriteSheet;
	private CountdownTimer clickTimer;

	public DevUI(float theRatio)
	{
		position = new Vector2f(-1.f, -1.f);
		size = new Vector2f(2.f, 2.f);
		clickTimer = new CountdownTimer(0.2f);
		uiSpriteSheet = new SpriteSheet("darklord/img/ui.png");
		blocksUI = new DevUIBlocks(uiSpriteSheet, theRatio);
		activeUI = blocksUI;
	}
	
	public void draw()
	{
		GL11.glPushMatrix();
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), 0.);
		GL11.glScaled(getSize().getX(), getSize().getY(), 1.);
		activeUI.draw();
		
		GL11.glPopMatrix();
	}
	
	public boolean isBlockMode()
	{
		return (activeUI instanceof DevUIBlocks);
	}
	
	public void init(GameEngine world)
	{
		blocksUI.init(world);
	}
	
	public Vector2f globalToLocal(Vector2f globalPos)
	{
		Vector2f localPos = globalPos.sub(position);
		localPos.setX(localPos.getX()/getSize().getX());
		localPos.setY(localPos.getY()/getSize().getY());
		
		return localPos;
	}
	
	public Buildable getActiveBuildable()
	{
		return blocksUI.getActiveBuildable();
	}
	
	public void mousePositionReaction(Vector2f globalPos)
	{
//		if (mapUI.isActive()) mapUI.mousePositionReaction(globalToLocal(globalPos));
	}
	
	public void mouseDownReaction(Vector2f globalPos, int button, LevelStructure level)
	{
		if (clickTimer.isDone())
		{
			activeUI.mouseDownReaction(globalToLocal(globalPos), button);
//			mapUI.mouseDownReaction(globalToLocal(globalPos), button, level);
			clickTimer.reset();
		}
	}
	
//	public void switchActiveUI()
//	{
//		if (activeUI instanceof IngameUIStatus)
//		{
//			activeUI = buildUI;
//		} else
//		{
//			if (activeUI instanceof IngameUIBuild) activeUI = statusUI;
//		}
//	}
	
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
	
	public void update(GameEngine world, float dt)
	{
		activeUI.update(world);
		clickTimer.update(dt);
	}
}
