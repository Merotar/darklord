package darklord.ui;

import org.lwjgl.opengl.GL11;

import darklord.blocks.Block;
import darklord.collectables.Collectable;
import darklord.enemies.Enemy;
import darklord.game.Buildable;
import darklord.game.CountdownTimer;
import darklord.game.Darklord;
import darklord.game.GameEngine;
import darklord.game.LevelStructure;
import darklord.game.Print;
import darklord.math.Vector2f;
import darklord.media.SpriteSheet;
import darklord.media.StaticText;

public class DevUI
{
	private Vector2f position, size;
	private DevUIBlocks blocksUI;
	private DevUIEnemies enemiesUI;
	private DevUICollectables collectableUI;
	private UI activeUI;
	private SpriteSheet uiSpriteSheet;
	private CountdownTimer clickTimer;
	private StaticText<Float> cursorPositionText;

	public DevUI(float theRatio)
	{
		position = new Vector2f(-1.f, -1.f);
		size = new Vector2f(2.f, 2.f);
		clickTimer = new CountdownTimer(0.2f);
		uiSpriteSheet = Darklord.ui;//new SpriteSheet("darklord/img/ui.png");
		blocksUI = new DevUIBlocks(uiSpriteSheet, theRatio);
		enemiesUI = new DevUIEnemies(uiSpriteSheet, theRatio);
		collectableUI = new DevUICollectables(uiSpriteSheet, theRatio);
		activeUI = blocksUI;
		cursorPositionText = new StaticText<Float>("00", 0.f);
	}
	
	public void draw()
	{
		GL11.glPushMatrix();
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), 0.);
		GL11.glScaled(getSize().getX(), getSize().getY(), 1.);
		activeUI.draw();
		
		cursorPositionText.draw(Darklord.textDrawer);
		GL11.glPopMatrix();
	}
	
	public boolean isInside(Vector2f thePosition)
	{
		boolean inside = true;
//		thePosition = globalToLocal(thePosition);
		
		if (thePosition.getX()>getPosition().getX()+getSize().getX()) inside = false;
		if (thePosition.getX()<getPosition().getX()) inside = false;
		if (thePosition.getY()>getPosition().getY()+getSize().getY()) inside = false;
		if (thePosition.getY()<getPosition().getY()) inside = false;
//		if (inside) Print.outln("inside of button "+getName());
		
		return inside;
	}
	
	public boolean isBlockMode()
	{
		return (activeUI instanceof DevUIBlocks);
	}
	
	public boolean isEnemyMode()
	{
		return (activeUI instanceof DevUIEnemies);
	}
	
	public boolean isCollectableMode()
	{
		return (activeUI instanceof DevUICollectables);
	}
	
	public void init(GameEngine engine)
	{
		cursorPositionText.setPosition(new Vector2f(0.1f, 0.05f));
		cursorPositionText.setSize(new Vector2f(0.05f, 0.05f));
		
		blocksUI.init(engine);
		enemiesUI.init(engine);
		collectableUI.init(engine);
	}
	
	public void setBlocksUI()
	{
		activeUI = blocksUI;
	}
	
	public void setEnemiesUI()
	{
		activeUI = enemiesUI;
	}
	
	public void setCollectablesUI()
	{
		activeUI = collectableUI;
	}
	
	public Vector2f globalToLocal(Vector2f globalPos)
	{
		Vector2f localPos = globalPos.sub(position);
		localPos.setX(localPos.getX()/getSize().getX());
		localPos.setY(localPos.getY()/getSize().getY());
		
		return localPos;
	}
	
	public Block getActiveBlock()
	{
		if (activeUI == blocksUI)
		{
			return blocksUI.getActiveBlock();
		}
		return null;
	}
	
	public Enemy getActiveEnemy()
	{
		if (activeUI == enemiesUI)
		{
			return enemiesUI.getActiveEnemy();
		}
		return null;
	}
	
	public Collectable getActiveCollectable()
	{
		if (activeUI == collectableUI)
		{
			return collectableUI.getActiveCollectable();
		}
		return null;
	}
	
	public void mousePositionReaction(Vector2f pos)
	{
//		cursorPositionText.setText(pos.getX()+"-"+pos.getY());
//		if (mapUI.isActive()) mapUI.mousePositionReaction(globalToLocal(globalPos));
	}
	
	public void setCursorInt(int x, int y)
	{
		cursorPositionText.setText(x+","+y);
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
